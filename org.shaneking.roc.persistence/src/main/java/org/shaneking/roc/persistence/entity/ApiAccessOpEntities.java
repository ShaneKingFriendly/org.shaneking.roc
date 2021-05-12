package org.shaneking.roc.persistence.entity;

import org.shaneking.ling.persistence.entity.sql.Channelized;
import org.shaneking.ling.persistence.entity.sql.Tenanted;
import org.shaneking.roc.persistence.CacheableEntities;

import javax.persistence.Transient;

public interface ApiAccessOpEntities extends CacheableEntities, Tenanted, Channelized {
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
