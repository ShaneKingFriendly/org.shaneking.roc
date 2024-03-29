package org.shaneking.roc.persistence.entity.sql.example;

import com.github.liaochong.myexcel.core.annotation.ExcelColumn;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.shaneking.ling.persistence.entity.sql.RrAuditLogEntities;
import org.shaneking.roc.persistence.entity.TenantedChannelizedEntity;

import javax.persistence.Column;
import javax.persistence.Lob;

@Accessors(chain = true)
@ToString(callSuper = true)
public abstract class RrAuditLogExample extends TenantedChannelizedEntity implements RrAuditLogEntities {
  @Column(length = 40, columnDefinition = "default '' COMMENT ''")
  @ExcelColumn
  @Getter
  @Setter
  private String tracingNo;

  /**
   * @see org.shaneking.ling.zero.util.Date0#DATE_TIME
   */
  @Column(length = 20, columnDefinition = "default '' COMMENT ''")
  @ExcelColumn
  @Getter
  @Setter
  private String reqDatetime;

  @Column(columnDefinition = "default '' COMMENT ''")
  @ExcelColumn
  @Getter
  @Setter
  private String reqIps;

  @Column(length = 40, columnDefinition = "default '' COMMENT ''")
  @ExcelColumn
  @Getter
  @Setter
  private String reqUserId;

  @Column(columnDefinition = "default '' COMMENT ''")
  @ExcelColumn
  @Getter
  @Lob
  @Setter
  private String reqJsonStrRaw;

  @Column(columnDefinition = "default '' COMMENT ''")
  @ExcelColumn
  @Getter
  @Lob
  @Setter
  private String reqJsonStr;

  @Column(columnDefinition = "default '' COMMENT ''")
  @ExcelColumn
  @Getter
  @Setter
  private String reqUrl;

  @Column(columnDefinition = "default '' COMMENT ''")
  @ExcelColumn
  @Getter
  @Setter
  private String reqSignature;

  @Column(length = 1, columnDefinition = "default '' COMMENT 'Y|N'")
  @ExcelColumn
  @Getter
  @Setter
  private String cached;//Y|N

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
  private String respJsonStrRaw;

  @Column(columnDefinition = "default '' COMMENT ''")
  @ExcelColumn
  @Getter
  @Setter
  private String respIps;

  @Column(length = 20, columnDefinition = "default '' COMMENT ''")
  @ExcelColumn
  @Getter
  @Setter
  private String respDatetime;
}
