package org.shaneking.roc.persistence.entity.sql.example;

import com.github.liaochong.myexcel.core.annotation.ExcelColumn;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.shaneking.roc.persistence.entity.ApiAccessOpEntities;
import org.shaneking.roc.persistence.entity.ChannelizedEntity;
import org.shaneking.roc.persistence.entity.sql.ChannelAccessUrlApiEntities;

import javax.persistence.Column;

@Accessors(chain = true)
@ToString(callSuper = true)
public abstract class ChannelAccessUrlApiExample extends ChannelizedEntity implements ChannelAccessUrlApiEntities {
  /**
   * @see ApiAccessOpEntities#OP__ALLOW
   * @see ApiAccessOpEntities#OP__DENY
   */
  @Column(length = 1, columnDefinition = "default '' COMMENT ''")
  @ExcelColumn
  @Getter
  @Setter
  private String op;

  @Column(columnDefinition = "default '' COMMENT ''")
  @ExcelColumn
  @Getter
  @Setter
  private String url;
}
