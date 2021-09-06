package sktest.roc.persistence.simple;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.ling.test.SKUnit;
import org.shaneking.ling.test.crypto.Crypto0Unit;
import org.shaneking.ling.zero.crypto.SKC1;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.roc.persistence.entity.sql.example.UserExample;
import org.shaneking.roc.persistence.simple.SimpleUserEntity;

import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

class SimpleUserEntityTest extends SKUnit {

  @Test
  void getHaha() {
    UserExample userEntity = new SimpleUserEntity();
    userEntity.setHaha(Crypto0Unit.I_LOVE_YOU);
    assertEquals(SKC1.ENCRYPTED_PREFIX + Crypto0Unit.I_LOVE_YOU__ENCRYPTED, userEntity.getHaha());
    userEntity.setHaha(SKC1.ENCRYPTED_PREFIX + Crypto0Unit.I_LOVE_YOU);
    assertEquals(SKC1.ENCRYPTED_PREFIX + Crypto0Unit.I_LOVE_YOU, userEntity.getHaha());
  }

  @Test
  void createTableAndIndexIfNotExistSql() throws IOException {
    Files.write(tstOFiles().toPath(), new SimpleUserEntity().createTableAndIndexIfNotExistSql().getBytes());
    Assertions.assertEquals(String.join(String0.BR_LINUX, Files.readAllLines(tstOFiles().toPath())).trim(), new SimpleUserEntity().createTableAndIndexIfNotExistSql().trim());
  }

  @Test
  void entityClass() {
    assertNotNull(new SimpleUserEntity().entityClass());
  }

  @Test
  void testToString() {
    assertAll(
      () -> Assertions.assertEquals("SimpleUserEntity(super=UserExample(super=TenantedEntity(super=AbstractCacheableEntity(super=AbstractDialectSqlEntity(id=null, ver=null, dd=N, ivd=null, lmDsz=null, lmUid=null, no=null), lastModifyUser=null), tenantId=null), name=null, haha=null, mobile=null, email=null))", new SimpleUserEntity().toString()),
      () -> assertEquals("{}", OM3.writeValueAsString(new SimpleUserEntity().nullSetter()))
    );
  }
}
