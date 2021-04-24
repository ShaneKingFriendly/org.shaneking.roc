package org.shaneking.roc.persistence.entity.sql;

import org.shaneking.roc.persistence.entity.TenantedChannelizedEntities;

import javax.persistence.Transient;

public interface ApiAccess3Entities extends TenantedChannelizedEntities {
  @Transient
  String COLUMN__ALLOW_SIGNATURE = "allow_signature";
  @Transient
  String FIELD__ALLOW_SIGNATURE = "allowSignature";
  @Transient
  String COLUMN__DENY_SIGNATURE = "deny_signature";
  @Transient
  String FIELD__DENY_SIGNATURE = "denySignature";

  String getAllowSignature();

  <T extends ApiAccess3Entities> T setAllowSignature(String allowSignature);

  String getDenySignature();

  <T extends ApiAccess3Entities> T setDenySignature(String denySignature);

  //@see sktest.roc.rr.cfg.RrCfg.helloApiAccess2Entity
  <T extends ApiAccess3Entities> Class<T> entityClass();
}
