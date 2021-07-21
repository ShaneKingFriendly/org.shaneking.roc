package sktest.roc.persistence;

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
    Files.write(tstOFiles().toPath(), new CacheableEntityPrepare().createTableAndIndexIfNotExistSql().getBytes());
    assertEquals(String.join(String0.BR_LINUX, Files.readAllLines(tstOFiles().toPath())).trim(), new CacheableEntityPrepare().createTableAndIndexIfNotExistSql().trim());
  }

  @Test
  void findHavingConditions() {
    assertNotNull(new CacheableEntityPrepare().findHavingConditions(Identified.FIELD__ID));
  }

  @Test
  void findWhereConditions() {
    assertNotNull(new CacheableEntityPrepare().findWhereConditions(Identified.FIELD__ID));
  }

  @Test
  void forceHavingCondition() {
    assertNotNull(new CacheableEntityPrepare().forceHavingCondition(Identified.FIELD__ID));
  }

  @Test
  void forceWhereCondition() {
    assertNotNull(new CacheableEntityPrepare().forceWhereCondition(Identified.FIELD__ID));
  }

  @Test
  void testToString() {
    assertEquals("CacheableEntityPrepare(super=AbstractCacheableEntity(super=AbstractDialectSqlEntity(id=null, version=null, dd=N, invalid=null, lastModifyDateTime=null, lastModifyUserId=null, no=null), lastModifyUser=null))", new CacheableEntityPrepare().toString());
  }
}
