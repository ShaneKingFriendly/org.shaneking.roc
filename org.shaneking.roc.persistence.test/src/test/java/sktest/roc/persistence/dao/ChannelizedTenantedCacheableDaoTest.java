package sktest.roc.persistence.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.ling.zero.lang.ZeroException;
import org.shaneking.ling.zero.util.List0;
import org.shaneking.ling.zero.util.UUID0;
import org.shaneking.roc.persistence.dao.ChannelizedTenantedCacheableDao;
import org.shaneking.roc.persistence.test.entity.Test4AuditLogEntity;
import org.shaneking.roc.test.SKSpringUnit;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class ChannelizedTenantedCacheableDaoTest extends SKSpringUnit {
  @Autowired
  private ChannelizedTenantedCacheableDao cacheableDao;

  @BeforeEach
  void beforeEach() {
    cacheableDao.delByIds(Test4AuditLogEntity.class, new Test4AuditLogEntity(), List0.newArrayList(cacheableDao.ids(Test4AuditLogEntity.class, new Test4AuditLogEntity(), null, null).split(String0.COMMA)), null, null);

    Test4AuditLogEntity test4CacheableEntity = new Test4AuditLogEntity();
    test4CacheableEntity.initWithUserIdAndId(String0.ARY_HEX, String0.ARY_HEX);
    cacheableDao.add(Test4AuditLogEntity.class, test4CacheableEntity, null, null);
  }

  @Test
  void add() {
    Test4AuditLogEntity test4CacheableEntity = new Test4AuditLogEntity();
    test4CacheableEntity.initWithUserIdAndId(UUID0.cUl33(), UUID0.cUl33());
    assertEquals(1, cacheableDao.add(Test4AuditLogEntity.class, test4CacheableEntity, null, null));
  }

  @Test
  void cnt() {
    assertEquals(1, cacheableDao.cnt(Test4AuditLogEntity.class, new Test4AuditLogEntity(), null, null));
  }

  @Test
  void ids() {
    assertEquals(String0.ARY_HEX, cacheableDao.ids(Test4AuditLogEntity.class, new Test4AuditLogEntity(), null, null));
  }

  @Test
  void delById() {
    Test4AuditLogEntity test4CacheableEntity = new Test4AuditLogEntity();
//    test4CacheableEntity.setId(String0.ARY_HEX);
    assertEquals(0, cacheableDao.delById(Test4AuditLogEntity.class, String0.ARY_DEC, null, null));
    assertEquals(0, cacheableDao.delById(Test4AuditLogEntity.class, test4CacheableEntity, String0.ARY_DEC, null, null));
    assertThrows(ZeroException.class, () -> cacheableDao.delById(Test4AuditLogEntity.class, test4CacheableEntity, null, null));
  }

  @Test
  void delByIds() {
    assertEquals(0, cacheableDao.delByIds(Test4AuditLogEntity.class, List0.newArrayList(String0.ARY_DEC), null, null));
    assertEquals(0, cacheableDao.delByIds(Test4AuditLogEntity.class, new Test4AuditLogEntity(), List0.newArrayList(String0.ARY_DEC), null, null));
  }

  @Test
  void modByIdsVer() {
    Test4AuditLogEntity test4CacheableEntity = new Test4AuditLogEntity();
    test4CacheableEntity.setLastModifyUserId(String0.ARY_HEX).setId(String0.ARY_HEX);
    assertEquals(1, cacheableDao.modByIdsVer(Test4AuditLogEntity.class, test4CacheableEntity, List0.newArrayList(String0.ARY_HEX), null, null));
  }

  @Test
  void modByIdVer() {
    Test4AuditLogEntity test4CacheableEntity = new Test4AuditLogEntity();
    test4CacheableEntity.setLastModifyUserId(String0.ARY_HEX).setId(String0.ARY_HEX);
    assertEquals(1, cacheableDao.modByIdVer(Test4AuditLogEntity.class, test4CacheableEntity, null, null));
  }

  @Test
  void lst() {
    assertEquals(String0.ARY_HEX, cacheableDao.lst(Test4AuditLogEntity.class, new Test4AuditLogEntity(), null, null).get(0).getId());
  }

  @Test
  void lstByIds() {
    assertEquals(String0.ARY_HEX, cacheableDao.lstByIds(Test4AuditLogEntity.class, new Test4AuditLogEntity(), List0.newArrayList(String0.ARY_HEX), null, null).get(0).getId());
    assertEquals(String0.ARY_HEX, cacheableDao.lstByIds(Test4AuditLogEntity.class, List0.newArrayList(String0.ARY_HEX), null, null).get(0).getId());

    assertEquals(String0.ARY_HEX, cacheableDao.lstByIds(Test4AuditLogEntity.class, new Test4AuditLogEntity(), List0.newArrayList(String0.ARY_HEX, String0.ARY_DEC), null, null).get(0).getId());
    assertEquals(String0.ARY_HEX, cacheableDao.lstByIds(Test4AuditLogEntity.class, List0.newArrayList(String0.ARY_HEX, String0.ARY_DEC), null, null).get(0).getId());
  }

  @Test
  void one() {
    assertEquals(String0.ARY_HEX, cacheableDao.one(Test4AuditLogEntity.class, new Test4AuditLogEntity(), null, null).getId());
    assertEquals(String0.ARY_HEX, cacheableDao.one(Test4AuditLogEntity.class, new Test4AuditLogEntity(), true, null, null).getId());

    assertThrows(ZeroException.class, () -> cacheableDao.one(Test4AuditLogEntity.class, (Test4AuditLogEntity) new Test4AuditLogEntity().setId(String0.ARY_DEC), null, null));
    assertNull(cacheableDao.one(Test4AuditLogEntity.class, (Test4AuditLogEntity) new Test4AuditLogEntity().setId(String0.ARY_DEC), true, null, null));
  }

  @Test
  void oneById() {
    assertEquals(String0.ARY_HEX, cacheableDao.oneById(Test4AuditLogEntity.class, new Test4AuditLogEntity(), String0.ARY_HEX, null, null).getId());
    assertEquals(String0.ARY_HEX, cacheableDao.oneById(Test4AuditLogEntity.class, new Test4AuditLogEntity(), String0.ARY_HEX, true, null, null).getId());
    assertEquals(String0.ARY_HEX, cacheableDao.oneById(Test4AuditLogEntity.class, String0.ARY_HEX, null, null).getId());
    assertEquals(String0.ARY_HEX, cacheableDao.oneById(Test4AuditLogEntity.class, String0.ARY_HEX, true, null, null).getId());

    assertThrows(ZeroException.class, () -> cacheableDao.oneById(Test4AuditLogEntity.class, new Test4AuditLogEntity(), String0.ARY_DEC, null, null));
    assertNull(cacheableDao.oneById(Test4AuditLogEntity.class, new Test4AuditLogEntity(), String0.ARY_DEC, true, null, null));
    assertThrows(ZeroException.class, () -> cacheableDao.oneById(Test4AuditLogEntity.class, String0.ARY_DEC, null, null));
    assertNull(cacheableDao.oneById(Test4AuditLogEntity.class, String0.ARY_DEC, true, null, null));
  }
}
