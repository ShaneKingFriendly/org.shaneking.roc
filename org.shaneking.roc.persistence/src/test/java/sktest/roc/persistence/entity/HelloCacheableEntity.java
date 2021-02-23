package sktest.roc.persistence.entity;

import lombok.ToString;
import lombok.experimental.Accessors;
import org.shaneking.ling.persistence.entity.sql.sqllite.SqlliteEntities;
import org.shaneking.roc.persistence.CacheableEntity;

import javax.persistence.Table;

@Accessors(chain = true)
@Table
@ToString(callSuper = true)
public class HelloCacheableEntity extends CacheableEntity implements SqlliteEntities {

}
