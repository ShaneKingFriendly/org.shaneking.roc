package org.shaneking.roc.persistence.entity.sql;

import org.shaneking.ling.persistence.entity.sql.Channelized;
import org.shaneking.roc.persistence.entity.ApiAccessUrlOpEntities;

public interface ChannelAccessUrlApiEntities extends ApiAccessUrlOpEntities, Channelized {
  //@see sktest.roc.rr.cfg.RrCfg.helloApiAccessUrlEntity
  <T extends ChannelAccessUrlApiEntities> Class<T> entityClass();
}
