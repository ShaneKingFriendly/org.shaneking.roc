package sktest.roc.persistence.entity.sql;

import org.junit.jupiter.api.Test;
import org.shaneking.ling.zero.lang.Integer0;

import static org.junit.jupiter.api.Assertions.*;

class ChannelEntitiesTest {

  @Test
  void getDszSeconds() {
    Integer i = null;
    assertAll(
      () -> assertThrows(NullPointerException.class, () -> assertFalse(i > 0)),
      () -> assertFalse(Integer0.null2Zero(i) > 0)
    );
  }
}
