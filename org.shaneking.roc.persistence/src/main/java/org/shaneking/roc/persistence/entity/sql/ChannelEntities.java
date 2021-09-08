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
  String TOKEN_VALUE_TYPE__SELF = "SELF";
  @Transient
  String TOKEN_VALUE_TYPE__PROP = "PROP";

  String getDescription();

  <T extends ChannelEntities> T setDescription(String description);

  String getEncTv();

  <T extends ChannelEntities> T setEncTv(String encTv);

  String getEncTf();

  <T extends ChannelEntities> T setEncTf(String encTf);

  String getEncTat();

  <T extends ChannelEntities> T setEncTat(String encTat);

  String getEncTvt();

  <T extends ChannelEntities> T setEncTvt(String encTvt);

  Integer getDszSeconds();

  <T extends ChannelEntities> T setDszSeconds(Integer dszForce);

  String getMvcTv();

  <T extends ChannelEntities> T setMvcTv(String mvcTv);

  String getMvcTf();

  <T extends ChannelEntities> T setMvcTf(String mvcTf);

  String getMvcTat();

  <T extends ChannelEntities> T setMvcTat(String mvcTat);

  String getMvcTvt();

  <T extends ChannelEntities> T setMvcTvt(String mvcTvt);

  //@see sktest.roc.rr.cfg.RrCfg.simpleChannelEntity
  <T extends ChannelEntities> Class<T> entityClass();
}
