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

  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION, fallbackExecution = true)
  public void afterCompletion(PayloadApplicationEvent<String> event) {
    RocCaches.NEW_MAP.get().remove(event.getPayload());
  }

  @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
  public void afterRollback(PayloadApplicationEvent<String> event) {
    if (cache != null) {
      for (Map.Entry<String, List<String>> entry : RocCaches.NEW_MAP.get().getOrDefault(String0.nullToEmpty(event.getPayload()), Map0.newHashMap()).entrySet()) {
        if (entry.getValue() == null) {
          cache.del(entry.getKey());
        } else {
          cache.hdel(entry.getKey(), entry.getValue().toArray(new String[0]));
        }
      }
    }
    RocCaches.NEW_MAP.get().remove(event.getPayload());
  }
}
