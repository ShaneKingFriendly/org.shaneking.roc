package sktest.roc.persistence.hello;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.ling.test.SKUnit;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.roc.persistence.hello.HelloRrAsyncLogEntity;

import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

class HelloRrAsyncLogEntityTest extends SKUnit {

  @Test
  void createTableAndIndexIfNotExistSql() throws IOException {
    Files.write(tstOFiles().toPath(), new HelloRrAsyncLogEntity().createTableAndIndexIfNotExistSql().getBytes());
    Assertions.assertEquals(String.join(String0.BR_LINUX, Files.readAllLines(tstOFiles().toPath())).trim(), new HelloRrAsyncLogEntity().createTableAndIndexIfNotExistSql().trim());
  }

  @Test
  void entityClass() {
    assertNotNull(new HelloRrAsyncLogEntity().entityClass());
  }

  @Test
  void testToString() {
    assertAll(
      () -> Assertions.assertEquals("HelloRrAsyncLogEntity(super=RrAsyncLogExample(super=TenantedEntity(super=AbstractCacheableEntity(super=AbstractDialectSqlEntity(id=null, ver=null, dd=N, ivd=null, lmDsz=null, lmUid=null, no=null), lastModifyUser=null), tenantId=null), reqJsonStrRaw=null, ctxJsonStr=null, startDatetime=null, reqJsonStr=null, rtnJsonStr=null, rtnCode=null, rtnMsg=null, doneDatetime=null))", new HelloRrAsyncLogEntity().toString()),
      () -> assertEquals("{}", OM3.writeValueAsString(new HelloRrAsyncLogEntity().nullSetter()))
    );
  }
}
