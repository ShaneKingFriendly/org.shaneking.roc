package org.shaneking.roc.rr;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.ling.zero.util.UUID0;

import javax.persistence.Transient;

@Accessors(chain = true)
@ToString
public class Pub {
  @Transient
  public static final String ERR_CODE__REQUIRED_CHANNEL_NUMBER = "PUB__REQUIRED_CHANNEL_NUMBER";
  @Getter
  @Setter
  private String channelNo;
  @Getter
  @Setter
  private String encoded;//Y|N(default)
  @Getter
  @Setter
  private ObjectNode jon;//json object node
  @Getter
  @Setter
  private String tenantNo;//if null same as channelNo
  @Getter
  @Setter
  private String tracingNo;//if null auto generate, design for series requests
  @Getter
  @Setter
  private String reqNo;//if null auto generate, this is global unique
  //unserviceable, if hacker ger request body, then can reset appropriate reqTimeMillis
//  private Integer reqTimeMillis;//Secure Communication

  public String gnnTracingNo() {
    if (String0.isNullOrEmpty(getTracingNo())) {
      setTracingNo(gnnReqNo());
    }
    return getTracingNo();
  }

  public String gnnReqNo() {
    if (String0.isNullOrEmpty(getReqNo())) {
      setReqNo(UUID0.cUl33());
    }
    return getReqNo();
  }
}
