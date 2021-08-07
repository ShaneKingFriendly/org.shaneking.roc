package org.shaneking.roc.persistence.entity.sql;

import org.shaneking.ling.persistence.entity.sql.Channelized;
import org.shaneking.ling.persistence.entity.sql.Tenanted;
import org.shaneking.roc.persistence.entity.ApiAccessSignatureOpEntities;

public interface ChannelAccessTenantSignatureApiEntities extends ApiAccessSignatureOpEntities, Channelized, Tenanted {
  //@see sktest.roc.rr.cfg.RrCfg.helloApiAccessSignatureEntity
  <T extends ChannelAccessTenantSignatureApiEntities> Class<T> entityClass();
}
