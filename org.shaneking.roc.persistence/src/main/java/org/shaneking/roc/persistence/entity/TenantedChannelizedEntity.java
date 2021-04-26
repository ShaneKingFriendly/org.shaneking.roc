package org.shaneking.roc.persistence.entity;

import com.github.liaochong.myexcel.core.annotation.ExcelColumn;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.shaneking.ling.persistence.entity.sql.Channelized;
import org.shaneking.ling.persistence.entity.sql.Tenanted;
import org.shaneking.roc.persistence.CacheableEntity;

import javax.persistence.Column;
import javax.persistence.Transient;

@Accessors(chain = true)
@ToString(callSuper = true)
public abstract class TenantedChannelizedEntity extends CacheableEntity implements Channelized, Tenanted {
  @Transient
  public static final String ERR_CODE__REQUIRED_CHANNEL_ID_AND_TENANT_ID = "TENANTED_CHANNELIZED_ENTITY__REQUIRED_CHANNEL_ID_AND_TENANT_ID";

  @Column(length = 40, columnDefinition = "default '' COMMENT ''")
  @ExcelColumn(style = {"title->color:red"})
  @Getter
  @Setter
  private String channelId;
  @Column(length = 40, columnDefinition = "default '' COMMENT ''")
  @ExcelColumn(style = {"title->color:red"})
  @Getter
  @Setter
  private String tenantId;
}
