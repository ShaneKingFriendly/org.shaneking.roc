package org.shaneking.roc.persistence.entity.sql;

import org.shaneking.ling.persistence.entity.sql.Named;
import org.shaneking.roc.persistence.CacheableEntities;

import javax.persistence.Transient;

public interface ChannelEntities extends CacheableEntities, Named {
  @Transient
  String ERR_CODE__NEED_ENCODING = "CHANNEL_ENTITIES__NEED_ENCODING";
  @Transient
  String ERR_CODE__INVALID_TIMESTAMP = "CHANNEL_ENTITIES__INVALID_TIMESTAMP";

  @Transient
  String TOKEN_VALUE_TYPE__SELF = "SELF";
  @Transient
  String TOKEN_VALUE_TYPE__PROP = "PROP";

  String getDescription();

  <T extends ChannelEntities> T setDescription(String description);

  String getTokenValue();

  <T extends ChannelEntities> T setTokenValue(String tokenValue);

  String getTokenForce();

  <T extends ChannelEntities> T setTokenForce(String tokenForce);

  String getTokenAlgorithmType();

  <T extends ChannelEntities> T setTokenAlgorithmType(String tokenAlgorithmType);

  String getTokenValueType();

  <T extends ChannelEntities> T setTokenValueType(String tokenValueType);

  Integer getDszSeconds();

  <T extends ChannelEntities> T setDszSeconds(Integer dszForce);

  //@see sktest.roc.rr.cfg.RrCfg.helloChannelEntity
  <T extends ChannelEntities> Class<T> entityClass();
}
