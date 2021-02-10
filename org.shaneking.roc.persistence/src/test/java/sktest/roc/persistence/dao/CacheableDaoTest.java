package sktest.roc.persistence.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.ling.zero.lang.ZeroException;
import org.shaneking.ling.zero.util.List0;
import org.shaneking.ling.zero.util.UUID0;
import org.shaneking.roc.persistence.dao.CacheableDao;
import org.shaneking.roc.test.SKSpringUnit;
import org.springframework.beans.factory.annotation.Autowired;
import sktest.roc.persistence.entity.HelloCacheableEntity;

import static org.junit.jupiter.api.Assertions.*;

class CacheableDaoTest extends SKSpringUnit {
  @Autowired
  private CacheableDao cacheableDao;

  @BeforeEach
  void beforeEach() {
    cacheableDao.delByIds(HelloCacheableEntity.class, new HelloCacheableEntity(), List0.newArrayList(cacheableDao.ids(HelloCacheableEntity.class, new HelloCacheableEntity()).split(String0.COMMA)));

    HelloCacheableEntity helloCacheableEntity = new HelloCacheableEntity();
    helloCacheableEntity.initWithUserIdAndId(String0.ARY_HEX, String0.ARY_HEX);
    cacheableDao.add(HelloCacheableEntity.class, helloCacheableEntity);
  }

  @Test
  void add() {
    HelloCacheableEntity helloCacheableEntity = new HelloCacheableEntity();
    helloCacheableEntity.initWithUserIdAndId(UUID0.cUl33(), UUID0.cUl33());
    assertEquals(1, cacheableDao.add(HelloCacheableEntity.class, helloCacheableEntity));
  }

  @Test
  void cnt() {
    assertEquals(Long.valueOf(1), cacheableDao.cnt(HelloCacheableEntity.class, new HelloCacheableEntity()));
  }

  @Test
  void ids() {
    assertEquals(String0.ARY_HEX, cacheableDao.ids(HelloCacheableEntity.class, new HelloCacheableEntity()));
  }

  @Test
  void delById() {
    assertEquals(0, cacheableDao.delById(HelloCacheableEntity.class, String0.ARY_DEC));
    assertEquals(0, cacheableDao.delById(HelloCacheableEntity.class, new HelloCacheableEntity(), String0.ARY_DEC));
    assertThrows(NullPointerException.class, () -> cacheableDao.delById(HelloCacheableEntity.class, new HelloCacheableEntity(), null));
    assertThrows(ZeroException.class, () -> cacheableDao.delById(HelloCacheableEntity.class, new HelloCacheableEntity()));

    HelloCacheableEntity helloCacheableEntity = new HelloCacheableEntity();
    helloCacheableEntity.setId(String0.ARY_HEX);
    assertEquals(1, cacheableDao.delById(HelloCacheableEntity.class, helloCacheableEntity));
  }

  @Test
  void delByIds() {
    assertEquals(0, cacheableDao.delByIds(HelloCacheableEntity.class, List0.newArrayList(String0.ARY_DEC)));
    assertEquals(0, cacheableDao.delByIds(HelloCacheableEntity.class, List0.newArrayList()));
    assertEquals(0, cacheableDao.delByIds(HelloCacheableEntity.class, new HelloCacheableEntity(), List0.newArrayList(String0.ARY_DEC)));
    assertEquals(0, cacheableDao.delByIds(HelloCacheableEntity.class, new HelloCacheableEntity(), List0.newArrayList()));
  }

  @Test
  void modByIdsVer() {
    HelloCacheableEntity helloCacheableEntity = new HelloCacheableEntity();
    assertThrows(ZeroException.class, () -> cacheableDao.modByIdsVer(HelloCacheableEntity.class, helloCacheableEntity, List0.newArrayList()));
    helloCacheableEntity.setLastModifyUserId(String0.ARY_HEX).setId(String0.ARY_HEX);
    assertEquals(1, cacheableDao.modByIdsVer(HelloCacheableEntity.class, helloCacheableEntity, List0.newArrayList(String0.ARY_HEX)));
  }

  @Test
  void modByIdVer() {
    HelloCacheableEntity helloCacheableEntity = new HelloCacheableEntity();
    assertThrows(ZeroException.class, () -> cacheableDao.modByIdVer(HelloCacheableEntity.class, helloCacheableEntity));
    helloCacheableEntity.setLastModifyUserId(String0.ARY_HEX).setId(String0.ARY_HEX);
    assertEquals(1, cacheableDao.modByIdVer(HelloCacheableEntity.class, helloCacheableEntity));
  }

  @Test
  void lst() {
    assertEquals(String0.ARY_HEX, cacheableDao.lst(HelloCacheableEntity.class, new HelloCacheableEntity()).get(0).getId());
  }

  @Test
  void lstByIds() {
    assertEquals(String0.ARY_HEX, cacheableDao.lstByIds(HelloCacheableEntity.class, new HelloCacheableEntity(), List0.newArrayList(String0.ARY_HEX)).get(0).getId());
    assertEquals(String0.ARY_HEX, cacheableDao.lstByIds(HelloCacheableEntity.class, List0.newArrayList(String0.ARY_HEX)).get(0).getId());

    assertEquals(String0.ARY_HEX, cacheableDao.lstByIds(HelloCacheableEntity.class, new HelloCacheableEntity(), List0.newArrayList(String0.ARY_HEX, String0.ARY_DEC)).get(0).getId());
    assertEquals(String0.ARY_HEX, cacheableDao.lstByIds(HelloCacheableEntity.class, List0.newArrayList(String0.ARY_HEX, String0.ARY_DEC)).get(0).getId());

    assertEquals(String0.ARY_HEX, cacheableDao.lstByIds(HelloCacheableEntity.class, new HelloCacheableEntity(), List0.newArrayList()).get(0).getId());
    assertEquals(String0.ARY_HEX, cacheableDao.lstByIds(HelloCacheableEntity.class, List0.newArrayList()).get(0).getId());
  }

  @Test
  void one() {
    assertEquals(String0.ARY_HEX, cacheableDao.one(HelloCacheableEntity.class, new HelloCacheableEntity()).getId());
    assertEquals(String0.ARY_HEX, cacheableDao.one(HelloCacheableEntity.class, new HelloCacheableEntity(), true).getId());

    assertThrows(ZeroException.class, () -> cacheableDao.one(HelloCacheableEntity.class, (HelloCacheableEntity) new HelloCacheableEntity().setId(String0.ARY_DEC)));
    assertNull(cacheableDao.one(HelloCacheableEntity.class, (HelloCacheableEntity) new HelloCacheableEntity().setId(String0.ARY_DEC), true));
  }

  @Test
  void oneById() {
    assertEquals(String0.ARY_HEX, cacheableDao.oneById(HelloCacheableEntity.class, new HelloCacheableEntity(), String0.ARY_HEX).getId());
    assertEquals(String0.ARY_HEX, cacheableDao.oneById(HelloCacheableEntity.class, new HelloCacheableEntity(), String0.ARY_HEX, true).getId());
    assertEquals(String0.ARY_HEX, cacheableDao.oneById(HelloCacheableEntity.class, String0.ARY_HEX).getId());
    assertEquals(String0.ARY_HEX, cacheableDao.oneById(HelloCacheableEntity.class, String0.ARY_HEX, true).getId());

    assertThrows(ZeroException.class, () -> cacheableDao.oneById(HelloCacheableEntity.class, new HelloCacheableEntity(), String0.ARY_DEC));
    assertNull(cacheableDao.oneById(HelloCacheableEntity.class, new HelloCacheableEntity(), String0.ARY_DEC, true));
    assertThrows(ZeroException.class, () -> cacheableDao.oneById(HelloCacheableEntity.class, String0.ARY_DEC));
    assertNull(cacheableDao.oneById(HelloCacheableEntity.class, String0.ARY_DEC, true));
  }
}
