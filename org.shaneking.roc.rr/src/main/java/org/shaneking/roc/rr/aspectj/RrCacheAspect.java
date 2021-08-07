package org.shaneking.roc.rr.aspectj;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.ling.rr.Resp;
import org.shaneking.ling.zero.annotation.ZeroAnnotation;
import org.shaneking.ling.zero.cache.ZeroCache;
import org.shaneking.ling.zero.lang.Boolean0;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.ling.zero.text.MF0;
import org.shaneking.roc.jackson.JavaType3;
import org.shaneking.roc.persistence.entity.sql.RrAuditLogEntities;
import org.shaneking.roc.rr.Ctx;
import org.shaneking.roc.rr.Req;
import org.shaneking.roc.rr.annotation.RrCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@ConditionalOnProperty(prefix = "sk.roc.rr.cache", value = "enabled", matchIfMissing = true)
@Slf4j
@Order(RrCacheAspect.ORDER)
public class RrCacheAspect {
  public static final int ORDER = 60000;

  @Value("${sk.roc.rr.cache.enabled:true}")
  private boolean enabled;

  @Autowired(required = false)
  private ZeroCache cache;

  @Pointcut("execution(@org.shaneking.roc.rr.annotation.RrCache * *..*.*(..))")
  private void pointcut() {
  }

  @Around("pointcut() && @annotation(rrCache)")
  public Object around(ProceedingJoinPoint pjp, RrCache rrCache) throws Throwable {
    Object rtn = null;
    boolean proceedBefore = false;
    boolean proceedAfter = false;
    if (enabled && cache != null) {
      if (pjp.getArgs().length > rrCache.reqParamIdx() && pjp.getArgs()[rrCache.reqParamIdx()] instanceof Req) {
        Req<?, ?> req = (Req<?, ?>) pjp.getArgs()[rrCache.reqParamIdx()];
        Ctx ctx = req.gnnCtx();
        String reqNo = req.getPub().gnnReqNo();
        String tracingNo = req.getPub().gnnTracingNo();
        try {
          req.setCtx(null).getPub().setReqNo(null);
          req.setCtx(null).getPub().setTracingNo(null);

          String key = String.join(String0.MORE, pjp.getSignature().toLongString(), OM3.writeValueAsString(req));
          String respCached = cache.get(key);

          RrAuditLogEntities auditLogEntity = ctx.getAuditLog();
          if (auditLogEntity != null) {
            auditLogEntity.setCached(Boolean0.yn(!String0.isNullOrEmpty(respCached)));
          }

          if (String0.isNullOrEmpty(respCached)) {
            log.info(MF0.fmt("{0} - {1}", ZeroCache.ERR_CODE__CACHE_HIT_MISS, key));
            req.setCtx(ctx).getPub().setReqNo(reqNo);
            req.setCtx(ctx).getPub().setTracingNo(tracingNo);
            proceedBefore = true;
            rtn = pjp.proceed();
            proceedAfter = true;
            if (rtn instanceof Resp && ((Resp<?>) rtn).getData() instanceof Req) {
              Ctx rstCtx = ((Req) ((Resp<?>) rtn).getData()).getCtx();///ctx has many abstract class, doesn't have construct.
              ((Req) ((Resp<?>) rtn).getData()).setCtx(null);
              cache.set(key, rrCache.cacheSeconds(), OM3.writeValueAsString(rtn));
              ((Req) ((Resp<?>) rtn).getData()).setCtx(rstCtx);
            }
          } else {
            log.info(MF0.fmt("{0} - {1} : {2}", ZeroCache.ERR_CODE__CACHE_HIT_ALL, key, respCached));
            Resp<?> resp = OM3.readValue(respCached, OM3.om().getTypeFactory().constructParametricType(Resp.class, JavaType3.resolveRtnJavaTypes(pjp)));
            ((Req<?, ?>) resp.getData()).setCtx(ctx).getPub().setTracingNo(tracingNo);
            rtn = resp;
          }
        } catch (Throwable throwable) {
          log.error(OM3.writeValueAsString(req), throwable);
          req.setCtx(ctx).getPub().setTracingNo(tracingNo);
          if (proceedBefore && !proceedAfter) {
            throw throwable;//process error
          } else {
            if (proceedAfter) {
              rtn = Resp.failed(Resp.CODE_UNKNOWN_EXCEPTION, String.valueOf(throwable), req);
            } else {
              rtn = pjp.proceed();
            }
          }
        }
      } else {
        log.error(MF0.fmt("{0} - {1} : {2}", ZeroAnnotation.ERR_CODE__ANNOTATION_SETTING_ERROR, pjp.getSignature().toLongString(), OM3.writeValueAsString(rrCache)));
        rtn = pjp.proceed();
      }
    } else {
      rtn = pjp.proceed();
    }
    return rtn;
  }
}
