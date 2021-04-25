package org.shaneking.roc.persistence.entity;

import com.github.liaochong.myexcel.core.annotation.ExcelColumn;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;

//@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {Numbered.COLUMN__NO})})
@Accessors(chain = true)
@ToString(callSuper = true)
public abstract class TenantNumberedEntity extends NumberedEntity implements TenantNumberedEntities {
  @Column(length = 40, columnDefinition = "default '' COMMENT ''")
  @ExcelColumn(style = {"title->color:red"})
  @Getter
  @Setter
  private String tenantId;
}
