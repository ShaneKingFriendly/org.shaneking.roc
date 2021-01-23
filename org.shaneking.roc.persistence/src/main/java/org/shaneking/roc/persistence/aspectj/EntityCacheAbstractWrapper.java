package org.shaneking.roc.persistence.aspectj;

import lombok.NonNull;

import java.util.List;
import java.util.Map;

public abstract class EntityCacheAbstractWrapper {
  public abstract Boolean del(@NonNull String key);

  public abstract Long hdel(@NonNull String key, @NonNull String... fields);

  public abstract String hget(@NonNull String key, @NonNull String field);

  @NonNull
  public abstract List<String> hmget(@NonNull String key, @NonNull String... fields);

  public abstract void hmset(@NonNull String key, @NonNull Map<String, String> map);

  public abstract void hset(@NonNull String key, @NonNull String field, @NonNull String value);
}
