package sktest.roc.persistence.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.ling.zero.util.List0;
import org.shaneking.roc.persistence.dao.TenantedNumberedDao;
import org.shaneking.roc.test.SKSpringUnit;
import org.springframework.beans.factory.annotation.Autowired;
import sktest.roc.persistence.entity.TenantedNumberedEntityPrepare;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class TenantedNumberedDaoTest extends SKSpringUnit {

  @Autowired
  private TenantedNumberedDao tenantedNumberedDao;

  @BeforeEach
  void beforeEach() {
    tenantedNumberedDao.getCacheableDao().rmvByIds(TenantedNumberedEntityPrepare.class, new TenantedNumberedEntityPrepare(), List0.newArrayList(tenantedNumberedDao.getCacheableDao().ids(TenantedNumberedEntityPrepare.class, new TenantedNumberedEntityPrepare()).split(String0.COMMA)));

    TenantedNumberedEntityPrepare simpleGlobalNumberedEntity = new TenantedNumberedEntityPrepare();
    simpleGlobalNumberedEntity.setTenantId(String0.ALPHABET).setNo(String0.ARY_HEX).initWithUidAndId(String0.ARY_HEX, String0.ARY_HEX);
    tenantedNumberedDao.getCacheableDao().add(TenantedNumberedEntityPrepare.class, simpleGlobalNumberedEntity);
  }

  @Test
  void oneByNo() {
    assertEquals(String0.ARY_HEX, tenantedNumberedDao.oneByNo(TenantedNumberedEntityPrepare.class, String0.ARY_HEX, String0.ALPHABET).getId());
    assertEquals(String0.ARY_HEX, tenantedNumberedDao.oneByNo(TenantedNumberedEntityPrepare.class, String0.ARY_HEX, String0.ALPHABET).getId());
    assertNull(tenantedNumberedDao.oneByNo(TenantedNumberedEntityPrepare.class, String0.ARY_L62, String0.ALPHABET));
  }
}
