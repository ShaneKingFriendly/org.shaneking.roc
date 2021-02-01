package sktest.roc.persistence.hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableConfigurationProperties
@EnableAspectJAutoProxy
@SpringBootApplication
public class SpringUnitApplication {
  public static void main(String[] args) {
    SpringApplication.run(SpringUnitApplication.class, args);
  }
}
