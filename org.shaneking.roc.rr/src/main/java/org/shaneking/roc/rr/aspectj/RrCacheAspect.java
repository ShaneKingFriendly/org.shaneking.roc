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
import org.shaneking.ling.rr.RespMsgBody;
import org.shaneking.ling.zero.annotation.ZeroAnnotation;
import org.shaneking.ling.zero.cache.ZeroCache;
import org.shaneking.ling.zero.crypto.MD5a;
import org.shaneking.ling.zero.lang.Boolean0;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.ling.zero.text.MF0;
import org.shaneking.roc.jackson.JavaType3;
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
  public static final int ORDER = 74000;
  @Value("${sk.roc.rr.cache.enabled:true}")
  private boolean enabled;
  @Autowired(required = false)
  private ZeroCache cache;

  @Around("pointcut() && @annotation(rrCache)")
  public Object around(ProceedingJoinPoint pjp, RrCache rrCache) throws Throwable {
    Object rtn = null;
    if (enabled && cache != null) {
      if (pjp.getArgs().length > rrCache.reqParamIdx() && pjp.getArgs()[rrCache.reqParamIdx()] instanceof Req) {
        boolean ifExceptionThenInProceed = false;
        Req<?> req = (Req<?>) pjp.getArgs()[rrCache.reqParamIdx()];
        try {
          String key = String.join(String0.MORE, pjp.getSignature().toLongString(), MD5a.encrypt(OM3.writeValueAsString(req.gnnMsg().getBdy())));
          String respMsgBody = cache.get(key);

          RrAuditLogEntities auditLogEntity = req.gnnCtx().getAuditLog();
          if (auditLogEntity != null) {
            auditLogEntity.setCached(Boolean0.yn(!String0.isNullOrEmpty(respMsgBody)));
          }

          if (String0.isNullOrEmpty(respMsgBody)) {
            log.info(MF0.fmt("{0} - {1}", ZeroCache.ERR_CODE__CACHE_HIT_MISS, key));
            ifExceptionThenInProceed = true;
            rtn = pjp.proceed();
            if (rtn instanceof Resp) {
              cache.set(key, rrCache.cacheSeconds(), OM3.writeValueAsString(((Resp<?, ?>) rtn).gnnMsg().gnnBody()));
            }
          } else {
            log.info(MF0.fmt("{0} - {1} : {2}", ZeroCache.ERR_CODE__CACHE_HIT_ALL, key, respMsgBody));
            rtn = Resp.success(req, null).srtMsgBody(OM3.readValue(respMsgBody, OM3.om().getTypeFactory().constructParametricType(RespMsgBody.class, JavaType3.resolveRtnJavaTypes(pjp))));
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
        log.error(MF0.fmt("{0} - {1} : {2}", ZeroAnnotation.ERR_CODE__ANNOTATION_SETTING_ERROR, pjp.getSignature().toLongString(), OM3.writeValueAsString(rrCache)));
        rtn = pjp.proceed();
      }
    } else {
      rtn = pjp.proceed();
    }
    return rtn;
  }

  @Pointcut("execution(@org.shaneking.roc.rr.annotation.RrCache * *..*.*(..))")
  private void pointcut() {
  }
}
