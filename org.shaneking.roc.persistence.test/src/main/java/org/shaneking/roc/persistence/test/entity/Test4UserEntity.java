package org.shaneking.roc.persistence.test.entity;

import lombok.ToString;
import lombok.experimental.Accessors;
import org.shaneking.ling.persistence.sql.entity.sqllite.SqlliteDialectSqlEntities;
import org.shaneking.ling.persistence.test.NullSetter;
import org.shaneking.roc.persistence.entity.UserEntity;
import org.springframework.stereotype.Component;

import javax.persistence.Table;

@Accessors(chain = true)
@Component
@Table
@ToString(callSuper = true)
public class Test4UserEntity extends UserEntity implements SqlliteDialectSqlEntities, NullSetter {
  @Override
  public Class<? extends Test4UserEntity> entityClass() {
    return Test4UserEntity.class;
  }
}
