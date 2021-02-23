package sktest.roc.persistence.entity;

import lombok.ToString;
import lombok.experimental.Accessors;
import org.shaneking.ling.persistence.entity.sql.sqllite.SqlliteSqlEntities;
import org.shaneking.ling.persistence.hello.NullSetter;
import org.shaneking.roc.persistence.entity.TenantNumberedEntity;

import javax.persistence.Table;

@Accessors(chain = true)
@Table
@ToString(callSuper = true)
public class HelloTenantNumberedEntity extends TenantNumberedEntity implements SqlliteSqlEntities, NullSetter {
}
