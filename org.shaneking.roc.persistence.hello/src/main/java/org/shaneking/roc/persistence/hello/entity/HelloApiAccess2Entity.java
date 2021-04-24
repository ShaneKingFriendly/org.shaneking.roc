package org.shaneking.roc.persistence.hello.entity;

import com.github.liaochong.myexcel.core.annotation.ExcelModel;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.shaneking.ling.persistence.entity.sql.Channelized;
import org.shaneking.ling.persistence.entity.sql.Tenanted;
import org.shaneking.ling.persistence.entity.sql.sqllite.SqlliteSqlEntities;
import org.shaneking.ling.persistence.hello.NullSetter;
import org.shaneking.roc.persistence.entity.sql.ApiAccess2Entities;
import org.shaneking.roc.persistence.entity.sql.ApiAccess2Entity;
import org.springframework.stereotype.Component;

import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Accessors(chain = true)
@Component
@ExcelModel(includeAllField = false, useFieldNameAsTitle = true)
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {Channelized.COLUMN__CHANNEL_ID, Tenanted.COLUMN__TENANT_ID, ApiAccess2Entities.COLUMN__ALLOW_URL})
  , @UniqueConstraint(columnNames = {Channelized.COLUMN__CHANNEL_ID, Tenanted.COLUMN__TENANT_ID, ApiAccess2Entities.COLUMN__DENY_URL})})
@ToString(callSuper = true)
public class HelloApiAccess2Entity extends ApiAccess2Entity implements SqlliteSqlEntities, NullSetter {
  @Override
  public Class<? extends HelloApiAccess2Entity> entityClass() {
    return this.getClass();
  }
}
