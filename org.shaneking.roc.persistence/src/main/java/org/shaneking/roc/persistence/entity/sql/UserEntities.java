package org.shaneking.roc.persistence.entity.sql;

import org.shaneking.roc.persistence.entity.TenantedNumberedEntities;

public interface UserEntities extends TenantedNumberedEntities {

  String getName();

  <T extends UserEntities> T setName(String name);

  String getHaha();

  <T extends UserEntities> T setHaha(String haha);

  String getMobile();

  <T extends UserEntities> T setMobile(String mobile);

  String getEmail();

  <T extends UserEntities> T setEmail(String email);

  //@see sktest.roc.rr.cfg.RrCfg.helloUserEntity
  <T extends UserEntities> Class<T> entityClass();
}
