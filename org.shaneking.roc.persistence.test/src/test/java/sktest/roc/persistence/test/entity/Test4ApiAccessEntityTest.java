package sktest.roc.persistence.test.entity;

import org.junit.jupiter.api.Test;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.roc.persistence.test.entity.Test4ApiAccessEntity;

import static org.junit.jupiter.api.Assertions.*;

class Test4ApiAccessEntityTest {

  @Test
  void entityClass() {
    assertNotNull(new Test4ApiAccessEntity().entityClass());
  }

  @Test
  void testToString() {
    assertAll(
      () -> assertEquals("Test4ApiAccessEntity(super=ApiAccessEntity(super=ChannelizedTenantedEntity(super=CacheableEntity(super=IdAdtVerEntity(super=IdAdtEntity(super=IdEntity(id=null), invalid=null, lastModifyDateTime=null, lastModifyUserId=null), version=null)), channelId=null, tenantId=null), allowUrlRegex=null, allowSignatureRegex=null, denyUrlRegex=null, denySignatureRegex=null))", new Test4ApiAccessEntity().toString()),
      () -> assertEquals("{}", OM3.writeValueAsString(new Test4ApiAccessEntity().nullSetter()))
    );
  }
}
