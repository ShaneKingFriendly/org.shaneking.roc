package org.shaneking.roc.persistence.entity.sql;

import com.github.liaochong.myexcel.core.annotation.ExcelColumn;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.shaneking.roc.persistence.entity.TenantedEntity;

import javax.persistence.Column;

@Accessors(chain = true)
@ToString(callSuper = true)
public abstract class ChannelEntity extends TenantedEntity implements ChannelEntities {
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

  @Column(columnDefinition = "default '' COMMENT ''")
  @ExcelColumn
  @Getter
  @Setter
  private String tokenValue;

  @Column(columnDefinition = "default '' COMMENT ''")
  @ExcelColumn
  @Getter
  @Setter
  private String tokenForce;

  @Column(columnDefinition = "default '' COMMENT ''")
  @ExcelColumn
  @Getter
  @Setter
  private String tokenAlgorithmType;

  @Column(columnDefinition = "default '' COMMENT ''")
  @ExcelColumn
  @Getter
  @Setter
  private String tokenValueType;
}
