package sktest.roc.persistence.entity.sql;

import lombok.ToString;
import lombok.experimental.Accessors;
import org.shaneking.ling.persistence.entity.sql.Channelized;
import org.shaneking.ling.persistence.entity.sql.Tenanted;
import org.shaneking.ling.persistence.entity.sql.sqllite.SqlliteSqlEntities;
import org.shaneking.ling.persistence.hello.NullSetter;
import org.shaneking.roc.persistence.entity.sql.ApiAccessSignatureEntity;
import org.springframework.stereotype.Component;

import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Accessors(chain = true)
@Component
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {Channelized.COLUMN__CHANNEL_ID, Tenanted.COLUMN__TENANT_ID, ApiAccessSignatureEntity.COLUMN__SIGNATURE})})
@ToString(callSuper = true)
public class HelloApiAccessSignatureEntity extends ApiAccessSignatureEntity implements SqlliteSqlEntities, NullSetter {
  @Override
  public Class<? extends HelloApiAccessSignatureEntity> entityClass() {
    return this.getClass();
  }
}