package org.shaneking.roc.zero.cache.listener;

import org.shaneking.ling.zero.cache.ZeroCache;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.ling.zero.util.List0;
import org.shaneking.ling.zero.util.Map0;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;
import java.util.Map;

@Component
public class CacheTransactionEventListener {
  @Autowired(required = false)
  private ZeroCache cache;

//  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
//  public void afterCommit(PayloadApplicationEvent<CacheTransactionEventObject> event) {
//    String transactionName = event.getPayload().getTransactionName();
//    if (!String0.isNullOrEmpty(transactionName)) {
//      ZeroCache.DEL_MAP.get().remove(transactionName);
//      ZeroCache.DEL_MAP2.get().remove(transactionName);
//    }
//  }
//
//  @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
@TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
public void afterRollback(PayloadApplicationEvent<CacheTransactionEventObject> event) {
  String transactionName = event.getPayload().getTransactionName();
  if (!String0.isNullOrEmpty(transactionName)) {
    if (cache != null) {
      //need remove other thread re-cache
      for (String key : ZeroCache.DEL_MAP.get().computeIfAbsent(transactionName, k -> List0.newArrayList())) {
        cache.del(true, key);
      }
      for (Map.Entry<String, List<String>> entry : ZeroCache.DEL_MAP2.get().computeIfAbsent(transactionName, k -> Map0.newHashMap()).entrySet()) {
        if (entry.getValue() == null) {
            cache.del(true, entry.getKey());
          } else {
            cache.hdel(true, entry.getKey(), entry.getValue().toArray(new String[0]));
          }
        }
      }
      ZeroCache.DEL_MAP.get().remove(transactionName);
      ZeroCache.DEL_MAP2.get().remove(transactionName);
    }
  }
}
