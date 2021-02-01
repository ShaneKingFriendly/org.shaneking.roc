package sktest.roc.persistence.entity;

import lombok.ToString;
import lombok.experimental.Accessors;
import org.shaneking.ling.persistence.hello.NullSetter;
import org.shaneking.ling.persistence.sql.entity.sqllite.SqlliteDialectSqlEntities;
import org.shaneking.roc.persistence.entity.AuditLogEntity;
import org.springframework.stereotype.Component;

import javax.persistence.Table;

@Accessors(chain = true)
@Component
@Table
@ToString(callSuper = true)
public class HelloAuditLogEntity extends AuditLogEntity implements SqlliteDialectSqlEntities, NullSetter {
  @Override
  public Class<? extends HelloAuditLogEntity> entityClass() {
    return HelloAuditLogEntity.class;
  }
}
