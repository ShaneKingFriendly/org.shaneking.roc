package sktest.roc.persistence.hello.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.ling.test.SKUnit;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.roc.persistence.hello.entity.HelloApiAccessSignatureEntity;

import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

class HelloApiAccessSignatureEntityTest extends SKUnit {

  @Test
  void createTableIfNotExistSql() throws IOException {
    Files.write(tstOFiles().toPath(), new HelloApiAccessSignatureEntity().createTableIfNotExistSql().getBytes());
    Assertions.assertEquals(String.join(String0.BR_LINUX, Files.readAllLines(tstOFiles().toPath())).trim(), new HelloApiAccessSignatureEntity().createTableIfNotExistSql().trim());
  }

  @Test
  void entityClass() {
    assertNotNull(new HelloApiAccessSignatureEntity().entityClass());
  }

  @Test
  void testToString() {
    assertAll(
      () -> Assertions.assertEquals("HelloApiAccessSignatureEntity(super=ApiAccessSignatureEntity(super=ApiAccessOpEntity(super=TenantedChannelizedEntity(super=CacheableEntity(super=AbstractDialectSqlEntity(id=null, dd=N, no=null, invalid=null, lastModifyDateTime=null, lastModifyUserId=null, version=null), lastModifyUser=null), channelId=null, tenantId=null), op=null), signature=null))", new HelloApiAccessSignatureEntity().toString()),
      () -> assertEquals("{}", OM3.writeValueAsString(new HelloApiAccessSignatureEntity().nullSetter()))
    );
  }
}
