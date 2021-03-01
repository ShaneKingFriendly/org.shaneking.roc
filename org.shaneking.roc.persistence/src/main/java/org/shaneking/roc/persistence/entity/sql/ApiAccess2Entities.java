package org.shaneking.roc.persistence.entity.sql;

import org.shaneking.roc.persistence.entity.ChannelizedEntities;

public interface ApiAccess2Entities extends ChannelizedEntities {

  String getAllowUrl();

  <T extends ApiAccess2Entities> T setAllowUrl(String allowUrl);

  String getDenyUrl();

  <T extends ApiAccess2Entities> T setDenyUrl(String denyUrl);

  //@see sktest.roc.rr.cfg.RrCfg.helloApiAccess2Entity
  <T extends ApiAccess2Entities> Class<T> entityClass();
}
