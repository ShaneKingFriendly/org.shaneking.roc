package org.shaneking.roc.persistence.entity.sql;

import org.shaneking.ling.persistence.entity.sql.Named;
import org.shaneking.roc.persistence.CacheableEntities;

public interface TenantEntities extends CacheableEntities, Named {

  //@see sktest.roc.rr.cfg.RrCfg.simpleTenantEntity
  <T extends TenantEntities> Class<T> entityClass();

  String getDescription();

  <T extends TenantEntities> T setDescription(String description);
}
