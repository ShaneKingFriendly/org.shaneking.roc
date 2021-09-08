package org.shaneking.roc.rr.aspectj;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.ling.persistence.entity.Numbered;
import org.shaneking.ling.rr.Resp;
import org.shaneking.ling.rr.RespException;
import org.shaneking.ling.zero.annotation.ZeroAnnotation;
import org.shaneking.ling.zero.lang.Object0;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.ling.zero.net.InetAddress0;
import org.shaneking.ling.zero.text.MF0;
import org.shaneking.ling.zero.time.LDT0;
import org.shaneking.ling.zero.time.ZDT0;
import org.shaneking.ling.zero.util.UUID0;
import org.shaneking.roc.persistence.dao.NumberedDao;
import org.shaneking.roc.persistence.entity.sql.ChannelEntities;
import org.shaneking.roc.persistence.entity.sql.RrAuditLogEntities;
import org.shaneking.roc.rr.Ctx;
import org.shaneking.roc.rr.Pub;
import org.shaneking.roc.rr.Req;
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
  public static final int ORDER = 20000;

  @Value("${sk.roc.rr.audit.enabled:true}")
  private boolean enabled;

  @Autowired
  private NumberedDao numberedDao;

  @Autowired(required = false)
  private RrAuditLogEntities auditLogEntityClass;
  @Autowired(required = false)
  private ChannelEntities channelEntityClass;

  @Pointcut("execution(@org.shaneking.roc.rr.annotation.RrAudit * *..*.*(..))")
  private void pointcut() {
  }

  @Around("pointcut() && @annotation(rrAudit)")
  public Object around(ProceedingJoinPoint pjp, RrAudit rrAudit) throws Throwable {
    Object rtn = null;
    boolean ifExceptionThenInProceed = false;
    if (enabled && auditLogEntityClass != null && channelEntityClass != null) {
      if (pjp.getArgs().length > rrAudit.reqParamIdx() && pjp.getArgs()[rrAudit.reqParamIdx()] instanceof Req) {
        Req<?, ?> req = (Req<?, ?>) pjp.getArgs()[rrAudit.reqParamIdx()];
        log.info(OM3.writeValueAsString(req));

        String reqNo = req.getPub().gnnReqNo();
        String tracingNo = req.getPub().gnnTracingNo();
        RrAuditLogEntities auditLogEntity = null;
        try {
          auditLogEntity = auditLogEntityClass.entityClass().newInstance();
          auditLogEntity.setId(UUID0.cUl33());
          auditLogEntity.setNo(reqNo);
          auditLogEntity.setIvd(String0.N);
          auditLogEntity.setLmDsz(ZDT0.on().dTSZ());
//          auditLogEntity.setLmUid();
          auditLogEntity.setVer(0);
//          auditLogEntity.setChannelId();
//          auditLogEntity.setTenantId();//crypto
          auditLogEntity.setTracingNo(tracingNo);
          auditLogEntity.setReqDatetime(LDT0.on().dts());
//          auditLogEntity.setReqIps();///web
//          auditLogEntity.setReqUserId();//crypto
          auditLogEntity.setReqJsonStrRaw(OM3.writeValueAsString(req));
//          auditLogEntity.setReqJsonStr();//crypto
//          auditLogEntity.setReqUrl();///web
          auditLogEntity.setReqSignature(pjp.getSignature().toLongString());
//          auditLogEntity.setCached();//cache
//          auditLogEntity.setRespJsonStr();//crypto
//          auditLogEntity.setRespJsonStrCtx();
//          auditLogEntity.setRespJsonStrRaw();
          auditLogEntity.setRespIps(OM3.writeValueAsString(InetAddress0.localHostExactAddress()));
//          auditLogEntity.setRespDatetime();
          req.gnnCtx().setAuditLog(auditLogEntity);

          if (req.getPub() == null || String0.isNullOrEmpty(req.getPub().getChannelNo())) {
            rtn = Resp.failed(Pub.ERR_CODE__REQUIRED_CHANNEL_NUMBER, OM3.writeValueAsString(req.getPub()), req);
          } else {
            ChannelEntities channelEntity = numberedDao.oneByNo(channelEntityClass.entityClass(), req.getPub().getChannelNo(), true);
            if (channelEntity == null) {
              rtn = Resp.failed(Numbered.ERR_CODE__NOT_FOUND_BY_NUMBER, req.getPub().getChannelNo(), req);
            } else {
              req.gnnCtx().setChannel(channelEntity);
              if (req.gnnCtx().getAuditLog() != null) {
                req.gnnCtx().getAuditLog().setChannelId(channelEntity.getId());
              }

              ifExceptionThenInProceed = true;
              rtn = pjp.proceed();
            }
          }
        } catch (Throwable throwable) {
          log.error(OM3.lp(rtn, req, auditLogEntity), throwable);
          if (ifExceptionThenInProceed) {
            if (throwable instanceof RespException) {
              rtn = Resp.failed(Resp.CODE_UNKNOWN_EXCEPTION, Resp.CODE_UNKNOWN_EXCEPTION, req).parseExp((RespException) throwable);
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
              auditLogEntity.setLmUid(auditLogEntity.getReqUserId());
              Ctx reqCtx = req.detach();
              auditLogEntity.setRespJsonStrCtx(OM3.writeValueAsString(reqCtx));
              if (rtn != null) {
                if (rtn instanceof Resp) {
                  Resp<?> resp = (Resp<?>) rtn;
                  if (resp.getData() instanceof Req) {
                    Req<?, ?> respData = (Req<?, ?>) resp.getData();
                    if (Object0.NULL.equals(auditLogEntity.getRespJsonStrCtx())
                      || OM3.OBJECT_ERROR_STRING.equals(auditLogEntity.getRespJsonStrCtx())
                      || OM3.writeValueAsString(new Ctx()).equals(auditLogEntity.getRespJsonStrCtx())) {
                      //maybe cached scenario
                      Ctx respCtx = respData.detach();
                      auditLogEntity.setRespJsonStrCtx(OM3.writeValueAsString(respCtx));
                    }
                    respData.detach();//maybe is detach again
                  }
                  resp.detach();
                }
                auditLogEntity.setRespJsonStrRaw(OM3.writeValueAsString(rtn));
              }
              auditLogEntity.setRespDatetime(LDT0.on().dts());

              log.info(OM3.writeValueAsString(auditLogEntity));
              numberedDao.getCacheableDao().add(auditLogEntity.entityClass(), auditLogEntity);
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
}
