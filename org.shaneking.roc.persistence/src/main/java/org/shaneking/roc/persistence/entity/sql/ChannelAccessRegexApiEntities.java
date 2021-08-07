package org.shaneking.roc.persistence.entity.sql;

import org.shaneking.ling.persistence.entity.sql.Channelized;
import org.shaneking.roc.persistence.entity.ApiAccessRegexEntities;

public interface ChannelAccessRegexApiEntities extends ApiAccessRegexEntities, Channelized {
  //@see sktest.roc.rr.cfg.RrCfg.helloApiAccessRegexEntity
  <T extends ChannelAccessRegexApiEntities> Class<T> entityClass();
}
