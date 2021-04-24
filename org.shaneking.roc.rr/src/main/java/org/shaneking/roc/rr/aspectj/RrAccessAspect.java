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
import org.shaneking.roc.persistence.dao.CacheableDao;
import org.shaneking.roc.persistence.entity.TenantedChannelizedEntities;
import org.shaneking.roc.persistence.entity.sql.ApiAccess2Entities;
import org.shaneking.roc.persistence.entity.sql.ApiAccess3Entities;
import org.shaneking.roc.persistence.entity.sql.ApiAccessEntities;
import org.shaneking.roc.rr.Req;
import org.shaneking.roc.rr.annotation.RrAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Aspect
@Component
@ConditionalOnProperty(prefix = "sk.roc.rr.access", value = "enabled")
@Slf4j
@Order(500)
public class RrAccessAspect {
  @Value("${sk.roc.rr.access.enabled:false}")
  private boolean enabled;

  @Autowired
  private CacheableDao cacheableDao;

  @Autowired(required = false)
  private ApiAccessEntities apiAccessEntityClass;
  @Autowired(required = false)
  private ApiAccess2Entities apiAccess2EntityClass;
  @Autowired(required = false)
  private ApiAccess3Entities apiAccess3EntityClass;

  @Pointcut("execution(@org.shaneking.roc.rr.annotation.RrAccess * *..*.*(..))")
  private void pointcut() {
  }

  @Around("pointcut() && @annotation(rrAccess)")
  public Object around(ProceedingJoinPoint pjp, RrAccess rrAccess) throws Throwable {
    Object rtn = null;
    boolean ifExceptionThenInProceed = false;
    if (enabled && (apiAccessEntityClass != null || apiAccess2EntityClass != null || apiAccess3EntityClass != null)) {
      if (pjp.getArgs().length > rrAccess.reqParamIdx() && pjp.getArgs()[rrAccess.reqParamIdx()] instanceof Req) {
        Req<?, ?> req = (Req<?, ?>) pjp.getArgs()[rrAccess.reqParamIdx()];
        if (String0.isNullOrEmpty(String0.nullOrEmptyTo(req.gnnCtx().gnaProxyChannelId(), req.gnnCtx().gnaChannelId())) || String0.isNullOrEmpty(req.gnnCtx().gnaTenantId())) {
          rtn = Resp.failed(TenantedChannelizedEntities.ERR_CODE__REQUIRED_CHANNEL_ID_AND_TENANT_ID, OM3.writeValueAsString(req.getPub()), req);
        } else {
          try {
            int paas1 = 0;
            int paas2 = 0;
            int paas3 = 0;
            ApiAccessEntities apiAccessEntity = null;
            ApiAccess2Entities apiAccess2EntityAllow = null;
            ApiAccess2Entities apiAccess2EntityDeny = null;
            ApiAccess3Entities apiAccess3EntityAllow = null;
            ApiAccess3Entities apiAccess3EntityDeny = null;
            if (apiAccessEntityClass != null) {
              ApiAccessEntities apiAccessEntitySelect = apiAccessEntityClass.entityClass().newInstance();
              apiAccessEntitySelect.setChannelId(String0.nullOrEmptyTo(req.gnnCtx().gnaProxyChannelId(), req.gnnCtx().gnaChannelId()));
              apiAccessEntitySelect.setTenantId(req.gnnCtx().gnaTenantId());
              apiAccessEntity = cacheableDao.one(apiAccessEntityClass.entityClass(), apiAccessEntitySelect, true);
              paas1 = apiAccessEntity == null ? 0 : (apiAccessEntity.check(req.gnnCtx().getAuditLog() == null ? null : req.gnnCtx().getAuditLog().getReqUrl(), pjp.getSignature().toLongString()) ? 1 : -10);
            }
            if (apiAccess2EntityClass != null && req.gnnCtx().getAuditLog() != null && !String0.isNullOrEmpty(req.getCtx().getAuditLog().getReqUrl())) {
              ApiAccess2Entities apiAccess2EntitySelectAllow = apiAccess2EntityClass.entityClass().newInstance();
              apiAccess2EntitySelectAllow.setChannelId(String0.nullOrEmptyTo(req.gnnCtx().gnaProxyChannelId(), req.gnnCtx().gnaChannelId()));
              apiAccess2EntitySelectAllow.setTenantId(req.gnnCtx().gnaTenantId());
              apiAccess2EntitySelectAllow.setAllowUrl(req.getCtx().getAuditLog().getReqUrl());
              apiAccess2EntityAllow = cacheableDao.one(apiAccess2EntityClass.entityClass(), apiAccess2EntitySelectAllow, true);
              paas2 = paas2 + (apiAccess2EntityAllow == null ? 0 : 1);

              ApiAccess2Entities apiAccess2EntitySelectDeny = apiAccess2EntityClass.entityClass().newInstance();
              apiAccess2EntitySelectDeny.setChannelId(String0.nullOrEmptyTo(req.gnnCtx().gnaProxyChannelId(), req.gnnCtx().gnaChannelId()));
              apiAccess2EntitySelectDeny.setTenantId(req.gnnCtx().gnaTenantId());
              apiAccess2EntitySelectDeny.setDenyUrl(req.getCtx().getAuditLog().getReqUrl());
              apiAccess2EntityDeny = cacheableDao.one(apiAccess2EntityClass.entityClass(), apiAccess2EntitySelectDeny, true);
              paas2 = paas2 + (apiAccess2EntityDeny == null ? 0 : -10);
            }
            if (apiAccess3EntityClass != null) {
              ApiAccess3Entities apiAccess3EntitySelectAllow = apiAccess3EntityClass.entityClass().newInstance();
              apiAccess3EntitySelectAllow.setChannelId(String0.nullOrEmptyTo(req.gnnCtx().gnaProxyChannelId(), req.gnnCtx().gnaChannelId()));
              apiAccess3EntitySelectAllow.setTenantId(req.gnnCtx().gnaTenantId());
              apiAccess3EntitySelectAllow.setAllowSignature(pjp.getSignature().toLongString());
              apiAccess3EntityAllow = cacheableDao.one(apiAccess3EntityClass.entityClass(), apiAccess3EntitySelectAllow, true);
              paas3 = paas3 + (apiAccess3EntityAllow == null ? 0 : 1);

              ApiAccess3Entities apiAccess3EntitySelectDeny = apiAccess3EntityClass.entityClass().newInstance();
              apiAccess3EntitySelectDeny.setChannelId(String0.nullOrEmptyTo(req.gnnCtx().gnaProxyChannelId(), req.gnnCtx().gnaChannelId()));
              apiAccess3EntitySelectDeny.setTenantId(req.gnnCtx().gnaTenantId());
              apiAccess3EntitySelectDeny.setDenySignature(pjp.getSignature().toLongString());
              apiAccess3EntityDeny = cacheableDao.one(apiAccess3EntityClass.entityClass(), apiAccess3EntitySelectDeny, true);
              paas3 = paas3 + (apiAccess3EntityDeny == null ? 0 : -10);
            }
            if (paas1 + paas2 + paas3 > 0) {
              ifExceptionThenInProceed = true;
              rtn = pjp.proceed();
            } else {
              rtn = Resp.failed(ApiAccessEntities.ERR_CODE__PERMISSION_DENIED, OM3.p(apiAccessEntity, apiAccess2EntityAllow, apiAccess2EntityDeny, apiAccess3EntityAllow, apiAccess3EntityDeny), req);
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
        log.error(MessageFormat.format("{0} - {1} : {2}", ZeroAnnotation.ERR_CODE__ANNOTATION_SETTING_ERROR, pjp.getSignature().toLongString(), OM3.writeValueAsString(rrAccess)));
        rtn = pjp.proceed();
      }
    } else {
      rtn = pjp.proceed();
    }
    return rtn;
  }
}
