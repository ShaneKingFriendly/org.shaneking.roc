package sktest.roc.persistence.test.entity;

import org.junit.jupiter.api.Test;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.ling.test.SKUnit;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.roc.persistence.test.entity.Test4AuditLogEntity;

import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

class Test4AuditLogEntityTest extends SKUnit {

  @Test
  void createTableIfNotExistSql() throws IOException {
//    Files.write(tstOFiles().toPath(), new Test4AuditLogEntity().createTableIfNotExistSql().getBytes());
    assertEquals(String.join(String0.BR_LINUX, Files.readAllLines(tstOFiles().toPath())).trim(), new Test4AuditLogEntity().createTableIfNotExistSql().trim());
  }

  @Test
  void entityClass() {
    assertNotNull(new Test4AuditLogEntity().entityClass());
  }

  @Test
  void testToString() {
    assertAll(
      () -> assertEquals("Test4AuditLogEntity(super=AuditLogEntity(super=ChannelizedTenantedEntity(super=TenantedEntity(super=CacheableEntity(super=IdAdtVerEntity(super=IdAdtEntity(super=IdEntity(id=null), invalid=null, lastModifyDateTime=null, lastModifyUserId=null), version=null)), tenantId=null), channelId=null), tracingId=null, reqDatetime=null, reqIps=null, reqUserId=null, reqJsonStrRaw=null, reqJsonStr=null, reqUrl=null, reqSignature=null, cached=null, respJsonStr=null, respJsonStrCtx=null, respJsonStrRaw=null, respIps=null, respDatetime=null))", new Test4AuditLogEntity().toString()),
      () -> assertEquals("{}", OM3.writeValueAsString(new Test4AuditLogEntity().nullSetter()))
    );
  }
}
