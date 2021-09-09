package org.shaneking.roc.persistence.entity.sql;

import org.shaneking.ling.persistence.entity.sql.Tenanted;
import org.shaneking.roc.persistence.CacheableEntities;

public interface RrAsyncLogEntities extends CacheableEntities, Tenanted {
  //@see sktest.roc.rr.cfg.RrCfg.simpleAsyncLogEntity
  <T extends RrAsyncLogEntities> Class<T> entityClass();

  String getCtxJsonStr();

  String getDoneDatetime();

  String getReqJsonStr();

  <T extends RrAsyncLogEntities> T setDoneDatetime(String doneDatetime);

  String getRtnCode();

  String getReqJsonStrRaw();

  String getRtnMsg();

  <T extends RrAsyncLogEntities> T setReqJsonStrRaw(String reqJsonStrRaw);

  String getRtnJsonStr();

  <T extends RrAsyncLogEntities> T setRtnJsonStr(String rtnJsonStr);

  String getStartDatetime();

  <T extends RrAsyncLogEntities> T setStartDatetime(String startDatetime);

  <T extends RrAsyncLogEntities> T setCtxJsonStr(String ctxJsonStr);

  <T extends RrAsyncLogEntities> T setReqJsonStr(String reqJsonStr);

  <T extends RrAsyncLogEntities> T setRtnMsg(String rtnMsg);

  <T extends RrAsyncLogEntities> T setRtnCode(String rtnCode);
}
