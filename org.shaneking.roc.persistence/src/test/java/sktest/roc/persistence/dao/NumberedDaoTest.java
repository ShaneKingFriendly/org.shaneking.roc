package sktest.roc.persistence.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.ling.zero.util.List0;
import org.shaneking.roc.persistence.dao.NumberedDao;
import org.shaneking.roc.test.SKSpringUnit;
import org.springframework.beans.factory.annotation.Autowired;
import sktest.roc.persistence.entity.NumberedEntityPrepare1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class NumberedDaoTest extends SKSpringUnit {

  @Autowired
  private NumberedDao numberedDao;

  @BeforeEach
  void beforeEach() {
    numberedDao.getCacheableDao().rmvByIds(NumberedEntityPrepare1.class, new NumberedEntityPrepare1(), List0.newArrayList(numberedDao.getCacheableDao().ids(NumberedEntityPrepare1.class, new NumberedEntityPrepare1()).split(String0.COMMA)));

    NumberedEntityPrepare1 simpleGlobalNumberedEntity = new NumberedEntityPrepare1();
    simpleGlobalNumberedEntity.setNo(String0.ARY_HEX).initWithUidAndId(String0.ARY_HEX, String0.ARY_HEX);
    numberedDao.getCacheableDao().add(NumberedEntityPrepare1.class, simpleGlobalNumberedEntity);
  }

  @Test
  void oneByNo() {
    assertEquals(String0.ARY_HEX, numberedDao.oneByNo(NumberedEntityPrepare1.class, String0.ARY_HEX).getId());
    assertEquals(String0.ARY_HEX, numberedDao.oneByNo(NumberedEntityPrepare1.class, String0.ARY_HEX).getId());
    assertNull(numberedDao.oneByNo(NumberedEntityPrepare1.class, String0.ARY_L62));
  }
}
