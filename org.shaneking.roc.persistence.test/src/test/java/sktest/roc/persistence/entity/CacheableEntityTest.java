package sktest.roc.persistence.entity;

import org.junit.jupiter.api.Test;
import org.shaneking.ling.persistence.sql.entity.IdEntity;
import org.shaneking.ling.test.SKUnit;
import org.shaneking.ling.zero.lang.String0;

import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CacheableEntityTest extends SKUnit {

  @Test
  void createTableIfNotExistSql() throws IOException {
//    Files.write(tstOFiles().toPath(), new Test4CacheableEntity().createTableIfNotExistSql().getBytes());
    assertEquals(String.join(String0.BR_LINUX, Files.readAllLines(tstOFiles().toPath())).trim(), new Test4CacheableEntity().createTableIfNotExistSql().trim());
  }

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
