package org.shaneking.roc.persistence.entity.sql;

import org.shaneking.ling.persistence.entity.sql.Channelized;
import org.shaneking.ling.persistence.entity.sql.Tenanted;
import org.shaneking.roc.persistence.CacheableEntities;

public interface RrAuditLogEntities extends CacheableEntities, Channelized, Tenanted {
  //@see sktest.roc.rr.cfg.RrCfg.simpleAuditLogEntity
  <T extends RrAuditLogEntities> Class<T> entityClass();

  String getCached();

  String getReqDatetime();

  String getReqIps();

  String getReqJsonStr();

  <T extends RrAuditLogEntities> T setCached(String cached);

  String getReqJsonStrRaw();

  String getReqUrl();

  <T extends RrAuditLogEntities> T setReqJsonStrRaw(String reqJsonStrRaw);

  String getReqSignature();

  <T extends RrAuditLogEntities> T setReqSignature(String reqSignature);

  String getRespJsonStr();

  String getRespJsonStrCtx();

  String getRespJsonStrRaw();

  String getReqUserId();

  <T extends RrAuditLogEntities> T setReqUserId(String reqUserId);

  String getRespDatetime();

  String getRespIps();

  <T extends RrAuditLogEntities> T setRespIps(String respIps);

  String getTracingNo();

  <T extends RrAuditLogEntities> T setTracingNo(String tracingNo);

  <T extends RrAuditLogEntities> T setReqDatetime(String reqDatetime);

  <T extends RrAuditLogEntities> T setReqIps(String reqIps);

  <T extends RrAuditLogEntities> T setRespDatetime(String respDatetime);

  <T extends RrAuditLogEntities> T setReqJsonStr(String reqJsonStr);

  <T extends RrAuditLogEntities> T setReqUrl(String reqUrl);

  <T extends RrAuditLogEntities> T setRespJsonStr(String respJsonStr);

  <T extends RrAuditLogEntities> T setRespJsonStrCtx(String respJsonStrCtx);

  <T extends RrAuditLogEntities> T setRespJsonStrRaw(String respJsonStrRaw);
}
