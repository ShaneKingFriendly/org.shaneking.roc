package org.shaneking.roc.persistence.entity.sql;

import com.github.liaochong.myexcel.core.annotation.ExcelColumn;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.shaneking.roc.persistence.CacheableEntity;

import javax.persistence.Column;

@Accessors(chain = true)
@ToString(callSuper = true)
public abstract class TenantEntity extends CacheableEntity implements TenantEntities {
  @Column(length = 10, unique = true, columnDefinition = "default '' COMMENT ''")
  @ExcelColumn
  @Getter
  @Setter
  private String name;

  @Column(columnDefinition = "default '' COMMENT ''")
  @ExcelColumn
  @Getter
  @Setter
  private String description;
}
