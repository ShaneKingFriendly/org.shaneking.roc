package sktest.roc.persistence.entity.sql;

import lombok.ToString;
import lombok.experimental.Accessors;
import org.shaneking.ling.persistence.entity.sql.sqllite.SqlliteSqlEntities;
import org.shaneking.roc.persistence.entity.sql.AuditLogExample;
import org.springframework.stereotype.Component;

import javax.persistence.Table;

@Accessors(chain = true)
@Component
@Table
@ToString(callSuper = true)
public class HelloAuditLogEntity extends AuditLogExample implements SqlliteSqlEntities {
  @Override
  public Class<? extends HelloAuditLogEntity> entityClass() {
    return this.getClass();
  }
}
