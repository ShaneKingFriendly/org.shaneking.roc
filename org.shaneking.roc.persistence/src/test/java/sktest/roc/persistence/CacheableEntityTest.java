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
    Files.write(tstOFiles().toPath(), new CacheableEntityPrepare1().createTableAndIndexIfNotExistSql().getBytes());
    assertEquals(String.join(String0.BR_LINUX, Files.readAllLines(tstOFiles().toPath())).trim(), new CacheableEntityPrepare1().createTableAndIndexIfNotExistSql().trim());
  }

  @Test
  void findHavingConditions() {
    assertNotNull(new CacheableEntityPrepare1().findHavingConditions(Identified.FIELD__ID));
  }

  @Test
  void findWhereConditions() {
    assertNotNull(new CacheableEntityPrepare1().findWhereConditions(Identified.FIELD__ID));
  }

  @Test
  void forceHavingCondition() {
    assertNotNull(new CacheableEntityPrepare1().forceHavingCondition(Identified.FIELD__ID));
  }

  @Test
  void forceWhereCondition() {
    assertNotNull(new CacheableEntityPrepare1().forceWhereCondition(Identified.FIELD__ID));
  }

  @Test
  void testToString() {
    assertEquals("CacheableEntityPrepare1(super=AbstractCacheableEntity(super=AbstractDialectSqlEntity(id=null, ver=null, dd=N, ivd=null, lmDsz=null, lmUid=null, no=null), lastModifyUser=null))", new CacheableEntityPrepare1().toString());
  }
}
