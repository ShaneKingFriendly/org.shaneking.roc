package sktest.roc.rr.cfg;

import org.shaneking.roc.persistence.entity.*;
import org.shaneking.roc.persistence.test.entity.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

public class RrCfg {

  @Bean
  @ConditionalOnMissingBean(ApiAccessEntity.class)
  public Test4ApiAccessEntity test4ApiAccessEntity() {
    return new Test4ApiAccessEntity();
  }

  @Bean
  @ConditionalOnMissingBean(AuditLogEntity.class)
  public Test4AuditLogEntity test4AuditLogEntity() {
    return new Test4AuditLogEntity();
  }

  @Bean
  @ConditionalOnMissingBean(ChannelEntity.class)
  public Test4ChannelEntity test4ChannelEntity() {
    return new Test4ChannelEntity();
  }

  @Bean
  @ConditionalOnMissingBean(TenantEntity.class)
  public Test4TenantEntity test4TenantEntity() {
    return new Test4TenantEntity();
  }

  @Bean
  @ConditionalOnMissingBean(UserEntity.class)
  public Test4UserEntity test4UserEntity() {
    return new Test4UserEntity();
  }
}
