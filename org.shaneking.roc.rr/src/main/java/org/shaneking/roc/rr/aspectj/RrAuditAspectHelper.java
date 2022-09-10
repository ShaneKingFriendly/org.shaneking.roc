package org.shaneking.roc.rr.aspectj;

import org.shaneking.ling.persistence.entity.sql.RrAuditLogEntities;
import org.shaneking.roc.persistence.dao.CacheableDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class RrAuditAspectHelper {
  @Autowired
  private CacheableDao cacheableDao;
  @Autowired(required = false)
  private RrAuditLogEntities auditLogEntityClass;

  @Async
  public void async(RrAuditLogEntities auditLogEntity) {
    if (auditLogEntityClass != null && auditLogEntity != null) {
      cacheableDao.add(auditLogEntityClass.entityClass(), auditLogEntity);
    }
  }
}
