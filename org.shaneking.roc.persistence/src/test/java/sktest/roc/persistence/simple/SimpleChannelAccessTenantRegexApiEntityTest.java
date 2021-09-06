package sktest.roc.persistence.simple;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.ling.test.SKUnit;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.roc.persistence.entity.sql.example.ChannelAccessTenantRegexApiExample;
import org.shaneking.roc.persistence.simple.SimpleChannelAccessTenantRegexApiEntity;

import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

class SimpleChannelAccessTenantRegexApiEntityTest extends SKUnit {

  @Test
  void check() {
    ChannelAccessTenantRegexApiExample apiAccessEntity = new SimpleChannelAccessTenantRegexApiEntity();
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
    Files.write(tstOFiles().toPath(), new SimpleChannelAccessTenantRegexApiEntity().createTableAndIndexIfNotExistSql().getBytes());
    Assertions.assertEquals(String.join(String0.BR_LINUX, Files.readAllLines(tstOFiles().toPath())).trim(), new SimpleChannelAccessTenantRegexApiEntity().createTableAndIndexIfNotExistSql().trim());
  }

  @Test
  void entityClass() {
    assertNotNull(new SimpleChannelAccessTenantRegexApiEntity().entityClass());
  }

  @Test
  void testToString() {
    assertAll(
      () -> Assertions.assertEquals("SimpleChannelAccessTenantRegexApiEntity(super=ChannelAccessTenantRegexApiExample(super=TenantedChannelizedEntity(super=AbstractCacheableEntity(super=AbstractDialectSqlEntity(id=null, ver=null, dd=N, ivd=null, lmDsz=null, lmUid=null, no=null), lastModifyUser=null), channelId=null, tenantId=null), allowUrlRegex=null, allowSignatureRegex=null, denyUrlRegex=null, denySignatureRegex=null))", new SimpleChannelAccessTenantRegexApiEntity().toString()),
      () -> assertEquals("{}", OM3.writeValueAsString(new SimpleChannelAccessTenantRegexApiEntity().nullSetter()))
    );
  }
}
