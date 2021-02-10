package sktest.roc.persistence.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.ling.zero.util.List0;
import org.shaneking.roc.persistence.dao.GlobalNumberedCacheDao;
import org.shaneking.roc.test.SKSpringUnit;
import org.springframework.beans.factory.annotation.Autowired;
import sktest.roc.persistence.entity.HelloGlobalNumberedEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class GlobalNumberedCacheDaoTest extends SKSpringUnit {

  @Autowired
  private GlobalNumberedCacheDao globalNumberedCacheDao;

  @BeforeEach
  void beforeEach() {
    globalNumberedCacheDao.getCacheableDao().delByIds(HelloGlobalNumberedEntity.class, new HelloGlobalNumberedEntity(), List0.newArrayList(globalNumberedCacheDao.getCacheableDao().ids(HelloGlobalNumberedEntity.class, new HelloGlobalNumberedEntity()).split(String0.COMMA)));

    HelloGlobalNumberedEntity helloGlobalNumberedEntity = new HelloGlobalNumberedEntity();
    helloGlobalNumberedEntity.setNo(String0.ARY_HEX).initWithUserIdAndId(String0.ARY_HEX, String0.ARY_HEX);
    globalNumberedCacheDao.getCacheableDao().add(HelloGlobalNumberedEntity.class, helloGlobalNumberedEntity);
  }

  @Test
  void oneByNo() {
    assertEquals(String0.ARY_HEX, globalNumberedCacheDao.oneByNo(HelloGlobalNumberedEntity.class, String0.ARY_HEX).getId());
    assertEquals(String0.ARY_HEX, globalNumberedCacheDao.oneByNo(HelloGlobalNumberedEntity.class, String0.ARY_HEX).getId());
    assertNull(globalNumberedCacheDao.oneByNo(HelloGlobalNumberedEntity.class, String0.ARY_L62));
  }
}
