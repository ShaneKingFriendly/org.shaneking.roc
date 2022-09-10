package org.shaneking.roc.rr.aspectj;

import org.shaneking.ling.persistence.entity.Numbered;
import org.shaneking.ling.persistence.entity.sql.ChannelEntities;
import org.shaneking.ling.persistence.entity.sql.TenantEntities;
import org.shaneking.ling.persistence.entity.sql.UserEntities;
import org.shaneking.ling.rr.Req;
import org.shaneking.ling.rr.Resp;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.roc.persistence.dao.NumberedDao;
import org.shaneking.roc.persistence.dao.TenantedNumberedDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RrCtxHelper {
  @Autowired
  private NumberedDao numberedDao;
  @Autowired
  private TenantedNumberedDao tenantedNumberedDao;

  public Resp<?, ?> ctxChannel(Req<?> req, ChannelEntities channelEntityClass) {
    Resp<?, ?> rtn = null;
    if (req.gnnCtx().getChannel() == null) {
      if (String0.isNullOrEmpty(req.getCno())) {
        rtn = Resp.failed(req, Req.ERR_CODE__REQUIRED_CHANNEL_NUMBER);
      } else {
        ChannelEntities channelEntity = numberedDao.oneByNo(channelEntityClass.entityClass(), req.getCno(), true);
        if (channelEntity == null) {
          rtn = Resp.failed(req, Numbered.ERR_CODE__NOT_FOUND_BY_NUMBER, req.getCno());
        } else {
          req.gnnCtx().setChannel(channelEntity);
        }
      }
    }
    return rtn;
  }

  public Resp<?, ?> ctxTenant(Req<?> req, TenantEntities tenantEntityClass) {
    Resp<?, ?> rtn = null;
    if (req.gnnCtx().getTenant() == null) {
      TenantEntities tenantEntity = numberedDao.oneByNo(tenantEntityClass.entityClass(), req.gnaMsgBdyTno(), true);
      if (tenantEntity == null) {
        rtn = Resp.failed(req, Numbered.ERR_CODE__NOT_FOUND_BY_NUMBER, req.gnaMsgBdyTno());
      } else {
        req.gnnCtx().setTenant(tenantEntity);
      }
    }
    return rtn;
  }

  public Resp<?, ?> ctxUser(Req<?> req, UserEntities userEntityClass) {
    Resp<?, ?> rtn = null;
    if (req.gnnCtx().getUser() == null) {
      UserEntities userEntity = tenantedNumberedDao.oneByNo(userEntityClass.entityClass(), req.gnaMsgBdyUno(), req.gnnCtx().gnaTenantId());
      if (userEntity == null) {
        rtn = Resp.failed(req, Numbered.ERR_CODE__NOT_FOUND_BY_NUMBER, req.gnaMsgBdyUno());
      } else {
        req.gnnCtx().setUser(userEntity);
      }
    }
    return rtn;
  }
}
