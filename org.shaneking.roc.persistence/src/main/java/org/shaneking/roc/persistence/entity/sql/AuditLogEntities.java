package org.shaneking.roc.persistence.entity.sql;

import org.shaneking.roc.persistence.entity.TenantedChannelizedEntities;

public interface AuditLogEntities extends TenantedChannelizedEntities {

  String getProxyChannelId();

  <T extends AuditLogEntities> T setProxyChannelId(String proxyChannelId);

  String getTracingNo();

  <T extends AuditLogEntities> T setTracingNo(String tracingNo);

  String getReqDatetime();

  <T extends AuditLogEntities> T setReqDatetime(String reqDatetime);

  String getReqIps();

  <T extends AuditLogEntities> T setReqIps(String reqIps);

  String getReqUserId();

  <T extends AuditLogEntities> T setReqUserId(String reqUserId);

  String getReqJsonStrRaw();

  <T extends AuditLogEntities> T setReqJsonStrRaw(String reqJsonStrRaw);

  String getReqJsonStr();

  <T extends AuditLogEntities> T setReqJsonStr(String reqJsonStr);

  String getReqUrl();

  <T extends AuditLogEntities> T setReqUrl(String reqUrl);

  String getReqSignature();

  <T extends AuditLogEntities> T setReqSignature(String reqSignature);

  String getCached();

  <T extends AuditLogEntities> T setCached(String cached);

  String getRespJsonStr();

  <T extends AuditLogEntities> T setRespJsonStr(String respJsonStr);

  String getRespJsonStrCtx();

  <T extends AuditLogEntities> T setRespJsonStrCtx(String respJsonStrCtx);

  String getRespJsonStrRaw();

  <T extends AuditLogEntities> T setRespJsonStrRaw(String respJsonStrRaw);

  String getRespIps();

  <T extends AuditLogEntities> T setRespIps(String respIps);

  String getRespDatetime();

  <T extends AuditLogEntities> T setRespDatetime(String respDatetime);

  //@see sktest.roc.rr.cfg.RrCfg.helloAuditLogEntity
  <T extends AuditLogEntities> Class<T> entityClass();
}
