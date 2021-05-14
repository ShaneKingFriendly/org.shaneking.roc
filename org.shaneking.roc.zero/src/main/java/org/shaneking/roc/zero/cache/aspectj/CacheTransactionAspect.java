package org.shaneking.roc.zero.cache.aspectj;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.shaneking.roc.zero.cache.listener.CacheTransactionEventObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Aspect
@Component
@ConditionalOnProperty(prefix = "sk.roc.zero.cache.transactional", value = "enabled", matchIfMissing = true)
@Slf4j
@Order(CacheTransactionAspect.ORDER)//@EnableTransactionManagement(order = <this)
public class CacheTransactionAspect {
  public static final int ORDER = 500000;

  @Autowired
  private ApplicationEventPublisher applicationEventPublisher;

  @Pointcut("execution(@org.springframework.transaction.annotation.Transactional * *..*.*(..))")
  private void pointcut() {
  }

  @Around("pointcut() && @annotation(transactional)")
  public Object around(ProceedingJoinPoint pjp, Transactional transactional) throws Throwable {
    applicationEventPublisher.publishEvent(new CacheTransactionEventObject().setReadOnly(transactional.readOnly())
      .setCurrentTransactionReadOnly(TransactionSynchronizationManager.isCurrentTransactionReadOnly())
      .setTransactionName(TransactionSynchronizationManager.getCurrentTransactionName()));
    return pjp.proceed();
  }
}
