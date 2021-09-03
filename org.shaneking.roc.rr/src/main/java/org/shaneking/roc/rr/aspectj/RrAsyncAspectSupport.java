package org.shaneking.roc.rr.aspectj;

import lombok.NonNull;
import org.aspectj.lang.ProceedingJoinPoint;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.ling.rr.Resp;
import org.shaneking.ling.zero.time.ZDT0;
import org.shaneking.roc.persistence.dao.CacheableDao;
import org.shaneking.roc.persistence.entity.sql.RrAsyncLogEntities;
import org.shaneking.roc.rr.Req;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

@Component
public class RrAsyncAspectSupport {
  @Autowired
  private CacheableDao cacheableDao;

  @Async
  public Future<Resp<?>> async(ProceedingJoinPoint pjp, Req<?, ?> req, @NonNull RrAsyncLogEntities asyncLogEntity) {
    Resp<?> resp;
    try {
      asyncLogEntity.setStartDatetime(ZDT0.on().dTSZ()).setReqJsonStr(OM3.writeValueAsString(req));
      resp = (Resp<?>) pjp.proceed();
      asyncLogEntity.setRtnJsonStr(OM3.writeValueAsString(((Req<?, ?>) resp.getData()).getPri().getRtn())).setRtnCode(resp.getCode()).setRtnMsg(resp.getMsg());
      asyncLogEntity.setDoneDatetime(ZDT0.on().dTSZ());
      cacheableDao.modByIdVer(asyncLogEntity.entityClass(), asyncLogEntity);
    } catch (Throwable throwable) {
      resp = Resp.failed(throwable.getClass().getName(), throwable.getMessage(), req);
    }
    return new AsyncResult<>(resp);
  }
}
