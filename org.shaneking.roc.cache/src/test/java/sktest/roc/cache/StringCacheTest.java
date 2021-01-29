package sktest.roc.cache;

import org.junit.jupiter.api.Test;
import org.shaneking.ling.cache.StringCaches;
import org.shaneking.ling.zero.util.List0;
import org.shaneking.ling.zero.util.Map0;
import org.shaneking.roc.test.SKSpringUnit;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class StringCacheTest extends SKSpringUnit {

  @Autowired
  private StringCaches stringCaches;

  @Test
  void test() {
    stringCaches.hmset("1", Map0.newHashMap("2", "3"));
    assertAll(
      () -> assertTrue(stringCaches.del("A")),
      () -> assertNull(stringCaches.get("A")),
      () -> assertEquals(0L, stringCaches.hdel("1", "A")),
      () -> assertNull(stringCaches.hget("1", "A")),
      () -> assertLinesMatch(List0.newArrayList(), stringCaches.hmget("1", "A")),
      () -> stringCaches.hmset("11", Map0.newHashMap("22", "33")),
      () -> stringCaches.hset("11", "22", "33"),
      () -> stringCaches.set("11", "22"),
      () -> stringCaches.set("11", 1, "22"),

      () -> stringCaches.hmset("1", Map0.newHashMap("22", "33")),
      () -> stringCaches.hset("1", "22", "33")
    );
  }
}
