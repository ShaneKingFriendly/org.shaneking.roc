package sktest.roc.persistence.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.ling.zero.util.List0;
import org.shaneking.roc.persistence.dao.NumberedCacheDao;
import org.shaneking.roc.test.SKSpringUnit;
import org.springframework.beans.factory.annotation.Autowired;
import sktest.roc.persistence.entity.HelloNumberedEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class NumberedCacheDaoTest extends SKSpringUnit {

  @Autowired
  private NumberedCacheDao numberedCacheDao;

  @BeforeEach
  void beforeEach() {
    numberedCacheDao.getCacheableDao().delByIds(HelloNumberedEntity.class, new HelloNumberedEntity(), List0.newArrayList(numberedCacheDao.getCacheableDao().ids(HelloNumberedEntity.class, new HelloNumberedEntity()).split(String0.COMMA)));

    HelloNumberedEntity helloGlobalNumberedEntity = new HelloNumberedEntity();
    helloGlobalNumberedEntity.setNo(String0.ARY_HEX).setTenantId(String0.ALPHABET).initWithUserIdAndId(String0.ARY_HEX, String0.ARY_HEX);
    numberedCacheDao.getCacheableDao().add(HelloNumberedEntity.class, helloGlobalNumberedEntity);
  }

  @Test
  void oneByNo() {
    assertEquals(String0.ARY_HEX, numberedCacheDao.oneByNo(HelloNumberedEntity.class, String0.ARY_HEX, String0.ALPHABET).getId());
    assertEquals(String0.ARY_HEX, numberedCacheDao.oneByNo(HelloNumberedEntity.class, String0.ARY_HEX, String0.ALPHABET).getId());
    assertNull(numberedCacheDao.oneByNo(HelloNumberedEntity.class, String0.ARY_L62, String0.ALPHABET));
  }
}
