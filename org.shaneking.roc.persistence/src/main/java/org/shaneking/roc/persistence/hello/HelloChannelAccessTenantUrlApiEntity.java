package org.shaneking.roc.persistence.hello;

import com.github.liaochong.myexcel.core.annotation.ExcelModel;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.shaneking.ling.persistence.entity.sql.Channelized;
import org.shaneking.ling.persistence.entity.sql.Tenanted;
import org.shaneking.ling.persistence.entity.sql.TenantedNumberedUniIdx;
import org.shaneking.ling.persistence.entity.sql.sqllite.SqlliteSqlEntities;
import org.shaneking.roc.persistence.entity.sql.example.ChannelAccessTenantUrlApiExample;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Accessors(chain = true)
@Component
@ConditionalOnProperty(prefix = "sk.roc.persistence.hello.entity", value = "enabled", matchIfMissing = false)
@ExcelModel(includeAllField = false, useFieldNameAsTitle = true)
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {Channelized.COLUMN__CHANNEL_ID, Tenanted.COLUMN__TENANT_ID, ChannelAccessTenantUrlApiExample.COLUMN__URL})})
@ToString(callSuper = true)
public class HelloChannelAccessTenantUrlApiEntity extends ChannelAccessTenantUrlApiExample implements SqlliteSqlEntities, TenantedNumberedUniIdx {
  @Override
  public Class<? extends HelloChannelAccessTenantUrlApiEntity> entityClass() {
    return this.getClass();
  }
}
