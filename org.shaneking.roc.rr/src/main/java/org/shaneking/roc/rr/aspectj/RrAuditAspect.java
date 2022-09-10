package org.shaneking.roc.rr.aspectj;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.ling.persistence.entity.sql.RrAuditLogEntities;
import org.shaneking.ling.rr.Req;
import org.shaneking.ling.rr.Resp;
import org.shaneking.ling.rr.RespException;
import org.shaneking.ling.zero.annotation.ZeroAnnotation;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.ling.zero.net.InetAddress0;
import org.shaneking.ling.zero.text.MF0;
import org.shaneking.ling.zero.time.LDT0;
import org.shaneking.ling.zero.time.ZDT0;
import org.shaneking.ling.zero.util.UUID0;
import org.shaneking.roc.persistence.dao.CacheableDao;
import org.shaneking.roc.rr.annotation.RrAudit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@ConditionalOnProperty(prefix = "sk.roc.rr.audit", value = "enabled", matchIfMissing = true)
@Slf4j
@Order(RrAuditAspect.ORDER)///small will first
public class RrAuditAspect {
  public static final int ORDER = 26000;
  @Value("${sk.roc.rr.audit.async:true}")
  private boolean async;
  @Value("${sk.roc.rr.audit.enabled:true}")
  private boolean enabled;
  @Autowired
  private CacheableDao cacheableDao;
  @Autowired
  private RrAuditAspectHelper auditAspectHelper;
  @Autowired(required = false)
  private RrAuditLogEntities auditLogEntityClass;

  @Around("pointcut() && @annotation(rrAudit)")
  public Object around(ProceedingJoinPoint pjp, RrAudit rrAudit) throws Throwable {
    Object rtn = null;
    if (enabled && auditLogEntityClass != null) {
      if (pjp.getArgs().length > rrAudit.reqParamIdx() && pjp.getArgs()[rrAudit.reqParamIdx()] instanceof Req) {
        boolean ifExceptionThenInProceed = false;
        Req<?> req = (Req<?>) pjp.getArgs()[rrAudit.reqParamIdx()];
        RrAuditLogEntities auditLogEntity = null;
        try {
          log.info(OM3.writeValueAsString(req));
          String reqNo = req.gnnMsg().gnnRno();
          String tracingNo = req.gnnMsg().gnnTno();

          auditLogEntity = auditLogEntityClass.entityClass().newInstance();
          auditLogEntity.setId(UUID0.cUl33());
          auditLogEntity.setVer(0);
          auditLogEntity.setDd(String0.N);
          auditLogEntity.setIvd(String0.N);
          auditLogEntity.setLmDsz(ZDT0.on().dTSZ());//below
//          auditLogEntity.setLmUid();//user
          auditLogEntity.setNo(reqNo);

//          auditLogEntity.setChannelId();//channel
//          auditLogEntity.setTenantId();//tenant

          auditLogEntity.setTracingNo(tracingNo);
          auditLogEntity.setReqDatetime(LDT0.on().dts());
//          auditLogEntity.setReqIps();///web
//          auditLogEntity.setReqUserId();//user
          auditLogEntity.setReqJsonStrRaw(req.jsonStr());//crypto//this
//          auditLogEntity.setReqJsonStr();//crypto
//          auditLogEntity.setReqUrl();///web
          auditLogEntity.setReqSignature(pjp.getSignature().toLongString());
//          auditLogEntity.setCached();//cache
//          auditLogEntity.setRespJsonStr();//crypto
//          auditLogEntity.setRespJsonStrCtx();//below
//          auditLogEntity.setRespJsonStrRaw();//crypto//below
          auditLogEntity.setRespIps(OM3.writeValueAsString(InetAddress0.localHostExactAddress()));
//          auditLogEntity.setRespDatetime();//below
          req.gnnCtx().setAuditLog(auditLogEntity);

          ifExceptionThenInProceed = true;
          rtn = pjp.proceed();
        } catch (Throwable throwable) {
          log.error(OM3.lp(rtn, req, auditLogEntity), throwable);
          if (ifExceptionThenInProceed) {
            if (throwable instanceof RespException) {
              rtn = Resp.failed(req, Resp.CODE_UNKNOWN_EXCEPTION, Resp.CODE_UNKNOWN_EXCEPTION).parseExp((RespException) throwable);
            } else {
              throw throwable;
            }
          } else {
            rtn = pjp.proceed();
          }
        } finally {
          try {
            if (auditLogEntity != null) {
              auditLogEntity.setLmDsz(ZDT0.on().dTSZ());
              auditLogEntity.setRespDatetime(LDT0.on().dts());
              auditLogEntity.setRespJsonStrCtx(req.gnnCtx().jsonStr());
              if (rtn instanceof Resp) {
                auditLogEntity.setRespJsonStrRaw(((Resp<?, ?>) rtn).jsonStr());
                ((Resp<?, ?>) rtn).setReq(null);
              }
              log.info(OM3.writeValueAsString(auditLogEntity));
              if (async) {
                auditAspectHelper.async(auditLogEntity);
              } else {
                cacheableDao.add(auditLogEntity.entityClass(), auditLogEntity);
              }
            }
          } catch (Throwable throwable) {
            ///ignore exception : just audit log error, business succeeded
            log.error(OM3.lp(rtn, req, auditLogEntity), throwable);
          }
        }
      } else {
        log.error(MF0.fmt("{0} - {1} : {2}", ZeroAnnotation.ERR_CODE__ANNOTATION_SETTING_ERROR, pjp.getSignature().toLongString(), OM3.writeValueAsString(rrAudit)));
        rtn = pjp.proceed();
      }
    } else {
      rtn = pjp.proceed();
    }
    return rtn;
  }

  @Pointcut("execution(@org.shaneking.roc.rr.annotation.RrAudit * *..*.*(..))")
  private void pointcut() {
  }
}
