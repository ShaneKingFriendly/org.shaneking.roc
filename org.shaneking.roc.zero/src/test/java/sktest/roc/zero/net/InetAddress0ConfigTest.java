package sktest.roc.zero.net;

import org.junit.jupiter.api.Test;
import org.shaneking.ling.zero.net.InetAddress0;
import org.shaneking.ling.zero.util.List0;
import org.shaneking.roc.test.SKSpringUnit;

import static org.junit.jupiter.api.Assertions.*;

class InetAddress0ConfigTest extends SKSpringUnit {

  @Test
  void postConstruct() {
    assertAll(
      () -> assertEquals("44.44.44.44", InetAddress0.customAddress("inet.address.test1")),
      () -> assertTrue(List0.newArrayList("44.44.44.45", "44.44.44.46").contains(InetAddress0.customAddress("inet.address.test2"))),
      () -> assertEquals("222.222.222.222", InetAddress0.customAddress("inet.address.test3")),
      () -> assertTrue(List0.newArrayList("222.222.222.223", "222.222.222.224").contains(InetAddress0.customAddress("inet.address.test4")))
    );
  }
}
