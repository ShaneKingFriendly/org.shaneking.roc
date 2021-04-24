package org.shaneking.roc.persistence.entity;

import org.shaneking.ling.persistence.entity.sql.TenantedChannelized;
import org.shaneking.roc.persistence.CacheableEntities;

import javax.persistence.Transient;

public interface TenantedChannelizedEntities extends CacheableEntities, TenantedChannelized {
  @Transient
  String ERR_CODE__REQUIRED_CHANNEL_ID_AND_TENANT_ID = "TENANTED_CHANNELIZED_ENTITY__REQUIRED_CHANNEL_ID_AND_TENANT_ID";
}
