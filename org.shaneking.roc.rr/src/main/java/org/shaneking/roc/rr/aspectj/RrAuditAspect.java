package org.shaneking.roc.rr.aspectj;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.ling.zero.annotation.ZeroAnnotation;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.ling.zero.util.Date0;
import org.shaneking.ling.zero.util.UUID0;
import org.shaneking.roc.persistence.dao.CacheableDao;
import org.shaneking.roc.persistence.entity.AuditLogEntity;
import org.shaneking.roc.rr.Req;
import org.shaneking.roc.rr.annotation.RrAudit;
import org.shaneking.roc.rr.ctx.RrCtx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Aspect
@Component
@Slf4j
@Order(500)///small will first
public class RrAuditAspect {
  @Value("${sk.roc.rr.audit.enabled:true}")
  private boolean enabled;

  @Autowired
  private CacheableDao cacheableDao;

  @Autowired
  private AuditLogEntity auditLogEntityClass;

  @Pointcut("execution(@org.shaneking.roc.rr.annotation.RrAudit * *..*.*(..))")
  private void pointcut() {
  }

  @Around("pointcut() && @annotation(rrAudit)")
  public Object around(ProceedingJoinPoint pjp, RrAudit rrAudit) throws Throwable {
    Object rtn = null;
    boolean proceed = false;
    if (enabled) {
      if (pjp.getArgs().length > rrAudit.reqParamIdx() && pjp.getArgs()[rrAudit.reqParamIdx()] instanceof Req) {
        Req<?, ?> req = (Req<?, ?>) pjp.getArgs()[rrAudit.reqParamIdx()];
        req.getPub().setTracingId(String0.nullOrEmptyTo(req.getPub().getTracingId(), UUID0.cUl33()));

        try {
          AuditLogEntity auditLogEntity = auditLogEntityClass.entityClass().newInstance();
          auditLogEntity.setLastModifyDateTime(Date0.on().dateTime()).setId(UUID0.cUl33());
          auditLogEntity.setTracingId(req.getPub().getTracingId()).setReqDatetime(Date0.on().datetimes());
          //TODO
          RrCtx.auditLog.set(auditLogEntity);

          proceed = true;
          rtn = pjp.proceed();
        } catch (Throwable throwable) {
          log.error(OM3.writeValueAsString(req), throwable);
          if (proceed) {
            throw throwable;
          } else {
            rtn = pjp.proceed();
          }
        } finally {
          try {
            AuditLogEntity auditLogEntity = RrCtx.auditLog.get();
            if (auditLogEntity != null) {
              if (rtn != null) {
                auditLogEntity.setRespJsonStrRaw(OM3.writeValueAsString(rtn));
              }
              auditLogEntity.setRespDatetime(Date0.on().datetimes()).setLastModifyDateTime(Date0.on().dateTime());
              log.info(OM3.writeValueAsString(auditLogEntity));
              cacheableDao.add(auditLogEntity.entityClass(), auditLogEntity);
            }
          } catch (Throwable throwable) {
            log.error(OM3.writeValueAsString(req), throwable);
          }
        }
      } else {
        log.error(MessageFormat.format("{0} - {1} : {2}", ZeroAnnotation.ERR_CODE__ANNOTATION_SETTING_ERROR, pjp.getSignature().getName(), OM3.writeValueAsString(rrAudit)));
        rtn = pjp.proceed();
      }
    } else {
      rtn = pjp.proceed();
    }
    return rtn;
  }
}
