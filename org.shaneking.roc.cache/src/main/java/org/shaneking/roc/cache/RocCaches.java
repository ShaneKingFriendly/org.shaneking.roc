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

  ThreadLocal<Map<String, List<String>>> DEL_MAP = ThreadLocal.withInitial(Map0::newHashMap);//by transaction, by key list
  ThreadLocal<Map<String, Map<String, List<String>>>> HDEL_MAP = ThreadLocal.withInitial(Map0::newHashMap);//by transaction, by key, field list

  default Boolean del(@NonNull String key) {
    return del(false, key);
  }

  default Boolean del(boolean withoutTransactional, @NonNull String key) {
    String transactionName = TransactionSynchronizationManager.getCurrentTransactionName();
    if (!withoutTransactional && !String0.isNullOrEmpty(transactionName) && !TransactionSynchronizationManager.isCurrentTransactionReadOnly()) {
      //need record operation key in transactional
      DEL_MAP.get().getOrDefault(transactionName, List0.newArrayList()).add(key);
    }
    CACHE.invalidate(key);
    return true;
  }

  default String get(@NonNull String key) {
    return CACHE.getIfPresent(key);
  }

  default Long hdel(@NonNull String key, @NonNull String... fields) {
    return hdel(false, key, fields);
  }

  default Long hdel(boolean withoutTransactional, @NonNull String key, @NonNull String... fields) {
    String transactionName = TransactionSynchronizationManager.getCurrentTransactionName();
    if (!withoutTransactional && !String0.isNullOrEmpty(transactionName) && !TransactionSynchronizationManager.isCurrentTransactionReadOnly()) {
      //need record operation key in transactional
      HDEL_MAP.get().getOrDefault(transactionName, Map0.newHashMap()).getOrDefault(key, List0.newArrayList()).addAll(List0.newArrayList(fields));
    }
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
    boolean contain = false;
    String transactionName = TransactionSynchronizationManager.getCurrentTransactionName();
    if (!String0.isNullOrEmpty(transactionName) && !TransactionSynchronizationManager.isCurrentTransactionReadOnly()) {
      //in transactional, if key in record operation key list, can not cache
      contain = DEL_MAP.get().getOrDefault(transactionName, List0.newArrayList()).contains(key);
      List<String> list = HDEL_MAP.get().getOrDefault(transactionName, Map0.newHashMap()).getOrDefault(key, List0.newArrayList());
      if (!contain && list.size() > 0) {
        for (String s : map.keySet()) {
          if (list.contains(s)) {
            contain = true;
            break;
          }
        }
      }
    }
    if (!contain) {
      map.forEach((k, v) -> CACHE.put(key + String0.MORE + k, v));
    }
  }

  default void hset(@NonNull String key, @NonNull String field, @NonNull String value) {
    boolean contain = false;
    String transactionName = TransactionSynchronizationManager.getCurrentTransactionName();
    if (!String0.isNullOrEmpty(transactionName) && !TransactionSynchronizationManager.isCurrentTransactionReadOnly()) {
      //in transactional, if key in record operation key list, can not cache
      contain = DEL_MAP.get().getOrDefault(transactionName, List0.newArrayList()).contains(key) || HDEL_MAP.get().getOrDefault(transactionName, Map0.newHashMap()).getOrDefault(key, List0.newArrayList()).contains(field);
    }
    if (!contain) {
      CACHE.put(key + String0.MORE + field, value);
    }
  }

  default void set(@NonNull String key, @NonNull String value) {
    boolean contain = false;
    String transactionName = TransactionSynchronizationManager.getCurrentTransactionName();
    if (!String0.isNullOrEmpty(transactionName) && !TransactionSynchronizationManager.isCurrentTransactionReadOnly()) {
      //in transactional, if key in record operation key list, can not cache
      contain = DEL_MAP.get().getOrDefault(transactionName, List0.newArrayList()).contains(key);
    }
    if (!contain) {
      CACHE.put(key, value);
    }
  }

  default void set(@NonNull String key, int seconds, @NonNull String value) {
    set(key, value);
  }
}
