package org.shaneking.roc.rr.ctx;

import org.shaneking.roc.persistence.entity.AuditLogEntity;

public class RrCtx {
  public static final ThreadLocal<AuditLogEntity> auditLog = new ThreadLocal<>();
}
