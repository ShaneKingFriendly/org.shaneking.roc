package sktest.roc.persistence.entity.sql;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.ling.test.SKUnit;
import org.shaneking.ling.test.crypto.Crypto0Unit;
import org.shaneking.ling.zero.crypto.SKC1;
import org.shaneking.roc.persistence.entity.sql.UserEntity;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HelloUserEntityTest extends SKUnit {

  @Test
  void getHaha() {
    UserEntity userEntity = new HelloUserEntity();
    userEntity.setHaha(Crypto0Unit.I_LOVE_YOU);
    assertEquals(SKC1.ENCRYPTED_PREFIX + Crypto0Unit.I_LOVE_YOU__ENCRYPTED, userEntity.getHaha());
    userEntity.setHaha(SKC1.ENCRYPTED_PREFIX + Crypto0Unit.I_LOVE_YOU);
    assertEquals(SKC1.ENCRYPTED_PREFIX + Crypto0Unit.I_LOVE_YOU, userEntity.getHaha());
  }

  @Test
  void testToString() {
    assertAll(
      () -> Assertions.assertEquals("HelloUserEntity(super=UserEntity(super=TenantedEntity(super=CacheableEntity(super=AbstractDialectSqlEntity(id=null, dd=N, no=null, invalid=null, lastModifyDateTime=null, lastModifyUserId=null, version=null), lastModifyUser=null), tenantId=null), name=null, haha=null, mobile=null, email=null))", new HelloUserEntity().toString()),
      () -> assertEquals("{}", OM3.writeValueAsString(new HelloUserEntity().nullSetter()))
    );
  }
}
