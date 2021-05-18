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
import sktest.roc.persistence.CacheableEntityPrepare;

import static org.junit.jupiter.api.Assertions.*;

class CacheableDaoTest extends SKSpringUnit {
  @Autowired
  private CacheableDao cacheableDao;

  @BeforeEach
  void beforeEach() {
    cacheableDao.rmvByIds(CacheableEntityPrepare.class, new CacheableEntityPrepare(), List0.newArrayList(cacheableDao.ids(CacheableEntityPrepare.class, new CacheableEntityPrepare()).split(String0.COMMA)));

    CacheableEntityPrepare cacheableEntityPrepare = new CacheableEntityPrepare();
    cacheableEntityPrepare.initWithUserIdAndId(String0.ARY_HEX, String0.ARY_HEX);
    cacheableDao.add(CacheableEntityPrepare.class, cacheableEntityPrepare);
  }

  @Test
  void add() {
    CacheableEntityPrepare cacheableEntityPrepare = new CacheableEntityPrepare();
    cacheableEntityPrepare.initWithUserIdAndId(UUID0.cUl33(), UUID0.cUl33());
    assertEquals(1, cacheableDao.add(CacheableEntityPrepare.class, cacheableEntityPrepare));
  }

  @Test
  void cnt() {
    assertEquals(Long.valueOf(1), cacheableDao.cnt(CacheableEntityPrepare.class, new CacheableEntityPrepare()));
  }

  @Test
  void ids() {
    assertEquals(String0.ARY_HEX, cacheableDao.ids(CacheableEntityPrepare.class, new CacheableEntityPrepare()));
  }

  @Test
  void delById() {
    assertEquals(0, cacheableDao.rmvById(CacheableEntityPrepare.class, String0.ARY_DEC));
    assertEquals(0, cacheableDao.rmvById(CacheableEntityPrepare.class, new CacheableEntityPrepare(), String0.ARY_DEC));
    assertThrows(NullPointerException.class, () -> cacheableDao.rmvById(CacheableEntityPrepare.class, new CacheableEntityPrepare(), null));
    assertThrows(ZeroException.class, () -> cacheableDao.rmvById(CacheableEntityPrepare.class, new CacheableEntityPrepare()));

    CacheableEntityPrepare cacheableEntityPrepare = new CacheableEntityPrepare();
    cacheableEntityPrepare.setId(String0.ARY_HEX);
    assertEquals(1, cacheableDao.rmvById(CacheableEntityPrepare.class, cacheableEntityPrepare));
  }

  @Test
  void delByIds() {
    assertEquals(0, cacheableDao.rmvByIds(CacheableEntityPrepare.class, List0.newArrayList(String0.ARY_DEC)));
    assertEquals(0, cacheableDao.rmvByIds(CacheableEntityPrepare.class, List0.newArrayList()));
    assertEquals(0, cacheableDao.rmvByIds(CacheableEntityPrepare.class, new CacheableEntityPrepare(), List0.newArrayList(String0.ARY_DEC)));
    assertEquals(0, cacheableDao.rmvByIds(CacheableEntityPrepare.class, new CacheableEntityPrepare(), List0.newArrayList()));
  }

  @Test
  void modByIdsVer() {
    CacheableEntityPrepare cacheableEntityPrepare = new CacheableEntityPrepare();
    assertThrows(ZeroException.class, () -> cacheableDao.modByIdsVer(CacheableEntityPrepare.class, cacheableEntityPrepare, List0.newArrayList()));
    cacheableEntityPrepare.setLastModifyUserId(String0.ARY_HEX).setId(String0.ARY_HEX);
    assertEquals(1, cacheableDao.modByIdsVer(CacheableEntityPrepare.class, cacheableEntityPrepare, List0.newArrayList(String0.ARY_HEX)));
  }

  @Test
  void modByIdVer() {
    CacheableEntityPrepare cacheableEntityPrepare = new CacheableEntityPrepare();
    assertThrows(ZeroException.class, () -> cacheableDao.modByIdVer(CacheableEntityPrepare.class, cacheableEntityPrepare));
    cacheableEntityPrepare.setLastModifyUserId(String0.ARY_HEX).setId(String0.ARY_HEX);
    assertEquals(1, cacheableDao.modByIdVer(CacheableEntityPrepare.class, cacheableEntityPrepare));
  }

  @Test
  void lst() {
    assertEquals(String0.ARY_HEX, cacheableDao.lst(CacheableEntityPrepare.class, new CacheableEntityPrepare()).get(0).getId());
  }

  @Test
  void lstByIds() {
    assertEquals(String0.ARY_HEX, cacheableDao.lstByIds(CacheableEntityPrepare.class, new CacheableEntityPrepare(), List0.newArrayList(String0.ARY_HEX)).get(0).getId());
    assertEquals(String0.ARY_HEX, cacheableDao.lstByIds(CacheableEntityPrepare.class, List0.newArrayList(String0.ARY_HEX)).get(0).getId());

    assertEquals(String0.ARY_HEX, cacheableDao.lstByIds(CacheableEntityPrepare.class, new CacheableEntityPrepare(), List0.newArrayList(String0.ARY_HEX, String0.ARY_DEC)).get(0).getId());
    assertEquals(String0.ARY_HEX, cacheableDao.lstByIds(CacheableEntityPrepare.class, List0.newArrayList(String0.ARY_HEX, String0.ARY_DEC)).get(0).getId());

    assertEquals(String0.ARY_HEX, cacheableDao.lstByIds(CacheableEntityPrepare.class, new CacheableEntityPrepare(), List0.newArrayList()).get(0).getId());
    assertEquals(String0.ARY_HEX, cacheableDao.lstByIds(CacheableEntityPrepare.class, List0.newArrayList()).get(0).getId());
  }

  @Test
  void one() {
    assertEquals(String0.ARY_HEX, cacheableDao.one(CacheableEntityPrepare.class, new CacheableEntityPrepare()).getId());
    assertEquals(String0.ARY_HEX, cacheableDao.one(CacheableEntityPrepare.class, new CacheableEntityPrepare(), true).getId());

    assertThrows(ZeroException.class, () -> cacheableDao.one(CacheableEntityPrepare.class, (CacheableEntityPrepare) new CacheableEntityPrepare().setId(String0.ARY_DEC)));
    assertNull(cacheableDao.one(CacheableEntityPrepare.class, (CacheableEntityPrepare) new CacheableEntityPrepare().setId(String0.ARY_DEC), true));
  }

  @Test
  void oneById() {
    assertEquals(String0.ARY_HEX, cacheableDao.oneById(CacheableEntityPrepare.class, new CacheableEntityPrepare(), String0.ARY_HEX).getId());
    assertEquals(String0.ARY_HEX, cacheableDao.oneById(CacheableEntityPrepare.class, new CacheableEntityPrepare(), String0.ARY_HEX, true).getId());
    assertEquals(String0.ARY_HEX, cacheableDao.oneById(CacheableEntityPrepare.class, String0.ARY_HEX).getId());
    assertEquals(String0.ARY_HEX, cacheableDao.oneById(CacheableEntityPrepare.class, String0.ARY_HEX, true).getId());

    assertThrows(ZeroException.class, () -> cacheableDao.oneById(CacheableEntityPrepare.class, new CacheableEntityPrepare(), String0.ARY_DEC));
    assertNull(cacheableDao.oneById(CacheableEntityPrepare.class, new CacheableEntityPrepare(), String0.ARY_DEC, true));
    assertThrows(ZeroException.class, () -> cacheableDao.oneById(CacheableEntityPrepare.class, String0.ARY_DEC));
    assertNull(cacheableDao.oneById(CacheableEntityPrepare.class, String0.ARY_DEC, true));
  }
}
