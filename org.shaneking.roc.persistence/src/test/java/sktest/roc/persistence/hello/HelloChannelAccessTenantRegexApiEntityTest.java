package sktest.roc.persistence.hello;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.ling.test.SKUnit;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.roc.persistence.entity.sql.example.ChannelAccessTenantRegexApiExample;
import org.shaneking.roc.persistence.hello.HelloChannelAccessTenantRegexApiEntity;

import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

class HelloChannelAccessTenantRegexApiEntityTest extends SKUnit {

  @Test
  void check() {
    ChannelAccessTenantRegexApiExample apiAccessEntity = new HelloChannelAccessTenantRegexApiEntity();
    assertAll(
      () -> assertFalse(apiAccessEntity.check(null, null)),
      () -> assertTrue(apiAccessEntity.check(String0.ARY_L62, String0.ARY_L62))
    );
    apiAccessEntity.setAllowSignatureRegex(String0.ARY_HEX).setAllowUrlRegex(String0.ARY_HEX).setDenySignatureRegex(String0.ARY_HEX).setDenyUrlRegex(String0.ARY_HEX);
    assertAll(
      () -> assertFalse(apiAccessEntity.check(null, null)),
      () -> assertFalse(apiAccessEntity.check(String0.ARY_L62, String0.ARY_L62))
    );
  }

  @Test
  void createTableAndIndexIfNotExistSql() throws IOException {
    Files.write(tstOFiles().toPath(), new HelloChannelAccessTenantRegexApiEntity().createTableAndIndexIfNotExistSql().getBytes());
    Assertions.assertEquals(String.join(String0.BR_LINUX, Files.readAllLines(tstOFiles().toPath())).trim(), new HelloChannelAccessTenantRegexApiEntity().createTableAndIndexIfNotExistSql().trim());
  }

  @Test
  void entityClass() {
    assertNotNull(new HelloChannelAccessTenantRegexApiEntity().entityClass());
  }

  @Test
  void testToString() {
    assertAll(
      () -> Assertions.assertEquals("HelloChannelAccessTenantRegexApiEntity(super=ChannelAccessTenantRegexApiExample(super=TenantedChannelizedEntity(super=AbstractCacheableEntity(super=AbstractDialectSqlEntity(id=null, version=null, dd=N, invalid=null, lastModifyDateTime=null, lastModifyUserId=null, no=null), lastModifyUser=null), channelId=null, tenantId=null), allowUrlRegex=null, allowSignatureRegex=null, denyUrlRegex=null, denySignatureRegex=null))", new HelloChannelAccessTenantRegexApiEntity().toString()),
      () -> assertEquals("{}", OM3.writeValueAsString(new HelloChannelAccessTenantRegexApiEntity().nullSetter()))
    );
  }
}
