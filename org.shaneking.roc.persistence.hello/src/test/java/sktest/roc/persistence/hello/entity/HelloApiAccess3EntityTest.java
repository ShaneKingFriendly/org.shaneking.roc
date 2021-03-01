package sktest.roc.persistence.hello.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.ling.test.SKUnit;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.roc.persistence.hello.entity.HelloApiAccess3Entity;

import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

class HelloApiAccess3EntityTest extends SKUnit {

  @Test
  void createTableIfNotExistSql() throws IOException {
//    Files.write(tstOFiles().toPath(), new HelloApiAccessEntity().createTableIfNotExistSql().getBytes());
    Assertions.assertEquals(String.join(String0.BR_LINUX, Files.readAllLines(tstOFiles().toPath())).trim(), new HelloApiAccess3Entity().createTableIfNotExistSql().trim());
  }

  @Test
  void entityClass() {
    assertNotNull(new HelloApiAccess3Entity().entityClass());
  }

  @Test
  void testToString() {
    assertAll(
      () -> Assertions.assertEquals("HelloApiAccess3Entity(super=ApiAccess3Entity(super=ChannelizedEntity(super=TenantedEntity(super=CacheableEntity(super=AbstractIdAdtVerSqlEntity(id=null, invalid=null, lastModifyDateTime=null, lastModifyUserId=null, version=null), lastModifyUser=null), tenantId=null), channelId=null), allowSignature=null, denySignature=null))", new HelloApiAccess3Entity().toString()),
      () -> assertEquals("{}", OM3.writeValueAsString(new HelloApiAccess3Entity().nullSetter()))
    );
  }
}
