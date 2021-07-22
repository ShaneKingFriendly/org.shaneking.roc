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
import org.shaneking.roc.persistence.entity.TenantedChannelizedEntity;
import org.shaneking.roc.persistence.entity.sql.ApiAccessOpEntities;
import org.shaneking.roc.persistence.entity.sql.ApiAccessRegexEntities;
import org.shaneking.roc.persistence.entity.sql.ApiAccessSignatureEntities;
import org.shaneking.roc.persistence.entity.sql.ApiAccessUrlEntities;
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
  private ApiAccessRegexEntities apiAccessRegexEntityClass;
  @Autowired(required = false)
  private ApiAccessUrlEntities apiAccessUrlEntityClass;
  @Autowired(required = false)
  private ApiAccessSignatureEntities apiAccessSignatureEntityClass;

  @Pointcut("execution(@org.shaneking.roc.rr.annotation.RrAccess * *..*.*(..))")
  private void pointcut() {
  }

  @Around("pointcut() && @annotation(rrAccess)")
  public Object around(ProceedingJoinPoint pjp, RrAccess rrAccess) throws Throwable {
    Object rtn = null;
    boolean ifExceptionThenInProceed = false;
    if (enabled && (apiAccessRegexEntityClass != null || apiAccessUrlEntityClass != null || apiAccessSignatureEntityClass != null)) {
      if (pjp.getArgs().length > rrAccess.reqParamIdx() && pjp.getArgs()[rrAccess.reqParamIdx()] instanceof Req) {
        Req<?, ?> req = (Req<?, ?>) pjp.getArgs()[rrAccess.reqParamIdx()];
        if (String0.isNullOrEmpty(req.gnnCtx().gnaChannelId()) || String0.isNullOrEmpty(req.gnnCtx().gnaTenantId())) {
          rtn = Resp.failed(TenantedChannelizedEntity.ERR_CODE__REQUIRED_CHANNEL_ID_AND_TENANT_ID, OM3.writeValueAsString(req.getPub()), req);
        } else {
          try {
            int regexPaas = 0;
            int urlPaas = 0;
            int signaturePaas = 0;
            ApiAccessRegexEntities apiAccessRegexEntity = null;
            ApiAccessUrlEntities apiAccessUrlEntity = null;
            ApiAccessSignatureEntities apiAccessSignatureEntity = null;
            if (apiAccessRegexEntityClass != null) {
              ApiAccessRegexEntities apiAccessRegexEntitySelect = apiAccessRegexEntityClass.entityClass().newInstance();
              apiAccessRegexEntitySelect.setChannelId(req.gnnCtx().gnaChannelId());
              apiAccessRegexEntitySelect.setTenantId(req.gnnCtx().gnaTenantId());
              apiAccessRegexEntity = cacheableDao.one(apiAccessRegexEntityClass.entityClass(), apiAccessRegexEntitySelect, true);
              regexPaas = apiAccessRegexEntity == null ? 0 : (apiAccessRegexEntity.check(req.gnnCtx().getAuditLog() == null ? null : req.gnnCtx().getAuditLog().getReqUrl(), pjp.getSignature().toLongString()) ? 1 : -1);
            }
            if (apiAccessUrlEntityClass != null && req.gnnCtx().getAuditLog() != null && !String0.isNullOrEmpty(req.getCtx().getAuditLog().getReqUrl())) {
              ApiAccessUrlEntities apiAccess4EntitySelect = apiAccessUrlEntityClass.entityClass().newInstance();
              apiAccess4EntitySelect.setChannelId(req.gnnCtx().gnaChannelId());
              apiAccess4EntitySelect.setTenantId(req.gnnCtx().gnaTenantId());
              apiAccess4EntitySelect.setUrl(req.getCtx().getAuditLog().getReqUrl());
              apiAccessUrlEntity = cacheableDao.one(apiAccessUrlEntityClass.entityClass(), apiAccess4EntitySelect, true);
              urlPaas = urlPaas + (apiAccessUrlEntity == null ? 0 : (ApiAccessOpEntities.OP__ALLOW.equals(apiAccessUrlEntity.getOp()) ? 2 : (ApiAccessOpEntities.OP__DENY.equals(apiAccessUrlEntity.getOp()) ? -2 : 0)));
            }
            if (apiAccessSignatureEntityClass != null) {
              ApiAccessSignatureEntities apiAccess5EntitySelect = apiAccessSignatureEntityClass.entityClass().newInstance();
              apiAccess5EntitySelect.setChannelId(req.gnnCtx().gnaChannelId());
              apiAccess5EntitySelect.setTenantId(req.gnnCtx().gnaTenantId());
              apiAccess5EntitySelect.setSignature(pjp.getSignature().toLongString());
              apiAccessSignatureEntity = cacheableDao.one(apiAccessSignatureEntityClass.entityClass(), apiAccess5EntitySelect, true);
              signaturePaas = signaturePaas + (apiAccessSignatureEntity == null ? 0 : (ApiAccessOpEntities.OP__ALLOW.equals(apiAccessSignatureEntity.getOp()) ? 2 : (ApiAccessOpEntities.OP__DENY.equals(apiAccessSignatureEntity.getOp()) ? -2 : 0)));
            }
            if (regexPaas + urlPaas + signaturePaas > 0) {
              ifExceptionThenInProceed = true;
              rtn = pjp.proceed();
            } else {
              rtn = Resp.failed(ApiAccessOpEntities.ERR_CODE__PERMISSION_DENIED, OM3.p(apiAccessRegexEntity, apiAccessUrlEntity, apiAccessSignatureEntity), req);
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
