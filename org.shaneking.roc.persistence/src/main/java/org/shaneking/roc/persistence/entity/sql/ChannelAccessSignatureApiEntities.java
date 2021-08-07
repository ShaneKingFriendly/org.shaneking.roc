package org.shaneking.roc.persistence.entity.sql;

import org.shaneking.ling.persistence.entity.sql.Channelized;
import org.shaneking.roc.persistence.entity.ApiAccessSignatureOpEntities;

public interface ChannelAccessSignatureApiEntities extends ApiAccessSignatureOpEntities, Channelized {
  //@see sktest.roc.rr.cfg.RrCfg.helloApiAccessSignatureEntity
  <T extends ChannelAccessSignatureApiEntities> Class<T> entityClass();
}
