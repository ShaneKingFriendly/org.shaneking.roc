package org.shaneking.roc.rr.aspectj;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.ling.rr.Resp;
import org.shaneking.ling.rr.RespException;
import org.shaneking.ling.zero.annotation.ZeroAnnotation;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.ling.zero.text.MF0;
import org.shaneking.ling.zero.util.Map0;
import org.shaneking.ling.zero.util.concurrent.atomic.AtomicLong0;
import org.shaneking.roc.rr.annotation.RrLimiting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Aspect
@Component
@ConditionalOnProperty(prefix = "sk.roc.rr.limiting", value = "enabled", matchIfMissing = true)
@Slf4j
@Order(RrLimitingAspect.ORDER)
public class RrLimitingAspect {
  public static final int ORDER = 34000;
  public static final String ERR_CODE__BUSY_NOW = "RR_LIMITING_ASPECT__BUSY_NOW";
  private final Map<String, AtomicLong> map = Map0.newConcurrentHashMap();
  @Value("${sk.roc.rr.limiting.enabled:true}")
  private boolean enabled;
  @Autowired
  private Environment environment;

  @Around("pointcut() && @annotation(rrLimiting)")
  public Object around(ProceedingJoinPoint pjp, RrLimiting rrLimiting) throws Throwable {
    Object rtn = null;
    if (enabled) {
      if (!String0.isNullOrEmpty(rrLimiting.prop()) && rrLimiting.limit() > 0) {
        Integer limit = environment.getProperty(rrLimiting.prop(), Integer.class, rrLimiting.limit());
        AtomicLong atomicLong = map.computeIfAbsent(rrLimiting.prop(), k -> new AtomicLong(0));
        if (AtomicLong0.tryIncreaseFailed(atomicLong, limit)) {
          try {
            rtn = pjp.proceed();
          } catch (Throwable throwable) {
            log.error(pjp.getSignature().toLongString(), throwable);
            throw throwable;
          } finally {
            AtomicLong0.tryDecreaseFailed(atomicLong);
          }
        } else {
          log.warn(MF0.fmt("{0} - {1} : {2}", ERR_CODE__BUSY_NOW, pjp.getSignature().toLongString(), OM3.writeValueAsString(rrLimiting)));
          throw new RespException(Resp.failed(null, ERR_CODE__BUSY_NOW, pjp.getSignature().toLongString()));
        }
      } else {
        log.error(MF0.fmt("{0} - {1} : {2}", ZeroAnnotation.ERR_CODE__ANNOTATION_SETTING_ERROR, pjp.getSignature().toLongString(), OM3.writeValueAsString(rrLimiting)));
        rtn = pjp.proceed();
      }
    } else {
      rtn = pjp.proceed();
    }
    return rtn;
  }

  @Pointcut("execution(@org.shaneking.roc.rr.annotation.RrLimiting * *..*.*(..))")
  private void pointcut() {
  }
}
