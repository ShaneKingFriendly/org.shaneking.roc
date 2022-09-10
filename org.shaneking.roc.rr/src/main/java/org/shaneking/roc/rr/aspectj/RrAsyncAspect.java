package org.shaneking.roc.rr.aspectj;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.ling.rr.Req;
import org.shaneking.ling.rr.Resp;
import org.shaneking.ling.rr.RespMsgBody;
import org.shaneking.ling.zero.annotation.ZeroAnnotation;
import org.shaneking.ling.zero.lang.Integer0;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.ling.zero.text.MF0;
import org.shaneking.ling.zero.util.UUID0;
import org.shaneking.roc.jackson.JavaType3;
import org.shaneking.roc.persistence.dao.NumberedDao;
import org.shaneking.roc.persistence.entity.sql.RrAsyncLogEntities;
import org.shaneking.roc.rr.annotation.RrAsync;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Aspect
@Component
@ConditionalOnProperty(prefix = "sk.roc.rr.async", value = "enabled", matchIfMissing = true)
@Slf4j
@Order(RrAsyncAspect.ORDER)
public class RrAsyncAspect {
  public static final int ORDER = 76000;
  @Value("${sk.roc.rr.async.enabled:true}")
  private boolean enabled;
  @Autowired
  private NumberedDao numberedDao;
  @Autowired
  private RrAsyncAspectHelper asyncAspectHelper;
  @Autowired(required = false)
  private RrAsyncLogEntities asyncLogEntities;

  @Around("pointcut() && @annotation(rrAsync)")
  public Object around(ProceedingJoinPoint pjp, RrAsync rrAsync) throws Throwable {
    Object rtn = null;
    if (enabled && asyncLogEntities != null) {
      if (pjp.getArgs().length > rrAsync.reqParamIdx() && pjp.getArgs()[rrAsync.reqParamIdx()] instanceof Req) {
        boolean ifExceptionThenInProceed = false;
        Req<?> req = (Req<?>) pjp.getArgs()[rrAsync.reqParamIdx()];
        try {
          if (Integer0.null2Zero(req.gnnMsg().getAsy()) > 0) {
            RrAsyncLogEntities asyncLogEntity = numberedDao.oneByNo(asyncLogEntities.entityClass(), req.gnnMsg().gnnRno(), true);
            if (asyncLogEntity == null) {
              asyncLogEntity = asyncLogEntities.entityClass().newInstance();
              asyncLogEntity.setTenantId(req.gnnCtx().gnaTenantId());
              asyncLogEntity.setNo(req.gnnMsg().gnnRno());
              asyncLogEntity.initWithUidAndId(req.gnnCtx().gnaUserId(), String0.nullOrEmptyTo(req.gnnCtx().gnaAuditId(), UUID0.cUl33()));
              numberedDao.getCacheableDao().add(asyncLogEntities.entityClass(), asyncLogEntity);//ignore concurrent insert scenario

              RrAsyncLogEntities modAsyncLogEntity = asyncLogEntities.entityClass().newInstance();
              modAsyncLogEntity.setId(asyncLogEntity.getId());
              Future<Resp<?, ?>> respFuture = asyncAspectHelper.async(pjp, req, modAsyncLogEntity);
              try {
                rtn = respFuture.get(req.gnnMsg().getAsy(), TimeUnit.SECONDS);
              } catch (InterruptedException | ExecutionException | TimeoutException e) {
                rtn = Resp.success(req, null);
              }
            } else {
              rtn = Resp.success(req, null).srtMsgBody(OM3.readValue(asyncLogEntity.getRespMsgBodyJsonStr(), OM3.om().getTypeFactory().constructParametricType(RespMsgBody.class, JavaType3.resolveRtnJavaTypes(pjp))));
            }
          } else {
            ifExceptionThenInProceed = true;
            rtn = pjp.proceed();
          }
        } catch (Throwable throwable) {
          log.error(OM3.writeValueAsString(req), throwable);
          if (ifExceptionThenInProceed) {
            throw throwable;//process error
          } else {
            rtn = pjp.proceed();
          }
        }

      } else {
        log.error(MF0.fmt("{0} - {1} : {2}", ZeroAnnotation.ERR_CODE__ANNOTATION_SETTING_ERROR, pjp.getSignature().toLongString(), OM3.writeValueAsString(rrAsync)));
        rtn = pjp.proceed();
      }
    } else {
      rtn = pjp.proceed();
    }
    return rtn;
  }

  @Pointcut("execution(@org.shaneking.roc.rr.annotation.RrAsync * *..*.*(..))")
  private void pointcut() {
  }
}
