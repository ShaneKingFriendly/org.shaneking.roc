package sktest.roc.persistence.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.ling.test.SKUnit;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.roc.persistence.entity.sql.ApiAccessEntity;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class HelloApiAccessEntityTest extends SKUnit {

  @Test
  void check() {
    assertAll(
      () -> assertTrue(Pattern.matches("^[\\s\\S]*(user/mod|user/rmvById)[\\s\\S]*$", "/user/mod")),
      () -> assertTrue(Pattern.matches("^[\\s\\S]*(user/mod|user/rmvById)[\\s\\S]*$", "/user/rmvById")),
      () -> assertTrue(Pattern.matches("^[\\s\\S]*(add|mge|lst)\\([\\s\\S]*$", "user.add(String s)")),
      () -> assertTrue(Pattern.matches("^[\\s\\S]*(add|mge|lst)\\([\\s\\S]*$", "user.mge(String s)")),
      () -> assertTrue(Pattern.matches("^[\\s\\S]*(add|mge|lst)\\([\\s\\S]*$", "user.lst(String s)")),
      () -> assertFalse(Pattern.matches("^[\\s\\S]*(add|mge|lst)\\([\\s\\S]*$", "user.ls(String s)"))
    );
    ApiAccessEntity apiAccessEntity = new HelloApiAccessEntity();
    assertAll(
      () -> assertFalse(apiAccessEntity.check(null, null)),
      () -> assertTrue(apiAccessEntity.check(String0.ARY_L62, String0.ARY_L62))
    );
    apiAccessEntity.setAllowSignatureRegex(String0.ARY_HEX).setAllowUrlRegex(String0.ARY_HEX).setDenySignatureRegex(String0.ARY_HEX).setDenyUrlRegex(String0.ARY_HEX);
    assertAll(
      () -> assertFalse(apiAccessEntity.check(null, null)),
      () -> assertFalse(apiAccessEntity.check(String0.ARY_L62, String0.ARY_L62))
    );
  }

  @Test
  void testToString() {
    assertAll(
      () -> Assertions.assertEquals("HelloApiAccessEntity(super=ApiAccessEntity(super=TenantedChannelizedEntity(super=ChannelizedEntity(super=CacheableEntity(super=AbstractIdAdtVerSqlEntity(id=null, invalid=null, lastModifyDateTime=null, lastModifyUserId=null, version=null), lastModifyUser=null), channelId=null), tenantId=null), allowUrlRegex=null, allowSignatureRegex=null, denyUrlRegex=null, denySignatureRegex=null))", new HelloApiAccessEntity().toString()),
      () -> assertEquals("{}", OM3.writeValueAsString(new HelloApiAccessEntity().nullSetter()))
    );
  }
}
