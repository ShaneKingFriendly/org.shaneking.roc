package org.shaneking.roc.persistence.entity.sql;

import org.shaneking.roc.persistence.entity.TenantChannelizedEntities;

public interface ApiAccess3Entities extends TenantChannelizedEntities {

  String getAllowSignature();

  <T extends ApiAccess3Entities> T setAllowSignature(String allowSignature);

  String getDenySignature();

  <T extends ApiAccess3Entities> T setDenySignature(String denySignature);

  //@see sktest.roc.rr.cfg.RrCfg.helloApiAccess2Entity
  <T extends ApiAccess3Entities> Class<T> entityClass();
}
