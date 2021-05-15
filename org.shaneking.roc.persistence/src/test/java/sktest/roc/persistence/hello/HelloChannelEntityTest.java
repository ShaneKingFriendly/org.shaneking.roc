package sktest.roc.persistence.hello;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.ling.test.SKUnit;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.roc.persistence.hello.HelloChannelEntity;

import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

class HelloChannelEntityTest extends SKUnit {

  @Test
  void createTableIfNotExistSql() throws IOException {
    Files.write(tstOFiles().toPath(), new HelloChannelEntity().createTableIfNotExistSql().getBytes());
    Assertions.assertEquals(String.join(String0.BR_LINUX, Files.readAllLines(tstOFiles().toPath())).trim(), new HelloChannelEntity().createTableIfNotExistSql().trim());
  }

  @Test
  void entityClass() {
    assertNotNull(new HelloChannelEntity().entityClass());
  }

  @Test
  void testToString() {
    assertAll(
      () -> Assertions.assertEquals("HelloChannelEntity(super=ChannelExample(super=AbstractCacheableEntity(super=AbstractDialectSqlEntity(id=null, dd=N, no=null, invalid=null, lastModifyDateTime=null, lastModifyUserId=null, version=null), lastModifyUser=null), name=null, description=null, tokenValue=null, tokenForce=null, tokenAlgorithmType=null, tokenValueType=null))", new HelloChannelEntity().toString()),
      () -> assertEquals("{}", OM3.writeValueAsString(new HelloChannelEntity().nullSetter()))
    );
  }
}
