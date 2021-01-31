package sktest.roc.persistence.test.entity;

import org.junit.jupiter.api.Test;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.roc.persistence.test.entity.Test4UserEntity;

import static org.junit.jupiter.api.Assertions.*;

class Test4UserEntityTest {

  @Test
  void entityClass() {
    assertNotNull(new Test4UserEntity().entityClass());
  }

  @Test
  void testToString() {
    assertAll(
      () -> assertEquals("Test4UserEntity(super=UserEntity(super=TenantedEntity(super=CacheableEntity(super=IdAdtVerEntity(super=IdAdtEntity(super=IdEntity(id=null), invalid=null, lastModifyDateTime=null, lastModifyUserId=null), version=null)), tenantId=null), haha=null, mobile=null, name=null, email=null))", new Test4UserEntity().toString()),
      () -> assertEquals("{}", OM3.writeValueAsString(new Test4UserEntity().nullSetter()))
    );
  }
}
