package sktest.roc.persistence.entity;

import org.junit.jupiter.api.Test;
import org.shaneking.ling.persistence.entity.Identified;
import org.shaneking.ling.test.SKUnit;
import org.shaneking.ling.zero.lang.String0;

import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CacheableEntityTest extends SKUnit {

  @Test
  void createTableIfNotExistSql() throws IOException {
//    Files.write(tstOFiles().toPath(), new HelloCacheableEntity().createTableIfNotExistSql().getBytes());
    assertEquals(String.join(String0.BR_LINUX, Files.readAllLines(tstOFiles().toPath())).trim(), new HelloCacheableEntity().createTableIfNotExistSql().trim());
  }

  @Test
  void findHavingConditions() {
    assertNotNull(new HelloCacheableEntity().findHavingConditions(Identified.FIELD__ID));
  }

  @Test
  void findWhereConditions() {
    assertNotNull(new HelloCacheableEntity().findWhereConditions(Identified.FIELD__ID));
  }

  @Test
  void forceHavingCondition() {
    assertNotNull(new HelloCacheableEntity().forceHavingCondition(Identified.FIELD__ID));
  }

  @Test
  void forceWhereCondition() {
    assertNotNull(new HelloCacheableEntity().forceWhereCondition(Identified.FIELD__ID));
  }

  @Test
  void testToString() {
    assertEquals("HelloCacheableEntity(super=CacheableEntity(super=IdAdtVerSqlEntitiesTemplate(id=null, invalid=null, lastModifyDateTime=null, lastModifyUserId=null, version=null), lastModifyUser=null))", new HelloCacheableEntity().toString());
  }
}
