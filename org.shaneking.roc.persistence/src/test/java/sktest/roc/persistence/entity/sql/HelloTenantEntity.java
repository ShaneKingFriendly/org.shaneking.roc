package sktest.roc.persistence.entity.sql;

import lombok.ToString;
import lombok.experimental.Accessors;
import org.shaneking.ling.persistence.entity.Numbered;
import org.shaneking.ling.persistence.entity.sql.sqllite.SqlliteSqlEntities;
import org.shaneking.roc.persistence.entity.sql.TenantEntity;
import org.springframework.stereotype.Component;

import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Accessors(chain = true)
@Component
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {Numbered.COLUMN__NO})})
@ToString(callSuper = true)
public class HelloTenantEntity extends TenantEntity implements SqlliteSqlEntities {
  @Override
  public Class<? extends HelloTenantEntity> entityClass() {
    return this.getClass();
  }
}
