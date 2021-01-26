package sktest.roc.rr.entity;

import org.shaneking.ling.persistence.sql.entity.sqllite.SqlliteDialectSqlEntities;
import org.shaneking.roc.persistence.entity.UserEntity;

public class Test4TenantEntity extends UserEntity implements SqlliteDialectSqlEntities {
  @Override
  public Class<? extends Test4TenantEntity> entityClass() {
    return Test4TenantEntity.class;
  }
}
