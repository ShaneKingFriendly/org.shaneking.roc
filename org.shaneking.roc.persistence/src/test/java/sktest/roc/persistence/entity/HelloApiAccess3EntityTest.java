package sktest.roc.persistence.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.ling.test.SKUnit;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HelloApiAccess3EntityTest extends SKUnit {

  @Test
  void testToString() {
    assertAll(
      () -> Assertions.assertEquals("HelloApiAccess3Entity(super=ApiAccess3Entity(super=TenantChannelizedEntity(super=TenantedEntity(super=CacheableEntity(super=AbstractIdAdtVerSqlEntity(id=null, invalid=null, lastModifyDateTime=null, lastModifyUserId=null, version=null), lastModifyUser=null), tenantId=null), channelId=null), allowSignature=null, denySignature=null))", new HelloApiAccess3Entity().toString()),
      () -> assertEquals("{}", OM3.writeValueAsString(new HelloApiAccess3Entity().nullSetter()))
    );
  }
}
