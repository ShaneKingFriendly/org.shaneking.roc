package org.shaneking.roc.persistence.entity.sql;

import org.shaneking.ling.persistence.entity.sql.Tenanted;
import org.shaneking.roc.persistence.entity.ReadableTenantEntities;

import javax.persistence.Transient;

public interface TenantReadableTenantEntities extends ReadableTenantEntities, Tenanted {
  @Transient
  String COLUMN__TO_TENANT_ID = "to_tenant_id";
  @Transient
  String FIELD__TO_TENANT_ID = "toTenantId";

  String getToTenantId();

  <T extends TenantReadableTenantEntities> T setToTenantId(String toTenantId);
}
