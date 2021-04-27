package sktest.roc.persistence.hello.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.ling.test.SKUnit;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.roc.persistence.hello.entity.HelloApiAccessRegexEntity;

import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

class HelloApiAccessRegexEntityTest extends SKUnit {

  @Test
  void createTableIfNotExistSql() throws IOException {
    Files.write(tstOFiles().toPath(), new HelloApiAccessRegexEntity().createTableIfNotExistSql().getBytes());
    Assertions.assertEquals(String.join(String0.BR_LINUX, Files.readAllLines(tstOFiles().toPath())).trim(), new HelloApiAccessRegexEntity().createTableIfNotExistSql().trim());
  }

  @Test
  void entityClass() {
    assertNotNull(new HelloApiAccessRegexEntity().entityClass());
  }

  @Test
  void testToString() {
    assertAll(
      () -> Assertions.assertEquals("HelloApiAccessRegexEntity(super=ApiAccessRegexEntity(super=TenantedChannelizedEntity(super=CacheableEntity(super=AbstractDialectSqlEntity(id=null, dd=N, no=null, invalid=null, lastModifyDateTime=null, lastModifyUserId=null, version=null), lastModifyUser=null), channelId=null, tenantId=null), allowUrlRegex=null, allowSignatureRegex=null, denyUrlRegex=null, denySignatureRegex=null))", new HelloApiAccessRegexEntity().toString()),
      () -> assertEquals("{}", OM3.writeValueAsString(new HelloApiAccessRegexEntity().nullSetter()))
    );
  }
}
