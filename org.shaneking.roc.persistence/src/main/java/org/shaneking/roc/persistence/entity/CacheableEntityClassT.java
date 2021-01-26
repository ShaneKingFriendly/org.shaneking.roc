package org.shaneking.roc.persistence.entity;

//can used in org.shaneking.roc.rr.aspectj.RrAuditAspect.around cacheableDao.add(auditLogEntity.entityClass(), auditLogEntity);
public interface CacheableEntityClassT {
  <T extends CacheableEntity> Class<T> entityClass();
}
