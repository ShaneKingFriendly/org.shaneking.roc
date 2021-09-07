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
import sktest.roc.persistence.CacheableEntityPrepare1;

import static org.junit.jupiter.api.Assertions.*;

class CacheableDaoTest extends SKSpringUnit {
  @Autowired
  private CacheableDao cacheableDao;

  @BeforeEach
  void beforeEach() {
    cacheableDao.rmvByIds(CacheableEntityPrepare1.class, new CacheableEntityPrepare1(), List0.newArrayList(cacheableDao.ids(CacheableEntityPrepare1.class, new CacheableEntityPrepare1()).split(String0.COMMA)));

    CacheableEntityPrepare1 cacheableEntityPrepare1 = new CacheableEntityPrepare1();
    cacheableEntityPrepare1.initWithUidAndId(String0.ARY_HEX, String0.ARY_HEX);
    cacheableDao.add(CacheableEntityPrepare1.class, cacheableEntityPrepare1);
  }

  @Test
  void add() {
    CacheableEntityPrepare1 cacheableEntityPrepare1 = new CacheableEntityPrepare1();
    cacheableEntityPrepare1.initWithUidAndId(UUID0.cUl33(), UUID0.cUl33());
    assertEquals(1, cacheableDao.add(CacheableEntityPrepare1.class, cacheableEntityPrepare1));
  }

  @Test
  void cnt() {
    assertEquals(Long.valueOf(1), cacheableDao.cnt(CacheableEntityPrepare1.class, new CacheableEntityPrepare1()));
  }

  @Test
  void ids() {
    assertEquals(String0.ARY_HEX, cacheableDao.ids(CacheableEntityPrepare1.class, new CacheableEntityPrepare1()));
  }

  @Test
  void delById() {
    assertEquals(0, cacheableDao.rmvById(CacheableEntityPrepare1.class, String0.ARY_DEC));
    assertEquals(0, cacheableDao.rmvById(CacheableEntityPrepare1.class, new CacheableEntityPrepare1(), String0.ARY_DEC));
    assertThrows(NullPointerException.class, () -> cacheableDao.rmvById(CacheableEntityPrepare1.class, new CacheableEntityPrepare1(), null));
    assertThrows(ZeroException.class, () -> cacheableDao.rmvById(CacheableEntityPrepare1.class, new CacheableEntityPrepare1()));

    CacheableEntityPrepare1 cacheableEntityPrepare1 = new CacheableEntityPrepare1();
    cacheableEntityPrepare1.setId(String0.ARY_HEX);
    assertEquals(1, cacheableDao.rmvById(CacheableEntityPrepare1.class, cacheableEntityPrepare1));
  }

  @Test
  void delByIds() {
    assertEquals(0, cacheableDao.rmvByIds(CacheableEntityPrepare1.class, List0.newArrayList(String0.ARY_DEC)));
    assertEquals(0, cacheableDao.rmvByIds(CacheableEntityPrepare1.class, List0.newArrayList()));
    assertEquals(0, cacheableDao.rmvByIds(CacheableEntityPrepare1.class, new CacheableEntityPrepare1(), List0.newArrayList(String0.ARY_DEC)));
    assertEquals(0, cacheableDao.rmvByIds(CacheableEntityPrepare1.class, new CacheableEntityPrepare1(), List0.newArrayList()));
  }

  @Test
  void modByIdsVer() {
    CacheableEntityPrepare1 cacheableEntityPrepare1 = new CacheableEntityPrepare1();
    assertThrows(ZeroException.class, () -> cacheableDao.modByIdsVer(CacheableEntityPrepare1.class, cacheableEntityPrepare1, List0.newArrayList()));
    cacheableEntityPrepare1.setLmUid(String0.ARY_HEX).setId(String0.ARY_HEX);
    assertEquals(1, cacheableDao.modByIdsVer(CacheableEntityPrepare1.class, cacheableEntityPrepare1, List0.newArrayList(String0.ARY_HEX)));
  }

  @Test
  void modByIdVer() {
    CacheableEntityPrepare1 cacheableEntityPrepare1 = new CacheableEntityPrepare1();
    assertThrows(ZeroException.class, () -> cacheableDao.modByIdVer(CacheableEntityPrepare1.class, cacheableEntityPrepare1));
    cacheableEntityPrepare1.setLmUid(String0.ARY_HEX).setId(String0.ARY_HEX);
    assertEquals(1, cacheableDao.modByIdVer(CacheableEntityPrepare1.class, cacheableEntityPrepare1));
  }

  @Test
  void lst() {
    assertEquals(String0.ARY_HEX, cacheableDao.lst(CacheableEntityPrepare1.class, new CacheableEntityPrepare1()).get(0).getId());
  }

  @Test
  void lstByIds() {
//    assertEquals(String0.ARY_HEX, cacheableDao.lstByIds(CacheableEntityPrepare1.class, new CacheableEntityPrepare1(), List0.newArrayList(String0.ARY_HEX)).get(0).getId());
    assertEquals(String0.ARY_HEX, cacheableDao.lstByIds(CacheableEntityPrepare1.class, List0.newArrayList(String0.ARY_HEX)).get(0).getId());

//    assertEquals(String0.ARY_HEX, cacheableDao.lstByIds(CacheableEntityPrepare1.class, new CacheableEntityPrepare1(), List0.newArrayList(String0.ARY_HEX, String0.ARY_DEC)).get(0).getId());
    assertEquals(String0.ARY_HEX, cacheableDao.lstByIds(CacheableEntityPrepare1.class, List0.newArrayList(String0.ARY_HEX, String0.ARY_DEC)).get(0).getId());

//    assertEquals(String0.ARY_HEX, cacheableDao.lstByIds(CacheableEntityPrepare1.class, new CacheableEntityPrepare1(), List0.newArrayList()).get(0).getId());
    assertEquals(String0.ARY_HEX, cacheableDao.lstByIds(CacheableEntityPrepare1.class, List0.newArrayList()).get(0).getId());
  }

  @Test
  void one() {
    assertEquals(String0.ARY_HEX, cacheableDao.one(CacheableEntityPrepare1.class, new CacheableEntityPrepare1()).getId());
    assertEquals(String0.ARY_HEX, cacheableDao.one(CacheableEntityPrepare1.class, new CacheableEntityPrepare1(), true).getId());

    assertThrows(ZeroException.class, () -> cacheableDao.one(CacheableEntityPrepare1.class, (CacheableEntityPrepare1) new CacheableEntityPrepare1().setId(String0.ARY_DEC)));
    assertNull(cacheableDao.one(CacheableEntityPrepare1.class, (CacheableEntityPrepare1) new CacheableEntityPrepare1().setId(String0.ARY_DEC), true));
  }

  @Test
  void oneById() {
    assertEquals(String0.ARY_HEX, cacheableDao.oneById(CacheableEntityPrepare1.class, new CacheableEntityPrepare1(), String0.ARY_HEX).getId());
    assertEquals(String0.ARY_HEX, cacheableDao.oneById(CacheableEntityPrepare1.class, new CacheableEntityPrepare1(), String0.ARY_HEX, true).getId());
    assertEquals(String0.ARY_HEX, cacheableDao.oneById(CacheableEntityPrepare1.class, String0.ARY_HEX).getId());
    assertEquals(String0.ARY_HEX, cacheableDao.oneById(CacheableEntityPrepare1.class, String0.ARY_HEX, true).getId());

    assertThrows(ZeroException.class, () -> cacheableDao.oneById(CacheableEntityPrepare1.class, new CacheableEntityPrepare1(), String0.ARY_DEC));
    assertNull(cacheableDao.oneById(CacheableEntityPrepare1.class, new CacheableEntityPrepare1(), String0.ARY_DEC, true));
    assertThrows(ZeroException.class, () -> cacheableDao.oneById(CacheableEntityPrepare1.class, String0.ARY_DEC));
    assertNull(cacheableDao.oneById(CacheableEntityPrepare1.class, String0.ARY_DEC, true));
  }
}
