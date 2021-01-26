package sktest.roc.rr.cfg;

import org.shaneking.roc.persistence.entity.ApiAccessEntity;
import org.shaneking.roc.persistence.entity.AuditLogEntity;
import org.shaneking.roc.persistence.entity.UserEntity;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import sktest.roc.rr.entity.Test4ApiAccessEntity;
import sktest.roc.rr.entity.Test4AuditLogEntity;
import sktest.roc.rr.entity.Test4UserEntity;

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
  @ConditionalOnMissingBean(UserEntity.class)
  public Test4UserEntity test4UserEntity() {
    return new Test4UserEntity();
  }
}
