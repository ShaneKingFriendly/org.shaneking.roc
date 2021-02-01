package sktest.roc.persistence.entity;

import lombok.ToString;
import lombok.experimental.Accessors;
import org.shaneking.ling.persistence.hello.NullSetter;
import org.shaneking.ling.persistence.sql.entity.sqllite.SqlliteDialectSqlEntities;
import org.shaneking.roc.persistence.entity.ApiAccessEntity;
import org.springframework.stereotype.Component;

import javax.persistence.Table;

@Accessors(chain = true)
@Component
@Table
@ToString(callSuper = true)
public class HelloApiAccessEntity extends ApiAccessEntity implements SqlliteDialectSqlEntities, NullSetter {
  @Override
  public Class<? extends HelloApiAccessEntity> entityClass() {
    return HelloApiAccessEntity.class;
  }
}
