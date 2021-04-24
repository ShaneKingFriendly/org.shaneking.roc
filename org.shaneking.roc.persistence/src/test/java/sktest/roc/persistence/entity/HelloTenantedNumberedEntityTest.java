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
  void createTableIfNotExistSql() throws IOException {
    Files.write(tstOFiles().toPath(), new HelloTenantedNumberedEntity().createTableIfNotExistSql().getBytes());
    assertEquals(String.join(String0.BR_LINUX, Files.readAllLines(tstOFiles().toPath())).trim(), new HelloTenantedNumberedEntity().createTableIfNotExistSql().trim());
  }

  @Test
  void testToString() {
    assertAll(
      () -> Assertions.assertEquals("HelloTenantedNumberedEntity(super=TenantedNumberedEntity(super=NumberedEntity(super=CacheableEntity(super=AbstractIdAdtVerSqlEntity(id=null, invalid=null, lastModifyDateTime=null, lastModifyUserId=null, version=null), lastModifyUser=null), no=null), tenantId=null))", new HelloTenantedNumberedEntity().toString()),
      () -> assertEquals("{}", OM3.writeValueAsString(new HelloTenantedNumberedEntity().nullSetter()))
    );
  }
}
