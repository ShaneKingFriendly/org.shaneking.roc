package sktest.roc.zero.lang;

import org.junit.jupiter.api.Test;
import org.shaneking.ling.zero.net.InetAddress0;
import org.shaneking.ling.zero.persistence.Pagination;
import org.shaneking.ling.zero.util.List0;
import org.shaneking.roc.zero.lang.Object0s;

import static org.junit.jupiter.api.Assertions.*;

class Object0sTest {

  @Test
  void copy() {
    Pagination p = new Pagination();
    p.setCount(1L).setIdx(2).setRows(3);
    assertAll(
      () -> assertNotSame(p, Object0s.copy(p)),
      () -> assertEquals(p.toString(), Object0s.copy(p).toString())
    );
  }
}
