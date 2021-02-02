package org.shaneking.roc.rr;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.ling.rr.Resp;
import org.shaneking.ling.zero.crypto.Crypto0;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.roc.persistence.entity.ChannelEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RrCryptoHelper {
  @Autowired
  private Environment environment;

  public <O, R> Req<O, R> encrypt(@NonNull Req<O, R> req, String tokenValue, String tokenForce, String tokenAlgorithmType, String tokenValueType) {
    String token = tokenValue;
    if (!String0.isNullOrEmpty(token) && ChannelEntity.TOKEN_VALUE_TYPE__PROP.equalsIgnoreCase(tokenValueType)) {
      token = environment.getProperty(token, token);
    }
    if (String0.isNullOrEmpty(req.getEnc())) {
      if (String0.isNullOrEmpty(tokenValue) && String0.Y.equalsIgnoreCase(tokenForce)) {
        throw new IllegalArgumentException(OM3.p(req, req, tokenValue, tokenForce, tokenAlgorithmType, tokenValueType));
      }
      log.info(OM3.writeValueAsString(req));
      if (Crypto0.ALGORITHM_NAME__AES.equalsIgnoreCase(tokenAlgorithmType)) {
        req.setEnc(Crypto0.aesEncrypt(OM3.writeValueAsString(req.getPri()), token)).setPri(null).getPub().setEncoded(String0.Y);
      } else if (String0.Y.equalsIgnoreCase(tokenForce)) {
        throw new IllegalArgumentException(OM3.p(req, tokenValue, tokenForce, tokenAlgorithmType, tokenValueType));
      }
      log.info(OM3.writeValueAsString(req));
    }

    return req;
  }

  public <O, R> Resp<Req<O, R>> decrypt(@NonNull Resp<Req<O, R>> resp, String tokenValue, String tokenAlgorithmType, String tokenValueType, TypeReference<Pri<O, R>> typeReference) {
    decrypt(resp.getData(), tokenValue, tokenAlgorithmType, tokenValueType, typeReference);
    return resp;
  }

  public <O, R> Req<O, R> decrypt(@NonNull Req<O, R> respData, String tokenValue, String tokenAlgorithmType, String tokenValueType, TypeReference<Pri<O, R>> typeReference) {
    String token = tokenValue;
    if (!String0.isNullOrEmpty(token) && ChannelEntity.TOKEN_VALUE_TYPE__PROP.equalsIgnoreCase(tokenValueType)) {
      token = environment.getProperty(token, token);
    }
    if (respData.getPri() == null) {
      if (String0.isNullOrEmpty(tokenValue) && String0.Y.equalsIgnoreCase(respData.getPub().getEncoded())) {
        throw new IllegalArgumentException(OM3.p(respData, tokenValue, tokenAlgorithmType, tokenValueType));
      }
      log.info(OM3.writeValueAsString(respData));
      if (Crypto0.ALGORITHM_NAME__AES.equalsIgnoreCase(tokenAlgorithmType)) {
        respData.setPri(OM3.readValue(Crypto0.aesDecrypt(respData.getEnc(), token), typeReference)).setEnc(null);
      } else if (String0.Y.equalsIgnoreCase(respData.getPub().getEncoded())) {
        throw new IllegalArgumentException(OM3.p(respData, tokenValue, tokenAlgorithmType, tokenValueType));
      }
      log.info(OM3.writeValueAsString(respData));
    }
    return respData;
  }
}
