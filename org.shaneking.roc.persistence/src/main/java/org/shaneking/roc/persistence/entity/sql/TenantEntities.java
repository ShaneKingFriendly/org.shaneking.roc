package org.shaneking.roc.persistence.entity.sql;

import org.shaneking.ling.persistence.entity.sql.Named;
import org.shaneking.roc.persistence.CacheableEntities;

public interface TenantEntities extends CacheableEntities, Named {

  String getDescription();

  <T extends TenantEntities> T setDescription(String description);

  //@see sktest.roc.rr.cfg.RrCfg.helloTenantEntity
  <T extends TenantEntities> Class<T> entityClass();
}
