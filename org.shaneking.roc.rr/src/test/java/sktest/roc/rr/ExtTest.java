package sktest.roc.rr;

import org.junit.jupiter.api.Test;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.ling.persistence.Pagination;
import org.shaneking.roc.rr.Ext;
import org.shaneking.roc.rr.Tbl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ExtTest {

  @Test
  void testToString() {
    assertAll(
      () -> assertEquals("Ext(dsz=null, jon=null, tbl=null, userNo=null)", new Ext().toString()),
      () -> assertEquals("Tbl(pagination=null)", new Ext().gnnTbl().toString()),
      () -> assertEquals("{}", OM3.writeValueAsString(new Ext())),
      () -> assertEquals("{}", OM3.writeValueAsString(new Ext().gnnTbl())),
      () -> assertEquals("{\"jon\":{},\"tbl\":{\"pagination\":{}},\"userNo\":\"userNo\"}", OM3.writeValueAsString(new Ext().setJon(OM3.createObjectNode()).setTbl(new Tbl().setPagination(new Pagination())).setUserNo("userNo")))
    );
  }
}
