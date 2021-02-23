package sktest.roc.persistence.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.ling.test.SKUnit;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.roc.persistence.entity.ApiAccessEntity;

import static org.junit.jupiter.api.Assertions.*;

class HelloApiAccessEntityTest extends SKUnit {

  @Test
  void check() {
    ApiAccessEntity apiAccessEntity = new HelloApiAccessEntity();
    assertAll(
      () -> assertFalse(apiAccessEntity.check(null, null)),
      () -> assertTrue(apiAccessEntity.check(String0.ARY_L62, String0.ARY_L62))
    );
    apiAccessEntity.setAllowSignatureRegex(String0.ARY_HEX).setAllowUrlRegex(String0.ARY_HEX).setDenySignatureRegex(String0.ARY_HEX).setDenyUrlRegex(String0.ARY_HEX);
    assertAll(
      () -> assertFalse(apiAccessEntity.check(null, null)),
      () -> assertFalse(apiAccessEntity.check(String0.ARY_L62, String0.ARY_L62))
    );
  }

  @Test
  void testToString() {
    assertAll(
      () -> Assertions.assertEquals("HelloApiAccessEntity(super=ApiAccessEntity(super=ChannelizedEntity(super=TenantedEntity(super=CacheableEntity(super=IdAdtVerSqlEntitiesTemplate(id=null, invalid=null, lastModifyDateTime=null, lastModifyUserId=null, version=null), lastModifyUser=null), tenantId=null), channelId=null), allowUrlRegex=null, allowSignatureRegex=null, denyUrlRegex=null, denySignatureRegex=null))", new HelloApiAccessEntity().toString()),
      () -> assertEquals("{}", OM3.writeValueAsString(new HelloApiAccessEntity().nullSetter()))
    );
  }
}
