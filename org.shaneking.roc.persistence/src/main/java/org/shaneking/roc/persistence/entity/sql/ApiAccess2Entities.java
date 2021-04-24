package org.shaneking.roc.persistence.entity.sql;

import org.shaneking.roc.persistence.entity.TenantedChannelizedEntities;

import javax.persistence.Transient;

public interface ApiAccess2Entities extends TenantedChannelizedEntities {
  @Transient
  String COLUMN__ALLOW_URL = "allow_url";
  @Transient
  String FIELD__ALLOW_URL = "allowUrl";
  @Transient
  String COLUMN__DENY_URL = "deny_url";
  @Transient
  String FIELD__DENY_URL = "denyUrl";

  String getAllowUrl();

  <T extends ApiAccess2Entities> T setAllowUrl(String allowUrl);

  String getDenyUrl();

  <T extends ApiAccess2Entities> T setDenyUrl(String denyUrl);

  //@see sktest.roc.rr.cfg.RrCfg.helloApiAccess2Entity
  <T extends ApiAccess2Entities> Class<T> entityClass();
}
