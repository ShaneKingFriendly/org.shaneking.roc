package org.shaneking.roc.rr.aspectj;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.ling.zero.annotation.ZeroAnnotation;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.ling.zero.lang.ZeroException;
import org.shaneking.ling.zero.util.Map0;
import org.shaneking.ling.zero.util.concurrent.atomic.AtomicLong0;
import org.shaneking.roc.rr.annotation.RrLimiting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Aspect
@Component
@ConditionalOnProperty(prefix = "sk.roc.rr.limiting", value = "enabled")
@Slf4j
@Order(300)
public class RrLimitingAspect {
  public static final String ERR_CODE__BUSY_NOW = "RR_LIMITING_ASPECT__BUSY_NOW";
  private final Map<String, AtomicLong> map = Map0.newConcurrentHashMap();
  @Value("${sk.roc.rr.limiting.enabled:false}")
  private boolean enabled;
  @Autowired
  private Environment environment;

  @Pointcut("execution(@org.shaneking.roc.rr.annotation.RrLimiting * *..*.*(..))")
  private void pointcut() {
  }

  @Around("pointcut() && @annotation(rrLimiting)")
  public Object around(ProceedingJoinPoint pjp, RrLimiting rrLimiting) throws Throwable {
    Object rtn = null;
    if (enabled) {
      if (!String0.isNullOrEmpty(rrLimiting.prop()) && rrLimiting.limit() > 0) {
        Integer limit = environment.getProperty(rrLimiting.prop(), Integer.class, rrLimiting.limit());
        AtomicLong atomicLong = map.getOrDefault(rrLimiting.prop(), new AtomicLong(0));
        if (AtomicLong0.tryIncreaseFailed(atomicLong, limit)) {
          try {
            rtn = pjp.proceed();
          } catch (Throwable throwable) {
            log.error(pjp.getSignature().getName(), throwable);
            throw throwable;
          } finally {
            AtomicLong0.tryDecreaseFailed(atomicLong);
          }
        } else {
          throw new ZeroException(ERR_CODE__BUSY_NOW);
        }
      } else {
        log.error(MessageFormat.format("{0} - {1} : {2}", ZeroAnnotation.ERR_CODE__ANNOTATION_SETTING_ERROR, pjp.getSignature().getName(), OM3.writeValueAsString(rrLimiting)));
        rtn = pjp.proceed();
      }
    } else {
      rtn = pjp.proceed();
    }
    return rtn;
  }
}
