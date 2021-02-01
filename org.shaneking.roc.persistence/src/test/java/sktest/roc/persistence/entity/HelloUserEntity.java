package sktest.roc.persistence.entity;

import lombok.ToString;
import lombok.experimental.Accessors;
import org.shaneking.ling.persistence.hello.NullSetter;
import org.shaneking.ling.persistence.sql.entity.sqllite.SqlliteDialectSqlEntities;
import org.shaneking.roc.persistence.entity.UserEntity;
import org.springframework.stereotype.Component;

import javax.persistence.Table;

@Accessors(chain = true)
@Component
@Table
@ToString(callSuper = true)
public class HelloUserEntity extends UserEntity implements SqlliteDialectSqlEntities, NullSetter {
  @Override
  public Class<? extends HelloUserEntity> entityClass() {
    return HelloUserEntity.class;
  }
}
