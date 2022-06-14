package org.shaneking.roc.rr;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.ling.rr.ReqMsg;
import org.shaneking.ling.rr.Resp;
import org.shaneking.ling.rr.RespMsg;
import org.shaneking.ling.zero.crypto.SKC1;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.roc.persistence.entity.sql.ChannelEntities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RrCryptoHelper {
  @Autowired
  private Environment environment;

  public <I, O> void rrCrypto(Req<I> req, String tokenValue, String tokenAlgorithmType, String tokenValueType, TypeReference<ReqMsg<I>> reqMsgTypeReference, Resp<O, Req<I>> resp, TypeReference<RespMsg<O>> respMsgTypeReference) {
    String token = tokenValue;
    if (!String0.isNullOrEmpty(token) && ChannelEntities.TOKEN_VALUE_TYPE__PROP.equalsIgnoreCase(tokenValueType)) {
      token = environment.getProperty(token, token);
    }
    if (String0.isNullOrEmpty(token)) {
      throw new IllegalArgumentException(OM3.lp(token, tokenValueType, tokenValue));
    } else {
      log.info(OM3.writeValueAsString(req));
      if (req != null && !String0.isNullOrEmpty(req.getEnc()) && reqMsgTypeReference != null) {
        if (SKC1.ALGORITHM_NAME.equalsIgnoreCase(tokenAlgorithmType)) {
          req.setMsg(OM3.readValue(SKC1.decrypt(req.getEnc(), token), reqMsgTypeReference)).setEnc(null);
        } else {
          throw new UnsupportedOperationException(OM3.lp(tokenAlgorithmType, req, tokenValue, tokenValueType));
        }
      } else if (req != null && req.getMsg() != null && reqMsgTypeReference == null) {
        if (SKC1.ALGORITHM_NAME.equalsIgnoreCase(tokenAlgorithmType)) {
          req.setEnc(SKC1.encrypt(OM3.writeValueAsString(req.getMsg()), token)).setMsg(null);
        } else {
          throw new UnsupportedOperationException(OM3.lp(tokenAlgorithmType, req, tokenValue, tokenValueType));
        }
      } else if (resp != null && !String0.isNullOrEmpty(resp.getEnc()) && reqMsgTypeReference != null) {
        if (SKC1.ALGORITHM_NAME.equalsIgnoreCase(tokenAlgorithmType)) {
          resp.setMsg(OM3.readValue(SKC1.decrypt(resp.getEnc(), token), respMsgTypeReference)).setEnc(null);
        } else {
          throw new UnsupportedOperationException(OM3.lp(tokenAlgorithmType, resp, tokenValue, tokenValueType));
        }
      } else if (resp != null && resp.getMsg() != null && respMsgTypeReference == null) {
        if (SKC1.ALGORITHM_NAME.equalsIgnoreCase(tokenAlgorithmType)) {
          resp.setEnc(SKC1.encrypt(OM3.writeValueAsString(resp.getMsg()), token)).setMsg(null);
        } else {
          throw new UnsupportedOperationException(OM3.lp(tokenAlgorithmType, resp, tokenValue, tokenValueType));
        }
      } else {
        throw new IllegalArgumentException(OM3.lp(token, req, resp));
      }
      log.info(OM3.writeValueAsString(req));
    }
  }

  public <I> Req<I> decryptReq(@NonNull Req<I> req, String tokenValue, String tokenAlgorithmType, String tokenValueType, TypeReference<ReqMsg<I>> typeReference) {
    rrCrypto(req, tokenValue, tokenAlgorithmType, tokenValueType, typeReference, null, null);
    return req;
  }

  public <O, I> Resp<O, Req<I>> decryptResp(@NonNull Resp<O, Req<I>> resp, String tokenValue, String tokenAlgorithmType, String tokenValueType, TypeReference<RespMsg<O>> typeReference) {
    rrCrypto(null, tokenValue, tokenAlgorithmType, tokenValueType, null, resp, typeReference);
    return resp;
  }

  public <I> Req<I> encryptReq(@NonNull Req<I> req, String tokenValue, String tokenAlgorithmType, String tokenValueType) {
    rrCrypto(req, tokenValue, tokenAlgorithmType, tokenValueType, null, null, null);
    return req;
  }

  public <O, I> Resp<O, Req<I>> encryptResp(@NonNull Resp<O, Req<I>> resp, String tokenValue, String tokenAlgorithmType, String tokenValueType) {
    rrCrypto(null, tokenValue, tokenAlgorithmType, tokenValueType, null, resp, null);
    return resp;
  }
}
