package org.shaneking.roc.persistence.cache;

import lombok.NonNull;

import java.util.List;
import java.util.Map;

public abstract class AbstractCache {
  public static final String ERR_CODE__ANNOTATION_SETTING_ERROR = "ABSTRACT_CACHE__ANNOTATION_SETTING_ERROR";
  public static final String ERR_CODE__CACHE_HIT_ALL = "ABSTRACT_CACHE__CACHE_HIT_ALL";
  public static final String ERR_CODE__CACHE_HIT_MISS = "ABSTRACT_CACHE__CACHE_HIT_MISS";
  public static final String ERR_CODE__CACHE_HIT_PART = "ABSTRACT_CACHE__CACHE_HIT_PART";

  public abstract Boolean del(@NonNull String key);

  public abstract String get(@NonNull String key);

  public abstract Long hdel(@NonNull String key, @NonNull String... fields);

  public abstract String hget(@NonNull String key, @NonNull String field);

  @NonNull
  public abstract List<String> hmget(@NonNull String key, @NonNull String... fields);

  public abstract void hmset(@NonNull String key, @NonNull Map<String, String> map);

  public abstract void hset(@NonNull String key, @NonNull String field, @NonNull String value);

  public abstract void set(@NonNull String key, @NonNull String value);

  public abstract void set(@NonNull String key, int seconds, @NonNull String value);
}
