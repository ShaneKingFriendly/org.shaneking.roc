package sktest.roc.persistence.entity;

import org.junit.jupiter.api.Test;
import org.shaneking.ling.jackson.databind.OM3;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ChannelizedEntityTest {

  @Test
  void testToString() {
    assertAll(
      () -> assertEquals("ChannelizedEntityPrepare1(super=ChannelizedEntity(super=AbstractCacheableEntity(super=AbstractDialectSqlEntity(id=null, ver=null, dd=N, ivd=null, lmDsz=null, lmUid=null, no=null), lastModifyUser=null), channelId=null))", new ChannelizedEntityPrepare1().toString()),
      () -> assertEquals("{}", OM3.writeValueAsString(new ChannelizedEntityPrepare1().nullSetter()))
    );
  }
}
