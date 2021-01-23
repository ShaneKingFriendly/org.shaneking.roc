package sktest.roc.test;

import org.junit.jupiter.api.Test;
import org.shaneking.roc.test.SKSpringMvcUnit;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class SKSpringMvcUnitTest extends SKSpringMvcUnit {
  @Test
  void test() throws Exception {
    assertNotNull(performJJ("/hello/world"));
  }
}
