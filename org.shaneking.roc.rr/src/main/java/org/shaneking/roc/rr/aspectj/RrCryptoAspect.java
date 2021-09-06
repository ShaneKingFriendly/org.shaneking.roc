package org.shaneking.roc.rr.aspectj;

import com.fasterxml.jackson.databind.JavaType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.ling.persistence.Entities;
import org.shaneking.ling.persistence.entity.Numbered;
import org.shaneking.ling.persistence.entity.sql.Tenanted;
import org.shaneking.ling.rr.Resp;
import org.shaneking.ling.zero.annotation.ZeroAnnotation;
import org.shaneking.ling.zero.crypto.SKC1;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.ling.zero.text.MF0;
import org.shaneking.ling.zero.util.List0;
import org.shaneking.roc.jackson.JavaType3;
import org.shaneking.roc.persistence.dao.NumberedDao;
import org.shaneking.roc.persistence.dao.TenantedNumberedDao;
import org.shaneking.roc.persistence.entity.TenantedResourceAccessibleEntities;
import org.shaneking.roc.persistence.entity.sql.*;
import org.shaneking.roc.rr.Ctx;
import org.shaneking.roc.rr.Pri;
import org.shaneking.roc.rr.Req;
import org.shaneking.roc.rr.annotation.RrCrypto;
import org.shaneking.roc.rr.service.RrAutoCreateUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Aspect
@Component
@ConditionalOnProperty(prefix = "sk.roc.rr.crypto", value = "enabled", matchIfMissing = true)
@Slf4j
@Order(RrCryptoAspect.ORDER)
public class RrCryptoAspect {
  public static final int ORDER = 40000;

  @Value("${sk.roc.rr.crypto.enabled:true}")
  private boolean enabled;

  @Autowired
  private Environment environment;

  @Autowired
  private NumberedDao numberedDao;
  @Autowired
  private TenantedNumberedDao tenantedNumberedDao;

  @Autowired(required = false)
  private TenantEntities tenantEntityClass;
  @Autowired(required = false)
  private UserEntities userEntityClass;

  @Autowired(required = false)
  private TenantReadTenantEntities tenantReadTenantEntities;
  @Autowired(required = false)
  private TenantUseTenantEntities tenantUseTenantEntities;
  @Autowired(required = false)
  private RrAutoCreateUserService autoCreateUserService;

  @Pointcut("execution(@org.shaneking.roc.rr.annotation.RrCrypto * *..*.*(..))")
  private void pointcut() {
  }

  @Around("pointcut() && @annotation(rrCrypto)")
  public Object around(ProceedingJoinPoint pjp, RrCrypto rrCrypto) throws Throwable {
    Object rtn = null;
    boolean proceedBefore = false;
    boolean proceedAfter = false;
    if (enabled && tenantEntityClass != null && userEntityClass != null) {
      if (pjp.getArgs().length > rrCrypto.reqParamIdx() && pjp.getArgs()[rrCrypto.reqParamIdx()] instanceof Req) {
        Req<?, ?> req = (Req<?, ?>) pjp.getArgs()[rrCrypto.reqParamIdx()];
        try {
          ChannelEntities channelEntity = req.gnnCtx().getChannel();
          if (String0.Y.equalsIgnoreCase(channelEntity.getTokenForce()) && (!String0.Y.equalsIgnoreCase(req.getPub().getEncoded()) || String0.isNullOrEmpty(req.getEnc()))) {
            rtn = Resp.failed(ChannelEntities.ERR_CODE__NEED_ENCODING, req.getPub().getEncoded(), req);
          } else {
            String token = channelEntity.getTokenValue();
            if (!String0.isNullOrEmpty(token) && ChannelEntities.TOKEN_VALUE_TYPE__PROP.equalsIgnoreCase(channelEntity.getTokenValueType())) {
              token = environment.getProperty(token, token);
            }

            if (String0.Y.equalsIgnoreCase(req.getPub().getEncoded()) && !String0.isNullOrEmpty(req.getEnc())) {
              String enc = req.getEnc();
              JavaType[] javaTypes = JavaType3.resolveArgJavaTypes(pjp, rrCrypto.reqParamIdx());
              if (SKC1.ALGORITHM_NAME.equalsIgnoreCase(channelEntity.getTokenAlgorithmType())) {
                enc = SKC1.decrypt(enc, token);
              }
              req.setPri(OM3.readValue(enc, OM3.om().getTypeFactory().constructParametricType(Pri.class, javaTypes))).setEnc(null);
            }

            TenantEntities tenantEntity = numberedDao.oneByNo(tenantEntityClass.entityClass(), String0.nullOrEmptyTo(req.getPri().getExt().getTenantNo(), req.getPub().getChannelNo()), true);
            if (tenantEntity == null) {
              rtn = Resp.failed(Numbered.ERR_CODE__NOT_FOUND_BY_NUMBER, String.valueOf(req.getPri().getExt().getTenantNo()), req);
            } else {
              req.gnnCtx().setTenant(tenantEntity);
              if (req.gnnCtx().getAuditLog() != null) {
                req.gnnCtx().getAuditLog().setTenantId(tenantEntity.getId());
              }
              initAccessibleTenantCtx(req.gnnCtx());

              UserEntities userEntity = tenantedNumberedDao.oneByNo(userEntityClass.entityClass(), req.getPri().gnnExt().getUserNo(), tenantEntity.getId(), true);
              if (userEntity == null && autoCreateUserService != null) {
                userEntity = autoCreateUserService.create(req);
              }
              if (userEntity == null) {
                rtn = Resp.failed(Entities.ERR_CODE__NOT_FOUND, req.getPri().gnnExt().getUserNo(), req);
              } else {
                req.gnnCtx().setUser(userEntity);
                if (req.gnnCtx().getAuditLog() != null) {
                  req.gnnCtx().getAuditLog().setReqUserId(userEntity.getId());
                }

                RrAuditLogEntities auditLogEntity = req.gnnCtx().getAuditLog();
                if (auditLogEntity != null) {
                  auditLogEntity.setReqJsonStr(OM3.writeValueAsString(req));
                }
                initReadableTenantUserCtx(req.gnnCtx());
                proceedBefore = true;
                rtn = pjp.proceed();
                proceedAfter = true;
                if (auditLogEntity != null) {
                  auditLogEntity.setRespJsonStr(OM3.writeValueAsString(rtn));
                }

                if (rtn instanceof Resp) {
                  Object respData = ((Resp<?>) rtn).getData();
                  if (respData instanceof Req) {
                    Req<?, ?> respReq = (Req<?, ?>) respData;

                    if (String0.Y.equalsIgnoreCase(respReq.getPub().getEncoded()) && respReq.getPri() != null) {
                      String enc = OM3.writeValueAsString(respReq.getPri());
                      if (SKC1.ALGORITHM_NAME.equalsIgnoreCase(channelEntity.getTokenAlgorithmType())) {
                        enc = SKC1.encrypt(enc, token);
                      }
                      respReq.setEnc(enc).setPri(null);
                    }
                  }
                }
              }
            }
          }
        } catch (Throwable throwable) {
          log.error(OM3.writeValueAsString(req), throwable);
          if (proceedBefore && !proceedAfter) {
            throw throwable;//process error
          } else {
            //data maybe corrupted
            rtn = Resp.failed(Resp.CODE_UNKNOWN_EXCEPTION, String.valueOf(throwable), req);
          }
        }
      } else {
        log.error(MF0.fmt("{0} - {1} : {2}", ZeroAnnotation.ERR_CODE__ANNOTATION_SETTING_ERROR, pjp.getSignature().toLongString(), OM3.writeValueAsString(rrCrypto)));
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
        ctx.getTutList().addAll(numberedDao.getCacheableDao().lst(tenantUseTenantEntities.entityClass(), tenantUseTenantEntities.entityClass().newInstance().setToTenantId(ctx.gnaTenantId())));
      }
      if (tenantReadTenantEntities != null) {
        ctx.getTrtList().addAll(numberedDao.getCacheableDao().lst(tenantReadTenantEntities.entityClass(), tenantReadTenantEntities.entityClass().newInstance().setToTenantId(ctx.gnaTenantId())));
      }
    } catch (Throwable throwable) {
      log.error(OM3.p(ctx), throwable);
    }
  }

  private void initReadableTenantUserCtx(Ctx ctx) {
    try {
      UserEntities lstUser = userEntityClass.entityClass().newInstance();
      lstUser.setNo(ctx.getUser().getNo());
      lstUser.forceWhereCondition(Tenanted.FIELD__TENANT_ID).resetVal(TenantedResourceAccessibleEntities.calc(ctx.getTrtList(), userEntityClass.entityClass().getName(), List0.newArrayList(ctx.getUser().getTenantId())));
      ctx.getRtuMap().putAll(numberedDao.getCacheableDao().lst(userEntityClass.entityClass(), lstUser).stream().collect(Collectors.toMap(UserEntities::getTenantId, u -> u)));
    } catch (Throwable throwable) {
      log.error(OM3.p(ctx), throwable);
    }
  }
}
