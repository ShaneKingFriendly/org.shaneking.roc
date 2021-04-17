package sktest.roc.persistence.entity;

import lombok.ToString;
import lombok.experimental.Accessors;
import org.shaneking.ling.persistence.entity.sql.sqllite.SqlliteSqlEntities;
import org.shaneking.ling.persistence.hello.NullSetter;
import org.shaneking.roc.persistence.entity.sql.ApiAccess3Entity;
import org.springframework.stereotype.Component;

import javax.persistence.Table;

@Accessors(chain = true)
@Component
@Table
@ToString(callSuper = true)
public class HelloApiAccess3Entity extends ApiAccess3Entity implements SqlliteSqlEntities, NullSetter {
  @Override
  public Class<? extends HelloApiAccess3Entity> entityClass() {
    return this.getClass();
  }
}
