package org.shaneking.roc.persistence.simple;

import com.github.liaochong.myexcel.core.annotation.ExcelModel;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.shaneking.ling.persistence.entity.NumberedUniIdx;
import org.shaneking.ling.persistence.entity.sql.NamedUniIdx;
import org.shaneking.ling.persistence.entity.sql.sqllite.SqlliteSqlEntities;
import org.shaneking.roc.persistence.entity.sql.example.ChannelExample;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.persistence.Table;

@Accessors(chain = true)
@Component
@ConditionalOnProperty(prefix = "sk.roc.persistence.simple.entity", value = "enabled", matchIfMissing = false)
@ExcelModel(includeAllField = false, useFieldNameAsTitle = true)
@Table
@ToString(callSuper = true)
public class SimpleChannelEntity extends ChannelExample implements NamedUniIdx, NumberedUniIdx, SqlliteSqlEntities {
  @Override
  public Class<? extends SimpleChannelEntity> entityClass() {
    return this.getClass();
  }
}
