package sktest.roc.persistence.entity;

import lombok.ToString;
import lombok.experimental.Accessors;
import org.shaneking.ling.persistence.entity.sql.Channelized;
import org.shaneking.ling.persistence.entity.sql.sqllite.SqlliteSqlEntities;
import org.shaneking.roc.persistence.entity.ChannelizedEntity;

import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Accessors(chain = true)
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {Channelized.COLUMN__CHANNEL_ID})})
@ToString(callSuper = true)
public class ChannelizedEntityPrepare extends ChannelizedEntity implements SqlliteSqlEntities {

}
