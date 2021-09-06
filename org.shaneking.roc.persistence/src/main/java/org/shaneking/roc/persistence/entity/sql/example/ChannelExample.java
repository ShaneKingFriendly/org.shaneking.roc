package org.shaneking.roc.persistence.entity.sql.example;

import com.github.liaochong.myexcel.core.annotation.ExcelColumn;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.shaneking.ling.zero.crypto.SKC1;
import org.shaneking.roc.persistence.AbstractCacheableEntity;
import org.shaneking.roc.persistence.entity.sql.ChannelEntities;

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
  private String tokenValue;

  @Column(columnDefinition = "default '' COMMENT 'Y|N'")
  @ExcelColumn
  @Getter
  @Setter
  private String tokenForce;

  /**
   * @see SKC1#ALGORITHM_NAME
   */
  @Column(columnDefinition = "default '' COMMENT ''")
  @ExcelColumn
  @Getter
  @Setter
  private String tokenAlgorithmType;

  /**
   * @see ChannelEntities#TOKEN_VALUE_TYPE__SELF
   * @see ChannelEntities#TOKEN_VALUE_TYPE__PROP
   */
  @Column(columnDefinition = "default '' COMMENT ''")
  @ExcelColumn
  @Getter
  @Setter
  private String tokenValueType;

  @Column(columnDefinition = "default 0 COMMENT ''")
  @ExcelColumn
  @Getter
  @Setter
  private Integer dszSeconds;
}
