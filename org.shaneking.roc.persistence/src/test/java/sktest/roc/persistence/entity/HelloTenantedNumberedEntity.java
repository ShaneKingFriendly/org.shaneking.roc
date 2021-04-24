package sktest.roc.persistence.entity;

import lombok.ToString;
import lombok.experimental.Accessors;
import org.shaneking.ling.persistence.entity.sql.Numbered;
import org.shaneking.ling.persistence.entity.sql.Tenanted;
import org.shaneking.ling.persistence.entity.sql.sqllite.SqlliteSqlEntities;
import org.shaneking.ling.persistence.hello.NullSetter;
import org.shaneking.roc.persistence.entity.TenantedNumberedEntity;

import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Accessors(chain = true)
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {Numbered.COLUMN__NO, Tenanted.COLUMN__TENANT_ID})})
@ToString(callSuper = true)
public class HelloTenantedNumberedEntity extends TenantedNumberedEntity implements SqlliteSqlEntities, NullSetter {
}
