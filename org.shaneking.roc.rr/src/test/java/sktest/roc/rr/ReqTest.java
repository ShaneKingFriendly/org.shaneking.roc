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
      () -> assertEquals("Req(ctx=null, enc=null, pri=null, pub=null)", Req.build().toString()),

      () -> assertEquals("{}", OM3.writeValueAsString(Req.build())),

      () -> assertEquals("{\"pri\":{}}", OM3.writeValueAsString(Req.build(Pri.build()))),

      () -> assertEquals("{\"pub\":{}}", OM3.writeValueAsString(Req.build(new ReqPub()))),

      () -> assertEquals("{\"pri\":{},\"pub\":{}}", OM3.writeValueAsString(Req.build(new ReqPub(), Pri.build()))),

      () -> assertEquals("{\"enc\":\"enc\",\"pub\":{}}", OM3.writeValueAsString(Req.build(new ReqPub(), "enc")))
    );
  }
}
