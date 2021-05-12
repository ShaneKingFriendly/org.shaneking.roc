package org.shaneking.roc.persistence.hello.entity;

import com.github.liaochong.myexcel.core.annotation.ExcelModel;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.shaneking.ling.persistence.entity.sql.sqllite.SqlliteSqlEntities;
import org.shaneking.roc.persistence.entity.TenantedNumberedEntities;
import org.shaneking.roc.persistence.entity.sql.UserExample;
import org.springframework.stereotype.Component;

import javax.persistence.Table;

@Accessors(chain = true)
@Component
@ExcelModel(includeAllField = false, useFieldNameAsTitle = true)
@Table
@ToString(callSuper = true)
public class HelloUserEntity extends UserExample implements SqlliteSqlEntities, TenantedNumberedEntities {
  @Override
  public Class<? extends HelloUserEntity> entityClass() {
    return this.getClass();
  }
}
