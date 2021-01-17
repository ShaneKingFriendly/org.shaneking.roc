package org.shaneking.roc.persistence;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@ComponentScan
@ConditionalOnBean(JdbcTemplate.class)
@ConditionalOnClass(JdbcTemplate.class)
@Configuration
public class PersistenceAutoConfiguration {
}
