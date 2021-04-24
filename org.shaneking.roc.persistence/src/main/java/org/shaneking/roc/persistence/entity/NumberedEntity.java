package org.shaneking.roc.persistence.entity;

import com.github.liaochong.myexcel.core.annotation.ExcelColumn;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.shaneking.roc.persistence.CacheableEntity;

import javax.persistence.Column;

@Accessors(chain = true)
@ToString(callSuper = true)
public abstract class NumberedEntity extends CacheableEntity implements NumberedEntities {
  @Column(length = 40, columnDefinition = "default '' COMMENT 'serial number'")
  @ExcelColumn(style = {"title->color:red"})
  @Getter
  @Setter
  private String no;
}
