package sktest.roc.persistence.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.ling.test.SKUnit;
import org.shaneking.ling.zero.lang.String0;

import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HelloTenantedNumberedEntityTest extends SKUnit {

  @Test
  void createTableAndIndexIfNotExistSql() throws IOException {
    Files.write(tstOFiles().toPath(), new HelloTenantedNumberedEntity().createTableAndIndexIfNotExistSql().getBytes());
    assertEquals(String.join(String0.BR_LINUX, Files.readAllLines(tstOFiles().toPath())).trim(), new HelloTenantedNumberedEntity().createTableAndIndexIfNotExistSql().trim());
  }

  @Test
  void testToString() {
    assertAll(
      () -> Assertions.assertEquals("HelloTenantedNumberedEntity(super=TenantedEntity(super=AbstractCacheableEntity(super=AbstractDialectSqlEntity(id=null, dd=N, no=null, invalid=null, lastModifyDateTime=null, lastModifyUserId=null, version=null), lastModifyUser=null), tenantId=null))", new HelloTenantedNumberedEntity().toString()),
      () -> assertEquals("{}", OM3.writeValueAsString(new HelloTenantedNumberedEntity().nullSetter()))
    );
  }
}
