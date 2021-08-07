package org.shaneking.roc.persistence.entity.sql;

import org.shaneking.ling.persistence.entity.sql.Channelized;
import org.shaneking.ling.persistence.entity.sql.Tenanted;
import org.shaneking.roc.persistence.entity.ApiAccessRegexEntities;

public interface ChannelAccessTenantRegexApiEntities extends ApiAccessRegexEntities, Channelized, Tenanted {
  //@see sktest.roc.rr.cfg.RrCfg.helloApiAccessRegexEntity
  <T extends ChannelAccessTenantRegexApiEntities> Class<T> entityClass();
}
