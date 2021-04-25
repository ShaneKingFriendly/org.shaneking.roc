package org.shaneking.roc.persistence.entity;

import org.shaneking.ling.persistence.entity.sql.TenantedNumbered;
import org.shaneking.roc.persistence.CacheableEntities;

//@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {Numbered.COLUMN__NO, Tenanted.COLUMN__TENANT_ID})})
public interface TenantedNumberedEntities extends CacheableEntities, TenantedNumbered {
}
