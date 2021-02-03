package sktest.roc.rr.aspectj;

import org.junit.jupiter.api.Test;
import org.shaneking.ling.rr.Resp;
import org.shaneking.roc.rr.Req;

import static org.junit.jupiter.api.Assertions.assertFalse;

class RrAuditAspectTest {

  @Test
  void around() {
    Resp resp = Resp.failed();
    assertFalse(resp.getData() instanceof Req);
  }
}
