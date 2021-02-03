package sktest.roc.persistence.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.ling.test.SKUnit;
import org.shaneking.ling.test.crypto.Crypto0Unit;
import org.shaneking.ling.zero.crypto.Crypto0;
import org.shaneking.roc.persistence.entity.UserEntity;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HelloUserEntityTest extends SKUnit {

  @Test
  void getHaha() {
    UserEntity userEntity = new HelloUserEntity();
    userEntity.setHaha(Crypto0Unit.I_LOVE_YOU);
    assertEquals(Crypto0.ENCRYPTED_PREFIX + Crypto0Unit.I_LOVE_YOU__ENCRYPTED, userEntity.getHaha());
    userEntity.setHaha(Crypto0.ENCRYPTED_PREFIX + Crypto0Unit.I_LOVE_YOU);
    assertEquals(Crypto0.ENCRYPTED_PREFIX + Crypto0Unit.I_LOVE_YOU, userEntity.getHaha());
  }

  @Test
  void testToString() {
    assertAll(
      () -> Assertions.assertEquals("HelloUserEntity(super=UserEntity(super=TenantedEntity(super=CacheableEntity(super=IdAdtVerEntity(super=IdAdtEntity(super=IdEntity(id=null), invalid=null, lastModifyDateTime=null, lastModifyUserId=null), version=null)), tenantId=null), no=null, name=null, haha=null, mobile=null, email=null))", new HelloUserEntity().toString()),
      () -> assertEquals("{}", OM3.writeValueAsString(new HelloUserEntity().nullSetter()))
    );
  }
}
