package org.shaneking.roc.rr.aspectj;

import lombok.NonNull;
import org.aspectj.lang.ProceedingJoinPoint;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.ling.rr.Req;
import org.shaneking.ling.rr.Resp;
import org.shaneking.ling.zero.time.LDT0;
import org.shaneking.roc.persistence.dao.CacheableDao;
import org.shaneking.roc.persistence.entity.sql.RrAsyncLogEntities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

@Component
public class RrAsyncAspectHelper {
  @Autowired
  private CacheableDao cacheableDao;

  @Async
  public Future<Resp<?, ?>> async(ProceedingJoinPoint pjp, Req<?> req, @NonNull RrAsyncLogEntities asyncLogEntity) {
    Resp<?, ?> resp;
    try {
      asyncLogEntity.setStartDatetime(LDT0.on().dts()).setReqJsonStr(OM3.writeValueAsString(req));
      resp = (Resp<?, ?>) pjp.proceed();
      asyncLogEntity.setRespJsonStr(OM3.writeValueAsString(resp)).setRespJsonStrCtx(OM3.writeValueAsString(req.gnnCtx())).setRespMsgBodyJsonStr(OM3.writeValueAsString(resp.getMsg().getBody())).setDoneDatetime(LDT0.on().dts());
      cacheableDao.modByIdVer(asyncLogEntity.entityClass(), asyncLogEntity);
    } catch (Throwable throwable) {
      resp = Resp.failed(req, Resp.CODE_UNKNOWN_EXCEPTION, throwable.getMessage());
    }
    return new AsyncResult<>(resp);
  }
}
