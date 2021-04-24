package sktest.roc.persistence.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.ling.test.SKUnit;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HelloAuditLogEntityTest extends SKUnit {

  @Test
  void testToString() {
    assertAll(
      () -> Assertions.assertEquals("HelloAuditLogEntity(super=AuditLogEntity(super=TenantedChannelizedEntity(super=ChannelizedEntity(super=CacheableEntity(super=AbstractIdAdtVerSqlEntity(id=null, invalid=null, lastModifyDateTime=null, lastModifyUserId=null, version=null), lastModifyUser=null), channelId=null), tenantId=null), proxyChannelId=null, tracingNo=null, reqDatetime=null, reqIps=null, reqUserId=null, reqJsonStrRaw=null, reqJsonStr=null, reqUrl=null, reqSignature=null, cached=null, respJsonStr=null, respJsonStrCtx=null, respJsonStrRaw=null, respIps=null, respDatetime=null))", new HelloAuditLogEntity().toString()),
      () -> assertEquals("{}", OM3.writeValueAsString(new HelloAuditLogEntity().nullSetter()))
    );
  }
}
