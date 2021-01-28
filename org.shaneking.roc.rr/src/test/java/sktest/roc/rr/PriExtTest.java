package sktest.roc.rr;

import org.junit.jupiter.api.Test;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.ling.persistence.sql.Pagination;
import org.shaneking.roc.rr.PriExt;
import org.shaneking.roc.rr.PriExtTable;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PriExtTest {

  @Test
  void testToString() {
    assertAll(
      () -> assertEquals("PriExt(jon=null, table=PriExtTable(pagination=null), userId=null)", new PriExt().toString()),
      () -> assertEquals("{\"table\":{}}", OM3.writeValueAsString(new PriExt())),
      () -> assertEquals("{\"jon\":{},\"table\":{\"pagination\":{}},\"userId\":\"userId\"}", OM3.writeValueAsString(new PriExt().setJon(OM3.createObjectNode()).setTable(new PriExtTable().setPagination(new Pagination())).setUserId("userId")))
    );
  }
}
