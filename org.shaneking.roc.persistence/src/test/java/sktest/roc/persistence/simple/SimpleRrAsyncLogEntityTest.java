package sktest.roc.persistence.simple;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.ling.test.SKUnit;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.roc.persistence.simple.SimpleRrAsyncLogEntity;

import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

class SimpleRrAsyncLogEntityTest extends SKUnit {

  @Test
  void createTableAndIndexIfNotExistSql() throws IOException {
    Files.write(tstOFiles().toPath(), new SimpleRrAsyncLogEntity().createTableAndIndexIfNotExistSql().getBytes());
    Assertions.assertEquals(String.join(String0.BR_LINUX, Files.readAllLines(tstOFiles().toPath())).trim(), new SimpleRrAsyncLogEntity().createTableAndIndexIfNotExistSql().trim());
  }

  @Test
  void entityClass() {
    assertNotNull(new SimpleRrAsyncLogEntity().entityClass());
  }

  @Test
  void testToString() {
    assertAll(
      () -> Assertions.assertEquals("SimpleRrAsyncLogEntity(super=RrAsyncLogExample(super=TenantedEntity(super=AbstractCacheableEntity(super=AbstractDialectSqlEntity(id=null, ver=null, dd=N, ivd=null, lmDsz=null, lmUid=null, no=null), lastModifyUser=null), tenantId=null), reqJsonStrRaw=null, ctxJsonStr=null, startDatetime=null, reqJsonStr=null, rtnJsonStr=null, rtnCode=null, rtnMsg=null, doneDatetime=null))", new SimpleRrAsyncLogEntity().toString()),
      () -> assertEquals("{}", OM3.writeValueAsString(new SimpleRrAsyncLogEntity().nullSetter()))
    );
  }
}
