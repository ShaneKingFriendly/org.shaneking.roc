package org.shaneking.roc.persistence.entity;

//can't used in org.shaneking.roc.rr.aspectj.RrAuditAspect.around cacheableDao.add(auditLogEntity.entityClass(), auditLogEntity);
public interface CacheableEntityClassQ {
  Class<? extends CacheableEntity> entityClass();
}
