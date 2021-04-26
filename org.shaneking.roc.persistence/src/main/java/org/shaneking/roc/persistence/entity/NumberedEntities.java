package org.shaneking.roc.persistence.entity;

import org.shaneking.ling.persistence.entity.Numbered;
import org.shaneking.ling.zero.util.List0;
import org.shaneking.ling.zero.util.Map0;
import org.shaneking.roc.persistence.CacheableEntities;

import java.util.List;
import java.util.Map;

//@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {Numbered.COLUMN__NO})})
public interface NumberedEntities extends CacheableEntities, Numbered {
  default Map<String, List<String>> genTableUniIdxMapExt() {
    return Map0.newHashMap(Numbered.COLUMN__NO, List0.newArrayList(Numbered.COLUMN__NO));
  }
}
