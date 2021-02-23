package sktest.roc.persistence.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.ling.zero.util.List0;
import org.shaneking.roc.persistence.dao.NumberedCacheableDao;
import org.shaneking.roc.test.SKSpringUnit;
import org.springframework.beans.factory.annotation.Autowired;
import sktest.roc.persistence.entity.HelloNumberedEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class NumberedCacheableDaoTest extends SKSpringUnit {

  @Autowired
  private NumberedCacheableDao numberedCacheableDao;

  @BeforeEach
  void beforeEach() {
    numberedCacheableDao.getCacheableDao().delByIds(HelloNumberedEntity.class, new HelloNumberedEntity(), List0.newArrayList(numberedCacheableDao.getCacheableDao().ids(HelloNumberedEntity.class, new HelloNumberedEntity()).split(String0.COMMA)));

    HelloNumberedEntity helloGlobalNumberedEntity = new HelloNumberedEntity();
    helloGlobalNumberedEntity.setNo(String0.ARY_HEX).initWithUserIdAndId(String0.ARY_HEX, String0.ARY_HEX);
    numberedCacheableDao.getCacheableDao().add(HelloNumberedEntity.class, helloGlobalNumberedEntity);
  }

  @Test
  void oneByNo() {
    assertEquals(String0.ARY_HEX, numberedCacheableDao.oneByNo(HelloNumberedEntity.class, String0.ARY_HEX).getId());
    assertEquals(String0.ARY_HEX, numberedCacheableDao.oneByNo(HelloNumberedEntity.class, String0.ARY_HEX).getId());
    assertNull(numberedCacheableDao.oneByNo(HelloNumberedEntity.class, String0.ARY_L62));
  }
}
