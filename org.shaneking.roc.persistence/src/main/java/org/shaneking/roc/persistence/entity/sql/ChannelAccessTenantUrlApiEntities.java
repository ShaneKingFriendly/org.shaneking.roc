package org.shaneking.roc.persistence.entity.sql;

import org.shaneking.ling.persistence.entity.sql.Channelized;
import org.shaneking.ling.persistence.entity.sql.Tenanted;
import org.shaneking.roc.persistence.entity.ApiAccessUrlOpEntities;

public interface ChannelAccessTenantUrlApiEntities extends ApiAccessUrlOpEntities, Channelized, Tenanted {
  //@see sktest.roc.rr.cfg.RrCfg.simpleApiAccessUrlEntity
  <T extends ChannelAccessTenantUrlApiEntities> Class<T> entityClass();
}
