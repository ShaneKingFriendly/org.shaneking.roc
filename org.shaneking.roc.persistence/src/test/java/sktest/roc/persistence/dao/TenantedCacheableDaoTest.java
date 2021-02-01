package sktest.roc.persistence.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.ling.zero.lang.ZeroException;
import org.shaneking.ling.zero.util.List0;
import org.shaneking.roc.persistence.dao.ChannelizedTenantedCacheableDao;
import org.shaneking.roc.persistence.dao.TenantedCacheableDao;
import org.shaneking.roc.test.SKSpringUnit;
import org.springframework.beans.factory.annotation.Autowired;
import sktest.roc.persistence.entity.HelloAuditLogEntity;

import static org.junit.jupiter.api.Assertions.*;

class TenantedCacheableDaoTest extends SKSpringUnit {
  @Autowired
  private TenantedCacheableDao cacheableDao;

  @BeforeEach
  void beforeEach() {
    cacheableDao.delByIds(HelloAuditLogEntity.class, new HelloAuditLogEntity(), List0.newArrayList(cacheableDao.ids(HelloAuditLogEntity.class, new HelloAuditLogEntity(), null).split(String0.COMMA)), null);

    HelloAuditLogEntity helloAuditLogEntity = new HelloAuditLogEntity();
    helloAuditLogEntity.initWithUserIdAndId(String0.ARY_HEX, String0.ARY_HEX);
    cacheableDao.add(HelloAuditLogEntity.class, helloAuditLogEntity, null);
  }

  @Test
  void delById() {
    HelloAuditLogEntity helloAuditLogEntity = new HelloAuditLogEntity();
//    helloAuditLogEntity.setId(String0.ARY_HEX);
    assertEquals(0, cacheableDao.delById(HelloAuditLogEntity.class, String0.ARY_DEC, null));
    assertThrows(NullPointerException.class, () -> cacheableDao.delById(HelloAuditLogEntity.class, helloAuditLogEntity, null, null));
  }

  @Test
  void delByIds() {
    assertEquals(0, cacheableDao.delByIds(HelloAuditLogEntity.class, List0.newArrayList(String0.ARY_DEC), null));
    assertEquals(0, cacheableDao.delByIds(HelloAuditLogEntity.class, new HelloAuditLogEntity(), List0.newArrayList(String0.ARY_DEC), null));
  }

  @Test
  void lstByIds() {
    assertEquals(String0.ARY_HEX, cacheableDao.lstByIds(HelloAuditLogEntity.class, List0.newArrayList(String0.ARY_HEX), null).get(0).getId());
    assertEquals(String0.ARY_HEX, cacheableDao.lstByIds(HelloAuditLogEntity.class, List0.newArrayList(String0.ARY_HEX, String0.ARY_DEC), null).get(0).getId());
  }

  @Test
  void oneById() {
    assertEquals(String0.ARY_HEX, cacheableDao.oneById(HelloAuditLogEntity.class, String0.ARY_HEX, null).getId());
    assertEquals(String0.ARY_HEX, cacheableDao.oneById(HelloAuditLogEntity.class, String0.ARY_HEX, true, null).getId());

    assertThrows(ZeroException.class, () -> cacheableDao.oneById(HelloAuditLogEntity.class, String0.ARY_DEC, null));
    assertNull(cacheableDao.oneById(HelloAuditLogEntity.class, String0.ARY_DEC, true, null));
  }

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
