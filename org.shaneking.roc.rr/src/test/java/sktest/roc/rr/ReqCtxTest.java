package sktest.roc.rr;

import org.junit.jupiter.api.Test;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.ling.persistence.sql.entity.sqllite.SqlliteDialectSqlEntities;
import org.shaneking.roc.persistence.entity.UserEntity;
import org.shaneking.roc.rr.ReqCtx;

import javax.persistence.Table;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ReqCtxTest {

  @Test
  void testToString() {
    assertAll(
      () -> assertEquals("ReqCtx(ips=null, jon=null, language=null, user=null)", new ReqCtx().toString()),
      () -> assertEquals("{}", OM3.writeValueAsString(new ReqCtx())),
      () -> assertEquals("{\"ips\":\"ips\",\"jon\":{},\"language\":\"language\",\"user\":{}}", OM3.writeValueAsString(new ReqCtx().setIps("ips").setJon(OM3.createObjectNode()).setLanguage("language").setUser(new Test4UserEntity())))
    );
  }

  @Table
  public static class Test4UserEntity extends UserEntity implements SqlliteDialectSqlEntities {

  }
}
