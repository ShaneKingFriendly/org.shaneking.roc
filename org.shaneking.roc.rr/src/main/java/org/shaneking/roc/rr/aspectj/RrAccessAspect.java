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
import org.shaneking.roc.persistence.dao.CacheableDao;
import org.shaneking.roc.persistence.entity.ApiAccessOpEntities;
import org.shaneking.roc.persistence.entity.TenantedChannelizedEntity;
import org.shaneking.roc.persistence.entity.sql.*;
import org.shaneking.roc.rr.Req;
import org.shaneking.roc.rr.annotation.RrAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@ConditionalOnProperty(prefix = "sk.roc.rr.access", value = "enabled", matchIfMissing = true)
@Slf4j
@Order(RrAccessAspect.ORDER)
public class RrAccessAspect {
  public static final int ORDER = 50000;

  @Value("${sk.roc.rr.access.enabled:true}")
  private boolean enabled;

  @Autowired
  private CacheableDao cacheableDao;

  @Autowired(required = false)
  private ChannelAccessRegexApiEntities channelAccessRegexApiEntityClass;
  @Autowired(required = false)
  private ChannelAccessUrlApiEntities channelAccessUrlApiEntityClass;
  @Autowired(required = false)
  private ChannelAccessSignatureApiEntities channelAccessSignatureApiEntityClass;

  @Autowired(required = false)
  private ChannelAccessTenantRegexApiEntities channelAccessTenantRegexApiEntityClass;
  @Autowired(required = false)
  private ChannelAccessTenantUrlApiEntities channelAccessTenantUrlApiEntityClass;
  @Autowired(required = false)
  private ChannelAccessTenantSignatureApiEntities channelAccessTenantSignatureApiEntityClass;

  @Pointcut("execution(@org.shaneking.roc.rr.annotation.RrAccess * *..*.*(..))")
  private void pointcut() {
  }

  @Around("pointcut() && @annotation(rrAccess)")
  public Object around(ProceedingJoinPoint pjp, RrAccess rrAccess) throws Throwable {
    Object rtn = null;
    boolean ifExceptionThenInProceed = false;
    if (enabled && (channelAccessRegexApiEntityClass != null || channelAccessUrlApiEntityClass != null || channelAccessSignatureApiEntityClass != null
      || channelAccessTenantRegexApiEntityClass != null || channelAccessTenantUrlApiEntityClass != null || channelAccessTenantSignatureApiEntityClass != null)) {
      if (pjp.getArgs().length > rrAccess.reqParamIdx() && pjp.getArgs()[rrAccess.reqParamIdx()] instanceof Req) {
        Req<?, ?> req = (Req<?, ?>) pjp.getArgs()[rrAccess.reqParamIdx()];
        if (String0.isNullOrEmpty(req.gnnCtx().gnaChannelId()) || String0.isNullOrEmpty(req.gnnCtx().gnaTenantId())) {
          rtn = Resp.failed(TenantedChannelizedEntity.ERR_CODE__REQUIRED_CHANNEL_ID_AND_TENANT_ID, OM3.writeValueAsString(req.getPub()), req);
        } else {
          try {
            int regexPaas = 0;
            int urlPaas = 0;
            int signaturePaas = 0;
            ChannelAccessRegexApiEntities channelAccessRegexApiEntity = null;
            ChannelAccessUrlApiEntities channelAccessUrlApiEntity = null;
            ChannelAccessSignatureApiEntities channelAccessSignatureApiEntity = null;
            ChannelAccessTenantRegexApiEntities channelAccessTenantRegexApiEntity = null;
            ChannelAccessTenantUrlApiEntities channelAccessTenantUrlApiEntity = null;
            ChannelAccessTenantSignatureApiEntities channelAccessTenantSignatureApiEntity = null;
            if (channelAccessRegexApiEntityClass != null) {
              ChannelAccessRegexApiEntities channelAccessRegexApiEntitySelect = channelAccessRegexApiEntityClass.entityClass().newInstance();
              channelAccessRegexApiEntitySelect.setChannelId(req.gnnCtx().gnaChannelId());
              channelAccessRegexApiEntity = cacheableDao.one(channelAccessRegexApiEntityClass.entityClass(), channelAccessRegexApiEntitySelect, true);
              regexPaas = channelAccessRegexApiEntity == null ? 0 : (channelAccessRegexApiEntity.check(req.gnnCtx().getAuditLog() == null ? null : req.gnnCtx().getAuditLog().getReqUrl(), pjp.getSignature().toLongString()) ? 1 : -1);
            }
            if (channelAccessUrlApiEntityClass != null && req.gnnCtx().getAuditLog() != null && !String0.isNullOrEmpty(req.getCtx().getAuditLog().getReqUrl())) {
              ChannelAccessUrlApiEntities channelAccessUrlApiEntitySelect = channelAccessUrlApiEntityClass.entityClass().newInstance();
              channelAccessUrlApiEntitySelect.setChannelId(req.gnnCtx().gnaChannelId());
              channelAccessUrlApiEntitySelect.setUrl(req.getCtx().getAuditLog().getReqUrl());
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

            if (channelAccessTenantRegexApiEntityClass != null) {
              ChannelAccessTenantRegexApiEntities channelAccessTenantRegexApiEntitySelect = channelAccessTenantRegexApiEntityClass.entityClass().newInstance();
              channelAccessTenantRegexApiEntitySelect.setChannelId(req.gnnCtx().gnaChannelId());
              channelAccessTenantRegexApiEntitySelect.setTenantId(req.gnnCtx().gnaTenantId());
              channelAccessTenantRegexApiEntity = cacheableDao.one(channelAccessTenantRegexApiEntityClass.entityClass(), channelAccessTenantRegexApiEntitySelect, true);
              regexPaas = channelAccessTenantRegexApiEntity == null ? 0 : (channelAccessTenantRegexApiEntity.check(req.gnnCtx().getAuditLog() == null ? null : req.gnnCtx().getAuditLog().getReqUrl(), pjp.getSignature().toLongString()) ? 4 : -4);
            }
            if (channelAccessTenantUrlApiEntityClass != null && req.gnnCtx().getAuditLog() != null && !String0.isNullOrEmpty(req.getCtx().getAuditLog().getReqUrl())) {
              ChannelAccessTenantUrlApiEntities channelAccessTenantUrlApiEntitySelect = channelAccessTenantUrlApiEntityClass.entityClass().newInstance();
              channelAccessTenantUrlApiEntitySelect.setChannelId(req.gnnCtx().gnaChannelId());
              channelAccessTenantUrlApiEntitySelect.setTenantId(req.gnnCtx().gnaTenantId());
              channelAccessTenantUrlApiEntitySelect.setUrl(req.getCtx().getAuditLog().getReqUrl());
              channelAccessTenantUrlApiEntity = cacheableDao.one(channelAccessTenantUrlApiEntityClass.entityClass(), channelAccessTenantUrlApiEntitySelect, true);
              urlPaas = urlPaas + (channelAccessTenantUrlApiEntity == null ? 0 : (ApiAccessOpEntities.OP__ALLOW.equals(channelAccessTenantUrlApiEntity.getOp()) ? 8 : (ApiAccessOpEntities.OP__DENY.equals(channelAccessTenantUrlApiEntity.getOp()) ? -8 : 0)));
            }
            if (channelAccessTenantSignatureApiEntityClass != null) {
              ChannelAccessTenantSignatureApiEntities channelAccessTenantSignatureApiEntitySelect = channelAccessTenantSignatureApiEntityClass.entityClass().newInstance();
              channelAccessTenantSignatureApiEntitySelect.setChannelId(req.gnnCtx().gnaChannelId());
              channelAccessTenantSignatureApiEntitySelect.setTenantId(req.gnnCtx().gnaTenantId());
              channelAccessTenantSignatureApiEntitySelect.setSignature(pjp.getSignature().toLongString());
              channelAccessTenantSignatureApiEntity = cacheableDao.one(channelAccessTenantSignatureApiEntityClass.entityClass(), channelAccessTenantSignatureApiEntitySelect, true);
              signaturePaas = signaturePaas + (channelAccessTenantSignatureApiEntity == null ? 0 : (ApiAccessOpEntities.OP__ALLOW.equals(channelAccessTenantSignatureApiEntity.getOp()) ? 8 : (ApiAccessOpEntities.OP__DENY.equals(channelAccessTenantSignatureApiEntity.getOp()) ? -8 : 0)));
            }
            if (regexPaas + urlPaas + signaturePaas > 0) {
              ifExceptionThenInProceed = true;
              rtn = pjp.proceed();
            } else {
              rtn = Resp.failed(ApiAccessOpEntities.ERR_CODE__PERMISSION_DENIED, OM3.p(channelAccessTenantRegexApiEntity, channelAccessTenantUrlApiEntity, channelAccessTenantSignatureApiEntity), req);
            }
          } catch (Throwable throwable) {
            log.error(OM3.writeValueAsString(req), throwable);
            if (ifExceptionThenInProceed) {
              throw throwable;//process error
            } else {
              rtn = Resp.failed(Resp.CODE_UNKNOWN_EXCEPTION, String.valueOf(throwable), req);
              //rtn can reused
            }
          }
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
