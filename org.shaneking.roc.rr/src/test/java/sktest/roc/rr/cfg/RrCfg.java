package sktest.roc.rr.cfg;

import org.shaneking.roc.persistence.entity.sql.*;
import org.shaneking.roc.persistence.hello.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

public class RrCfg {

  @Bean
  @ConditionalOnMissingBean(ApiAccessRegexEntities.class)
  public HelloApiAccessRegexEntity helloApiAccessRegexEntity() {
    return new HelloApiAccessRegexEntity();
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
