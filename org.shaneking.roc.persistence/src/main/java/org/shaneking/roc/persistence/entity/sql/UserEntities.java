package org.shaneking.roc.persistence.entity.sql;

import org.shaneking.ling.persistence.entity.sql.Named;
import org.shaneking.ling.persistence.entity.sql.Tenanted;
import org.shaneking.roc.persistence.CacheableEntities;

public interface UserEntities extends CacheableEntities, Named, Tenanted {

  //@see sktest.roc.rr.cfg.RrCfg.simpleUserEntity
  <T extends UserEntities> Class<T> entityClass();

  String getEmail();

  String getHaha();

  String getMobile();

  <T extends UserEntities> T setEmail(String email);

  <T extends UserEntities> T setHaha(String haha);

  <T extends UserEntities> T setMobile(String mobile);
}
