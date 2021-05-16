package org.shaneking.roc.rr.service;

import org.shaneking.roc.persistence.entity.sql.ChannelEntities;
import org.shaneking.roc.persistence.entity.sql.TenantEntities;
import org.shaneking.roc.persistence.entity.sql.UserEntities;

public interface RrAutoCreateUserService {
  UserEntities create(String userNo, TenantEntities tenant, ChannelEntities channel, ChannelEntities proxyChannel);
}
