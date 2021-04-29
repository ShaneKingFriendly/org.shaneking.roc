package org.shaneking.roc.persistence.hello.entity;

import com.github.liaochong.myexcel.core.annotation.ExcelModel;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.shaneking.ling.persistence.entity.sql.sqllite.SqlliteSqlEntities;
import org.shaneking.roc.persistence.entity.NumberedEntities;
import org.shaneking.roc.persistence.entity.sql.ChannelEntity;
import org.springframework.stereotype.Component;

import javax.persistence.Table;

@Accessors(chain = true)
@Component
@ExcelModel(includeAllField = false, useFieldNameAsTitle = true)
@Table
@ToString(callSuper = true)
public class HelloChannelEntity extends ChannelEntity implements SqlliteSqlEntities, NumberedEntities {
  @Override
  public Class<? extends HelloChannelEntity> entityClass() {
    return this.getClass();
  }
}
