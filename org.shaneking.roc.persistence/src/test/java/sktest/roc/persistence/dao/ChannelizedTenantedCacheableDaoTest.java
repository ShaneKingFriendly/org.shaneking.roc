package sktest.roc.persistence.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.ling.zero.lang.ZeroException;
import org.shaneking.ling.zero.util.List0;
import org.shaneking.ling.zero.util.UUID0;
import org.shaneking.roc.persistence.dao.ChannelizedTenantedCacheableDao;
import org.shaneking.roc.test.SKSpringUnit;
import org.springframework.beans.factory.annotation.Autowired;
import sktest.roc.persistence.entity.HelloAuditLogEntity;

import static org.junit.jupiter.api.Assertions.*;

class ChannelizedTenantedCacheableDaoTest extends SKSpringUnit {
  @Autowired
  private ChannelizedTenantedCacheableDao cacheableDao;

  @BeforeEach
  void beforeEach() {
    cacheableDao.delByIds(HelloAuditLogEntity.class, new HelloAuditLogEntity(), List0.newArrayList(cacheableDao.ids(HelloAuditLogEntity.class, new HelloAuditLogEntity(), null, null).split(String0.COMMA)), null, null);

    HelloAuditLogEntity helloAuditLogEntity = new HelloAuditLogEntity();
    helloAuditLogEntity.initWithUserIdAndId(String0.ARY_HEX, String0.ARY_HEX);
    cacheableDao.add(HelloAuditLogEntity.class, helloAuditLogEntity, null, null);
  }

  @Test
  void add() {
    HelloAuditLogEntity helloAuditLogEntity = new HelloAuditLogEntity();
    helloAuditLogEntity.initWithUserIdAndId(UUID0.cUl33(), UUID0.cUl33());
    assertEquals(1, cacheableDao.add(HelloAuditLogEntity.class, helloAuditLogEntity, null, null));
  }

  @Test
  void cnt() {
    assertEquals(1, cacheableDao.cnt(HelloAuditLogEntity.class, new HelloAuditLogEntity(), null, null));
  }

  @Test
  void ids() {
    assertEquals(String0.ARY_HEX, cacheableDao.ids(HelloAuditLogEntity.class, new HelloAuditLogEntity(), null, null));
  }

  @Test
  void delById() {
    HelloAuditLogEntity helloAuditLogEntity = new HelloAuditLogEntity();
//    helloAuditLogEntity.setId(String0.ARY_HEX);
    assertEquals(0, cacheableDao.delById(HelloAuditLogEntity.class, String0.ARY_DEC, null, null));
    assertEquals(0, cacheableDao.delById(HelloAuditLogEntity.class, helloAuditLogEntity, String0.ARY_DEC, null, null));
    assertThrows(ZeroException.class, () -> cacheableDao.delById(HelloAuditLogEntity.class, helloAuditLogEntity, null, null));
  }

  @Test
  void delByIds() {
    assertEquals(0, cacheableDao.delByIds(HelloAuditLogEntity.class, List0.newArrayList(String0.ARY_DEC), null, null));
    assertEquals(0, cacheableDao.delByIds(HelloAuditLogEntity.class, new HelloAuditLogEntity(), List0.newArrayList(String0.ARY_DEC), null, null));
  }

  @Test
  void modByIdsVer() {
    HelloAuditLogEntity helloAuditLogEntity = new HelloAuditLogEntity();
    helloAuditLogEntity.setLastModifyUserId(String0.ARY_HEX).setId(String0.ARY_HEX);
    assertEquals(1, cacheableDao.modByIdsVer(HelloAuditLogEntity.class, helloAuditLogEntity, List0.newArrayList(String0.ARY_HEX), null, null));
  }

  @Test
  void modByIdVer() {
    HelloAuditLogEntity helloAuditLogEntity = new HelloAuditLogEntity();
    helloAuditLogEntity.setLastModifyUserId(String0.ARY_HEX).setId(String0.ARY_HEX);
    assertEquals(1, cacheableDao.modByIdVer(HelloAuditLogEntity.class, helloAuditLogEntity, null, null));
  }

  @Test
  void lst() {
    assertEquals(String0.ARY_HEX, cacheableDao.lst(HelloAuditLogEntity.class, new HelloAuditLogEntity(), null, null).get(0).getId());
  }

  @Test
  void lstByIds() {
    assertEquals(String0.ARY_HEX, cacheableDao.lstByIds(HelloAuditLogEntity.class, new HelloAuditLogEntity(), List0.newArrayList(String0.ARY_HEX), null, null).get(0).getId());
    assertEquals(String0.ARY_HEX, cacheableDao.lstByIds(HelloAuditLogEntity.class, List0.newArrayList(String0.ARY_HEX), null, null).get(0).getId());

    assertEquals(String0.ARY_HEX, cacheableDao.lstByIds(HelloAuditLogEntity.class, new HelloAuditLogEntity(), List0.newArrayList(String0.ARY_HEX, String0.ARY_DEC), null, null).get(0).getId());
    assertEquals(String0.ARY_HEX, cacheableDao.lstByIds(HelloAuditLogEntity.class, List0.newArrayList(String0.ARY_HEX, String0.ARY_DEC), null, null).get(0).getId());
  }

  @Test
  void one() {
    assertEquals(String0.ARY_HEX, cacheableDao.one(HelloAuditLogEntity.class, new HelloAuditLogEntity(), null, null).getId());
    assertEquals(String0.ARY_HEX, cacheableDao.one(HelloAuditLogEntity.class, new HelloAuditLogEntity(), true, null, null).getId());

    assertThrows(ZeroException.class, () -> cacheableDao.one(HelloAuditLogEntity.class, (HelloAuditLogEntity) new HelloAuditLogEntity().setId(String0.ARY_DEC), null, null));
    assertNull(cacheableDao.one(HelloAuditLogEntity.class, (HelloAuditLogEntity) new HelloAuditLogEntity().setId(String0.ARY_DEC), true, null, null));
  }

  @Test
  void oneById() {
    assertEquals(String0.ARY_HEX, cacheableDao.oneById(HelloAuditLogEntity.class, new HelloAuditLogEntity(), String0.ARY_HEX, null, null).getId());
    assertEquals(String0.ARY_HEX, cacheableDao.oneById(HelloAuditLogEntity.class, new HelloAuditLogEntity(), String0.ARY_HEX, true, null, null).getId());
    assertEquals(String0.ARY_HEX, cacheableDao.oneById(HelloAuditLogEntity.class, String0.ARY_HEX, null, null).getId());
    assertEquals(String0.ARY_HEX, cacheableDao.oneById(HelloAuditLogEntity.class, String0.ARY_HEX, true, null, null).getId());

    assertThrows(ZeroException.class, () -> cacheableDao.oneById(HelloAuditLogEntity.class, new HelloAuditLogEntity(), String0.ARY_DEC, null, null));
    assertNull(cacheableDao.oneById(HelloAuditLogEntity.class, new HelloAuditLogEntity(), String0.ARY_DEC, true, null, null));
    assertThrows(ZeroException.class, () -> cacheableDao.oneById(HelloAuditLogEntity.class, String0.ARY_DEC, null, null));
    assertNull(cacheableDao.oneById(HelloAuditLogEntity.class, String0.ARY_DEC, true, null, null));
  }
}
