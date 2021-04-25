package org.shaneking.roc.persistence.entity;

import com.github.liaochong.myexcel.core.annotation.ExcelColumn;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;

//@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {Numbered.COLUMN__NO, Tenanted.COLUMN__TENANT_ID})})
@Accessors(chain = true)
@ToString(callSuper = true)
public abstract class TenantedNumberedEntity extends NumberedEntity implements TenantedNumberedEntities {
  @Column(length = 40, columnDefinition = "default '' COMMENT ''")
  @ExcelColumn(style = {"title->color:red"})
  @Getter
  @Setter
  private String tenantId;
}
