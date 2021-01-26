package org.shaneking.roc.rr.aspectj;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.ling.rr.Resp;
import org.shaneking.ling.zero.annotation.ZeroAnnotation;
import org.shaneking.ling.zero.crypto.Crypto0;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.roc.persistence.dao.CacheableDao;
import org.shaneking.roc.persistence.entity.ApiAccessEntity;
import org.shaneking.roc.persistence.entity.ChannelEntity;
import org.shaneking.roc.persistence.entity.TenantEntity;
import org.shaneking.roc.persistence.entity.UserEntity;
import org.shaneking.roc.rr.Req;
import org.shaneking.roc.rr.annotation.RrCrypto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Aspect
@Component
@Slf4j
@Order(600)
public class RrCryptoAspect {
  @Value("${sk.roc.rr.crypto.enabled:true}")
  private boolean enabled;

  @Autowired
  private CacheableDao cacheableDao;

  @Autowired
  private ApiAccessEntity apiAccessEntityClass;

  @Autowired
  private ChannelEntity channelEntityClass;

  @Autowired
  private UserEntity userEntityClass;

  @Autowired
  private TenantEntity tenantEntityClass;

  @Pointcut("execution(@org.shaneking.roc.rr.annotation.RrCrypto * *..*.*(..))")
  private void pointcut() {
  }

  @Around("pointcut() && @annotation(rrCrypto)")
  public Object around(ProceedingJoinPoint pjp, RrCrypto rrCrypto) throws Throwable {
    Object rtn = null;
    boolean proceed = false;
    if (enabled) {
      if (pjp.getArgs().length > rrCrypto.reqParamIdx() && pjp.getArgs()[rrCrypto.reqParamIdx()] instanceof Req) {
        Req<?, ?> req = (Req<?, ?>) pjp.getArgs()[rrCrypto.reqParamIdx()];
        log.info(OM3.writeValueAsString(req));
        ChannelEntity channelEntity = req.getCtx().getChannel();
        if (channelEntity == null) {
          channelEntity = channelEntityClass.entityClass().newInstance();
          channelEntity.setTokenForce(String0.N).setTokenAlgorithmType(Crypto0.ALGORITHM_NAME__AES).setTokenValueType(ChannelEntity.TOKEN_VALUE_TYPE__SELF);
        }
        if (String0.Y.equalsIgnoreCase(channelEntity.getTokenForce()) && (!String0.Y.equalsIgnoreCase(req.getPub().getEncoded()) || String0.isNullOrEmpty(req.getEnc()))) {
          rtn = Resp.failed(ChannelEntity.ERR_CODE__NEED_ENCODING, req.getPub().getEncoded(), req);
        } else {
          //TODO
        }
      } else {
        log.error(MessageFormat.format("{0} - {1} : {2}", ZeroAnnotation.ERR_CODE__ANNOTATION_SETTING_ERROR, pjp.getSignature().getName(), OM3.writeValueAsString(rrCrypto)));
        rtn = pjp.proceed();
      }
    } else {
      rtn = pjp.proceed();
    }
    return rtn;
  }
}
