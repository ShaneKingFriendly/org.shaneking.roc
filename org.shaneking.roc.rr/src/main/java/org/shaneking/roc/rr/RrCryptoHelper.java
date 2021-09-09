package org.shaneking.roc.rr;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.ling.rr.Resp;
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

  public <O, R> Req<O, R> decrypt(@NonNull Req<O, R> respData, String tokenValue, String tokenAlgorithmType, String tokenValueType, TypeReference<Pri<O, R>> typeReference) {
    String token = tokenValue;
    if (!String0.isNullOrEmpty(token) && ChannelEntities.TOKEN_VALUE_TYPE__PROP.equalsIgnoreCase(tokenValueType)) {
      token = environment.getProperty(token, token);
    }
    if (respData.getPri() == null) {
      if (String0.isNullOrEmpty(tokenValue) && String0.Y.equalsIgnoreCase(respData.getPub().getEncoded())) {
        throw new IllegalArgumentException(OM3.p(respData, tokenValue, tokenAlgorithmType, tokenValueType));
      }
      log.info(OM3.writeValueAsString(respData));
      if (SKC1.ALGORITHM_NAME.equalsIgnoreCase(tokenAlgorithmType)) {
        respData.setPri(OM3.readValue(SKC1.decrypt(respData.getEnc(), token), typeReference)).setEnc(null);
      } else if (String0.Y.equalsIgnoreCase(respData.getPub().getEncoded())) {
        throw new IllegalArgumentException(OM3.p(respData, tokenValue, tokenAlgorithmType, tokenValueType));
      }
      log.info(OM3.writeValueAsString(respData));
    }
    return respData;
  }

  public <O, R> Resp<Req<O, R>> decrypt(@NonNull Resp<Req<O, R>> resp, String tokenValue, String tokenAlgorithmType, String tokenValueType, TypeReference<Pri<O, R>> typeReference) {
    decrypt(resp.getData(), tokenValue, tokenAlgorithmType, tokenValueType, typeReference);
    return resp;
  }

  public <O, R> Req<O, R> encrypt(@NonNull Req<O, R> req, String tokenValue, String tokenForce, String tokenAlgorithmType, String tokenValueType) {
    String token = tokenValue;
    if (!String0.isNullOrEmpty(token) && ChannelEntities.TOKEN_VALUE_TYPE__PROP.equalsIgnoreCase(tokenValueType)) {
      token = environment.getProperty(token, token);
    }
    if (String0.isNullOrEmpty(req.getEnc())) {
      if (String0.isNullOrEmpty(tokenValue) && String0.Y.equalsIgnoreCase(tokenForce)) {
        throw new IllegalArgumentException(OM3.p(req, req, tokenValue, tokenForce, tokenAlgorithmType, tokenValueType));
      }
      log.info(OM3.writeValueAsString(req));
      if (SKC1.ALGORITHM_NAME.equalsIgnoreCase(tokenAlgorithmType)) {
        req.setEnc(SKC1.encrypt(OM3.writeValueAsString(req.getPri()), token)).setPri(null).getPub().setEncoded(String0.Y);
      } else if (String0.Y.equalsIgnoreCase(tokenForce)) {
        throw new IllegalArgumentException(OM3.p(req, tokenValue, tokenForce, tokenAlgorithmType, tokenValueType));
      }
      log.info(OM3.writeValueAsString(req));
    }

    return req;
  }
}
