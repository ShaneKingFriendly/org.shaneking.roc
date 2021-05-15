package sktest.roc.rr.biz;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.ling.zero.crypto.SKC1;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.roc.persistence.entity.sql.ChannelEntities;
import org.shaneking.roc.persistence.hello.HelloUserEntity;
import org.shaneking.roc.rr.*;
import org.shaneking.roc.test.SKSpringUnit;
import org.springframework.beans.factory.annotation.Autowired;
import sktest.roc.rr.biz.impl.UserBizImpl;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class UserBizTest extends SKSpringUnit {

  @Autowired
  private UserBizImpl userBiz;

  @Autowired
  private RrCryptoHelper cryptoHelper;

  @Test
  void curd() {
    assertEquals(String0.ARY_DEC, String0.DIGITAL);

    ExecutorService executorService = Executors.newFixedThreadPool(3);
    executorService.submit(() -> {
      while (true) {
        log.info(OM3.writeValueAsString(userBiz.lst(Req.build(pub(), Pri.<HelloUserEntity, List<HelloUserEntity>>build().setExt(ext()).setObj(new HelloUserEntity())))));
      }
    });
    executorService.submit(() -> {
      while (true) {
        log.info(OM3.writeValueAsString(userBiz.lst(Req.build(pub(), Pri.<HelloUserEntity, List<HelloUserEntity>>build().setExt(ext()).setObj(new HelloUserEntity())))));
      }
    });
    executorService.submit(() -> {
      while (true) {
        log.info(OM3.writeValueAsString(userBiz.lst(Req.build(pub(), Pri.<HelloUserEntity, List<HelloUserEntity>>build().setExt(ext()).setObj(new HelloUserEntity())))));
      }
    });

    String id = "1612353237501_DcNd45KtJXPmSpz2xRB";
    for (int i = 0; i < 3; i++) {
      log.info(OM3.writeValueAsString(userBiz.add(Req.build(pub(), Pri.<HelloUserEntity, Integer>build().setExt(ext()).setObj(userEntity(id))))));
      log.info(OM3.writeValueAsString(userBiz.modByIdVer(Req.build(pub(), Pri.<HelloUserEntity, Integer>build().setExt(ext()).setObj(userEntity(id))))));
      log.info(OM3.writeValueAsString(cryptoHelper.decrypt(userBiz.rmvById(cryptoHelper.encrypt(Req.build(pub().setEncoded(String0.Y), Pri.<String, Integer>build().setExt(ext()).setObj(id))
        , "494c6f7665596f75", String0.N, SKC1.SK__CRYPTO__ALGORITHM_NAME, ChannelEntities.TOKEN_VALUE_TYPE__SELF))
        , "494c6f7665596f75", SKC1.SK__CRYPTO__ALGORITHM_NAME, ChannelEntities.TOKEN_VALUE_TYPE__SELF, new TypeReference<Pri<String, Integer>>() {
        })));
    }
  }

  Pub pub() {
    return new Pub().setTenantNo("tstTenantNo").setChannelNo("tstChannelNo");
  }

  Ext ext() {
    return new Ext().setUserNo("tstUserNo");
  }

  HelloUserEntity userEntity(String id) {
    HelloUserEntity userEntity = new HelloUserEntity();
    userEntity.initWithUserIdAndId("1612262610216_koFVLCNZrhezbgULWqW", id);
    return userEntity;
  }
}
