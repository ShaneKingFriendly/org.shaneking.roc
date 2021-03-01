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
import org.shaneking.roc.persistence.entity.TenantChannelizedEntities;
import org.shaneking.roc.persistence.entity.sql.ApiAccess2Entities;
import org.shaneking.roc.persistence.entity.sql.ApiAccessEntities;
import org.shaneking.roc.rr.Req;
import org.shaneking.roc.rr.annotation.RrAccess2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Aspect
@Component
@ConditionalOnProperty(prefix = "sk.roc.rr.access2", value = "enabled")
@Slf4j
@Order(500)
public class RrAccess2Aspect {
  @Value("${sk.roc.rr.access2.enabled:false}")
  private boolean enabled;

  @Autowired
  private CacheableDao cacheableDao;

  @Autowired
  private ApiAccess2Entities apiAccess2EntityClass;

  @Pointcut("execution(@org.shaneking.roc.rr.annotation.RrAccess2 * *..*.*(..))")
  private void pointcut() {
  }

  @Around("pointcut() && @annotation(rrAccess2)")
  public Object around(ProceedingJoinPoint pjp, RrAccess2 rrAccess2) throws Throwable {
    Object rtn = null;
    boolean ifExceptionThenInProceed = false;
    if (enabled) {
      if (pjp.getArgs().length > rrAccess2.reqParamIdx() && pjp.getArgs()[rrAccess2.reqParamIdx()] instanceof Req) {
        Req<?, ?> req = (Req<?, ?>) pjp.getArgs()[rrAccess2.reqParamIdx()];
        if (String0.isNullOrEmpty(req.gnnCtx().gnaChannelId()) || String0.isNullOrEmpty(req.gnnCtx().gnaTenantId())) {
          rtn = Resp.failed(TenantChannelizedEntities.ERR_CODE__REQUIRED_CHANNEL_ID_AND_TENANT_ID, OM3.writeValueAsString(req.getPub()), req);
        } else {
          try {
            if (req.gnnCtx().getAuditLog() == null || String0.isNullOrEmpty(req.getCtx().getAuditLog().getReqUrl())) {
              ifExceptionThenInProceed = true;
              rtn = pjp.proceed();
            } else {
              ApiAccess2Entities apiAccess2EntitySelect = apiAccess2EntityClass.entityClass().newInstance();
              apiAccess2EntitySelect.setChannelId(req.gnnCtx().gnaChannelId());
              apiAccess2EntitySelect.setTenantId(req.gnnCtx().gnaTenantId());
              apiAccess2EntitySelect.setAllowUrl(req.getCtx().getAuditLog().getReqUrl());
              ApiAccess2Entities apiAccess2Entity = cacheableDao.one(apiAccess2EntityClass.entityClass(), apiAccess2EntitySelect, true);
              if (apiAccess2Entity == null) {
                rtn = Resp.failed(ApiAccessEntities.ERR_CODE__PERMISSION_DENIED, OM3.writeValueAsString(apiAccess2EntitySelect), req);
              } else {
                ifExceptionThenInProceed = true;
                rtn = pjp.proceed();
              }
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
        log.error(MessageFormat.format("{0} - {1} : {2}", ZeroAnnotation.ERR_CODE__ANNOTATION_SETTING_ERROR, pjp.getSignature().toLongString(), OM3.writeValueAsString(rrAccess2)));
        rtn = pjp.proceed();
      }
    } else {
      rtn = pjp.proceed();
    }
    return rtn;
  }
}
