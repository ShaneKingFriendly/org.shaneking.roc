package sktest.roc.persistence.entity;

import lombok.ToString;
import lombok.experimental.Accessors;
import org.shaneking.ling.persistence.entity.NumberedUniIdx;
import org.shaneking.ling.persistence.entity.sql.sqllite.SqlliteSqlEntities;
import org.shaneking.roc.persistence.AbstractCacheableEntity;

import javax.persistence.Table;

@Accessors(chain = true)
@Table(name = "t_numbered_entity_prepare")
@ToString(callSuper = true)
public class NumberedEntityPrepare1 extends AbstractCacheableEntity implements SqlliteSqlEntities, NumberedUniIdx {
}
