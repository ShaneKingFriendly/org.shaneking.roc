package sktest.roc.persistence.entity;

import lombok.ToString;
import lombok.experimental.Accessors;
import org.shaneking.ling.persistence.entity.sql.Channelized;
import org.shaneking.ling.persistence.entity.sql.Tenanted;
import org.shaneking.ling.persistence.entity.sql.sqllite.SqlliteSqlEntities;
import org.shaneking.ling.persistence.hello.NullSetter;
import org.shaneking.roc.persistence.entity.sql.ApiAccess3Entities;
import org.shaneking.roc.persistence.entity.sql.ApiAccess3Entity;
import org.springframework.stereotype.Component;

import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Accessors(chain = true)
@Component
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {Channelized.COLUMN__CHANNEL_ID, Tenanted.COLUMN__TENANT_ID, ApiAccess3Entities.COLUMN__ALLOW_SIGNATURE, ApiAccess3Entities.COLUMN__DENY_SIGNATURE})})
@ToString(callSuper = true)
public class HelloApiAccess3Entity extends ApiAccess3Entity implements SqlliteSqlEntities, NullSetter {
  @Override
  public Class<? extends HelloApiAccess3Entity> entityClass() {
    return this.getClass();
  }
}
