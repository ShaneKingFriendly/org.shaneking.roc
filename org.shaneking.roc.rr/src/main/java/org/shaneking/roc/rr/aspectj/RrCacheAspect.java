package org.shaneking.roc.rr.aspectj;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.shaneking.ling.cache.StringCaches;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.ling.rr.Resp;
import org.shaneking.ling.zero.annotation.ZeroAnnotation;
import org.shaneking.ling.zero.lang.Boolean0;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.roc.jackson.JavaType3;
import org.shaneking.roc.persistence.entity.AuditLogEntity;
import org.shaneking.roc.rr.Req;
import org.shaneking.roc.rr.annotation.RrCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Aspect
@Component
@ConditionalOnProperty(prefix = "sk.roc.rr.cache", value = "enabled")
@Slf4j
@Order(600)
public class RrCacheAspect {
  @Value("${sk.roc.rr.cache.enabled:false}")
  private boolean enabled;

  @Autowired
  private StringCaches cache;

  @Pointcut("execution(@org.shaneking.roc.rr.annotation.RrCache * *..*.*(..))")
  private void pointcut() {
  }

  @Around("pointcut() && @annotation(rrCache)")
  public Object around(ProceedingJoinPoint pjp, RrCache rrCache) throws Throwable {
    Object rtn = null;
    boolean proceedBefore = false;
    boolean proceedAfter = false;
    if (enabled) {
      if (pjp.getArgs().length > rrCache.reqParamIdx() && pjp.getArgs()[rrCache.reqParamIdx()] instanceof Req) {
        Req<?, ?> req = (Req<?, ?>) pjp.getArgs()[rrCache.reqParamIdx()];
        String tracingId = req.getPub().getTracingId();
        try {
          req.getPub().setTracingId(null);

          String key = String.join(String0.MORE, pjp.getSignature().getName(), OM3.writeValueAsString(req));
          String respCached = cache.get(key);

          AuditLogEntity auditLogEntity = req.getCtx().getAuditLog();
          if (auditLogEntity != null) {
            auditLogEntity.setCached(Boolean0.yn(!String0.isNullOrEmpty(respCached)));
          }

          if (String0.isNullOrEmpty(respCached)) {
            log.info(MessageFormat.format("{0} - {1}", StringCaches.ERR_CODE__CACHE_HIT_MISS, key));
            req.getPub().setTracingId(tracingId);
            proceedBefore = true;
            rtn = pjp.proceed();
            proceedAfter = true;
            if (rtn instanceof Resp && ((Resp<?>) rtn).getData() instanceof Req) {
              cache.set(key, rrCache.cacheSeconds(), OM3.writeValueAsString(rtn));
            }
          } else {
            log.info(MessageFormat.format("{0} - {1} : {2}", StringCaches.ERR_CODE__CACHE_HIT_ALL, key, respCached));
            Resp<?> resp = OM3.readValue(respCached, OM3.om().getTypeFactory().constructParametricType(Resp.class, JavaType3.resolveRtnJavaTypes(pjp)));
            ((Req<?, ?>) resp.getData()).getPub().setTracingId(tracingId);
            rtn = resp;
          }
        } catch (Throwable throwable) {
          log.error(OM3.writeValueAsString(req), throwable);
          req.getPub().setTracingId(tracingId);
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
        log.error(MessageFormat.format("{0} - {1} : {2}", ZeroAnnotation.ERR_CODE__ANNOTATION_SETTING_ERROR, pjp.getSignature().getName(), OM3.writeValueAsString(rrCache)));
        rtn = pjp.proceed();
      }
    } else {
      rtn = pjp.proceed();
    }
    return rtn;
  }
}
