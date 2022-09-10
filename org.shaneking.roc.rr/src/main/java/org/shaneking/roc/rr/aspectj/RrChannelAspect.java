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
import org.shaneking.roc.persistence.dao.CacheableDao;
import org.shaneking.roc.persistence.entity.ApiAccessOpEntities;
import org.shaneking.roc.persistence.entity.sql.ChannelAccessRegexApiEntities;
import org.shaneking.roc.persistence.entity.sql.ChannelAccessSignatureApiEntities;
import org.shaneking.roc.persistence.entity.sql.ChannelAccessUrlApiEntities;
import org.shaneking.roc.rr.annotation.RrChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@ConditionalOnProperty(prefix = "sk.roc.rr.channel", value = "enabled", matchIfMissing = true)
@Slf4j
@Order(RrChannelAspect.ORDER)
public class RrChannelAspect {
  public static final int ORDER = 30000;
  @Value("${sk.roc.rr.channel.enabled:true}")
  private boolean enabled;
  @Autowired
  private CacheableDao cacheableDao;
  @Autowired(required = false)
  private ChannelEntities channelEntityClass;
  @Autowired(required = false)
  private ChannelAccessRegexApiEntities channelAccessRegexApiEntityClass;
  @Autowired(required = false)
  private ChannelAccessSignatureApiEntities channelAccessSignatureApiEntityClass;
  @Autowired(required = false)
  private ChannelAccessUrlApiEntities channelAccessUrlApiEntityClass;
  @Autowired
  private RrCtxHelper rrCtxHelper;

  @Around("pointcut() && @annotation(rrChannel)")
  public Object around(ProceedingJoinPoint pjp, RrChannel rrChannel) throws Throwable {
    Object rtn = null;
    if (enabled && channelEntityClass != null
      && (channelAccessRegexApiEntityClass != null || channelAccessUrlApiEntityClass != null || channelAccessSignatureApiEntityClass != null)) {
      if (pjp.getArgs().length > rrChannel.reqParamIdx() && pjp.getArgs()[rrChannel.reqParamIdx()] instanceof Req) {
        Req<?> req = (Req<?>) pjp.getArgs()[rrChannel.reqParamIdx()];
        boolean ifExceptionThenInProceed = false;
        try {
          rtn = rrCtxHelper.ctxChannel(req, channelEntityClass);
          if (rtn == null) {
            ChannelEntities channelEntity = req.gnnCtx().getChannel();
            req.getCtx().getAuditLog().setChannelId(channelEntity.getId());
            int regexPaas = 0;
            int urlPaas = 0;
            int signaturePaas = 0;
            ChannelAccessRegexApiEntities channelAccessRegexApiEntity = null;
            ChannelAccessUrlApiEntities channelAccessUrlApiEntity = null;
            ChannelAccessSignatureApiEntities channelAccessSignatureApiEntity = null;
            if (channelAccessRegexApiEntityClass != null) {
              ChannelAccessRegexApiEntities channelAccessRegexApiEntitySelect = channelAccessRegexApiEntityClass.entityClass().newInstance();
              channelAccessRegexApiEntitySelect.setChannelId(req.gnnCtx().gnaChannelId());
              channelAccessRegexApiEntity = cacheableDao.one(channelAccessRegexApiEntityClass.entityClass(), channelAccessRegexApiEntitySelect, true);
              regexPaas = channelAccessRegexApiEntity == null ? 0 : (channelAccessRegexApiEntity.check(req.gnnCtx().getAuditLog() == null ? null : req.gnnCtx().getAuditLog().getReqUrl(), pjp.getSignature().toLongString()) ? 1 : -1);
            }
            if (channelAccessUrlApiEntityClass != null && req.gnnCtx().getAuditLog() != null && !String0.isNullOrEmpty(req.gnnCtx().getAuditLog().getReqUrl())) {
              ChannelAccessUrlApiEntities channelAccessUrlApiEntitySelect = channelAccessUrlApiEntityClass.entityClass().newInstance();
              channelAccessUrlApiEntitySelect.setChannelId(req.gnnCtx().gnaChannelId());
              channelAccessUrlApiEntitySelect.setUrl(req.gnnCtx().getAuditLog().getReqUrl());
              channelAccessUrlApiEntity = cacheableDao.one(channelAccessUrlApiEntityClass.entityClass(), channelAccessUrlApiEntitySelect, true);
              urlPaas = urlPaas + (channelAccessUrlApiEntity == null ? 0 : (ApiAccessOpEntities.OP__ALLOW.equals(channelAccessUrlApiEntity.getOp()) ? 2 : (ApiAccessOpEntities.OP__DENY.equals(channelAccessUrlApiEntity.getOp()) ? -2 : 0)));
            }
            if (channelAccessSignatureApiEntityClass != null) {
              ChannelAccessSignatureApiEntities channelAccessSignatureApiEntitySelect = channelAccessSignatureApiEntityClass.entityClass().newInstance();
              channelAccessSignatureApiEntitySelect.setChannelId(req.gnnCtx().gnaChannelId());
              channelAccessSignatureApiEntitySelect.setSignature(pjp.getSignature().toLongString());
              channelAccessSignatureApiEntity = cacheableDao.one(channelAccessSignatureApiEntityClass.entityClass(), channelAccessSignatureApiEntitySelect, true);
              signaturePaas = signaturePaas + (channelAccessSignatureApiEntity == null ? 0 : (ApiAccessOpEntities.OP__ALLOW.equals(channelAccessSignatureApiEntity.getOp()) ? 2 : (ApiAccessOpEntities.OP__DENY.equals(channelAccessSignatureApiEntity.getOp()) ? -2 : 0)));
            }
            if (regexPaas + urlPaas + signaturePaas > 0) {//default close
              ifExceptionThenInProceed = true;
              rtn = pjp.proceed();
            } else {
              rtn = Resp.failed(req, ApiAccessOpEntities.ERR_CODE__PERMISSION_DENIED, OM3.p(channelAccessRegexApiEntity, channelAccessUrlApiEntity, channelAccessSignatureApiEntity));
            }
          }
        } catch (Throwable throwable) {
          log.error(OM3.writeValueAsString(req), throwable);
          if (ifExceptionThenInProceed) {
            throw throwable;//process error
          } else {
            rtn = Resp.failed(req, Resp.CODE_UNKNOWN_EXCEPTION, String.valueOf(throwable));
          }
        }
      } else {
        log.error(MF0.fmt("{0} - {1} : {2}", ZeroAnnotation.ERR_CODE__ANNOTATION_SETTING_ERROR, pjp.getSignature().toLongString(), OM3.writeValueAsString(rrChannel)));
        rtn = pjp.proceed();
      }
    } else {
      rtn = pjp.proceed();
    }
    return rtn;
  }

  @Pointcut("execution(@org.shaneking.roc.rr.annotation.RrChannel * *..*.*(..))")
  private void pointcut() {
  }
}
