package sktest.roc.persistence.entity;

import lombok.ToString;
import lombok.experimental.Accessors;
import org.shaneking.ling.persistence.entity.sql.sqllite.SqlliteSqlEntities;
import org.shaneking.ling.persistence.hello.NullSetter;
import org.shaneking.roc.persistence.entity.sql.AuditLogEntity;
import org.springframework.stereotype.Component;

import javax.persistence.Table;

@Accessors(chain = true)
@Component
@Table
@ToString(callSuper = true)
public class HelloAuditLogEntity extends AuditLogEntity implements SqlliteSqlEntities, NullSetter {
  @Override
  public Class<? extends HelloAuditLogEntity> entityClass() {
    return this.getClass();
  }
}
