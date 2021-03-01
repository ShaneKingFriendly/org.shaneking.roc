package org.shaneking.roc.persistence.entity;

import org.shaneking.ling.persistence.entity.sql.TenantChannelized;
import org.shaneking.roc.persistence.CacheableEntities;

import javax.persistence.Transient;

public interface TenantChannelizedEntities extends CacheableEntities, TenantChannelized {
  @Transient
  String ERR_CODE__REQUIRED_CHANNEL_ID_AND_TENANT_ID = "TENANT_CHANNELIZED_ENTITY__REQUIRED_CHANNEL_ID_AND_TENANT_ID";
}
