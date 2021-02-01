package sktest.roc.persistence.dao;

import org.junit.jupiter.api.Test;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.ling.test.SKUnit;
import org.shaneking.roc.persistence.dao.ChannelizedTenantedCacheableDao;
import org.shaneking.roc.persistence.dao.TenantedCacheableDao;
import org.shaneking.roc.persistence.hello.entity.HelloAuditLogEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TenantedCacheableDaoTest extends SKUnit {
  @Test
  void protectInsert() {
    HelloAuditLogEntity auditLogEntity = new HelloAuditLogEntity();
    ChannelizedTenantedCacheableDao.protectInsert(auditLogEntity, "channelId");
    ChannelizedTenantedCacheableDao.protectInsert(auditLogEntity, "channelId1,channelId2");
    TenantedCacheableDao.protectInsert(auditLogEntity, "tenantId");
    TenantedCacheableDao.protectInsert(auditLogEntity, "tenantId1,tenantId2");
    assertEquals("{\"tenantId\":\"tenantId1,tenantId2\",\"channelId\":\"channelId1,channelId2\"}", OM3.writeValueAsString(auditLogEntity));
  }

  @Test
  void protectUpdate() {
    HelloAuditLogEntity auditLogEntity = new HelloAuditLogEntity();
    ChannelizedTenantedCacheableDao.protectUpdate(auditLogEntity, "channelId");
    ChannelizedTenantedCacheableDao.protectUpdate(auditLogEntity, "channelId1,channelId2");
    TenantedCacheableDao.protectUpdate(auditLogEntity, "tenantId");
    TenantedCacheableDao.protectUpdate(auditLogEntity, "tenantId1,tenantId2");
    assertEquals("{\"whereConditions\":{\"tenantId\":{\"op\":\"=\",\"cs\":\"tenantId1,tenantId2\"},\"channelId\":{\"op\":\"=\",\"cs\":\"channelId1,channelId2\"}},\"tenantId\":\"tenantId1,tenantId2\",\"channelId\":\"channelId1,channelId2\"}", OM3.writeValueAsString(auditLogEntity));
  }

  @Test
  void protectSelect() {
    HelloAuditLogEntity auditLogEntity = new HelloAuditLogEntity();
    ChannelizedTenantedCacheableDao.protectSelect(auditLogEntity, "channelId");
    ChannelizedTenantedCacheableDao.protectSelect(auditLogEntity, "channelId1,channelId2");
    TenantedCacheableDao.protectSelect(auditLogEntity, "tenantId");
    TenantedCacheableDao.protectSelect(auditLogEntity, "tenantId1,tenantId2");
    assertEquals("{\"whereConditions\":{\"tenantId\":{\"op\":\"in\",\"cl\":[\"tenantId1\",\"tenantId2\"],\"cs\":\"tenantId\"},\"channelId\":{\"op\":\"in\",\"cl\":[\"channelId1\",\"channelId2\"],\"cs\":\"channelId\"}}}", OM3.writeValueAsString(auditLogEntity));
  }
}
