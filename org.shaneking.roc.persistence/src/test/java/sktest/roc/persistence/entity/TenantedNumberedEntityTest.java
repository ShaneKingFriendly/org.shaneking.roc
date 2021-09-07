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

class TenantedNumberedEntityTest extends SKUnit {

  @Test
  void createTableAndIndexIfNotExistSql() throws IOException {
    Files.write(tstOFiles().toPath(), new TenantedNumberedEntityPrepare1().createTableAndIndexIfNotExistSql().getBytes());
    assertEquals(String.join(String0.BR_LINUX, Files.readAllLines(tstOFiles().toPath())).trim(), new TenantedNumberedEntityPrepare1().createTableAndIndexIfNotExistSql().trim());
  }

  @Test
  void testToString() {
    assertAll(
      () -> Assertions.assertEquals("TenantedNumberedEntityPrepare1(super=TenantedEntity(super=AbstractCacheableEntity(super=AbstractDialectSqlEntity(id=null, ver=null, dd=N, ivd=null, lmDsz=null, lmUid=null, no=null), lastModifyUser=null), tenantId=null))", new TenantedNumberedEntityPrepare1().toString()),
      () -> assertEquals("{}", OM3.writeValueAsString(new TenantedNumberedEntityPrepare1().nullSetter()))
    );
  }
}
