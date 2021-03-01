package sktest.roc.persistence.entity;

import lombok.ToString;
import lombok.experimental.Accessors;
import org.shaneking.ling.persistence.entity.sql.sqllite.SqlliteSqlEntities;
import org.shaneking.ling.persistence.hello.NullSetter;
import org.shaneking.roc.persistence.entity.sql.ApiAccess2Entity;
import org.springframework.stereotype.Component;

import javax.persistence.Table;

@Accessors(chain = true)
@Component
@Table
@ToString(callSuper = true)
public class HelloApiAccess2Entity extends ApiAccess2Entity implements SqlliteSqlEntities, NullSetter {
  @Override
  public Class<? extends HelloApiAccess2Entity> entityClass() {
    return HelloApiAccess2Entity.class;
  }
}
