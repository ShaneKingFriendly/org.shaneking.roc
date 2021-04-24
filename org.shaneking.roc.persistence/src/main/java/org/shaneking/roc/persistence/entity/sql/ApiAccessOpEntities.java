package org.shaneking.roc.persistence.entity.sql;

import org.shaneking.roc.persistence.entity.TenantedChannelizedEntities;

import javax.persistence.Transient;

public interface ApiAccessOpEntities extends TenantedChannelizedEntities {
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
