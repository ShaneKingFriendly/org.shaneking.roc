package sktest.roc.rr;

import org.junit.jupiter.api.Test;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.roc.rr.Ext;
import org.shaneking.roc.rr.Pri;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PriTest {

  @Test
  void build() {
    assertAll(
      () -> assertEquals("Pri(ext=null, obj=null, rtn=null)", Pri.build().toString()),
      () -> assertEquals("{}", OM3.writeValueAsString(Pri.build())),
      () -> assertEquals("{\"rtn\":\"rtn\"}", OM3.writeValueAsString(Pri.build("rtn"))),
      () -> assertEquals("{\"obj\":\"obj\",\"rtn\":\"rtn\"}", OM3.writeValueAsString(Pri.build("rtn", "obj"))),
      () -> assertEquals("{\"ext\":{},\"obj\":\"obj\",\"rtn\":\"rtn\"}", OM3.writeValueAsString(Pri.build("rtn", "obj", new Ext())))
    );
  }
}
