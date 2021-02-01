package sktest.roc.rr.cfg;

import org.shaneking.roc.persistence.entity.*;
import org.shaneking.roc.persistence.hello.entity.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

public class RrCfg {

  @Bean
  @ConditionalOnMissingBean(ApiAccessEntity.class)
  public HelloApiAccessEntity helloApiAccessEntity() {
    return new HelloApiAccessEntity();
  }

  @Bean
  @ConditionalOnMissingBean(AuditLogEntity.class)
  public HelloAuditLogEntity helloAuditLogEntity() {
    return new HelloAuditLogEntity();
  }

  @Bean
  @ConditionalOnMissingBean(ChannelEntity.class)
  public HelloChannelEntity helloChannelEntity() {
    return new HelloChannelEntity();
  }

  @Bean
  @ConditionalOnMissingBean(TenantEntity.class)
  public HelloTenantEntity helloTenantEntity() {
    return new HelloTenantEntity();
  }

  @Bean
  @ConditionalOnMissingBean(UserEntity.class)
  public HelloUserEntity helloUserEntity() {
    return new HelloUserEntity();
  }
}
