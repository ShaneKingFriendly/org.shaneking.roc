package org.shaneking.roc.persistence.entity.sql;

import org.shaneking.roc.persistence.entity.NumberedEntities;

public interface TenantEntities extends NumberedEntities {

  String getName();

  <T extends TenantEntities> T setName(String name);

  String getDescription();

  <T extends TenantEntities> T setDescription(String description);

  //@see sktest.roc.rr.cfg.RrCfg.helloTenantEntity
  <T extends TenantEntities> Class<T> entityClass();
}
