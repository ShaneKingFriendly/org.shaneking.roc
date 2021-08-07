package sktest.roc.rr.cfg;

import org.shaneking.roc.persistence.entity.sql.*;
import org.shaneking.roc.persistence.hello.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RrCfg {

  @Bean
  @ConditionalOnMissingBean(RrAuditLogEntities.class)
  public HelloRrAuditLogEntity helloAuditLogEntity() {
    return new HelloRrAuditLogEntity();
  }

  @Bean
  @ConditionalOnMissingBean(ChannelAccessTenantRegexApiEntities.class)
  public HelloChannelAccessTenantRegexApiEntity helloChannelAccessTenantRegexApiEntity() {
    return new HelloChannelAccessTenantRegexApiEntity();
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
