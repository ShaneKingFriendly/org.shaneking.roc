package org.shaneking.roc.persistence.entity.sql.example;

import com.github.liaochong.myexcel.core.annotation.ExcelColumn;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.shaneking.roc.persistence.entity.TenantedEntity;
import org.shaneking.roc.persistence.entity.sql.RrAsyncLogEntities;

import javax.persistence.Column;
import javax.persistence.Lob;

@Accessors(chain = true)
@ToString(callSuper = true)
public abstract class RrAsyncLogExample extends TenantedEntity implements RrAsyncLogEntities {
  @Column(length = 20, columnDefinition = "default '' COMMENT ''")
  @ExcelColumn
  @Getter
  @Setter
  private String startDatetime;

  @Column(columnDefinition = "default '' COMMENT ''")
  @ExcelColumn
  @Getter
  @Lob
  @Setter
  private String reqJsonStr;

  @Column(columnDefinition = "default '' COMMENT ''")
  @ExcelColumn
  @Getter
  @Lob
  @Setter
  private String respJsonStr;

  @Column(columnDefinition = "default '' COMMENT ''")
  @ExcelColumn
  @Getter
  @Lob
  @Setter
  private String respJsonStrCtx;

  @Column(columnDefinition = "default '' COMMENT ''")
  @ExcelColumn
  @Getter
  @Lob
  @Setter
  private String respMsgBodyJsonStr;

  @Column(length = 20, columnDefinition = "default '' COMMENT ''")
  @ExcelColumn
  @Getter
  @Setter
  private String doneDatetime;
}
