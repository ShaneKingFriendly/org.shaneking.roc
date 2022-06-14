package sktest.roc.rr;

import org.junit.jupiter.api.Test;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.roc.rr.Req;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ReqTest {

  @Test
  void build() {
    assertAll(
      () -> assertEquals("Req(ctx=null, enc=null, pri=null, pub=null)", Req.build().toString()),
      () -> assertEquals("{}", OM3.writeValueAsString(Req.build())),
      () -> assertEquals("{\"pri\":{},\"pub\":{}}", OM3.writeValueAsString(Req.build()))
    );
  }
}
