package org.shaneking.roc.persistence.entity;

import org.shaneking.ling.persistence.entity.Numbered;
import org.shaneking.ling.persistence.entity.sql.Tenanted;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.ling.zero.util.List0;
import org.shaneking.ling.zero.util.Map0;
import org.shaneking.roc.persistence.CacheableEntities;

import java.util.List;
import java.util.Map;

//@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {Numbered.COLUMN__NO, Tenanted.COLUMN__TENANT_ID})})
public interface TenantedNumberedEntities extends CacheableEntities, Tenanted, Numbered {
  default Map<String, List<String>> genTableUniIdxMapExt() {
    return Map0.newHashMap(Numbered.COLUMN__NO + String0.UNDERLINE + Tenanted.COLUMN__TENANT_ID, List0.newArrayList(Numbered.COLUMN__NO, Tenanted.COLUMN__TENANT_ID));
  }
}
