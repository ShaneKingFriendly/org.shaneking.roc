package sktest.roc.rr.biz;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.ling.rr.ReqMsg;
import org.shaneking.ling.rr.ReqMsgBdy;
import org.shaneking.ling.rr.RespMsg;
import org.shaneking.ling.zero.crypto.SKC1;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.roc.persistence.entity.sql.ChannelEntities;
import org.shaneking.roc.persistence.simple.SimpleUserEntity;
import org.shaneking.roc.rr.Ext;
import org.shaneking.roc.rr.Pub;
import org.shaneking.roc.rr.Req;
import org.shaneking.roc.rr.RrCryptoHelper;
import org.shaneking.roc.test.SKSpringUnit;
import org.springframework.beans.factory.annotation.Autowired;
import sktest.roc.rr.biz.impl.UserBizImpl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class UserBizTest extends SKSpringUnit {

  @Autowired
  private RrCryptoHelper cryptoHelper;

  @Autowired
  private UserBizImpl userBiz;

  @Test
  void curd() {
    assertEquals(String0.ARY_DEC, String0.DIGITAL);

    ExecutorService executorService = Executors.newFixedThreadPool(3);
    executorService.submit(() -> {
      while (true) {
        log.info(OM3.writeValueAsString(userBiz.lst(Req.<SimpleUserEntity>build().setCno("tstChannelNo").setMsg(ReqMsg.<SimpleUserEntity>build().setUno("tstUserNo").setBdy(ReqMsgBdy.<SimpleUserEntity>build().setTno("tstTenantNo").setObj(new SimpleUserEntity()))))));
      }
    });
    executorService.submit(() -> {
      while (true) {
        log.info(OM3.writeValueAsString(userBiz.lst(Req.<SimpleUserEntity>build().setCno("tstChannelNo").setMsg(ReqMsg.<SimpleUserEntity>build().setUno("tstUserNo").setBdy(ReqMsgBdy.<SimpleUserEntity>build().setTno("tstTenantNo").setObj(new SimpleUserEntity()))))));
      }
    });
    executorService.submit(() -> {
      while (true) {
        log.info(OM3.writeValueAsString(userBiz.lst(Req.<SimpleUserEntity>build().setCno("tstChannelNo").setMsg(ReqMsg.<SimpleUserEntity>build().setUno("tstUserNo").setBdy(ReqMsgBdy.<SimpleUserEntity>build().setTno("tstTenantNo").setObj(new SimpleUserEntity()))))));
      }
    });

    String id = "1612353237501_DcNd45KtJXPmSpz2xRB";
    for (int i = 0; i < 3; i++) {
      log.info(OM3.writeValueAsString(userBiz.add(Req.<SimpleUserEntity>build().setCno("tstChannelNo").setMsg(ReqMsg.<SimpleUserEntity>build().setUno("tstUserNo").setBdy(ReqMsgBdy.<SimpleUserEntity>build().setTno("tstTenantNo").setObj(userEntity(id)))))));
      log.info(OM3.writeValueAsString(userBiz.modByIdVer(Req.<SimpleUserEntity>build().setCno("tstChannelNo").setMsg(ReqMsg.<SimpleUserEntity>build().setUno("tstUserNo").setBdy(ReqMsgBdy.<SimpleUserEntity>build().setTno("tstTenantNo").setObj(userEntity(id)))))));
      log.info(OM3.writeValueAsString(cryptoHelper.decryptResp(userBiz.rmvById(cryptoHelper.encryptReq(Req.<String>build().setCno("tstChannelNo").setMsg(ReqMsg.<String>build().setUno("tstUserNo").setBdy(ReqMsgBdy.<String>build().setTno("tstTenantNo").setObj(id)))
          , "494c6f7665596f75", SKC1.ALGORITHM_NAME, ChannelEntities.TOKEN_VALUE_TYPE__SELF))
        , "494c6f7665596f75", SKC1.ALGORITHM_NAME, ChannelEntities.TOKEN_VALUE_TYPE__SELF, new TypeReference<RespMsg<Integer>>() {
        })));
    }
  }

  Ext ext() {
    return new Ext().setTenantNo("tstTenantNo").setUserNo("tstUserNo");
  }

  Pub pub() {
    return new Pub().setChannelNo("tstChannelNo");
  }

  SimpleUserEntity userEntity(String id) {
    SimpleUserEntity userEntity = new SimpleUserEntity();
    userEntity.initWithUidAndId("1612262610216_koFVLCNZrhezbgULWqW", id);
    return userEntity;
  }
}
