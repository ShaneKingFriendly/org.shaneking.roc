package org.shaneking.roc.persistence.entity.sql;

import org.shaneking.ling.persistence.entity.sql.Tenanted;
import org.shaneking.roc.persistence.CacheableEntities;

public interface RrAsyncLogEntities extends CacheableEntities, Tenanted {
  String getReqJsonStrRaw();

  <T extends RrAsyncLogEntities> T setReqJsonStrRaw(String reqJsonStrRaw);

  String getCtxJsonStr();

  <T extends RrAsyncLogEntities> T setCtxJsonStr(String ctxJsonStr);

  String getStartDatetime();

  <T extends RrAsyncLogEntities> T setStartDatetime(String startDatetime);

  String getReqJsonStr();

  <T extends RrAsyncLogEntities> T setReqJsonStr(String reqJsonStr);

  String getRtnJsonStr();

  <T extends RrAsyncLogEntities> T setRtnJsonStr(String rtnJsonStr);

  String getRtnCode();

  <T extends RrAsyncLogEntities> T setRtnCode(String rtnCode);

  String getRtnMsg();

  <T extends RrAsyncLogEntities> T setRtnMsg(String rtnMsg);

  String getDoneDatetime();

  <T extends RrAsyncLogEntities> T setDoneDatetime(String doneDatetime);

  //@see sktest.roc.rr.cfg.RrCfg.simpleAsyncLogEntity
  <T extends RrAsyncLogEntities> Class<T> entityClass();
}
