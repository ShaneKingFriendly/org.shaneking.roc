package org.shaneking.roc.rr.aspectj;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.ling.persistence.entity.Numbered;
import org.shaneking.ling.persistence.entity.TenantedResourceAccessibleEntities;
import org.shaneking.ling.persistence.entity.sql.Tenanted;
import org.shaneking.ling.persistence.entity.sql.UserEntities;
import org.shaneking.ling.rr.Ctx;
import org.shaneking.ling.rr.Req;
import org.shaneking.ling.rr.Resp;
import org.shaneking.ling.zero.annotation.ZeroAnnotation;
import org.shaneking.ling.zero.text.MF0;
import org.shaneking.ling.zero.util.List0;
import org.shaneking.roc.persistence.dao.TenantedNumberedDao;
import org.shaneking.roc.rr.annotation.RrUser;
import org.shaneking.roc.rr.service.RrAutoCreateUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Aspect
@Component
@ConditionalOnProperty(prefix = "sk.roc.rr.user", value = "enabled", matchIfMissing = true)
@Slf4j
@Order(RrUserAspect.ORDER)
public class RrUserAspect {
  public static final int ORDER = 70000;
  @Value("${sk.roc.rr.user.enabled:true}")
  private boolean enabled;
  @Autowired
  private TenantedNumberedDao tenantedNumberedDao;
  @Autowired(required = false)
  private UserEntities userEntityClass;
  @Autowired
  private RrCtxHelper rrCtxHelper;
  @Autowired(required = false)
  private RrAutoCreateUserService autoCreateUserService;

  @Around("pointcut() && @annotation(rrUser)")
  public Object around(ProceedingJoinPoint pjp, RrUser rrUser) throws Throwable {
    Object rtn = null;
    if (enabled && userEntityClass != null) {
      if (pjp.getArgs().length > rrUser.reqParamIdx() && pjp.getArgs()[rrUser.reqParamIdx()] instanceof Req) {
        Req<?> req = (Req<?>) pjp.getArgs()[rrUser.reqParamIdx()];
        boolean ifExceptionThenInProceed = false;
        try {
          rtn = rrCtxHelper.ctxUser(req, userEntityClass);
          if (rtn != null && autoCreateUserService != null) {
            autoCreateUserService.createUser(req);
            rtn = rrCtxHelper.ctxUser(req, userEntityClass);
          }
          if (rtn == null) {
            UserEntities userEntity = req.getCtx().getUser();
            req.getCtx().getAuditLog().setLmUid(userEntity.getId());
            req.getCtx().getAuditLog().setLastModifyUser(userEntity);

            UserEntities reqUserEntity = tenantedNumberedDao.oneByNo(userEntityClass.entityClass(), req.gnaMsgUno(), req.gnnCtx().gnaTenantId(), true);
            if (reqUserEntity == null && autoCreateUserService != null) {
              reqUserEntity = autoCreateUserService.createReqUser(req);
            }
            if (reqUserEntity == null) {
              rtn = Resp.failed(req, Numbered.ERR_CODE__NOT_FOUND_BY_NUMBER, req.gnaMsgUno());
            } else {
              req.gnnCtx().getAuditLog().setReqUserId(reqUserEntity.getId());

              initReadableTenantUserCtx(req.gnnCtx());

              ifExceptionThenInProceed = true;
              rtn = pjp.proceed();
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
        log.error(MF0.fmt("{0} - {1} : {2}", ZeroAnnotation.ERR_CODE__ANNOTATION_SETTING_ERROR, pjp.getSignature().toLongString(), OM3.writeValueAsString(rrUser)));
        rtn = pjp.proceed();
      }
    } else {
      rtn = pjp.proceed();
    }
    return rtn;
  }

  private void initReadableTenantUserCtx(Ctx ctx) {
    try {
      UserEntities lstUser = userEntityClass.entityClass().newInstance();
      lstUser.setNo(ctx.gnaUserNo());
      lstUser.forceWhereCondition(Tenanted.FIELD__TENANT_ID).resetVal(TenantedResourceAccessibleEntities.calc(ctx.getTrtList(), userEntityClass.entityClass().getName(), List0.newArrayList(ctx.gnaTenantId())));
      ctx.getRtuMap().putAll(tenantedNumberedDao.getCacheableDao().lst(userEntityClass.entityClass(), lstUser).stream().collect(Collectors.toMap(UserEntities::getTenantId, u -> u)));
    } catch (Throwable throwable) {
      log.error(OM3.p(ctx), throwable);
    }
  }

  @Pointcut("execution(@org.shaneking.roc.rr.annotation.RrUser * *..*.*(..))")
  private void pointcut() {
  }
}
