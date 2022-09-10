package org.shaneking.roc.persistence.entity.sql.example;

import com.github.liaochong.myexcel.core.annotation.ExcelColumn;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.shaneking.ling.persistence.entity.sql.ChannelEntities;
import org.shaneking.ling.zero.crypto.SKC1;
import org.shaneking.roc.persistence.AbstractCacheableEntity;

import javax.persistence.Column;

@Accessors(chain = true)
@ToString(callSuper = true)
public abstract class ChannelExample extends AbstractCacheableEntity implements ChannelEntities {
  @Column(columnDefinition = "default '' COMMENT ''")
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
  private String encTv;//TokenValue

  @Column(length = 1, columnDefinition = "default '' COMMENT 'Y|N'")
  @ExcelColumn
  @Getter
  @Setter
  private String encTf;//TokenForce

  /**
   * @see SKC1#ALGORITHM_NAME
   */
  @Column(columnDefinition = "default '' COMMENT ''")
  @ExcelColumn
  @Getter
  @Setter
  private String encTat;//TokenAlgorithmType

  /**
   * @see ChannelEntities#TOKEN_VALUE_TYPE__SELF
   * @see ChannelEntities#TOKEN_VALUE_TYPE__PROP
   */
  @Column(length = 7, columnDefinition = "default '' COMMENT ''")
  @ExcelColumn
  @Getter
  @Setter
  private String encTvt;//TokenValueType

  @Column(columnDefinition = "default 0 COMMENT ''")
  @ExcelColumn
  @Getter
  @Setter
  private Integer dszSeconds;

  @Column(columnDefinition = "default '' COMMENT ''")
  @ExcelColumn
  @Getter
  @Setter
  private String mvcTv;//TokenValue

  @Column(length = 1, columnDefinition = "default '' COMMENT 'Y|N'")
  @ExcelColumn
  @Getter
  @Setter
  private String mvcTf;//TokenForce

  /**
   * @see SKC1#ALGORITHM_NAME
   */
  @Column(columnDefinition = "default '' COMMENT ''")
  @ExcelColumn
  @Getter
  @Setter
  private String mvcTat;//TokenAlgorithmType

  /**
   * @see ChannelEntities#TOKEN_VALUE_TYPE__SELF
   * @see ChannelEntities#TOKEN_VALUE_TYPE__PROP
   */
  @Column(length = 7, columnDefinition = "default '' COMMENT ''")
  @ExcelColumn
  @Getter
  @Setter
  private String mvcTvt;//TokenValueType
}
