package sktest.roc.persistence.test.entity;

import org.junit.jupiter.api.Test;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.ling.test.SKUnit;
import org.shaneking.roc.persistence.test.entity.Test4ChannelEntity;

import static org.junit.jupiter.api.Assertions.*;

class Test4ChannelEntityTest extends SKUnit {

  @Test
  void entityClass() {
    assertNotNull(new Test4ChannelEntity().entityClass());
  }

  @Test
  void testToString() {
    assertAll(
      () -> assertEquals("Test4ChannelEntity(super=ChannelEntity(super=TenantedEntity(super=CacheableEntity(super=IdAdtVerEntity(super=IdAdtEntity(super=IdEntity(id=null), invalid=null, lastModifyDateTime=null, lastModifyUserId=null), version=null)), tenantId=null), name=null, description=null, tokenValue=null, tokenForce=null, tokenAlgorithmType=null, tokenValueType=null))", new Test4ChannelEntity().toString()),
      () -> assertEquals("{}", OM3.writeValueAsString(new Test4ChannelEntity().nullSetter()))
    );
  }
}
