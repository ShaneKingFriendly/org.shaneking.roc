package org.shaneking.roc.persistence.entity.sql;

import org.shaneking.ling.persistence.entity.sql.Channelized;
import org.shaneking.ling.persistence.entity.sql.Tenanted;
import org.shaneking.roc.persistence.entity.ReadableTenantEntities;

public interface ChannelReadableTenantEntities extends ReadableTenantEntities, Channelized, Tenanted {
  <T extends ChannelReadableTenantEntities> Class<T> entityClass();
}
