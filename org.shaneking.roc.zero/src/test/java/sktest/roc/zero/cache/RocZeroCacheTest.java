package sktest.roc.zero.cache;

import org.junit.jupiter.api.Test;
import org.shaneking.ling.zero.cache.ZeroCache;
import org.shaneking.ling.zero.util.List0;
import org.shaneking.ling.zero.util.Map0;
import org.shaneking.roc.test.SKSpringUnit;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class RocZeroCacheTest extends SKSpringUnit {

  @Autowired
  private ZeroCache cache;

  @Autowired
  private RocZeroCachePrepare1 rocZeroCachePrepare1;

  @Test
  void testCache() {
    cache.hmset("1", Map0.newHashMap("2", "3"));
    assertAll(
      () -> assertTrue(cache.del("A")),
      () -> assertNull(cache.get("A")),
      () -> assertEquals(0L, cache.hdel("1", "A")),
      () -> assertNull(cache.hget("1", "A")),
      () -> assertLinesMatch(List0.newArrayList(), cache.hmget("1", "A")),
      () -> cache.hmset("11", Map0.newHashMap("22", "33")),
      () -> cache.hset("11", "22", "33"),
      () -> cache.set("11", "22"),
      () -> cache.set("11", 1, "22"),

      () -> cache.hmset("1", Map0.newHashMap("22", "33")),
      () -> cache.hset("1", "22", "33")
    );
  }

  @Test
  void testTrans() {
    assertAll(
      () -> assertDoesNotThrow(() -> rocZeroCachePrepare1.test(false)),
      () -> assertThrows(RuntimeException.class, () -> rocZeroCachePrepare1.test(true))
    );
  }
}
