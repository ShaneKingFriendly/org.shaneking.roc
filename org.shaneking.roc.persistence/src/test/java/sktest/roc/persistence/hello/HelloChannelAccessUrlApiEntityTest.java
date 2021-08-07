package sktest.roc.persistence.hello;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.ling.test.SKUnit;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.roc.persistence.hello.HelloChannelAccessUrlApiEntity;

import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

class HelloChannelAccessUrlApiEntityTest extends SKUnit {

  @Test
  void createTableAndIndexIfNotExistSql() throws IOException {
    Files.write(tstOFiles().toPath(), new HelloChannelAccessUrlApiEntity().createTableAndIndexIfNotExistSql().getBytes());
    Assertions.assertEquals(String.join(String0.BR_LINUX, Files.readAllLines(tstOFiles().toPath())).trim(), new HelloChannelAccessUrlApiEntity().createTableAndIndexIfNotExistSql().trim());
  }

  @Test
  void entityClass() {
    assertNotNull(new HelloChannelAccessUrlApiEntity().entityClass());
  }

  @Test
  void testToString() {
    assertAll(
      () -> Assertions.assertEquals("HelloChannelAccessUrlApiEntity(super=ChannelAccessUrlApiExample(super=ChannelizedEntity(super=AbstractCacheableEntity(super=AbstractDialectSqlEntity(id=null, version=null, dd=N, invalid=null, lastModifyDateTime=null, lastModifyUserId=null, no=null), lastModifyUser=null), channelId=null), op=null, url=null))", new HelloChannelAccessUrlApiEntity().toString()),
      () -> assertEquals("{}", OM3.writeValueAsString(new HelloChannelAccessUrlApiEntity().nullSetter()))
    );
  }
}
