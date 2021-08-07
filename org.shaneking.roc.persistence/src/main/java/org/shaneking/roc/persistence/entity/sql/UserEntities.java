package org.shaneking.roc.persistence.entity.sql;

import org.shaneking.ling.persistence.entity.sql.Named;
import org.shaneking.ling.persistence.entity.sql.Tenanted;
import org.shaneking.roc.persistence.CacheableEntities;

public interface UserEntities extends CacheableEntities, Named, Tenanted {

  String getHaha();

  <T extends UserEntities> T setHaha(String haha);

  String getMobile();

  <T extends UserEntities> T setMobile(String mobile);

  String getEmail();

  <T extends UserEntities> T setEmail(String email);

  //@see sktest.roc.rr.cfg.RrCfg.helloUserEntity
  <T extends UserEntities> Class<T> entityClass();
}
