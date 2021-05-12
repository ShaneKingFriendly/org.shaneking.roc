package sktest.roc.persistence.entity.sql;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.ling.test.SKUnit;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HelloApiAccessSignatureEntityTest extends SKUnit {

  @Test
  void testToString() {
    assertAll(
      () -> Assertions.assertEquals("HelloApiAccessSignatureEntity(super=ApiAccessSignatureExample(super=TenantedChannelizedEntity(super=AbstractCacheableEntity(super=AbstractDialectSqlEntity(id=null, dd=N, no=null, invalid=null, lastModifyDateTime=null, lastModifyUserId=null, version=null), lastModifyUser=null), channelId=null, tenantId=null), op=null, signature=null))", new HelloApiAccessSignatureEntity().toString()),
      () -> assertEquals("{}", OM3.writeValueAsString(new HelloApiAccessSignatureEntity().nullSetter()))
    );
  }
}
