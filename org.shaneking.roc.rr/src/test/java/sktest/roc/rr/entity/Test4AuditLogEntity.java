package sktest.roc.rr.entity;

import org.shaneking.ling.persistence.sql.entity.sqllite.SqlliteDialectSqlEntities;
import org.shaneking.roc.persistence.entity.AuditLogEntity;

public class Test4AuditLogEntity extends AuditLogEntity implements SqlliteDialectSqlEntities {
  @Override
  public Class<? extends Test4AuditLogEntity> entityClass() {
    return Test4AuditLogEntity.class;
  }
}
