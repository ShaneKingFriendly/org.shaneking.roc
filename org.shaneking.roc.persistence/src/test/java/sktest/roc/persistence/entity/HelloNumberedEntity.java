package sktest.roc.persistence.entity;

import lombok.ToString;
import lombok.experimental.Accessors;
import org.shaneking.ling.persistence.entity.sql.sqllite.SqlliteSqlEntities;
import org.shaneking.ling.persistence.hello.NullSetter;
import org.shaneking.roc.persistence.entity.NumberedEntity;

import javax.persistence.Table;

@Accessors(chain = true)
@Table
@ToString(callSuper = true)
public class HelloNumberedEntity extends NumberedEntity implements SqlliteSqlEntities, NullSetter {
}
