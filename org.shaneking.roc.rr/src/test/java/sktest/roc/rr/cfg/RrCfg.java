package sktest.roc.rr.cfg;

import org.shaneking.roc.persistence.entity.sql.*;
import org.shaneking.roc.persistence.hello.entity.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

public class RrCfg {

  @Bean
  @ConditionalOnMissingBean(ApiAccessEntities.class)
  public HelloApiAccessEntity helloApiAccessEntity() {
    return new HelloApiAccessEntity();
  }

  @Bean
  @ConditionalOnMissingBean(ApiAccess2Entities.class)
  public HelloApiAccess2Entity helloApiAccess2Entity() {
    return new HelloApiAccess2Entity();
  }

  @Bean
  @ConditionalOnMissingBean(ApiAccess3Entities.class)
  public HelloApiAccess3Entity helloApiAccess3Entity() {
    return new HelloApiAccess3Entity();
  }

  @Bean
  @ConditionalOnMissingBean(AuditLogEntities.class)
  public HelloAuditLogEntity helloAuditLogEntity() {
    return new HelloAuditLogEntity();
  }

  @Bean
  @ConditionalOnMissingBean(ChannelEntities.class)
  public HelloChannelEntity helloChannelEntity() {
    return new HelloChannelEntity();
  }

  @Bean
  @ConditionalOnMissingBean(TenantEntities.class)
  public HelloTenantEntity helloTenantEntity() {
    return new HelloTenantEntity();
  }

  @Bean
  @ConditionalOnMissingBean(UserEntities.class)
  public HelloUserEntity helloUserEntity() {
    return new HelloUserEntity();
  }
}
