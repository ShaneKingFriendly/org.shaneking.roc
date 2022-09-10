package sktest.roc.rr.biz;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.ling.rr.Req;
import org.shaneking.ling.rr.ReqMsg;
import org.shaneking.ling.rr.ReqMsgBdy;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.roc.persistence.simple.SimpleUserEntity;
import org.shaneking.roc.test.SKSpringUnit;
import org.springframework.beans.factory.annotation.Autowired;
import sktest.roc.rr.biz.impl.UserBizImpl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class UserBizTest extends SKSpringUnit {

  @Autowired
  private UserBizImpl userBiz;

  @Test
  void curd() {
    assertEquals(String0.ARY_DEC, String0.DIGITAL);

    ExecutorService executorService = Executors.newFixedThreadPool(3);
    executorService.submit(() -> {
      while (true) {
        log.info(OM3.writeValueAsString(userBiz.lst(req().srtObj(new SimpleUserEntity()))));
      }
    });
    executorService.submit(() -> {
      while (true) {
        log.info(OM3.writeValueAsString(userBiz.lst(req().srtObj(new SimpleUserEntity()))));
      }
    });
    executorService.submit(() -> {
      while (true) {
        log.info(OM3.writeValueAsString(userBiz.lst(req().srtObj(new SimpleUserEntity()))));
      }
    });

    String id = "1612353237501_DcNd45KtJXPmSpz2xRB";
    for (int i = 0; i < 3; i++) {
      log.info(OM3.writeValueAsString(userBiz.add(req().srtObj(userEntity(id)))));
      log.info(OM3.writeValueAsString(userBiz.modByIdVer(req().srtObj(userEntity(id)))));
//      log.info(OM3.writeValueAsString(cryptoHelper.decrypt(userBiz.rmvById(cryptoHelper.encrypt(Req.build(pub().setEncoded(String0.Y), Pri.<String, Integer>build().setExt(ext()).setObj(id))
//          , "494c6f7665596f75", String0.N, SKC1.ALGORITHM_NAME, ChannelEntities.TOKEN_VALUE_TYPE__SELF))
//        , "494c6f7665596f75", SKC1.ALGORITHM_NAME, ChannelEntities.TOKEN_VALUE_TYPE__SELF, new TypeReference<Pri<String, Integer>>() {
//        })));
    }
  }

  ReqMsgBdy<SimpleUserEntity> reqMsgBdy() {
    return ReqMsgBdy.<SimpleUserEntity>build().setTno("tstTenantNo");
  }

  ReqMsg<SimpleUserEntity> reqMsg() {
    return ReqMsg.<SimpleUserEntity>build().setUno("tstUserNo").setBdy(reqMsgBdy());
  }

  Req<SimpleUserEntity> req() {
    return Req.<SimpleUserEntity>build().setCno("tstChannelNo").setMsg(reqMsg());
  }

  SimpleUserEntity userEntity(String id) {
    SimpleUserEntity userEntity = new SimpleUserEntity();
    userEntity.initWithUidAndId("1612262610216_koFVLCNZrhezbgULWqW", id);
    return userEntity;
  }
}
