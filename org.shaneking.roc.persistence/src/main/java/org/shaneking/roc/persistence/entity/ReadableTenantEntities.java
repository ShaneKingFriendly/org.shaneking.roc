package org.shaneking.roc.persistence.entity;

import org.apache.commons.compress.utils.Lists;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.roc.persistence.CacheableEntities;

import javax.persistence.Transient;
import java.util.List;

public interface ReadableTenantEntities extends CacheableEntities {
  @Transient
  String COLUMN__DEFAULT_READABLE = "default_readable";
  @Transient
  String FIELD__DEFAULT_READABLE = "defaultReadable";

  String getDefaultReadable();

  <T extends ReadableTenantEntities> T setDefaultReadable(String defaultReadable);

  String calcReadableTenantId(String clazz);

  default List<String> calcReadableTenantIds(List<? extends ReadableTenantEntities> list, String clazz) {
    List<String> rtn = Lists.newArrayList();
    if (list != null && !String0.isNullOrEmpty(clazz)) {
      for (ReadableTenantEntities readableTenantEntities : list) {
        String tId = readableTenantEntities.calcReadableTenantId(clazz);
        if (!String0.isNullOrEmpty(tId)) {
          rtn.add(tId);
        }
      }
    }
    return rtn;
  }
}
