package org.shaneking.roc.rr;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Transient;

@Accessors(chain = true)
@ToString
public class Pub {
  @Transient
  public static final String ERR_CODE__REQUIRED_CHANNEL_NAME = "REQ_PUB__REQUIRED_CHANNEL_NAME";
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
