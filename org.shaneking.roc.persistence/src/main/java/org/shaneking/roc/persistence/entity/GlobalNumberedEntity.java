package org.shaneking.roc.persistence.entity;

import com.github.liaochong.myexcel.core.annotation.ExcelColumn;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.shaneking.ling.persistence.GlobalNumbered;

import javax.persistence.Column;

@Accessors(chain = true)
@ToString(callSuper = true)
public abstract class GlobalNumberedEntity extends CacheableEntity implements GlobalNumbered {
  @Column(unique = true, length = 40, columnDefinition = "default '' COMMENT ''")
  @ExcelColumn(style = {"title->color:red"})
  @Getter
  @Setter
  private String no;
}
