package org.shaneking.roc.persistence.hello.entity;

import com.github.liaochong.myexcel.core.annotation.ExcelModel;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.shaneking.ling.persistence.entity.sql.Numbered;
import org.shaneking.ling.persistence.entity.sql.Tenanted;
import org.shaneking.ling.persistence.entity.sql.sqllite.SqlliteSqlEntities;
import org.shaneking.ling.persistence.hello.NullSetter;
import org.shaneking.roc.persistence.entity.sql.UserEntity;
import org.springframework.stereotype.Component;

import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Accessors(chain = true)
@Component
@ExcelModel(includeAllField = false, useFieldNameAsTitle = true)
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {Numbered.COLUMN__NO, Tenanted.COLUMN__TENANT_ID})})
@ToString(callSuper = true)
public class HelloUserEntity extends UserEntity implements SqlliteSqlEntities, NullSetter {
  @Override
  public Class<? extends HelloUserEntity> entityClass() {
    return this.getClass();
  }
}
