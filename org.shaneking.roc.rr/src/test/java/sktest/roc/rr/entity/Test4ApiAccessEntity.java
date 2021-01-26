package sktest.roc.rr.entity;

import org.shaneking.ling.persistence.sql.entity.sqllite.SqlliteDialectSqlEntities;
import org.shaneking.roc.persistence.entity.ApiAccessEntity;

public class Test4ApiAccessEntity extends ApiAccessEntity implements SqlliteDialectSqlEntities {
  @Override
  public Class<? extends Test4ApiAccessEntity> entityClass() {
    return Test4ApiAccessEntity.class;
  }
}
