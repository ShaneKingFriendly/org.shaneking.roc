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
  public Resp<Integer, Req<SimpleUserEntity>> add(Req<SimpleUserEntity> req) {
    return Resp.success(req, cacheableDao.add(SimpleUserEntity.class, CacheableDao.pti(req.getMsg().getBdy().getObj(), req.gnnCtx().gnaTenantId())));
  }

  @RrLimiting(prop = "sktest.roc.rr.biz.impl.UserBizImpl.lst", limit = 1)
  @RrAudit
  @RrAccess
  @RrCache
  @RrCrypto
  public Resp<List<SimpleUserEntity>, Req<SimpleUserEntity>> lst(Req<SimpleUserEntity> req) {
    return Resp.success(req, cacheableDao.lst(SimpleUserEntity.class, CacheableDao.pts(req.getMsg().getBdy().getObj(), req.gnnCtx().gnaTenantId())));
  }

  @RrAudit
  @RrAccess
  @RrCrypto
  public Resp<Integer, Req<SimpleUserEntity>> modByIdVer(Req<SimpleUserEntity> req) {
    return Resp.success(req, cacheableDao.modByIdVer(SimpleUserEntity.class, CacheableDao.ptu(req.getMsg().getBdy().getObj(), req.gnnCtx().gnaTenantId())));
  }

  @RrAudit
  @RrAccess
  @RrCrypto
  public Resp<Integer, Req<String>> rmvById(Req<String> req) {
    Resp<Integer, Req<String>> resp = Resp.success(req, null);
    SimpleUserEntity userEntity = new SimpleUserEntity();
    userEntity.setId(req.getMsg().getBdy().getObj());
    return resp.srt(cacheableDao.rmvById(SimpleUserEntity.class, CacheableDao.ptu(userEntity, req.gnnCtx().gnaTenantId())));
  }
}
