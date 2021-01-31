package sktest.roc.persistence.test.entity;

import org.junit.jupiter.api.Test;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.ling.test.SKUnit;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.roc.persistence.entity.ApiAccessEntity;
import org.shaneking.roc.persistence.test.entity.Test4ApiAccessEntity;

import static org.junit.jupiter.api.Assertions.*;

class Test4ApiAccessEntityTest extends SKUnit {

  @Test
  void check() {
    ApiAccessEntity apiAccessEntity = new Test4ApiAccessEntity();
    assertAll(
      () -> assertFalse(apiAccessEntity.check(null, null)),
      () -> assertFalse(apiAccessEntity.check(String0.ARY_L62, String0.ARY_L62))
    );
    apiAccessEntity.setAllowSignatureRegex(String0.ARY_HEX).setAllowUrlRegex(String0.ARY_HEX).setDenySignatureRegex(String0.ARY_HEX).setDenyUrlRegex(String0.ARY_HEX);
    assertAll(
      () -> assertFalse(apiAccessEntity.check(null, null)),
      () -> assertFalse(apiAccessEntity.check(String0.ARY_L62, String0.ARY_L62))
    );
  }

  @Test
  void entityClass() {
    assertNotNull(new Test4ApiAccessEntity().entityClass());
  }

  @Test
  void testToString() {
    assertAll(
      () -> assertEquals("Test4ApiAccessEntity(super=ApiAccessEntity(super=ChannelizedTenantedEntity(super=TenantedEntity(super=CacheableEntity(super=IdAdtVerEntity(super=IdAdtEntity(super=IdEntity(id=null), invalid=null, lastModifyDateTime=null, lastModifyUserId=null), version=null)), tenantId=null), channelId=null), allowUrlRegex=null, allowSignatureRegex=null, denyUrlRegex=null, denySignatureRegex=null))", new Test4ApiAccessEntity().toString()),
      () -> assertEquals("{}", OM3.writeValueAsString(new Test4ApiAccessEntity().nullSetter()))
    );
  }
}
