package sktest.roc.persistence.entity;

import org.junit.jupiter.api.Test;
import org.shaneking.ling.persistence.sql.entity.IdEntity;
import org.shaneking.ling.test.SKUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CacheableEntityTest extends SKUnit {

  @Test
  void findHavingConditions() {
    assertNotNull(new Test4CacheableEntity().findHavingConditions(IdEntity.FIELD__ID));
  }

  @Test
  void findWhereConditions() {
    assertNotNull(new Test4CacheableEntity().findWhereConditions(IdEntity.FIELD__ID));
  }

  @Test
  void forceHavingCondition() {
    assertNotNull(new Test4CacheableEntity().forceHavingCondition(IdEntity.FIELD__ID));
  }

  @Test
  void forceWhereCondition() {
    assertNotNull(new Test4CacheableEntity().forceWhereCondition(IdEntity.FIELD__ID));
  }

  @Test
  void testToString() {
    assertEquals("Test4CacheableEntity(super=CacheableEntity(super=IdAdtVerEntity(super=IdAdtEntity(super=IdEntity(id=null), invalid=null, lastModifyDateTime=null, lastModifyUserId=null), version=null)))", new Test4CacheableEntity().toString());
  }
}
