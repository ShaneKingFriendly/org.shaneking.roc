package org.shaneking.roc.persistence.entity.sql;

import javax.persistence.Transient;

public interface ApiAccess5Entities extends ApiAccessOpEntities {
  @Transient
  String COLUMN__SIGNATURE = "signature";
  @Transient
  String FIELD__SIGNATURE = "signature";

  String getSignature();

  <T extends ApiAccess5Entities> T setSignature(String signature);

  //@see sktest.roc.rr.cfg.RrCfg.helloApiAccess5Entity
  <T extends ApiAccess5Entities> Class<T> entityClass();
}
