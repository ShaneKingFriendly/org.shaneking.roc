package org.shaneking.roc.persistence.entity.sql;

import org.shaneking.ling.persistence.CacheableEntities;
import org.shaneking.ling.persistence.entity.sql.Tenanted;

public interface RrAsyncLogEntities extends CacheableEntities, Tenanted {
  //@see sktest.roc.rr.cfg.RrCfg.simpleAsyncLogEntity
  <T extends RrAsyncLogEntities> Class<T> entityClass();

  String getStartDatetime();

  <T extends RrAsyncLogEntities> T setStartDatetime(String startDatetime);

  String getReqJsonStr();

  <T extends RrAsyncLogEntities> T setReqJsonStr(String reqJsonStr);

  String getRespJsonStr();

  <T extends RrAsyncLogEntities> T setRespJsonStr(String respJsonStr);

  String getRespJsonStrCtx();

  <T extends RrAsyncLogEntities> T setRespJsonStrCtx(String respJsonStrCtx);

  String getRespMsgBodyJsonStr();

  <T extends RrAsyncLogEntities> T setRespMsgBodyJsonStr(String respJsonStr);

  String getDoneDatetime();

  <T extends RrAsyncLogEntities> T setDoneDatetime(String doneDatetime);
}
