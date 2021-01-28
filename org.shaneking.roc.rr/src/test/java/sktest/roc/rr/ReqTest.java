package sktest.roc.rr;

import org.junit.jupiter.api.Test;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.roc.rr.Pri;
import org.shaneking.roc.rr.Req;
import org.shaneking.roc.rr.ReqPub;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ReqTest {

  @Test
  void build() {
    assertAll(
      () -> assertEquals("Req(ctx=ReqCtx(auditLog=null, channel=null, jon=null, language=null, tenant=null, user=null), enc=null, pri=null, pub=null)", Req.build().toString()),
      () -> assertEquals("{\"ctx\":{}}", OM3.writeValueAsString(Req.build())),
      () -> assertEquals("{\"ctx\":{},\"pri\":{\"ext\":{\"table\":{}}}}", OM3.writeValueAsString(Req.build(Pri.build()))),
      () -> assertEquals("{\"ctx\":{},\"pub\":{}}", OM3.writeValueAsString(Req.build(new ReqPub()))),
      () -> assertEquals("{\"ctx\":{},\"pri\":{\"ext\":{\"table\":{}}},\"pub\":{}}", OM3.writeValueAsString(Req.build(new ReqPub(), Pri.build()))),
      () -> assertEquals("{\"ctx\":{},\"enc\":\"enc\",\"pub\":{}}", OM3.writeValueAsString(Req.build(new ReqPub(), "enc")))
    );
  }
}
