package org.shaneking.roc.rr;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.shaneking.roc.persistence.entity.UserEntity;

@Accessors(chain = true)
@ToString
public class ReqCtx {
  @Getter
  @Setter
  private String ips;
  @Getter
  @Setter
  private ObjectNode jon;//json object node
  @Getter
  @Setter
  private String language;//default zh-CN, ref: http://www.rfc-editor.org/rfc/bcp/bcp47.txt
  @Getter
  @Setter
  private UserEntity user;
}
