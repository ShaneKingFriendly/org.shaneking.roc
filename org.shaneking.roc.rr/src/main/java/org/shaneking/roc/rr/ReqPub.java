package org.shaneking.roc.rr;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@ToString
public class ReqPub {
  @Getter
  @Setter
  private String channelName;
  @Getter
  @Setter
  private String encoded;//Y|N(default)
  @Getter
  @Setter
  private ObjectNode jon;//json object node
  @Getter
  @Setter
  private String tenantName;//if null same as channelName
  @Getter
  @Setter
  private String tracingId;
}
