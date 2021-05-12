package org.shaneking.roc.persistence;

//can used in org.shaneking.roc.rr.aspectj.RrAuditAspect.around cacheableDao.add(auditLogEntity.entityClass(), auditLogEntity);
public interface CacheableEntityClassT {
  <T extends AbstractCacheableEntity> Class<T> entityClass();
}
