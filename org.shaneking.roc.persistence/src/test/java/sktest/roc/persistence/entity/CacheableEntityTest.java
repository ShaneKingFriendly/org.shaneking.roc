package sktest.roc.persistence.entity;

import org.junit.jupiter.api.Test;
import org.shaneking.ling.test.SKUnit;

class CacheableEntityTest extends SKUnit {
  @Test
  void test() {
    tstPrint(new Test4CacheableEntity().createTableIfNotExistSql());
  }
}
