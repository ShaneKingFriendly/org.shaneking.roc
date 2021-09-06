package sktest.roc.rr.cfg;

import org.shaneking.roc.persistence.entity.sql.*;
import org.shaneking.roc.persistence.simple.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RrCfg {

  @Bean
  @ConditionalOnMissingBean(RrAuditLogEntities.class)
  public SimpleRrAuditLogEntity simpleAuditLogEntity() {
    return new SimpleRrAuditLogEntity();
  }

  @Bean
  @ConditionalOnMissingBean(ChannelAccessTenantRegexApiEntities.class)
  public SimpleChannelAccessTenantRegexApiEntity simpleChannelAccessTenantRegexApiEntity() {
    return new SimpleChannelAccessTenantRegexApiEntity();
  }

  @Bean
  @ConditionalOnMissingBean(ChannelEntities.class)
  public SimpleChannelEntity simpleChannelEntity() {
    return new SimpleChannelEntity();
  }

  @Bean
  @ConditionalOnMissingBean(TenantEntities.class)
  public SimpleTenantEntity simpleTenantEntity() {
    return new SimpleTenantEntity();
  }

  @Bean
  @ConditionalOnMissingBean(UserEntities.class)
  public SimpleUserEntity simpleUserEntity() {
    return new SimpleUserEntity();
  }
}
