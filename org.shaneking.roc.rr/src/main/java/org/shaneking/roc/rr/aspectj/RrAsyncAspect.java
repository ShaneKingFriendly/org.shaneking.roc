package org.shaneking.roc.rr.aspectj;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.ling.rr.Resp;
import org.shaneking.ling.zero.annotation.ZeroAnnotation;
import org.shaneking.ling.zero.lang.Integer0;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.ling.zero.text.MF0;
import org.shaneking.ling.zero.util.UUID0;
import org.shaneking.roc.persistence.dao.NumberedDao;
import org.shaneking.roc.persistence.entity.sql.RrAsyncLogEntities;
import org.shaneking.roc.rr.Req;
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
  public static final int ORDER = 80000;

  @Value("${sk.roc.rr.async.enabled:true}")
  private boolean enabled;

  @Autowired
  private NumberedDao numberedDao;

  @Autowired
  private RrAsyncAspectSupport asyncAspectSupport;

  @Autowired(required = false)
  private RrAsyncLogEntities asyncLogEntities;

  @Pointcut("execution(@org.shaneking.roc.rr.annotation.RrAsync * *..*.*(..))")
  private void pointcut() {
  }

  @Around("pointcut() && @annotation(rrAsync)")
  public Object around(ProceedingJoinPoint pjp, RrAsync rrAsync) throws Throwable {
    Object rtn = null;
    if (enabled) {
      if (pjp.getArgs().length > rrAsync.reqParamIdx() && pjp.getArgs()[rrAsync.reqParamIdx()] instanceof Req) {
        Req req = (Req<?, ?>) pjp.getArgs()[rrAsync.reqParamIdx()];
        if (Integer0.null2Zero(req.getPri().getExt().getAsync()) > 0 && asyncLogEntities != null) {
          RrAsyncLogEntities asyncLogEntity = numberedDao.oneByNo(asyncLogEntities.entityClass(), req.getPub().gnnReqNo(), true);
          if (asyncLogEntity == null) {
            asyncLogEntity = asyncLogEntities.entityClass().newInstance();
            asyncLogEntity.setReqJsonStrRaw(OM3.writeValueAsString(req)).setCtxJsonStr(OM3.writeValueAsString(req.gnnCtx())).setTenantId(req.gnnCtx().gnaTenantId());
            asyncLogEntity.setNo(req.getPub().gnnReqNo());
            asyncLogEntity.initWithUidAndId(req.gnnCtx().gnaUserId(), String0.nullOrEmptyTo(req.gnnCtx().gnaAuditId(), UUID0.cUl33()));
            numberedDao.getCacheableDao().add(asyncLogEntities.entityClass(), asyncLogEntity);

            RrAsyncLogEntities modAsyncLogEntity = asyncLogEntities.entityClass().newInstance();
            modAsyncLogEntity.setId(asyncLogEntity.getId());
            Future<Resp<?>> respFuture = asyncAspectSupport.async(pjp, req, modAsyncLogEntity);
            try {
              rtn = respFuture.get(req.getPri().getExt().getAsync(), TimeUnit.SECONDS);
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
              rtn = Resp.success(req);
            }
          } else {
            Resp<?> resp = Resp.success(req);
            if (!String0.isNullOrEmpty(asyncLogEntity.getRtnJsonStr())) {
              req.getPri().setRtn(OM3.readValue(asyncLogEntity.getRtnJsonStr(), rrAsync.rtnType()));
              resp.setCode(String0.nullOrEmptyTo(asyncLogEntity.getRtnCode(), Resp.CODE_UNKNOWN_EXCEPTION));
              resp.setMsg(asyncLogEntity.getRtnMsg());
            }
            rtn = resp;
          }
        } else {
          rtn = pjp.proceed();
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
}
