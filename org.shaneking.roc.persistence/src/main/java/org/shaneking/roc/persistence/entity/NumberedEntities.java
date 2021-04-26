package org.shaneking.roc.persistence.entity;

import org.shaneking.ling.persistence.entity.Numbered;
import org.shaneking.roc.persistence.CacheableEntities;

//@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {Numbered.COLUMN__NO})})
public interface NumberedEntities extends CacheableEntities, Numbered {
}
