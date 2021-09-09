package org.shaneking.roc.persistence.entity.sql;

import org.shaneking.ling.persistence.entity.sql.Named;
import org.shaneking.roc.persistence.CacheableEntities;

import javax.persistence.Transient;

public interface ChannelEntities extends CacheableEntities, Named {
  @Transient
  String ERR_CODE__ALGORITHM_UNSUPPORTED = "CHANNEL_ENTITIES__ALGORITHM_UNSUPPORTED";
  @Transient
  String ERR_CODE__BAD_REQUEST = "CHANNEL_ENTITIES__BAD_REQUEST";
  @Transient
  String ERR_CODE__NEED_ENCODING = "CHANNEL_ENTITIES__NEED_ENCODING";
  @Transient
  String ERR_CODE__NEED_MVC = "CHANNEL_ENTITIES__NEED_MVC";
  @Transient
  String ERR_CODE__INVALID_TIMESTAMP = "CHANNEL_ENTITIES__INVALID_TIMESTAMP";

  @Transient
  String TOKEN_VALUE_TYPE__PROP = "PROP";
  @Transient
  String TOKEN_VALUE_TYPE__SELF = "SELF";

  //@see sktest.roc.rr.cfg.RrCfg.simpleChannelEntity
  <T extends ChannelEntities> Class<T> entityClass();

  String getDescription();

  Integer getDszSeconds();

  <T extends ChannelEntities> T setDszSeconds(Integer dszForce);

  String getEncTf();

  String getEncTat();

  String getEncTvt();

  <T extends ChannelEntities> T setEncTat(String encTat);

  String getEncTv();

  String getMvcTv();

  String getMvcTvt();

  <T extends ChannelEntities> T setEncTv(String encTv);

  String getMvcTat();

  <T extends ChannelEntities> T setMvcTat(String mvcTat);

  String getMvcTf();

  <T extends ChannelEntities> T setMvcTf(String mvcTf);

  <T extends ChannelEntities> T setDescription(String description);

  <T extends ChannelEntities> T setEncTf(String encTf);

  <T extends ChannelEntities> T setEncTvt(String encTvt);

  <T extends ChannelEntities> T setMvcTv(String mvcTv);

  <T extends ChannelEntities> T setMvcTvt(String mvcTvt);
}
