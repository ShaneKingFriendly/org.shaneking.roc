package sktest.roc.rr.biz.impl;

import org.shaneking.ling.rr.Req;
import org.shaneking.ling.rr.Resp;
import org.shaneking.roc.persistence.dao.CacheableDao;
import org.shaneking.roc.persistence.simple.SimpleUserEntity;
import org.shaneking.roc.rr.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserBizImpl {

  @Autowired
  private CacheableDao cacheableDao;

  @RrAudit
  @RrTenant
  @RrCrypto
  public Resp<Integer, Req<SimpleUserEntity>> add(Req<SimpleUserEntity> req) {
    Resp<Integer, Req<SimpleUserEntity>> resp = Resp.success(req, null);
    resp.gnnMsg().gnnBody().setData(cacheableDao.add(SimpleUserEntity.class, CacheableDao.pti(req.gnnMsg().gnnBdy().getObj(), req.gnnCtx().gnaTenantId())));
    return resp;
  }

  @RrLimiting(prop = "sktest.roc.rr.biz.impl.UserBizImpl.lst", limit = 1)
  @RrAudit
  @RrTenant
  @RrCache
  @RrCrypto
  public Resp<List<SimpleUserEntity>, Req<SimpleUserEntity>> lst(Req<SimpleUserEntity> req) {
    Resp<List<SimpleUserEntity>, Req<SimpleUserEntity>> resp = Resp.success(req, null);
    resp.gnnMsg().gnnBody().setData(cacheableDao.lst(SimpleUserEntity.class, CacheableDao.pts(req.gnnMsg().gnnBdy().getObj(), req.gnnCtx().gnaTenantId())));
    return resp;
  }

  @RrAudit
  @RrTenant
  @RrCrypto
  public Resp<Integer, Req<SimpleUserEntity>> modByIdVer(Req<SimpleUserEntity> req) {
    Resp<Integer, Req<SimpleUserEntity>> resp = Resp.success(req, null);
    resp.gnnMsg().gnnBody().setData(cacheableDao.modByIdVer(SimpleUserEntity.class, CacheableDao.ptu(req.gnnMsg().gnnBdy().getObj(), req.gnnCtx().gnaTenantId())));
    return resp;
  }

  @RrAudit
  @RrTenant
  @RrCrypto
  public Resp<Integer, Req<String>> rmvById(Req<String> req) {
    Resp<Integer, Req<String>> resp = Resp.success(req, null);
    SimpleUserEntity userEntity = new SimpleUserEntity();
    userEntity.setId(req.gnnMsg().gnnBdy().getObj());
    resp.gnnMsg().gnnBody().setData(cacheableDao.rmvById(SimpleUserEntity.class, CacheableDao.ptu(userEntity, req.gnnCtx().gnaTenantId())));
    return resp;
  }
}
