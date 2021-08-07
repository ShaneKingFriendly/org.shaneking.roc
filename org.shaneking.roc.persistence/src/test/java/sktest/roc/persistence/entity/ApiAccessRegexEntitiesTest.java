package sktest.roc.persistence.entity;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class ApiAccessRegexEntitiesTest {
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
  }
}
