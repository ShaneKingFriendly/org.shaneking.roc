package sktest.roc.persistence.test.entity;

import org.junit.jupiter.api.Test;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.roc.persistence.test.entity.Test4AuditLogEntity;

import static org.junit.jupiter.api.Assertions.*;

class Test4AuditLogEntityTest {

  @Test
  void entityClass() {
    assertNotNull(new Test4AuditLogEntity().entityClass());
  }

  @Test
  void testToString() {
    assertAll(
      () -> assertEquals("Test4AuditLogEntity(super=AuditLogEntity(super=ChannelizedTenantedEntity(super=CacheableEntity(super=IdAdtVerEntity(super=IdAdtEntity(super=IdEntity(id=null), invalid=null, lastModifyDateTime=null, lastModifyUserId=null), version=null)), channelId=null, tenantId=null), tracingId=null, reqDatetime=null, reqIps=null, reqUserId=null, reqJsonStrRaw=null, reqJsonStr=null, reqUrl=null, reqSignature=null, cached=null, respJsonStr=null, respJsonStrCtx=null, respJsonStrRaw=null, respIps=null, respDatetime=null))", new Test4AuditLogEntity().toString()),
      () -> assertEquals("{}", OM3.writeValueAsString(new Test4AuditLogEntity().nullSetter()))
    );
  }
}
