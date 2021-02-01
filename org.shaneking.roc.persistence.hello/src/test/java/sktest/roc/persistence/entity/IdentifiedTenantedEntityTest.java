package sktest.roc.persistence.entity;

import org.junit.jupiter.api.Test;
import org.shaneking.ling.jackson.databind.OM3;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class IdentifiedTenantedEntityTest {

  @Test
  void testToString() {
    assertAll(
      () -> assertEquals("HelloIdentifiedTenantedEntity(super=IdentifiedTenantedEntity(super=TenantedEntity(super=CacheableEntity(super=IdAdtVerEntity(super=IdAdtEntity(super=IdEntity(id=null), invalid=null, lastModifyDateTime=null, lastModifyUserId=null), version=null)), tenantId=null), bid=null))", new HelloIdentifiedTenantedEntity().toString()),
      () -> assertEquals("{}", OM3.writeValueAsString(new HelloIdentifiedTenantedEntity().nullSetter()))
    );
  }
}
