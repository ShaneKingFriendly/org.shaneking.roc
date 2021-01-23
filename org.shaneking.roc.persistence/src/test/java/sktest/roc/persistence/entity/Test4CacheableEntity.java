package sktest.roc.persistence.entity;

import lombok.ToString;
import lombok.experimental.Accessors;
import org.shaneking.ling.persistence.sql.entity.sqllite.SqlliteDialectSqlEntities;
import org.shaneking.roc.persistence.entity.CacheableEntity;

import javax.persistence.Table;

@Accessors(chain = true)
@Table
@ToString(callSuper = true)
public class Test4CacheableEntity extends CacheableEntity implements SqlliteDialectSqlEntities {

}
