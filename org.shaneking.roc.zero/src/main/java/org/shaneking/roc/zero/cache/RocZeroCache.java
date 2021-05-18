package org.shaneking.roc.zero.cache;

import lombok.NonNull;
import org.shaneking.ling.zero.cache.ZeroCache;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.ling.zero.util.List0;
import org.shaneking.ling.zero.util.LruMap;
import org.shaneking.ling.zero.util.Map0;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;
import java.util.Map;

public interface RocZeroCache extends ZeroCache {
  default Boolean del(boolean withoutTransactional, @NonNull String key) {
    if (!withoutTransactional && inTransactional()) {
      //need record operation key in transactional
      DEL_MAP.get().computeIfAbsent(currentTransactionName(), k -> List0.newArrayList()).add(key);
    }
    LRU_MAP.remove(key);
    return true;
  }

  default Long hdel(boolean withoutTransactional, @NonNull String key, @NonNull String... fields) {
    if (!withoutTransactional && inTransactional()) {
      //need record operation key in transactional
      DEL_MAP2.get().computeIfAbsent(currentTransactionName(), k -> Map0.newHashMap()).computeIfAbsent(key, k -> List0.newArrayList()).addAll(List0.newArrayList(fields));
    }
    long rtn = 0L;
    LruMap<String, String> map = LRU_MAP2.get(key);
    if (map != null) {
      for (String field : fields) {
        rtn += String0.isNullOrEmpty(map.remove(field)) ? 0 : 1;
      }
    }
    return rtn;
  }

  default void hmset(@NonNull String key, @NonNull Map<String, String> map) {
    boolean contain = false;
    if (inTransactional()) {
      //in transactional, if key in record operation key list, can not cache
      contain = DEL_MAP.get().computeIfAbsent(currentTransactionName(), k -> List0.newArrayList()).contains(key);
      List<String> containList = DEL_MAP2.get().computeIfAbsent(currentTransactionName(), k -> Map0.newHashMap()).computeIfAbsent(key, k -> List0.newArrayList());
      if (!contain) {
        LruMap<String, String> lruMap = LRU_MAP2.computeIfAbsent(key, k -> new LruMap<>(1023));
        for (String field : map.keySet()) {
          if (!containList.contains(field)) {
            lruMap.put(field, map.get(field));
          }
        }
      }
    } else {
      LRU_MAP2.computeIfAbsent(key, k -> new LruMap<>(1023)).putAll(map);
    }
  }

  default void hset(@NonNull String key, @NonNull String field, @NonNull String value) {
    boolean contain = false;
    if (inTransactional()) {
      //in transactional, if key in record operation key list, can not cache
      contain = DEL_MAP.get().computeIfAbsent(currentTransactionName(), k -> List0.newArrayList()).contains(key)
        || DEL_MAP2.get().computeIfAbsent(currentTransactionName(), k -> Map0.newHashMap()).computeIfAbsent(key, k -> List0.newArrayList()).contains(field);
    }
    if (!contain) {
      LRU_MAP2.computeIfAbsent(key, k -> new LruMap<>(1023)).put(field, value);
    }
  }

  default void set(@NonNull String key, @NonNull String value) {
    boolean contain = false;
    if (inTransactional()) {
      //in transactional, if key in record operation key list, can not cache
      contain = DEL_MAP.get().computeIfAbsent(currentTransactionName(), k -> List0.newArrayList()).contains(key);
    }
    if (!contain) {
      LRU_MAP.put(key, value);
    }
  }

  default boolean inTransactional() {
    return !String0.isNullOrEmpty(currentTransactionName()) && !TransactionSynchronizationManager.isCurrentTransactionReadOnly();
  }

  default String currentTransactionName() {
    return TransactionSynchronizationManager.getCurrentTransactionName();
  }
}
