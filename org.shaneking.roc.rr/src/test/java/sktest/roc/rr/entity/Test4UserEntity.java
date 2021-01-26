package sktest.roc.rr.entity;

import org.shaneking.ling.persistence.sql.entity.sqllite.SqlliteDialectSqlEntities;
import org.shaneking.roc.persistence.entity.UserEntity;

public class Test4UserEntity extends UserEntity implements SqlliteDialectSqlEntities {
  @Override
  public Class<? extends Test4UserEntity> entityClass() {
    return Test4UserEntity.class;
  }
}
