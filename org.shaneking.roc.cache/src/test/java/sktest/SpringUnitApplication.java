package sktest;

import org.shaneking.roc.cache.aspectj.CacheTransactionAspect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableConfigurationProperties
//@EnableAspectJAutoProxy
@EnableTransactionManagement(order = CacheTransactionAspect.ORDER - 500)
@SpringBootApplication
public class SpringUnitApplication {
  public static void main(String[] args) {
    SpringApplication.run(SpringUnitApplication.class, args);
  }
}
