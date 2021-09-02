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
import org.shaneking.ling.zero.persistence.Tuple;
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
  public static final int ORDER = 80000;

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
        Tuple.Quadruple<Ctx, String, String, String> detached = detach(req);
        try {
          attach(req, Tuple.of(null, null, null, null));

          String key = String.join(String0.MORE, pjp.getSignature().toLongString(), OM3.writeValueAsString(req));
          String respCached = cache.get(key);

          RrAuditLogEntities auditLogEntity = Tuple.getFirst(detached).getAuditLog();
          if (auditLogEntity != null) {
            auditLogEntity.setCached(Boolean0.yn(!String0.isNullOrEmpty(respCached)));
          }

          if (String0.isNullOrEmpty(respCached)) {
            log.info(MF0.fmt("{0} - {1}", ZeroCache.ERR_CODE__CACHE_HIT_MISS, key));
            attach(req, detached);
            proceedBefore = true;
            rtn = pjp.proceed();
            proceedAfter = true;
            if (rtn instanceof Resp && ((Resp<?>) rtn).getData() instanceof Req) {
              Resp<?> resp = (Resp<?>) rtn;
              Tuple.Pair<Boolean, Tuple.Quadruple<Ctx, String, String, String>> respDetached = detach(resp);
              attach(resp, Tuple.of(null, Tuple.of(null, null, null, null)));
              cache.set(key, rrCache.cacheSeconds(), OM3.writeValueAsString(rtn));
              attach(resp, respDetached);
            }
          } else {
            log.info(MF0.fmt("{0} - {1} : {2}", ZeroCache.ERR_CODE__CACHE_HIT_ALL, key, respCached));
            Resp<?> resp = OM3.readValue(respCached, OM3.om().getTypeFactory().constructParametricType(Resp.class, JavaType3.resolveRtnJavaTypes(pjp)));
            attach((Req<?, ?>) resp.getData(), detached);
            rtn = resp;
          }
        } catch (Throwable throwable) {
          log.error(OM3.writeValueAsString(req), throwable);
          attach(req, detached);
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

  private Tuple.Quadruple<Ctx, String, String, String> detach(Req<?, ?> req) {
    return Tuple.of(req.gnnCtx(), req.getPub().gnnReqNo(), req.getPub().gnnTracingNo(), req.getPri().getExt().getDsz());
  }

  private void attach(Req<?, ?> req, Tuple.Quadruple<Ctx, String, String, String> detached) {
    req.setCtx(Tuple.getFirst(detached)).getPub().setReqNo(Tuple.getSecond(detached)).setTracingNo(Tuple.getThird(detached));
    req.getPri().getExt().setDsz(Tuple.getFourth(detached));
  }

  private Tuple.Pair<Boolean, Tuple.Quadruple<Ctx, String, String, String>> detach(Resp<?> resp) {
    return Tuple.of(resp.getRbk(), detach((Req<?, ?>) resp.getData()));
  }

  private void attach(Resp<?> resp, Tuple.Pair<Boolean, Tuple.Quadruple<Ctx, String, String, String>> detached) {
    resp.setRbk(Tuple.getFirst(detached));
    attach((Req<?, ?>) resp.getData(), Tuple.getSecond(detached));
  }
}
