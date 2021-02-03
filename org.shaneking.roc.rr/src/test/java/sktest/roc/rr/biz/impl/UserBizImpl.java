package sktest.roc.rr.biz.impl;

import org.shaneking.ling.rr.Resp;
import org.shaneking.roc.persistence.dao.TenantedCacheableDao;
import org.shaneking.roc.persistence.hello.entity.HelloUserEntity;
import org.shaneking.roc.rr.Req;
import org.shaneking.roc.rr.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserBizImpl {

  @Autowired
  private TenantedCacheableDao cacheableDao;

  @RrAudit
  @RrAccess
  @RrCrypto
  public Resp<Req<HelloUserEntity, Integer>> add(Req<HelloUserEntity, Integer> req) {
    Resp<Req<HelloUserEntity, Integer>> resp = Resp.success(req);
    req.getPri().setRtn(cacheableDao.add(HelloUserEntity.class, req.getPri().getObj(), req.gnnCtx().getTenant().getId()));
    return resp;
  }

  @RrAudit
  @RrAccess
  @RrCrypto
  public Resp<Req<String, Integer>> delById(Req<String, Integer> req) {
    Resp<Req<String, Integer>> resp = Resp.success(req);
    req.getPri().setRtn(cacheableDao.delById(HelloUserEntity.class, req.getPri().getObj(), req.gnnCtx().getTenant().getId()));
    return resp;
  }

  @RrAudit
  @RrAccess
  @RrCrypto
  public Resp<Req<HelloUserEntity, Integer>> modByIdVer(Req<HelloUserEntity, Integer> req) {
    Resp<Req<HelloUserEntity, Integer>> resp = Resp.success(req);
    req.getPri().setRtn(cacheableDao.modByIdVer(HelloUserEntity.class, req.getPri().getObj(), req.gnnCtx().getTenant().getId()));
    return resp;
  }

  @RrLimiting(prop = "sktest.roc.rr.biz.impl.UserBizImpl.lst", limit = 1)
  @RrAudit
  @RrAccess
  @RrCache
  @RrCrypto
  public Resp<Req<HelloUserEntity, List<HelloUserEntity>>> lst(Req<HelloUserEntity, List<HelloUserEntity>> req) {
    Resp<Req<HelloUserEntity, List<HelloUserEntity>>> resp = Resp.success(req);
    req.getPri().setRtn(cacheableDao.lst(HelloUserEntity.class, req.getPri().getObj(), req.gnnCtx().getTenant().getId()));
    return resp;
  }
}
