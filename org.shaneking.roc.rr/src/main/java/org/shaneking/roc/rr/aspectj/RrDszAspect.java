package org.shaneking.roc.rr.aspectj;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.ling.rr.Resp;
import org.shaneking.ling.zero.annotation.ZeroAnnotation;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.ling.zero.text.MF0;
import org.shaneking.ling.zero.time.ZDT0;
import org.shaneking.roc.persistence.entity.sql.ChannelEntities;
import org.shaneking.roc.rr.Req;
import org.shaneking.roc.rr.annotation.RrAccess;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.ZonedDateTime;

@Aspect
@Component
@ConditionalOnProperty(prefix = "sk.roc.rr.dsz", value = "enabled", matchIfMissing = true)
@Slf4j
@Order(RrDszAspect.ORDER)
public class RrDszAspect {
  public static final int ORDER = 60000;

  @Value("${sk.roc.rr.dsz.enabled:true}")
  private boolean enabled;

  @Pointcut("execution(@org.shaneking.roc.rr.annotation.RrAccess * *..*.*(..))")
  private void pointcut() {
  }

  @Around("pointcut() && @annotation(rrAccess)")
  public Object around(ProceedingJoinPoint pjp, RrAccess rrAccess) throws Throwable {
    Object rtn = null;
    if (enabled) {
      if (pjp.getArgs().length > rrAccess.reqParamIdx() && pjp.getArgs()[rrAccess.reqParamIdx()] instanceof Req) {
        Req<?, ?> req = (Req<?, ?>) pjp.getArgs()[rrAccess.reqParamIdx()];
        ChannelEntities channelEntities = req.gnnCtx().getChannel();
        if (channelEntities != null && channelEntities.getDszSeconds() != null && channelEntities.getDszSeconds() > 0
          && (String0.isNullOrEmpty(req.getPri().getExt().getDsz()) || Duration.between(ZDT0.on().dTSZ(req.getPri().getExt().getDsz()).getZonedDateTime(), ZonedDateTime.now()).getSeconds() > channelEntities.getDszSeconds())) {
          rtn = Resp.failed(ChannelEntities.ERR_CODE__INVALID_TIMESTAMP, req.getPri().getExt().getDsz(), req);
        } else {
          rtn = pjp.proceed();
        }
      } else {
        log.error(MF0.fmt("{0} - {1} : {2}", ZeroAnnotation.ERR_CODE__ANNOTATION_SETTING_ERROR, pjp.getSignature().toLongString(), OM3.writeValueAsString(rrAccess)));
        rtn = pjp.proceed();
      }
    } else {
      rtn = pjp.proceed();
    }
    return rtn;
  }
}
