package org.shaneking.roc.rr.service;

import org.shaneking.ling.persistence.entity.sql.UserEntities;
import org.shaneking.ling.rr.Req;

public interface RrAutoCreateUserService {
  UserEntities createUser(Req<?> req);

  UserEntities createReqUser(Req<?> req);
}
