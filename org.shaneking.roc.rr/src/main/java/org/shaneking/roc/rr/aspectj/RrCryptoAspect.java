package org.shaneking.roc.rr.aspectj;

import com.fasterxml.jackson.databind.JavaType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.ling.persistence.AbstractEntity;
import org.shaneking.ling.persistence.Tenanted;
import org.shaneking.ling.rr.Resp;
import org.shaneking.ling.zero.annotation.ZeroAnnotation;
import org.shaneking.ling.zero.crypto.Crypto0;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.roc.jackson.JavaType3;
import org.shaneking.roc.persistence.dao.TenantedCacheableDao;
import org.shaneking.roc.persistence.entity.AuditLogEntity;
import org.shaneking.roc.persistence.entity.ChannelEntity;
import org.shaneking.roc.persistence.entity.TenantEntity;
import org.shaneking.roc.persistence.entity.UserEntity;
import org.shaneking.roc.rr.Pri;
import org.shaneking.roc.rr.Req;
import org.shaneking.roc.rr.annotation.RrCrypto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Aspect
@Component
@ConditionalOnProperty(prefix = "sk.roc.rr.crypto", value = "enabled")
@Slf4j
@Order(700)
public class RrCryptoAspect {
  @Value("${sk.roc.rr.crypto.enabled:false}")
  private boolean enabled;

  @Autowired
  private Environment environment;

  @Autowired
  private TenantedCacheableDao tenantedCacheableDao;

  @Autowired
  private ChannelEntity channelEntityClass;

  @Autowired
  private UserEntity userEntityClass;

  @Pointcut("execution(@org.shaneking.roc.rr.annotation.RrCrypto * *..*.*(..))")
  private void pointcut() {
  }

  @Around("pointcut() && @annotation(rrCrypto)")
  public Object around(ProceedingJoinPoint pjp, RrCrypto rrCrypto) throws Throwable {
    Object rtn = null;
    boolean proceedBefore = false;
    boolean proceedAfter = false;
    if (enabled) {
      if (pjp.getArgs().length > rrCrypto.reqParamIdx() && pjp.getArgs()[rrCrypto.reqParamIdx()] instanceof Req) {
        Req<?, ?> req = (Req<?, ?>) pjp.getArgs()[rrCrypto.reqParamIdx()];
        try {
          TenantEntity tenantEntity = req.getCtx().getTenant();
          if (tenantEntity == null) {
            rtn = Resp.failed(Tenanted.ERR_CODE__NOT_FOUND, OM3.writeValueAsString(req.getCtx()), req);
          } else {
            ChannelEntity channelEntity = req.getCtx().getChannel();
            if (channelEntity == null) {
              channelEntity = channelEntityClass.entityClass().newInstance();
              channelEntity.setTokenForce(String0.N).setTokenAlgorithmType(Crypto0.ALGORITHM_NAME__AES).setTokenValueType(ChannelEntity.TOKEN_VALUE_TYPE__SELF);
            }
            if (String0.Y.equalsIgnoreCase(channelEntity.getTokenForce()) && (!String0.Y.equalsIgnoreCase(req.getPub().getEncoded()) || String0.isNullOrEmpty(req.getEnc()))) {
              rtn = Resp.failed(ChannelEntity.ERR_CODE__NEED_ENCODING, req.getPub().getEncoded(), req);
            } else {
              String token = channelEntity.getTokenValue();
              if (!String0.isNullOrEmpty(token) && ChannelEntity.TOKEN_VALUE_TYPE__PROP.equalsIgnoreCase(channelEntity.getTokenValueType())) {
                token = environment.getProperty(token, token);
              }

              if (String0.Y.equalsIgnoreCase(req.getPub().getEncoded()) && !String0.isNullOrEmpty(req.getEnc())) {
                String enc = req.getEnc();
                JavaType[] javaTypes = JavaType3.resolveArgJavaTypes(pjp, rrCrypto.reqParamIdx());
                if (Crypto0.ALGORITHM_NAME__AES.equalsIgnoreCase(channelEntity.getTokenAlgorithmType())) {
                  enc = Crypto0.aesDecrypt(enc, token);
                }
                req.setPri(OM3.readValue(enc, OM3.om().getTypeFactory().constructParametricType(Pri.class, javaTypes))).setEnc(null);
              }


              UserEntity userEntity = tenantedCacheableDao.oneById(userEntityClass.entityClass(), userEntityClass.entityClass().newInstance(), req.getPri().getExt().getUserId(), true, tenantEntity.getId());
              if (userEntity == null) {
                rtn = Resp.failed(AbstractEntity.ERR_CODE__NOT_FOUND, req.getPri().getExt().getUserId(), req);
              } else {
                req.getCtx().setUser(userEntity);
                if (req.getCtx().getAuditLog() != null) {
                  req.getCtx().getAuditLog().setReqUserId(userEntity.getId());
                }

                AuditLogEntity auditLogEntity = req.getCtx().getAuditLog();
                if (auditLogEntity != null) {
                  auditLogEntity.setReqJsonStr(OM3.writeValueAsString(req));
                }
                proceedBefore = true;
                rtn = pjp.proceed();
                proceedAfter = true;
                if (auditLogEntity != null) {
                  auditLogEntity.setRespJsonStr(OM3.writeValueAsString(rtn));
                }

                if (rtn instanceof Resp) {
                  Object respData = ((Resp<?>) rtn).getData();
                  if (respData instanceof Req) {
                    Req<?, ?> respReq = (Req<?, ?>) respData;

                    if (String0.Y.equalsIgnoreCase(respReq.getPub().getEncoded()) && respReq.getPri() != null) {
                      String enc = OM3.writeValueAsString(respReq.getPri());
                      if (Crypto0.ALGORITHM_NAME__AES.equalsIgnoreCase(channelEntity.getTokenAlgorithmType())) {
                        enc = Crypto0.aesEncrypt(enc, token);
                      }
                      respReq.setEnc(enc).setPri(null);
                    }
                  }
                }
              }
            }
          }
        } catch (Throwable throwable) {
          log.error(OM3.writeValueAsString(req), throwable);
          if (proceedBefore && !proceedAfter) {
            throw throwable;//process error
          } else {
            //data maybe corrupted
            rtn = Resp.failed(Resp.CODE_UNKNOWN_EXCEPTION, String.valueOf(throwable), req);
          }
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
