package sktest.roc.persistence.entity;

import lombok.ToString;
import lombok.experimental.Accessors;
import org.shaneking.ling.persistence.entity.sql.TenantedNumberedUniIdx;
import org.shaneking.ling.persistence.entity.sql.sqllite.SqlliteSqlEntities;
import org.shaneking.roc.persistence.entity.TenantedEntity;

import javax.persistence.Table;

@Accessors(chain = true)
@Table(name = "t_tenanted_numbered_entity_prepare")
@ToString(callSuper = true)
public class TenantedNumberedEntityPrepare1 extends TenantedEntity implements SqlliteSqlEntities, TenantedNumberedUniIdx {
}
