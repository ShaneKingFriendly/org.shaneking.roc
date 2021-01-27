package org.shaneking.roc.rr.aspectj;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.ling.persistence.Named;
import org.shaneking.ling.rr.Resp;
import org.shaneking.ling.zero.annotation.ZeroAnnotation;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.roc.persistence.dao.CacheableDao;
import org.shaneking.roc.persistence.entity.ApiAccessEntity;
import org.shaneking.roc.persistence.entity.ChannelEntity;
import org.shaneking.roc.persistence.entity.TenantEntity;
import org.shaneking.roc.rr.Req;
import org.shaneking.roc.rr.ReqPub;
import org.shaneking.roc.rr.annotation.RrAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Aspect
@Component
@Slf4j
@Order(500)
public class RrAccessAspect {
  @Value("${sk.roc.rr.access.enabled:true}")
  private boolean enabled;

  @Autowired
  private CacheableDao cacheableDao;

  @Autowired
  private ApiAccessEntity apiAccessEntityClass;

  @Autowired
  private ChannelEntity channelEntityClass;

  @Autowired
  private TenantEntity tenantEntityClass;

  @Pointcut("execution(@org.shaneking.roc.rr.annotation.RrAccess * *..*.*(..))")
  private void pointcut() {
  }

  @Around("pointcut() && @annotation(rrAccess)")
  public Object around(ProceedingJoinPoint pjp, RrAccess rrAccess) throws Throwable {
    Object rtn = null;
    boolean proceedBefore = false;
    boolean proceedAfter = false;
    if (enabled) {
      if (pjp.getArgs().length > rrAccess.reqParamIdx() && pjp.getArgs()[rrAccess.reqParamIdx()] instanceof Req) {
        Req<?, ?> req = (Req<?, ?>) pjp.getArgs()[rrAccess.reqParamIdx()];
        if (req.getPub() == null || String0.isNullOrEmpty(req.getPub().getChannelName())) {
          rtn = Resp.failed(ReqPub.ERR_CODE__REQUIRED_CHANNEL_NAME, OM3.writeValueAsString(req.getPub()), req);
        } else {
          try {
            ChannelEntity channelEntity = cacheableDao.one(channelEntityClass.entityClass(), channelEntityClass.entityClass().newInstance().setName(req.getPub().getChannelName()), true);
            if (channelEntity == null) {
              rtn = Resp.failed(Named.ERR_CODE__NOT_FOUND_BY_NAME, req.getPub().getChannelName(), req);
            } else {
              req.getCtx().setChannel(channelEntity);
              if (req.getCtx().getAuditLog() != null) {
                req.getCtx().getAuditLog().setChannelId(channelEntity.getId());
              }

              TenantEntity tenantEntity = cacheableDao.one(tenantEntityClass.entityClass(), tenantEntityClass.entityClass().newInstance().setName(String0.nullOrEmptyTo(req.getPub().getTenantName(), req.getPub().getChannelName())), true);
              if (tenantEntity == null) {
                rtn = Resp.failed(Named.ERR_CODE__NOT_FOUND_BY_NAME, String.valueOf(req.getPub().getTenantName()), req);
              } else {
                req.getCtx().setTenant(tenantEntity);
                if (req.getCtx().getAuditLog() != null) {
                  req.getCtx().getAuditLog().setTenantId(tenantEntity.getId());
                }

                ApiAccessEntity apiAccessEntitySelect = apiAccessEntityClass.entityClass().newInstance();
                apiAccessEntitySelect.setChannelId(channelEntity.getId()).setTenantId(tenantEntity.getId());
                ApiAccessEntity apiAccessEntity = cacheableDao.one(apiAccessEntityClass.entityClass(), apiAccessEntitySelect, true);
                if (apiAccessEntity == null) {
                  rtn = Resp.failed(ApiAccessEntity.ERR_CODE__PERMISSION_DENIED, OM3.writeValueAsString(apiAccessEntitySelect), req);
                } else {
                  if (apiAccessEntity.check(req.getCtx().getAuditLog() == null ? null : req.getCtx().getAuditLog().getReqUrl(), pjp.getSignature().getName())) {
                    proceedBefore = true;
                    rtn = pjp.proceed();
                    proceedAfter = true;
                  } else {
                    rtn = Resp.failed(ApiAccessEntity.ERR_CODE__PERMISSION_DENIED, OM3.writeValueAsString(apiAccessEntity), req);
                  }
                }
              }
            }
          } catch (Throwable throwable) {
            log.error(OM3.writeValueAsString(req), throwable);
            if (proceedBefore && !proceedAfter) {
              throw throwable;//process error
            } else {
              if (!proceedAfter) {
                rtn = Resp.failed(Resp.CODE_UNKNOWN_EXCEPTION, String.valueOf(throwable), req);
              }
              //rtn can reused
            }
          }
        }
      } else {
        log.error(MessageFormat.format("{0} - {1} : {2}", ZeroAnnotation.ERR_CODE__ANNOTATION_SETTING_ERROR, pjp.getSignature().getName(), OM3.writeValueAsString(rrAccess)));
        rtn = pjp.proceed();
      }
    } else {
      rtn = pjp.proceed();
    }
    return rtn;
  }
}
