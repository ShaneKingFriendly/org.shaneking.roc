package sktest.roc.persistence;

import lombok.ToString;
import lombok.experimental.Accessors;
import org.shaneking.ling.persistence.entity.sql.sqllite.SqlliteSqlEntities;
import org.shaneking.roc.persistence.AbstractCacheableEntity;

import javax.persistence.Table;

@Accessors(chain = true)
@Table(name = "t_cacheable_entity_prepare")
@ToString(callSuper = true)
public class CacheableEntityPrepare1 extends AbstractCacheableEntity implements SqlliteSqlEntities {

}
