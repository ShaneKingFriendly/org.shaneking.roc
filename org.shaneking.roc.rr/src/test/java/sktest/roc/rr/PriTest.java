package sktest.roc.rr;

import org.junit.jupiter.api.Test;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.roc.rr.Pri;
import org.shaneking.roc.rr.PriExt;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PriTest {

  @Test
  void build() {
    assertAll(
      () -> assertEquals("Pri(ext=PriExt(jon=null, table=PriExtTable(pagination=null), userId=null), obj=null, rtn=null)", Pri.build().toString()),
      () -> assertEquals("{\"ext\":{\"table\":{}}}", OM3.writeValueAsString(Pri.build())),
      () -> assertEquals("{\"ext\":{\"table\":{}},\"rtn\":\"rtn\"}", OM3.writeValueAsString(Pri.build("rtn"))),
      () -> assertEquals("{\"ext\":{\"table\":{}},\"obj\":\"obj\",\"rtn\":\"rtn\"}", OM3.writeValueAsString(Pri.build("rtn", "obj"))),
      () -> assertEquals("{\"ext\":{\"table\":{}},\"obj\":\"obj\",\"rtn\":\"rtn\"}", OM3.writeValueAsString(Pri.build("rtn", "obj", new PriExt())))
    );
  }
}
