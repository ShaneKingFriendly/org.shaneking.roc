package sktest.roc.rr.aspectj;

import org.junit.jupiter.api.Test;
import org.shaneking.ling.rr.Resp;
import org.shaneking.ling.rr.RespMsg;
import org.shaneking.ling.rr.RespMsgBody;

import static org.junit.jupiter.api.Assertions.assertFalse;

class RrAuditAspectTest {

  @Test
  void around() {
    Resp resp = Resp.build();
    assertFalse(resp.getMsg() instanceof RespMsg);
    assertFalse(resp.gnnMsg().getBody() instanceof RespMsgBody);
  }
}
