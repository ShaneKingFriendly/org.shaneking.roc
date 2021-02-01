package sktest.roc.persistence.hello.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.ling.test.SKUnit;
import org.shaneking.ling.test.crypto.Crypto0Unit;
import org.shaneking.ling.zero.crypto.Crypto0;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.roc.persistence.entity.UserEntity;
import org.shaneking.roc.persistence.hello.entity.HelloUserEntity;

import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

class HelloUserEntityTest extends SKUnit {

  @Test
  void createTableIfNotExistSql() throws IOException {
    Files.write(tstOFiles().toPath(), new HelloUserEntity().createTableIfNotExistSql().getBytes());
    Assertions.assertEquals(String.join(String0.BR_LINUX, Files.readAllLines(tstOFiles().toPath())).trim(), new HelloUserEntity().createTableIfNotExistSql().trim());
  }

  @Test
  void entityClass() {
    assertNotNull(new HelloUserEntity().entityClass());
  }

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
      () -> Assertions.assertEquals("HelloUserEntity(super=UserEntity(super=TenantedEntity(super=CacheableEntity(super=IdAdtVerEntity(super=IdAdtEntity(super=IdEntity(id=null), invalid=null, lastModifyDateTime=null, lastModifyUserId=null), version=null)), tenantId=null), haha=null, mobile=null, name=null, email=null))", new HelloUserEntity().toString()),
      () -> assertEquals("{}", OM3.writeValueAsString(new HelloUserEntity().nullSetter()))
    );
  }
}
