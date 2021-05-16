package org.shaneking.roc.persistence.entity;

import lombok.NonNull;
import org.shaneking.ling.persistence.entity.sql.Tenanted;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.ling.zero.util.List0;
import org.shaneking.roc.persistence.CacheableEntities;

import javax.persistence.Transient;
import java.util.List;

public interface ReadableTenantEntities extends CacheableEntities, Tenanted {
  @Transient
  String COLUMN__DEFAULT_READABLE = "default_readable";
  @Transient
  String FIELD__DEFAULT_READABLE = "defaultReadable";

  static List<String> calc(List<? extends ReadableTenantEntities> list, String clazz, @NonNull List<String> defaultTenantIds) {
    List<String> rtn = List0.newArrayList();
    rtn.addAll(defaultTenantIds);
    if (list != null && list.size() > 0 && !String0.isNullOrEmpty(clazz)) {
      for (ReadableTenantEntities readableTenantEntities : list) {
        String tId = readableTenantEntities.calcReadableTenantId(clazz);
        if (!String0.isNullOrEmpty(tId)) {
          rtn.add(tId);
        }
      }
    }
    return rtn;
  }

  String getDefaultReadable();

  <T extends ReadableTenantEntities> T setDefaultReadable(String defaultReadable);

  String calcReadableTenantId(String clazz);
}
