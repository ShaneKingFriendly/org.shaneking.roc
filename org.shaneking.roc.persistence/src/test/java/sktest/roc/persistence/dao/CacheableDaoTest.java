package sktest.roc.persistence.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.ling.zero.util.List0;
import org.shaneking.ling.zero.util.UUID0;
import org.shaneking.roc.persistence.dao.CacheableDao;
import org.shaneking.roc.test.SKSpringUnit;
import org.springframework.beans.factory.annotation.Autowired;
import sktest.roc.persistence.entity.Test4CacheableEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CacheableDaoTest extends SKSpringUnit {
  @Autowired
  private CacheableDao cacheableDao;

  @BeforeEach
  void beforeEach() {
    cacheableDao.delByIds(Test4CacheableEntity.class, new Test4CacheableEntity(), List0.newArrayList(cacheableDao.ids(Test4CacheableEntity.class, new Test4CacheableEntity()).split(String0.COMMA)));

    Test4CacheableEntity test4CacheableEntity = new Test4CacheableEntity();
    test4CacheableEntity.initWithUserIdAndId(String0.ARY_HEX, String0.ARY_HEX);
    cacheableDao.add(Test4CacheableEntity.class, test4CacheableEntity);
  }

  @Test
  void add() {
    Test4CacheableEntity test4CacheableEntity = new Test4CacheableEntity();
    test4CacheableEntity.initWithUserIdAndId(UUID0.cUl33(), UUID0.cUl33());
    assertEquals(1, cacheableDao.add(Test4CacheableEntity.class, test4CacheableEntity));
  }

  @Test
  void cnt() {
    assertEquals(1, cacheableDao.cnt(Test4CacheableEntity.class, new Test4CacheableEntity()));
  }

  @Test
  void ids() {
    assertEquals(String0.ARY_HEX, cacheableDao.ids(Test4CacheableEntity.class, new Test4CacheableEntity()));
  }

  @Test
  void delById() {
    Test4CacheableEntity test4CacheableEntity = new Test4CacheableEntity();
    test4CacheableEntity.setId(String0.ARY_HEX);
    assertEquals(1, cacheableDao.delById(Test4CacheableEntity.class, test4CacheableEntity));
  }

  @Test
  void delByIds() {
    assertEquals(1, cacheableDao.delByIds(Test4CacheableEntity.class, new Test4CacheableEntity(), List0.newArrayList(String0.ARY_HEX)));
  }

  @Test
  void modByIdsVer() {
    Test4CacheableEntity test4CacheableEntity = new Test4CacheableEntity();
    test4CacheableEntity.setLastModifyUserId(String0.ARY_HEX).setId(String0.ARY_HEX);
    assertEquals(1, cacheableDao.modByIdsVer(Test4CacheableEntity.class, test4CacheableEntity, List0.newArrayList(String0.ARY_HEX)));
  }

  @Test
  void modByIdVer() {
    Test4CacheableEntity test4CacheableEntity = new Test4CacheableEntity();
    test4CacheableEntity.setLastModifyUserId(String0.ARY_HEX).setId(String0.ARY_HEX);
    assertEquals(1, cacheableDao.modByIdVer(Test4CacheableEntity.class, test4CacheableEntity));
  }

  @Test
  void lst() {
    assertEquals(String0.ARY_HEX, cacheableDao.lst(Test4CacheableEntity.class, new Test4CacheableEntity()).get(0).getId());
  }

  @Test
  void lstByIds() {
    assertEquals(String0.ARY_HEX, cacheableDao.lstByIds(Test4CacheableEntity.class, new Test4CacheableEntity(), List0.newArrayList(String0.ARY_HEX)).get(0).getId());
  }

  @Test
  void one() {
    assertEquals(String0.ARY_HEX, cacheableDao.one(Test4CacheableEntity.class, new Test4CacheableEntity()).getId());
  }

  @Test
  void oneById() {
    assertEquals(String0.ARY_HEX, cacheableDao.oneById(Test4CacheableEntity.class, new Test4CacheableEntity(), String0.ARY_HEX).getId());
  }
}
