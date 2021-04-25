package org.shaneking.roc.persistence.entity;

import org.shaneking.ling.persistence.entity.sql.TenantNumbered;

//@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {Numbered.COLUMN__NO})})
public interface TenantNumberedEntities extends NumberedEntities, TenantNumbered {
}
