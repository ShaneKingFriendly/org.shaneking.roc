package sktest.roc.persistence.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.ling.zero.util.List0;
import org.shaneking.roc.persistence.dao.TenantNumberedCacheableDao;
import org.shaneking.roc.test.SKSpringUnit;
import org.springframework.beans.factory.annotation.Autowired;
import sktest.roc.persistence.entity.HelloTenantNumberedEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class TenantNumberedCacheableDaoTest extends SKSpringUnit {

  @Autowired
  private TenantNumberedCacheableDao tenantNumberedCacheableDao;

  @BeforeEach
  void beforeEach() {
    tenantNumberedCacheableDao.getCacheableDao().rmvByIds(HelloTenantNumberedEntity.class, new HelloTenantNumberedEntity(), List0.newArrayList(tenantNumberedCacheableDao.getCacheableDao().ids(HelloTenantNumberedEntity.class, new HelloTenantNumberedEntity()).split(String0.COMMA)));

    HelloTenantNumberedEntity helloGlobalNumberedEntity = new HelloTenantNumberedEntity();
    helloGlobalNumberedEntity.setNo(String0.ARY_HEX).setTenantId(String0.ALPHABET).initWithUserIdAndId(String0.ARY_HEX, String0.ARY_HEX);
    tenantNumberedCacheableDao.getCacheableDao().add(HelloTenantNumberedEntity.class, helloGlobalNumberedEntity);
  }

  @Test
  void oneByNo() {
    assertEquals(String0.ARY_HEX, tenantNumberedCacheableDao.oneByNo(HelloTenantNumberedEntity.class, String0.ARY_HEX, String0.ALPHABET).getId());
    assertEquals(String0.ARY_HEX, tenantNumberedCacheableDao.oneByNo(HelloTenantNumberedEntity.class, String0.ARY_HEX, String0.ALPHABET).getId());
    assertNull(tenantNumberedCacheableDao.oneByNo(HelloTenantNumberedEntity.class, String0.ARY_L62, String0.ALPHABET));
  }
}
