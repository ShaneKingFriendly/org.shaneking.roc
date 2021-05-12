package sktest.roc.persistence.entity;

import lombok.ToString;
import lombok.experimental.Accessors;
import org.shaneking.ling.persistence.entity.sql.sqllite.SqlliteSqlEntities;
import org.shaneking.roc.persistence.AbstractCacheableEntity;
import org.shaneking.roc.persistence.entity.NumberedEntities;

import javax.persistence.Table;

@Accessors(chain = true)
@Table
@ToString(callSuper = true)
public class HelloNumberedEntity extends AbstractCacheableEntity implements SqlliteSqlEntities, NumberedEntities {
}
