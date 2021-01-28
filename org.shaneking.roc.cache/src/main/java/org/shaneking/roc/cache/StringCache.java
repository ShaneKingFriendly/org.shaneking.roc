package org.shaneking.roc.cache;

import lombok.NonNull;
import org.shaneking.ling.cache.StringCaches;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(prefix = "sk.roc.cache.string", value = "enabled")
public class StringCache implements StringCaches {
  @Override
  public void set(@NonNull String key, int seconds, @NonNull String value) {
    //
  }
}
