package org.shaneking.roc.persistence.entity;

import com.github.liaochong.myexcel.core.annotation.ExcelColumn;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.apache.commons.compress.utils.Lists;
import org.shaneking.ling.zero.lang.String0;

import javax.persistence.Column;
import java.util.List;

@Accessors(chain = true)
@ToString(callSuper = true)
public abstract class TenantReadableTenantEntity extends TenantedEntity implements TenantReadableTenantEntities {

  @Column(length = 40, columnDefinition = "default '' COMMENT ''")
  @ExcelColumn(style = {"title->color:red"})
  @Getter
  @Setter
  private String toTenantId;

  @Column(nullable = false, length = 1, columnDefinition = "default 'N' COMMENT ''")
  @ExcelColumn(style = {"title->color:red"})
  @Getter
  @Setter
  private String defaultReadable;

  public static List<String> calcReadableTenantIds(List<TenantReadableTenantEntities> list, String clazz) {
    List<String> rtn = Lists.newArrayList();
    if (list != null && !String0.isNullOrEmpty(clazz)) {
      for (TenantReadableTenantEntities tenantReadableTenantEntities : list) {
        String tId = tenantReadableTenantEntities.calcReadableTenantId(clazz);
        if (!String0.isNullOrEmpty(tId)) {
          rtn.add(tId);
        }
      }
    }
    return rtn;
  }
}
