package sktest.roc.persistence.test.entity;

import org.junit.jupiter.api.Test;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.ling.test.SKUnit;
import org.shaneking.roc.persistence.test.entity.Test4TenantEntity;

import static org.junit.jupiter.api.Assertions.*;

class Test4TenantEntityTest extends SKUnit {

  @Test
  void entityClass() {
    assertNotNull(new Test4TenantEntity().entityClass());
  }

  @Test
  void testToString() {
    assertAll(
      () -> assertEquals("Test4TenantEntity(super=TenantEntity(super=CacheableEntity(super=IdAdtVerEntity(super=IdAdtEntity(super=IdEntity(id=null), invalid=null, lastModifyDateTime=null, lastModifyUserId=null), version=null)), name=null, description=null))", new Test4TenantEntity().toString()),
      () -> assertEquals("{}", OM3.writeValueAsString(new Test4TenantEntity().nullSetter()))
    );
  }
}
