package org.shaneking.roc.persistence.simple;

import com.github.liaochong.myexcel.core.annotation.ExcelModel;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.shaneking.ling.persistence.entity.sql.Channelized;
import org.shaneking.ling.persistence.entity.sql.ChannelizedNumberedUniIdx;
import org.shaneking.ling.persistence.entity.sql.sqllite.SqlliteSqlEntities;
import org.shaneking.roc.persistence.entity.sql.example.ChannelAccessSignatureApiExample;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Accessors(chain = true)
@Component
@ConditionalOnProperty(prefix = "sk.roc.persistence.simple.entity", value = "enabled", matchIfMissing = false)
@ExcelModel(includeAllField = false, useFieldNameAsTitle = true)
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {Channelized.COLUMN__CHANNEL_ID, ChannelAccessSignatureApiExample.COLUMN__SIGNATURE})})
@ToString(callSuper = true)
public class SimpleChannelAccessSignatureApiEntity extends ChannelAccessSignatureApiExample implements ChannelizedNumberedUniIdx, SqlliteSqlEntities {
  @Override
  public Class<? extends SimpleChannelAccessSignatureApiEntity> entityClass() {
    return this.getClass();
  }
}
