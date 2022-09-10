package org.shaneking.roc.rr.aspectj;

import com.fasterxml.jackson.databind.JavaType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.ling.persistence.entity.sql.ChannelEntities;
import org.shaneking.ling.rr.Req;
import org.shaneking.ling.rr.ReqMsg;
import org.shaneking.ling.rr.Resp;
import org.shaneking.ling.zero.annotation.ZeroAnnotation;
import org.shaneking.ling.zero.crypto.MD5a;
import org.shaneking.ling.zero.crypto.SKC1;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.ling.zero.text.MF0;
import org.shaneking.roc.jackson.JavaType3;
import org.shaneking.roc.rr.annotation.RrCrypto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Aspect
@Component
@ConditionalOnProperty(prefix = "sk.roc.rr.crypto", value = "enabled", matchIfMissing = true)
@Slf4j
@Order(RrCryptoAspect.ORDER)
public class RrCryptoAspect {
  public static final int ORDER = 36000;
  @Value("${sk.roc.rr.crypto.enabled:true}")
  private boolean enabled;
  @Autowired
  private Environment environment;

  @Around("pointcut() && @annotation(rrCrypto)")
  public Object around(ProceedingJoinPoint pjp, RrCrypto rrCrypto) throws Throwable {
    Object rtn = null;
    if (enabled) {
      if (pjp.getArgs().length > rrCrypto.reqParamIdx() && pjp.getArgs()[rrCrypto.reqParamIdx()] instanceof Req) {
        Req<?> req = (Req<?>) pjp.getArgs()[rrCrypto.reqParamIdx()];
        boolean ifExceptionThenInProceed = false;
        try {
          boolean decoded = false;
          boolean verified = false;
          ChannelEntities channelEntity = req.gnnCtx().getChannel();
          //first for resp encode
          String token = channelEntity.getEncTv();
          if (!String0.isNullOrEmpty(token) && ChannelEntities.TOKEN_VALUE_TYPE__PROP.equalsIgnoreCase(channelEntity.getEncTvt())) {
            token = environment.getProperty(token, token);
          }

          if (String0.isNullOrEmpty(req.getEnc())) {
            if (String0.Y.equalsIgnoreCase(channelEntity.getEncTf())) {
              rtn = Resp.failed(req, ChannelEntities.ERR_CODE__NEED_ENCODING, channelEntity.getNo());
            }
          } else {
            //has enc
            String enc = req.getEnc();
            if ((String0.Y.equalsIgnoreCase(channelEntity.getMvcTf()) && String0.isNullOrEmpty(req.getMvc()))) {
              rtn = Resp.failed(req, ChannelEntities.ERR_CODE__NEED_MVC, channelEntity.getNo());
            } else if (!String0.isNullOrEmpty(req.getMvc())) {
              if (MD5a.ALGORITHM_NAME.equalsIgnoreCase(channelEntity.getMvcTat())) {
                if (!req.getMvc().equalsIgnoreCase(MD5a.encrypt(enc))) {
                  rtn = Resp.failed(req, ChannelEntities.ERR_CODE__BAD_REQUEST_WITH_TAMPERED, OM3.lp(req.getMvc(), enc));
                } else {
                  verified = true;
                }
              } else {
                rtn = Resp.failed(req, ChannelEntities.ERR_CODE__ALGORITHM_UNSUPPORTED, channelEntity.getMvcTat());
              }
            }
            //enc verified (maybe not need to verify)

            if (rtn == null) {
              JavaType[] javaTypes = JavaType3.resolveArgJavaTypes(pjp, rrCrypto.reqParamIdx());
              if (SKC1.ALGORITHM_NAME.equalsIgnoreCase(channelEntity.getEncTat())) {
                enc = SKC1.decrypt(enc, token);
                decoded = true;
              } else {
                rtn = Resp.failed(req, ChannelEntities.ERR_CODE__ALGORITHM_UNSUPPORTED, channelEntity.getEncTat());
              }
              req.setMsg(OM3.readValue(enc, OM3.om().getTypeFactory().constructParametricType(ReqMsg.class, javaTypes))).setEnc(null);
              if (req.gnnCtx().getAuditLog() != null) {
                req.gnnCtx().getAuditLog().setReqJsonStr(req.jsonStr());
              }
            }
            //enc decoded
          }

          if (rtn == null) {
            if (req.getMsg() == null || OM3.OBJECT_ERROR_STRING.equals(OM3.writeValueAsString(req.getMsg()))) {//can't use gnnMsg
              rtn = Resp.failed(req, ChannelEntities.ERR_CODE__BAD_REQUEST_WITH_MESSAGE);
            } else {
              ifExceptionThenInProceed = true;
              rtn = pjp.proceed();
              if (rtn instanceof Resp && decoded) {
                String enc = OM3.writeValueAsString(((Resp<?, ?>) rtn).getMsg());
                if (SKC1.ALGORITHM_NAME.equalsIgnoreCase(channelEntity.getEncTat())) {
                  enc = SKC1.encrypt(enc, token);
                }
                if (req.gnnCtx().getAuditLog() != null) {
                  req.gnnCtx().getAuditLog().setRespJsonStr(((Resp<?, ?>) rtn).jsonStr());
                }
                ((Resp<?, ?>) rtn).setEnc(enc).setMsg(null);
                if (verified) {
                  ((Resp<?, ?>) rtn).setMvc(MD5a.encrypt(enc));
                }
              }
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
        log.error(MF0.fmt("{0} - {1} : {2}", ZeroAnnotation.ERR_CODE__ANNOTATION_SETTING_ERROR, pjp.getSignature().toLongString(), OM3.writeValueAsString(rrCrypto)));
        rtn = pjp.proceed();
      }
    } else {
      rtn = pjp.proceed();
    }
    return rtn;
  }

  @Pointcut("execution(@org.shaneking.roc.rr.annotation.RrCrypto * *..*.*(..))")
  private void pointcut() {
  }
}
