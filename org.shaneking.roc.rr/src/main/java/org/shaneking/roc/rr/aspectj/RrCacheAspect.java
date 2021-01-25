package org.shaneking.roc.rr.aspectj;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.ling.rr.Resp;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.ling.zero.lang.ZeroException;
import org.shaneking.roc.jackson.JavaType3;
import org.shaneking.roc.persistence.cache.AbstractCache;
import org.shaneking.roc.rr.Req;
import org.shaneking.roc.rr.annotation.RrCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Aspect
@Component
@Slf4j
@Order(700)
public class RrCacheAspect {
  @Autowired
  private AbstractCache cache;

  @Value("${sk.roc.rr.cache.enabled:true}")
  private boolean enabled;

  @Pointcut("execution(@org.shaneking.roc.rr.annotation.RrCache * *..*.*(..))")
  private void pointcut() {
  }

  @Around("pointcut() && @annotation(rrCache)")
  public Object around(ProceedingJoinPoint pjp, RrCache rrCache) throws Throwable {
    Object rtn = null;
    if (enabled) {
      if (pjp.getArgs().length > rrCache.reqParamIdx() && pjp.getArgs()[rrCache.reqParamIdx()] instanceof Req) {
        Req<?, ?> req = (Req<?, ?>) pjp.getArgs()[rrCache.reqParamIdx()];
        String tracingId = req.getPub().getTracingId();
        try {
          req.getPub().setTracingId(null);
          String key = String.join(String0.MORE, pjp.getSignature().getName(), OM3.writeValueAsString(req));
          String respCached = cache.get(key);
          if (String0.isNullOrEmpty(respCached)) {
            log.info(MessageFormat.format("{0} - {1}", AbstractCache.ERR_CODE__CACHE_HIT_MISS, key));
            req.getPub().setTracingId(tracingId);
            rtn = pjp.proceed();
            if (rtn instanceof Resp && ((Resp<?>) rtn).getData() instanceof Req) {
              cache.set(key, rrCache.cacheSeconds(), OM3.writeValueAsString(rtn));
            }
          } else {
            log.info(MessageFormat.format("{0} - {1} : {2}", AbstractCache.ERR_CODE__CACHE_HIT_ALL, key, respCached));
            Resp<?> resp = OM3.readValue(respCached, OM3.om().getTypeFactory().constructParametricType(Resp.class, JavaType3.resolveRtnJavaTypes(pjp)));
            ((Req<?, ?>) resp.getData()).getPub().setTracingId(tracingId);
            rtn = resp;
          }
        } catch (Throwable throwable) {
          log.error(OM3.writeValueAsString(req), throwable);
          req.getPub().setTracingId(tracingId);
          rtn = pjp.proceed();
        }
      } else {
        log.error(MessageFormat.format("{0} - {1} : {2}", ZeroException.ERR_CODE__ANNOTATION_SETTING_ERROR, pjp.getSignature().getName(), OM3.writeValueAsString(rrCache)));
      }
    } else {
      rtn = pjp.proceed();
    }
    return rtn;
  }
}
