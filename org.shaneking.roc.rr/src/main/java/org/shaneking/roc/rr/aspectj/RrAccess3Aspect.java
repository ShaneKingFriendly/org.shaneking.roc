package org.shaneking.roc.rr.aspectj;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.ling.rr.Resp;
import org.shaneking.ling.zero.annotation.ZeroAnnotation;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.roc.persistence.dao.CacheableDao;
import org.shaneking.roc.persistence.entity.sql.ApiAccess3Entities;
import org.shaneking.roc.persistence.entity.sql.ApiAccessEntities;
import org.shaneking.roc.rr.Pub;
import org.shaneking.roc.rr.Req;
import org.shaneking.roc.rr.annotation.RrAccess3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Aspect
@Component
@ConditionalOnProperty(prefix = "sk.roc.rr.access3", value = "enabled")
@Slf4j
@Order(500)
public class RrAccess3Aspect {
  @Value("${sk.roc.rr.access3.enabled:false}")
  private boolean enabled;

  @Autowired
  private CacheableDao cacheableDao;

  @Autowired
  private ApiAccess3Entities apiAccess3EntityClass;

  @Pointcut("execution(@org.shaneking.roc.rr.annotation.RrAccess3 * *..*.*(..))")
  private void pointcut() {
  }

  @Around("pointcut() && @annotation(rrAccess3)")
  public Object around(ProceedingJoinPoint pjp, RrAccess3 rrAccess3) throws Throwable {
    Object rtn = null;
    boolean ifExceptionThenInProceed = false;
    if (enabled) {
      if (pjp.getArgs().length > rrAccess3.reqParamIdx() && pjp.getArgs()[rrAccess3.reqParamIdx()] instanceof Req) {
        Req<?, ?> req = (Req<?, ?>) pjp.getArgs()[rrAccess3.reqParamIdx()];
        if (req.getPub() == null || String0.isNullOrEmpty(req.getPub().getChannelName())) {
          rtn = Resp.failed(Pub.ERR_CODE__REQUIRED_CHANNEL_NAME, OM3.writeValueAsString(req.getPub()), req);
        } else {
          try {
            ApiAccess3Entities apiAccess3EntitySelect = apiAccess3EntityClass.entityClass().newInstance();
            apiAccess3EntitySelect.setChannelId(req.gnnCtx().gnaChannelId());
            apiAccess3EntitySelect.setTenantId(req.gnnCtx().gnaTenantId());
            apiAccess3EntitySelect.setAllowSignature(pjp.getSignature().toLongString());
            ApiAccess3Entities apiAccess3Entity = cacheableDao.one(apiAccess3EntityClass.entityClass(), apiAccess3EntitySelect, true);
            if (apiAccess3Entity == null) {
              rtn = Resp.failed(ApiAccessEntities.ERR_CODE__PERMISSION_DENIED, OM3.writeValueAsString(apiAccess3EntitySelect), req);
            } else {
              ifExceptionThenInProceed = true;
              rtn = pjp.proceed();
            }
          } catch (Throwable throwable) {
            log.error(OM3.writeValueAsString(req), throwable);
            if (ifExceptionThenInProceed) {
              throw throwable;//process error
            } else {
              rtn = Resp.failed(Resp.CODE_UNKNOWN_EXCEPTION, String.valueOf(throwable), req);
              //rtn can reused
            }
          }
        }
      } else {
        log.error(MessageFormat.format("{0} - {1} : {2}", ZeroAnnotation.ERR_CODE__ANNOTATION_SETTING_ERROR, pjp.getSignature().toLongString(), OM3.writeValueAsString(rrAccess3)));
        rtn = pjp.proceed();
      }
    } else {
      rtn = pjp.proceed();
    }
    return rtn;
  }
}
