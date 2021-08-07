package org.shaneking.roc.persistence.entity.sql;

import org.shaneking.ling.persistence.entity.sql.Channelized;
import org.shaneking.ling.persistence.entity.sql.Tenanted;
import org.shaneking.roc.persistence.CacheableEntities;

public interface RrAuditLogEntities extends CacheableEntities, Channelized, Tenanted {
  String getTracingNo();

  <T extends RrAuditLogEntities> T setTracingNo(String tracingNo);

  String getReqDatetime();

  <T extends RrAuditLogEntities> T setReqDatetime(String reqDatetime);

  String getReqIps();

  <T extends RrAuditLogEntities> T setReqIps(String reqIps);

  String getReqUserId();

  <T extends RrAuditLogEntities> T setReqUserId(String reqUserId);

  String getReqJsonStrRaw();

  <T extends RrAuditLogEntities> T setReqJsonStrRaw(String reqJsonStrRaw);

  String getReqJsonStr();

  <T extends RrAuditLogEntities> T setReqJsonStr(String reqJsonStr);

  String getReqUrl();

  <T extends RrAuditLogEntities> T setReqUrl(String reqUrl);

  String getReqSignature();

  <T extends RrAuditLogEntities> T setReqSignature(String reqSignature);

  String getCached();

  <T extends RrAuditLogEntities> T setCached(String cached);

  String getRespJsonStr();

  <T extends RrAuditLogEntities> T setRespJsonStr(String respJsonStr);

  String getRespJsonStrCtx();

  <T extends RrAuditLogEntities> T setRespJsonStrCtx(String respJsonStrCtx);

  String getRespJsonStrRaw();

  <T extends RrAuditLogEntities> T setRespJsonStrRaw(String respJsonStrRaw);

  String getRespIps();

  <T extends RrAuditLogEntities> T setRespIps(String respIps);

  String getRespDatetime();

  <T extends RrAuditLogEntities> T setRespDatetime(String respDatetime);

  //@see sktest.roc.rr.cfg.RrCfg.helloAuditLogEntity
  <T extends RrAuditLogEntities> Class<T> entityClass();
}
