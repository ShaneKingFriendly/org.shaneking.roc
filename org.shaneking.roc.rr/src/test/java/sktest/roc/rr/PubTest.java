package sktest.roc.rr;

import org.junit.jupiter.api.Test;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.ling.zero.util.UUID0;
import org.shaneking.roc.rr.Pub;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PubTest {

  @Test
  void testToString() {
    String cul33 = UUID0.cUl33();
    assertAll(
      () -> assertEquals("Pub(channelNo=null, encoded=null, jon=null, tenantNo=null, tracingNo=null)", new Pub().toString()),
      () -> assertEquals("{}", OM3.writeValueAsString(new Pub())),
      () -> assertEquals("{\"channelNo\":\"channelNo\",\"encoded\":\"N\",\"jon\":{},\"tenantNo\":\"tenantNo\",\"tracingNo\":\"" + cul33 + "\"}", OM3.writeValueAsString(new Pub().setChannelNo("channelNo").setEncoded(String0.N).setJon(OM3.createObjectNode()).setTenantNo("tenantNo").setTracingNo(cul33)))
    );
  }
}
