package org.shaneking.roc.persistence.entity;

import com.github.liaochong.myexcel.core.annotation.ExcelColumn;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;

@Accessors(chain = true)
@ToString(callSuper = true)
public abstract class TenantChannelizedEntity extends TenantedEntity implements TenantChannelizedEntities {
  @Column(length = 40, columnDefinition = "default '' COMMENT ''")
  @ExcelColumn
  @Getter
  @Setter
  private String channelId;
}
