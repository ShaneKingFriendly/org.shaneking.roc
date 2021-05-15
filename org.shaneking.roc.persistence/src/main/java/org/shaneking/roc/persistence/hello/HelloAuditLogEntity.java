package org.shaneking.roc.persistence.hello;

import com.github.liaochong.myexcel.core.annotation.ExcelModel;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.shaneking.ling.persistence.entity.sql.sqllite.SqlliteSqlEntities;
import org.shaneking.roc.persistence.entity.sql.AuditLogExample;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.persistence.Table;

@Accessors(chain = true)
@Component
@ConditionalOnProperty(prefix = "sk.roc.persistence.hello.entity", value = "enabled")
@ExcelModel(includeAllField = false, useFieldNameAsTitle = true)
@Table
@ToString(callSuper = true)
public class HelloAuditLogEntity extends AuditLogExample implements SqlliteSqlEntities {
  @Override
  public Class<? extends HelloAuditLogEntity> entityClass() {
    return this.getClass();
  }
}
