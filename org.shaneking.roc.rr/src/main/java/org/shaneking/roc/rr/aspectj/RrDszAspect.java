package org.shaneking.roc.rr.aspectj;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.ling.persistence.entity.sql.ChannelEntities;
import org.shaneking.ling.rr.Req;
import org.shaneking.ling.rr.Resp;
import org.shaneking.ling.zero.annotation.ZeroAnnotation;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.ling.zero.text.MF0;
import org.shaneking.ling.zero.time.ZDT0;
import org.shaneking.roc.rr.annotation.RrDsz;
import org.springframework.beans.factory.annotation.Autowired;
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
  public static final int ORDER = 72000;
  @Value("${sk.roc.rr.dsz.enabled:true}")
  private boolean enabled;
  @Autowired(required = false)
  private ChannelEntities channelEntityClass;
  @Autowired
  private RrCtxHelper rrCtxHelper;

  @Around("pointcut() && @annotation(rrDsz)")
  public Object around(ProceedingJoinPoint pjp, RrDsz rrDsz) throws Throwable {
    Object rtn = null;
    if (enabled && channelEntityClass != null) {
      if (pjp.getArgs().length > rrDsz.reqParamIdx() && pjp.getArgs()[rrDsz.reqParamIdx()] instanceof Req) {
        Req<?> req = (Req<?>) pjp.getArgs()[rrDsz.reqParamIdx()];
        ChannelEntities channelEntity = req.gnnCtx().getChannel();
        if (channelEntity.getDszSeconds() != null && channelEntity.getDszSeconds() > 0
          && (String0.isNullOrEmpty(req.gnnMsg().getDsz()) || Duration.between(ZDT0.on().dTSZ(req.gnnMsg().getDsz()).getZonedDateTime(), ZonedDateTime.now()).getSeconds() > channelEntity.getDszSeconds())) {
          rtn = Resp.failed(req, ChannelEntities.ERR_CODE__INVALID_TIMESTAMP, req.gnnMsg().getDsz());
        } else {
          rtn = pjp.proceed();//must no exception below
          if (rtn instanceof Resp && !String0.isNullOrEmpty(req.gnnMsg().getDsz())) {
            ((Resp<?, ?>) rtn).getMsg().setDsz(ZDT0.on().dTSZ());
          }
        }
      } else {
        log.error(MF0.fmt("{0} - {1} : {2}", ZeroAnnotation.ERR_CODE__ANNOTATION_SETTING_ERROR, pjp.getSignature().toLongString(), OM3.writeValueAsString(rrDsz)));
        rtn = pjp.proceed();
      }
    } else {
      rtn = pjp.proceed();
    }
    return rtn;
  }

  @Pointcut("execution(@org.shaneking.roc.rr.annotation.RrDsz * *..*.*(..))")
  private void pointcut() {
  }
}
