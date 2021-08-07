package org.shaneking.roc.persistence.entity;

import javax.persistence.Transient;

public interface ApiAccessUrlOpEntities extends ApiAccessOpEntities {
  @Transient
  String COLUMN__URL = "url";
  @Transient
  String FIELD__URL = "url";

  String getUrl();

  <T extends ApiAccessUrlOpEntities> T setUrl(String url);
}
