package org.shaneking.roc.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.NonNull;
import org.shaneking.ling.cache.StringCaches;
import org.shaneking.ling.zero.lang.String0;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
@ConditionalOnProperty(prefix = "sk.roc.cache.string", value = "enabled")
public class StringCache implements StringCaches {
  public static final Cache<String, String> CACHE = Caffeine.newBuilder().expireAfterWrite(180, TimeUnit.SECONDS).initialCapacity(88).maximumSize(1023).build();

  @Override
  public Boolean del(@NonNull String key) {
    CACHE.invalidate(key);
    return true;
  }

  @Override
  public String get(@NonNull String key) {
    return CACHE.getIfPresent(key);
  }

  @Override
  public Long hdel(@NonNull String key, @NonNull String... fields) {
    CACHE.invalidateAll(Arrays.stream(fields).map(field -> key + String0.MORE + field).filter(Objects::nonNull).collect(Collectors.toList()));
    return 0L;
  }

  @Override
  public String hget(@NonNull String key, @NonNull String field) {
    return CACHE.getIfPresent(key + String0.MORE + field);
  }

  @Override
  public @NonNull List<String> hmget(@NonNull String key, @NonNull String... fields) {
    return Arrays.stream(fields).map(field -> CACHE.getIfPresent(key + String0.MORE + field)).filter(Objects::nonNull).collect(Collectors.toList());
  }

  @Override
  public void hmset(@NonNull String key, @NonNull Map<String, String> map) {
    map.forEach((k, v) -> CACHE.put(key + String0.MORE + k, v));
  }

  @Override
  public void hset(@NonNull String key, @NonNull String field, @NonNull String value) {
    CACHE.put(key + String0.MORE + field, value);
  }

  @Override
  public void set(@NonNull String key, @NonNull String value) {
    CACHE.put(key, value);
  }

  @Override
  public void set(@NonNull String key, int seconds, @NonNull String value) {
    CACHE.put(key, value);
  }
}
