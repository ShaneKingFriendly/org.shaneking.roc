package org.shaneking.roc.zero.cache.listener;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Accessors(chain = true)
@ToString
public class CacheTransactionEventObject {
  /**
   * @see TransactionSynchronizationManager#getCurrentTransactionName()
   */
  @Getter
  @Setter
  private String transactionName;
  /**
   * @see TransactionSynchronizationManager#isCurrentTransactionReadOnly()
   */
  @Getter
  @Setter
  private Boolean currentTransactionReadOnly;
  /**
   * @see Transactional#readOnly()
   */
  @Getter
  @Setter
  private boolean readOnly;
}
