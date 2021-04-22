package sktest.roc.persistence.hello.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.ling.test.SKUnit;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.roc.persistence.hello.entity.HelloAuditLogEntity;

import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

class HelloAuditLogEntityTest extends SKUnit {

  @Test
  void createTableIfNotExistSql() throws IOException {
//    Files.write(tstOFiles().toPath(), new HelloAuditLogEntity().createTableIfNotExistSql().getBytes());
    Assertions.assertEquals(String.join(String0.BR_LINUX, Files.readAllLines(tstOFiles().toPath())).trim(), new HelloAuditLogEntity().createTableIfNotExistSql().trim());
  }

  @Test
  void entityClass() {
    assertNotNull(new HelloAuditLogEntity().entityClass());
  }

  @Test
  void testToString() {
    assertAll(
      () -> Assertions.assertEquals("HelloAuditLogEntity(super=AuditLogEntity(super=TenantChannelizedEntity(super=TenantedEntity(super=CacheableEntity(super=AbstractIdAdtVerSqlEntity(id=null, invalid=null, lastModifyDateTime=null, lastModifyUserId=null, version=null), lastModifyUser=null), tenantId=null), channelId=null), proxyChannelId=null, tracingNo=null, reqDatetime=null, reqIps=null, reqUserId=null, reqJsonStrRaw=null, reqJsonStr=null, reqUrl=null, reqSignature=null, cached=null, respJsonStr=null, respJsonStrCtx=null, respJsonStrRaw=null, respIps=null, respDatetime=null))", new HelloAuditLogEntity().toString()),
      () -> assertEquals("{}", OM3.writeValueAsString(new HelloAuditLogEntity().nullSetter()))
    );
  }
}
