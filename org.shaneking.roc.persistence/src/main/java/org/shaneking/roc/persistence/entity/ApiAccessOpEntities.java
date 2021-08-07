package org.shaneking.roc.persistence.entity;

import org.shaneking.roc.persistence.CacheableEntities;

import javax.persistence.Transient;

public interface ApiAccessOpEntities extends CacheableEntities {
  @Transient
  String ERR_CODE__PERMISSION_DENIED = "API_ACCESS_OP__PERMISSION_DENIED";

  @Transient
  String COLUMN__OP = "op";
  @Transient
  String FIELD__OP = "op";

  @Transient
  String OP__ALLOW = "A";
  @Transient
  String OP__DENY = "D";

  String getOp();

  <T extends ApiAccessOpEntities> T setOp(String op);
}
