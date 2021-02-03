package sktest.roc.rr.biz;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.ling.zero.crypto.Crypto0;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.ling.zero.util.UUID0;
import org.shaneking.roc.persistence.entity.ChannelEntity;
import org.shaneking.roc.persistence.hello.entity.HelloUserEntity;
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

    String id = UUID0.cUl33();
    for (int i = 0; i < 10; i++) {
      log.info(OM3.writeValueAsString(userBiz.add(Req.build(pub(), Pri.<HelloUserEntity, Integer>build().setExt(ext()).setObj(userEntity(id))))));
      log.info(OM3.writeValueAsString(userBiz.modByIdVer(Req.build(pub(), Pri.<HelloUserEntity, Integer>build().setExt(ext()).setObj(userEntity(id))))));
      log.info(OM3.writeValueAsString(cryptoHelper.decrypt(userBiz.delById(cryptoHelper.encrypt(Req.build(pub().setEncoded(String0.Y), Pri.<String, Integer>build().setExt(ext()).setObj(id))
        , "494c6f7665596f75", String0.N, Crypto0.ALGORITHM_NAME__AES, ChannelEntity.TOKEN_VALUE_TYPE__SELF))
        , "494c6f7665596f75", Crypto0.ALGORITHM_NAME__AES, ChannelEntity.TOKEN_VALUE_TYPE__SELF, new TypeReference<Pri<String, Integer>>() {
        })));
    }
  }

  Pub pub() {
    return new Pub().setTenantName("tsttenant").setChannelName("tstchannel");
  }

  Ext ext() {
    return new Ext().setUserNo("tstuserno");
  }

  HelloUserEntity userEntity(String id) {
    HelloUserEntity userEntity = new HelloUserEntity();
    userEntity.initWithUserIdAndId("1612262610216_koFVLCNZrhezbgULWqW", id);
    return userEntity;
  }
}
