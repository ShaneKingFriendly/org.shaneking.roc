package org.shaneking.roc.rr.aspectj;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.ling.persistence.entity.sql.TenantEntities;
import org.shaneking.ling.persistence.entity.sql.TenantReadTenantEntities;
import org.shaneking.ling.persistence.entity.sql.TenantUseTenantEntities;
import org.shaneking.ling.rr.Ctx;
import org.shaneking.ling.rr.Req;
import org.shaneking.ling.rr.Resp;
import org.shaneking.ling.zero.annotation.ZeroAnnotation;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.ling.zero.text.MF0;
import org.shaneking.roc.persistence.dao.CacheableDao;
import org.shaneking.roc.persistence.entity.ApiAccessOpEntities;
import org.shaneking.roc.persistence.entity.sql.ChannelAccessTenantRegexApiEntities;
import org.shaneking.roc.persistence.entity.sql.ChannelAccessTenantSignatureApiEntities;
import org.shaneking.roc.persistence.entity.sql.ChannelAccessTenantUrlApiEntities;
import org.shaneking.roc.rr.annotation.RrTenant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@ConditionalOnProperty(prefix = "sk.roc.rr.tenant", value = "enabled", matchIfMissing = true)
@Slf4j
@Order(RrTenantAspect.ORDER)
public class RrTenantAspect {
  public static final int ORDER = 50000;
  @Value("${sk.roc.rr.tenant.enabled:true}")
  private boolean enabled;
  @Autowired
  private CacheableDao cacheableDao;
  @Autowired(required = false)
  private TenantEntities tenantEntityClass;
  @Autowired(required = false)
  private ChannelAccessTenantRegexApiEntities channelAccessTenantRegexApiEntityClass;
  @Autowired(required = false)
  private ChannelAccessTenantSignatureApiEntities channelAccessTenantSignatureApiEntityClass;
  @Autowired(required = false)
  private ChannelAccessTenantUrlApiEntities channelAccessTenantUrlApiEntityClass;
  @Autowired
  private RrCtxHelper rrCtxHelper;
  @Autowired(required = false)
  private TenantReadTenantEntities tenantReadTenantEntities;
  @Autowired(required = false)
  private TenantUseTenantEntities tenantUseTenantEntities;

  @Around("pointcut() && @annotation(rrTenant)")
  public Object around(ProceedingJoinPoint pjp, RrTenant rrTenant) throws Throwable {
    Object rtn = null;
    if (enabled && tenantEntityClass != null
      && (channelAccessTenantRegexApiEntityClass != null || channelAccessTenantUrlApiEntityClass != null || channelAccessTenantSignatureApiEntityClass != null)) {
      if (pjp.getArgs().length > rrTenant.reqParamIdx() && pjp.getArgs()[rrTenant.reqParamIdx()] instanceof Req) {
        Req<?> req = (Req<?>) pjp.getArgs()[rrTenant.reqParamIdx()];
        boolean ifExceptionThenInProceed = false;
        rtn = rrCtxHelper.ctxTenant(req, tenantEntityClass);
        if (rtn == null) {
          TenantEntities tenantEntity = req.gnnCtx().getTenant();
          req.getCtx().getAuditLog().setTenantId(tenantEntity.getId());
          try {
            initAccessibleTenantCtx(req.gnnCtx());

            int regexPaas = 0;
            int urlPaas = 0;
            int signaturePaas = 0;
            ChannelAccessTenantRegexApiEntities channelAccessTenantRegexApiEntity = null;
            ChannelAccessTenantUrlApiEntities channelAccessTenantUrlApiEntity = null;
            ChannelAccessTenantSignatureApiEntities channelAccessTenantSignatureApiEntity = null;
            if (channelAccessTenantRegexApiEntityClass != null) {
              ChannelAccessTenantRegexApiEntities channelAccessTenantRegexApiEntitySelect = channelAccessTenantRegexApiEntityClass.entityClass().newInstance();
              channelAccessTenantRegexApiEntitySelect.setChannelId(req.gnnCtx().gnaChannelId());
              channelAccessTenantRegexApiEntitySelect.setTenantId(req.gnnCtx().gnaTenantId());
              channelAccessTenantRegexApiEntity = cacheableDao.one(channelAccessTenantRegexApiEntityClass.entityClass(), channelAccessTenantRegexApiEntitySelect, true);
              regexPaas = channelAccessTenantRegexApiEntity == null ? 0 : (channelAccessTenantRegexApiEntity.check(req.gnnCtx().getAuditLog() == null ? null : req.gnnCtx().getAuditLog().getReqUrl(), pjp.getSignature().toLongString()) ? 1 : -1);
            }
            if (channelAccessTenantUrlApiEntityClass != null && req.gnnCtx().getAuditLog() != null && !String0.isNullOrEmpty(req.gnnCtx().getAuditLog().getReqUrl())) {
              ChannelAccessTenantUrlApiEntities channelAccessTenantUrlApiEntitySelect = channelAccessTenantUrlApiEntityClass.entityClass().newInstance();
              channelAccessTenantUrlApiEntitySelect.setChannelId(req.gnnCtx().gnaChannelId());
              channelAccessTenantUrlApiEntitySelect.setTenantId(req.gnnCtx().gnaTenantId());
              channelAccessTenantUrlApiEntitySelect.setUrl(req.gnnCtx().getAuditLog().getReqUrl());
              channelAccessTenantUrlApiEntity = cacheableDao.one(channelAccessTenantUrlApiEntityClass.entityClass(), channelAccessTenantUrlApiEntitySelect, true);
              urlPaas = urlPaas + (channelAccessTenantUrlApiEntity == null ? 0 : (ApiAccessOpEntities.OP__ALLOW.equals(channelAccessTenantUrlApiEntity.getOp()) ? 2 : (ApiAccessOpEntities.OP__DENY.equals(channelAccessTenantUrlApiEntity.getOp()) ? -2 : 0)));
            }
            if (channelAccessTenantSignatureApiEntityClass != null) {
              ChannelAccessTenantSignatureApiEntities channelAccessTenantSignatureApiEntitySelect = channelAccessTenantSignatureApiEntityClass.entityClass().newInstance();
              channelAccessTenantSignatureApiEntitySelect.setChannelId(req.gnnCtx().gnaChannelId());
              channelAccessTenantSignatureApiEntitySelect.setTenantId(req.gnnCtx().gnaTenantId());
              channelAccessTenantSignatureApiEntitySelect.setSignature(pjp.getSignature().toLongString());
              channelAccessTenantSignatureApiEntity = cacheableDao.one(channelAccessTenantSignatureApiEntityClass.entityClass(), channelAccessTenantSignatureApiEntitySelect, true);
              signaturePaas = signaturePaas + (channelAccessTenantSignatureApiEntity == null ? 0 : (ApiAccessOpEntities.OP__ALLOW.equals(channelAccessTenantSignatureApiEntity.getOp()) ? 2 : (ApiAccessOpEntities.OP__DENY.equals(channelAccessTenantSignatureApiEntity.getOp()) ? -2 : 0)));
            }
            if (regexPaas + urlPaas + signaturePaas >= 0) {//default open
              ifExceptionThenInProceed = true;
              rtn = pjp.proceed();
            } else {
              rtn = Resp.failed(req, ApiAccessOpEntities.ERR_CODE__PERMISSION_DENIED, OM3.p(channelAccessTenantRegexApiEntity, channelAccessTenantUrlApiEntity, channelAccessTenantSignatureApiEntity));
            }
          } catch (Throwable throwable) {
            log.error(OM3.writeValueAsString(req), throwable);
            if (ifExceptionThenInProceed) {
              throw throwable;//process error
            } else {
              rtn = Resp.failed(req, Resp.CODE_UNKNOWN_EXCEPTION, String.valueOf(throwable));
            }
          }
        }
      } else {
        log.error(MF0.fmt("{0} - {1} : {2}", ZeroAnnotation.ERR_CODE__ANNOTATION_SETTING_ERROR, pjp.getSignature().toLongString(), OM3.writeValueAsString(rrTenant)));
        rtn = pjp.proceed();
      }
    } else {
      rtn = pjp.proceed();
    }
    return rtn;
  }

  private void initAccessibleTenantCtx(Ctx ctx) {
    try {
      if (tenantUseTenantEntities != null) {
        ctx.getTutList().addAll(cacheableDao.lst(tenantUseTenantEntities.entityClass(), tenantUseTenantEntities.entityClass().newInstance().setToTenantId(ctx.gnaTenantId())));
      }
      if (tenantReadTenantEntities != null) {
        ctx.getTrtList().addAll(cacheableDao.lst(tenantReadTenantEntities.entityClass(), tenantReadTenantEntities.entityClass().newInstance().setToTenantId(ctx.gnaTenantId())));
      }
    } catch (Throwable throwable) {
      log.error(OM3.p(ctx), throwable);
    }
  }

  @Pointcut("execution(@org.shaneking.roc.rr.annotation.RrTenant * *..*.*(..))")
  private void pointcut() {
  }
}
