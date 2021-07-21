package sktest.roc.persistence.entity;

import org.junit.jupiter.api.Test;
import org.shaneking.ling.jackson.databind.OM3;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ChannelizedEntityTest {

  @Test
  void testToString() {
    assertAll(
      () -> assertEquals("ChannelizedEntityPrepare(super=ChannelizedEntity(super=AbstractCacheableEntity(super=AbstractDialectSqlEntity(id=null, version=null, dd=N, invalid=null, lastModifyDateTime=null, lastModifyUserId=null, no=null), lastModifyUser=null), channelId=null))", new ChannelizedEntityPrepare().toString()),
      () -> assertEquals("{}", OM3.writeValueAsString(new ChannelizedEntityPrepare().nullSetter()))
    );
  }
}
