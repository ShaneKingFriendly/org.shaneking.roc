package org.shaneking.roc.persistence.test.entity;

import lombok.ToString;
import lombok.experimental.Accessors;
import org.shaneking.ling.persistence.sql.entity.sqllite.SqlliteDialectSqlEntities;
import org.shaneking.ling.persistence.test.NullSetter;
import org.shaneking.roc.persistence.entity.TenantEntity;
import org.springframework.stereotype.Component;

import javax.persistence.Table;

@Accessors(chain = true)
@Component
@Table
@ToString(callSuper = true)
public class Test4TenantEntity extends TenantEntity implements SqlliteDialectSqlEntities, NullSetter {
  @Override
  public Class<? extends Test4TenantEntity> entityClass() {
    return Test4TenantEntity.class;
  }
}
