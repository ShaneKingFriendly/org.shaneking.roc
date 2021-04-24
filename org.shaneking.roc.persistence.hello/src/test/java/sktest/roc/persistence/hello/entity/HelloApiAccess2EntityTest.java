package sktest.roc.persistence.hello.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.ling.test.SKUnit;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.roc.persistence.hello.entity.HelloApiAccess2Entity;

import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

class HelloApiAccess2EntityTest extends SKUnit {

  @Test
  void createTableIfNotExistSql() throws IOException {
    Files.write(tstOFiles().toPath(), new HelloApiAccess2Entity().createTableIfNotExistSql().getBytes());
    Assertions.assertEquals(String.join(String0.BR_LINUX, Files.readAllLines(tstOFiles().toPath())).trim(), new HelloApiAccess2Entity().createTableIfNotExistSql().trim());
  }

  @Test
  void entityClass() {
    assertNotNull(new HelloApiAccess2Entity().entityClass());
  }

  @Test
  void testToString() {
    assertAll(
      () -> Assertions.assertEquals("HelloApiAccess2Entity(super=ApiAccess2Entity(super=TenantedChannelizedEntity(super=ChannelizedEntity(super=CacheableEntity(super=AbstractIdAdtVerSqlEntity(id=null, invalid=null, lastModifyDateTime=null, lastModifyUserId=null, version=null), lastModifyUser=null), channelId=null), tenantId=null), allowUrl=null, denyUrl=null))", new HelloApiAccess2Entity().toString()),
      () -> assertEquals("{}", OM3.writeValueAsString(new HelloApiAccess2Entity().nullSetter()))
    );
  }
}
