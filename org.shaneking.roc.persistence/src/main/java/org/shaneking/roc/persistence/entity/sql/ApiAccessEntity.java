package org.shaneking.roc.persistence.entity.sql;

import com.github.liaochong.myexcel.core.annotation.ExcelColumn;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.shaneking.roc.persistence.entity.TenantedChannelizedEntity;

import javax.persistence.Column;

@Accessors(chain = true)
@ToString(callSuper = true)
public abstract class ApiAccessEntity extends TenantedChannelizedEntity implements ApiAccessEntities {
  @Column(columnDefinition = "default '' COMMENT ''")
  @ExcelColumn
  @Getter
  @Setter
  private String allowUrlRegex;

  @Column(columnDefinition = "default '' COMMENT ''")
  @ExcelColumn
  @Getter
  @Setter
  private String allowSignatureRegex;

  @Column(columnDefinition = "default '' COMMENT ''")
  @ExcelColumn
  @Getter
  @Setter
  private String denyUrlRegex;

  @Column(columnDefinition = "default '' COMMENT ''")
  @ExcelColumn
  @Getter
  @Setter
  private String denySignatureRegex;
}
