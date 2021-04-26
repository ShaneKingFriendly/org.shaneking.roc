package org.shaneking.roc.persistence.entity.sql;

import org.shaneking.ling.persistence.entity.sql.Tenanted;

import javax.persistence.Transient;

public interface TenantReadableTenantEntities extends Tenanted {
  @Transient
  String COLUMN__DEFAULT_READABLE = "default_readable";
  @Transient
  String FIELD__DEFAULT_READABLE = "defaultReadable";
  @Transient
  String COLUMN__TO_TENANT_ID = "to_tenant_id";
  @Transient
  String FIELD__TO_TENANT_ID = "toTenantId";

  String getDefaultReadable();

  <T extends TenantReadableTenantEntities> T setDefaultReadable(String defaultReadable);

  String getToTenantId();

  <T extends TenantReadableTenantEntities> T setToTenantId(String toTenantId);

  String calcReadableTenantId(String clazz);
}
