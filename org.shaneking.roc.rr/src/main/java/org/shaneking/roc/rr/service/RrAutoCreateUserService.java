package org.shaneking.roc.rr.service;

import org.shaneking.roc.persistence.entity.sql.UserEntities;
import org.shaneking.roc.rr.Req;

public interface RrAutoCreateUserService {
  UserEntities create(Req<?, ?> req);
}
