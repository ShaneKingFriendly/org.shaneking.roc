package sktest.roc.rr.biz.impl;

import org.shaneking.ling.rr.Resp;
import org.shaneking.roc.persistence.dao.CacheableDao;
import org.shaneking.roc.persistence.simple.SimpleUserEntity;
import org.shaneking.roc.rr.Req;
import org.shaneking.roc.rr.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserBizImpl {

  @Autowired
  private CacheableDao cacheableDao;

  @RrAudit
  @RrAccess
  @RrCrypto
  public Resp<Req<SimpleUserEntity, Integer>> add(Req<SimpleUserEntity, Integer> req) {
    Resp<Req<SimpleUserEntity, Integer>> resp = Resp.success(req);
    req.getPri().setRtn(cacheableDao.add(SimpleUserEntity.class, CacheableDao.pti(req.getPri().getObj(), req.gnnCtx().gnaTenantId())));
    return resp;
  }

  @RrLimiting(prop = "sktest.roc.rr.biz.impl.UserBizImpl.lst", limit = 1)
  @RrAudit
  @RrAccess
  @RrCache
  @RrCrypto
  public Resp<Req<SimpleUserEntity, List<SimpleUserEntity>>> lst(Req<SimpleUserEntity, List<SimpleUserEntity>> req) {
    Resp<Req<SimpleUserEntity, List<SimpleUserEntity>>> resp = Resp.success(req);
    req.getPri().setRtn(cacheableDao.lst(SimpleUserEntity.class, CacheableDao.pts(req.getPri().getObj(), req.gnnCtx().gnaTenantId())));
    return resp;
  }

  @RrAudit
  @RrAccess
  @RrCrypto
  public Resp<Req<SimpleUserEntity, Integer>> modByIdVer(Req<SimpleUserEntity, Integer> req) {
    Resp<Req<SimpleUserEntity, Integer>> resp = Resp.success(req);
    req.getPri().setRtn(cacheableDao.modByIdVer(SimpleUserEntity.class, CacheableDao.ptu(req.getPri().getObj(), req.gnnCtx().gnaTenantId())));
    return resp;
  }

  @RrAudit
  @RrAccess
  @RrCrypto
  public Resp<Req<String, Integer>> rmvById(Req<String, Integer> req) {
    Resp<Req<String, Integer>> resp = Resp.success(req);
    SimpleUserEntity userEntity = new SimpleUserEntity();
    userEntity.setId(req.getPri().getObj());
    req.getPri().setRtn(cacheableDao.rmvById(SimpleUserEntity.class, CacheableDao.ptu(userEntity, req.gnnCtx().gnaTenantId())));
    return resp;
  }
}
