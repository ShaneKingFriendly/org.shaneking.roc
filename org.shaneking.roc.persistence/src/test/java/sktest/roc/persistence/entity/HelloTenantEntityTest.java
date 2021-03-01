package sktest.roc.persistence.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.ling.test.SKUnit;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HelloTenantEntityTest extends SKUnit {

  @Test
  void testToString() {
    assertAll(
      () -> Assertions.assertEquals("HelloTenantEntity(super=TenantEntity(super=NumberedEntity(super=CacheableEntity(super=AbstractIdAdtVerSqlEntity(id=null, invalid=null, lastModifyDateTime=null, lastModifyUserId=null, version=null), lastModifyUser=null), no=null), name=null, description=null))", new HelloTenantEntity().toString()),
      () -> assertEquals("{}", OM3.writeValueAsString(new HelloTenantEntity().nullSetter()))
    );
  }
}
