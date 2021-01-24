package sktest.roc.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.shaneking.ling.test.SKUnit;
import org.shaneking.roc.jackson.JavaType3;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JavaType3Test extends SKUnit {

  @Test
  void resolveJavaTypes() {
    assertEquals(0, JavaType3.resolveJavaTypes(new ObjectMapper().getTypeFactory().constructType(String.class)).length);
  }
}
