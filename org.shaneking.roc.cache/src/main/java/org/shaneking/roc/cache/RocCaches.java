package org.shaneking.roc.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.NonNull;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.ling.zero.util.List0;
import org.shaneking.ling.zero.util.Map0;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public interface RocCaches {
  String ERR_CODE__CACHE_HIT_ALL = "ROC_CACHES__CACHE_HIT_ALL";
  String ERR_CODE__CACHE_HIT_MISS = "ROC_CACHES__CACHE_HIT_MISS";
  String ERR_CODE__CACHE_HIT_PART = "ROC_CACHES__CACHE_HIT_PART";

  Cache<String, String> CACHE = Caffeine.newBuilder().expireAfterWrite(180, TimeUnit.SECONDS).initialCapacity(88).maximumSize(1023).build();

  ThreadLocal<Map<String, Map<String, List<String>>>> NEW_MAP = ThreadLocal.withInitial(Map0::newHashMap);//by transaction, by key, field list

  default Boolean del(@NonNull String key) {
    CACHE.invalidate(key);
    return true;
  }

  default String get(@NonNull String key) {
    return CACHE.getIfPresent(key);
  }

  default Long hdel(@NonNull String key, @NonNull String... fields) {
    CACHE.invalidateAll(Arrays.stream(fields).map(field -> key + String0.MORE + field).filter(Objects::nonNull).collect(Collectors.toList()));
    return 0L;
  }

  default String hget(@NonNull String key, @NonNull String field) {
    return CACHE.getIfPresent(key + String0.MORE + field);
  }

  default @NonNull List<String> hmget(@NonNull String key, @NonNull String... fields) {
    return Arrays.stream(fields).map(field -> CACHE.getIfPresent(key + String0.MORE + field)).filter(Objects::nonNull).collect(Collectors.toList());
  }

  default void hmset(@NonNull String key, @NonNull Map<String, String> map) {
    NEW_MAP.get().getOrDefault(String0.nullToEmpty(TransactionSynchronizationManager.getCurrentTransactionName()), Map0.newHashMap()).getOrDefault(key, List0.newArrayList()).addAll(map.keySet());
    map.forEach((k, v) -> CACHE.put(key + String0.MORE + k, v));
  }

  default void hset(@NonNull String key, @NonNull String field, @NonNull String value) {
    NEW_MAP.get().getOrDefault(String0.nullToEmpty(TransactionSynchronizationManager.getCurrentTransactionName()), Map0.newHashMap()).getOrDefault(key, List0.newArrayList()).add(field);
    CACHE.put(key + String0.MORE + field, value);
  }

  default void set(@NonNull String key, @NonNull String value) {
    NEW_MAP.get().getOrDefault(String0.nullToEmpty(TransactionSynchronizationManager.getCurrentTransactionName()), Map0.newHashMap()).put(key, null);
    CACHE.put(key, value);
  }

  default void set(@NonNull String key, int seconds, @NonNull String value) {
    NEW_MAP.get().getOrDefault(String0.nullToEmpty(TransactionSynchronizationManager.getCurrentTransactionName()), Map0.newHashMap()).put(key, null);
    CACHE.put(key, value);
  }
}
