package org.shaneking.roc.rr;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.shaneking.ling.zero.util.Map0;
import org.shaneking.roc.persistence.entity.sql.AuditLogEntities;
import org.shaneking.roc.persistence.entity.sql.ChannelEntities;
import org.shaneking.roc.persistence.entity.sql.TenantEntities;
import org.shaneking.roc.persistence.entity.sql.UserEntities;

import java.util.Map;

@Accessors(chain = true)
@ToString
public class Ctx {
  @Getter
  @Setter
  private AuditLogEntities auditLog;
  @Getter
  @Setter
  private ChannelEntities channel;
  @Getter
  @Setter
  private ChannelEntities proxyChannel;
  @Getter
  @Setter
  private ObjectNode jon;//json object node
  @Getter
  @Setter
  private String language;//default zh-CN, ref: http://www.rfc-editor.org/rfc/bcp/bcp47.txt
  @Getter
  @Setter
  private TenantEntities tenant;
  @Getter
  @Setter
  private UserEntities user;
  @Getter
  @Setter
  private Map<String, UserEntities> rtuMap = Map0.newHashMap();//readable tenant user map

  public String gnaChannelId() {
    return getChannel() == null ? null : getChannel().getId();
  }

  public String gnaProxyChannelId() {
    return getProxyChannel() == null ? null : getProxyChannel().getId();
  }

  public String gnaTenantId() {
    return getTenant() == null ? null : getTenant().getId();
  }

  public String gnaUserId() {
    return getUser() == null ? null : getUser().getId();
  }
}
