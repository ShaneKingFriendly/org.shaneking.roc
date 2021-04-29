package org.shaneking.roc.cache.listener;

import org.shaneking.ling.zero.lang.String0;
import org.shaneking.ling.zero.util.Map0;
import org.shaneking.roc.cache.RocCaches;
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
  private RocCaches cache;

  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  public void afterCommit(PayloadApplicationEvent<CacheTransactionEventObject> event) {
    String transactionName = event.getPayload().getTransactionName();
    if (String0.isNullOrEmpty(transactionName)) {
      RocCaches.HDEL_MAP.get().remove(transactionName);
    }
  }

  @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
  public void afterRollback(PayloadApplicationEvent<CacheTransactionEventObject> event) {
    String transactionName = event.getPayload().getTransactionName();
    if (cache != null && !String0.isNullOrEmpty(transactionName)) {
      for (Map.Entry<String, List<String>> entry : RocCaches.HDEL_MAP.get().getOrDefault(transactionName, Map0.newHashMap()).entrySet()) {
        if (entry.getValue() == null) {
          cache.del(entry.getKey());
        } else {
          cache.hdel(entry.getKey(), entry.getValue().toArray(new String[0]));
        }
      }
    }
    RocCaches.HDEL_MAP.get().remove(transactionName);
  }
}
