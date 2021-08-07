package sktest.roc.persistence.hello;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.ling.test.SKUnit;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.roc.persistence.hello.HelloRrAuditLogEntity;

import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

class HelloRrAuditLogEntityTest extends SKUnit {

  @Test
  void createTableAndIndexIfNotExistSql() throws IOException {
    Files.write(tstOFiles().toPath(), new HelloRrAuditLogEntity().createTableAndIndexIfNotExistSql().getBytes());
    Assertions.assertEquals(String.join(String0.BR_LINUX, Files.readAllLines(tstOFiles().toPath())).trim(), new HelloRrAuditLogEntity().createTableAndIndexIfNotExistSql().trim());
  }

  @Test
  void entityClass() {
    assertNotNull(new HelloRrAuditLogEntity().entityClass());
  }

  @Test
  void testToString() {
    assertAll(
      () -> Assertions.assertEquals("HelloRrAuditLogEntity(super=RrAuditLogExample(super=TenantedChannelizedEntity(super=AbstractCacheableEntity(super=AbstractDialectSqlEntity(id=null, version=null, dd=N, invalid=null, lastModifyDateTime=null, lastModifyUserId=null, no=null), lastModifyUser=null), channelId=null, tenantId=null), tracingNo=null, reqDatetime=null, reqIps=null, reqUserId=null, reqJsonStrRaw=null, reqJsonStr=null, reqUrl=null, reqSignature=null, cached=null, respJsonStr=null, respJsonStrCtx=null, respJsonStrRaw=null, respIps=null, respDatetime=null))", new HelloRrAuditLogEntity().toString()),
      () -> assertEquals("{}", OM3.writeValueAsString(new HelloRrAuditLogEntity().nullSetter()))
    );
  }
}
