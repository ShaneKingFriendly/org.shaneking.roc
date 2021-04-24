package sktest.roc.persistence.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.ling.test.SKUnit;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HelloTenantNumberedEntityTest extends SKUnit {

  @Test
  void testToString() {
    assertAll(
      () -> Assertions.assertEquals("HelloTenantNumberedEntity(super=TenantNumberedEntity(super=NumberedEntity(super=CacheableEntity(super=AbstractIdAdtVerSqlEntity(id=null, invalid=null, lastModifyDateTime=null, lastModifyUserId=null, version=null), lastModifyUser=null), no=null), tenantId=null))", new HelloTenantNumberedEntity().toString()),
      () -> assertEquals("{}", OM3.writeValueAsString(new HelloTenantNumberedEntity().nullSetter()))
    );
  }
}
