package org.shaneking.roc.zero.net;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.ling.zero.net.InetAddress0;
import org.shaneking.ling.zero.util.Map0;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

@Component
@ConfigurationProperties(prefix = InetAddress0Config.PREFIX, ignoreInvalidFields = true)
@Slf4j
public class InetAddress0Config {
  public static final String PREFIX = "sk.roc.zero.net.address";
  @Getter
  @Setter
  private boolean enabled = true;
  @Getter
  @Setter
  private String propPath;
  @Getter
  @Setter
  private Map<String, String> vhosts = Map0.newHashMap();

  @PostConstruct
  public void postConstruct() {
    if (this.isEnabled()) {
      if (!String0.isNullOrEmpty(this.getPropPath())) {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
          Resource[] resources = resolver.getResources(this.getPropPath());
          for (Resource resource : resources) {
            ResourceBundle resourceBundle = new PropertyResourceBundle(resource.getInputStream());
            resourceBundle.keySet().forEach(k -> InetAddress0.putCustomHost(k, String0.nullToEmpty(resourceBundle.getString(k))));
          }
        } catch (Exception e) {
          log.error(this.getPropPath(), e);
        }
      }
      this.getVhosts().forEach(InetAddress0::putCustomHost);
    }
  }
}
