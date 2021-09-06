package sktest.roc.persistence.simple;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.ling.test.SKUnit;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.roc.persistence.simple.SimpleChannelAccessSignatureApiEntity;

import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

class SimpleChannelAccessSignatureApiEntityTest extends SKUnit {

  @Test
  void createTableAndIndexIfNotExistSql() throws IOException {
    Files.write(tstOFiles().toPath(), new SimpleChannelAccessSignatureApiEntity().createTableAndIndexIfNotExistSql().getBytes());
    Assertions.assertEquals(String.join(String0.BR_LINUX, Files.readAllLines(tstOFiles().toPath())).trim(), new SimpleChannelAccessSignatureApiEntity().createTableAndIndexIfNotExistSql().trim());
  }

  @Test
  void entityClass() {
    assertNotNull(new SimpleChannelAccessSignatureApiEntity().entityClass());
  }

  @Test
  void testToString() {
    assertAll(
      () -> Assertions.assertEquals("SimpleChannelAccessSignatureApiEntity(super=ChannelAccessSignatureApiExample(super=ChannelizedEntity(super=AbstractCacheableEntity(super=AbstractDialectSqlEntity(id=null, ver=null, dd=N, ivd=null, lmDsz=null, lmUid=null, no=null), lastModifyUser=null), channelId=null), op=null, signature=null))", new SimpleChannelAccessSignatureApiEntity().toString()),
      () -> assertEquals("{}", OM3.writeValueAsString(new SimpleChannelAccessSignatureApiEntity().nullSetter()))
    );
  }
}
