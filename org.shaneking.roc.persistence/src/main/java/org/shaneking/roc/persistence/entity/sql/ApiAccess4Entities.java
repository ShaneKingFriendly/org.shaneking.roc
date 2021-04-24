package org.shaneking.roc.persistence.entity.sql;

import javax.persistence.Transient;

public interface ApiAccess4Entities extends ApiAccessOpEntities {
  @Transient
  String COLUMN__URL = "url";
  @Transient
  String FIELD__URL = "url";

  String getUrl();

  <T extends ApiAccess4Entities> T setUrl(String url);

  //@see sktest.roc.rr.cfg.RrCfg.helloApiAccess4Entity
  <T extends ApiAccess4Entities> Class<T> entityClass();
}
