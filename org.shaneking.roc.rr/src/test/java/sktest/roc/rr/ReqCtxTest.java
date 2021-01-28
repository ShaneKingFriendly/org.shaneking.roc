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
      () -> assertEquals("ReqCtx(auditLog=null, channel=null, jon=null, language=null, tenant=null, user=null)", new ReqCtx().toString()),
      () -> assertEquals("{}", OM3.writeValueAsString(new ReqCtx())),
      () -> assertEquals("{\"jon\":{},\"language\":\"language\",\"user\":{}}", OM3.writeValueAsString(new ReqCtx().setJon(OM3.createObjectNode()).setLanguage("language").setUser(new Test4UserEntity())))
    );
  }

  @Table
  public static class Test4UserEntity extends UserEntity implements SqlliteDialectSqlEntities {

    @Override
    public Class<Test4UserEntity> entityClass() {
      return Test4UserEntity.class;
    }
  }
}
