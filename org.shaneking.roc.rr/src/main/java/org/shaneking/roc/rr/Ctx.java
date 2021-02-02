package org.shaneking.roc.rr;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.shaneking.roc.persistence.entity.AuditLogEntity;
import org.shaneking.roc.persistence.entity.ChannelEntity;
import org.shaneking.roc.persistence.entity.TenantEntity;
import org.shaneking.roc.persistence.entity.UserEntity;

@Accessors(chain = true)
@ToString
public class Ctx {
  @Getter
  @Setter
  private AuditLogEntity auditLog;
  @Getter
  @Setter
  private ChannelEntity channel;
  @Getter
  @Setter
  private ObjectNode jon;//json object node
  @Getter
  @Setter
  private String language;//default zh-CN, ref: http://www.rfc-editor.org/rfc/bcp/bcp47.txt
  @Getter
  @Setter
  private TenantEntity tenant;
  @Getter
  @Setter
  private UserEntity user;
}
