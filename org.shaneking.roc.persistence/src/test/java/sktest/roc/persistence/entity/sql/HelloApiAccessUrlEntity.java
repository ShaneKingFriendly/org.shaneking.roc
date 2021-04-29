package sktest.roc.persistence.entity.sql;

import lombok.ToString;
import lombok.experimental.Accessors;
import org.shaneking.ling.persistence.entity.sql.Channelized;
import org.shaneking.ling.persistence.entity.sql.Tenanted;
import org.shaneking.ling.persistence.entity.sql.sqllite.SqlliteSqlEntities;
import org.shaneking.roc.persistence.entity.sql.ApiAccessUrlEntity;
import org.springframework.stereotype.Component;

import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Accessors(chain = true)
@Component
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {Channelized.COLUMN__CHANNEL_ID, Tenanted.COLUMN__TENANT_ID, ApiAccessUrlEntity.COLUMN__URL})})
@ToString(callSuper = true)
public class HelloApiAccessUrlEntity extends ApiAccessUrlEntity implements SqlliteSqlEntities {
  @Override
  public Class<? extends HelloApiAccessUrlEntity> entityClass() {
    return this.getClass();
  }
}
