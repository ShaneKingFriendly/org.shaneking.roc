package sktest.roc.test;

import org.junit.jupiter.api.Test;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.roc.test.SKSpringUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SKSpringUnitTest extends SKSpringUnit {
  @Test
  void test() {
    assertEquals(String0.ARY_DEC, String0.DIGITAL);
  }
}
