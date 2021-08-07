package org.shaneking.roc.persistence.entity;

import javax.persistence.Transient;

public interface ApiAccessSignatureOpEntities extends ApiAccessOpEntities {
  @Transient
  String COLUMN__SIGNATURE = "signature";
  @Transient
  String FIELD__SIGNATURE = "signature";

  String getSignature();

  <T extends ApiAccessSignatureOpEntities> T setSignature(String signature);
}
