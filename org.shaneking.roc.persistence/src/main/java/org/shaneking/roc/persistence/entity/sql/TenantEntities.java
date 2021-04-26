package org.shaneking.roc.persistence.entity.sql;

import org.shaneking.roc.persistence.CacheableEntities;

public interface TenantEntities extends CacheableEntities {

  String getName();

  <T extends TenantEntities> T setName(String name);

  String getDescription();

  <T extends TenantEntities> T setDescription(String description);

  //@see sktest.roc.rr.cfg.RrCfg.helloTenantEntity
  <T extends TenantEntities> Class<T> entityClass();
}
