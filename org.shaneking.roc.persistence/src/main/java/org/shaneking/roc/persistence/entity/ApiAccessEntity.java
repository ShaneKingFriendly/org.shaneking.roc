package org.shaneking.roc.persistence.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.shaneking.ling.zero.lang.String0;

import javax.persistence.Column;
import javax.persistence.Transient;
import java.util.regex.Pattern;

@Accessors(chain = true)
@ToString(callSuper = true)
public abstract class ApiAccessEntity extends ChannelizedTenantedEntity {
  @Transient
  public static final String ERR_CODE__PERMISSION_DENIED = "API_ACCESS__PERMISSION_DENIED";

  @Column(columnDefinition = "default '' COMMENT ''")
  @Getter
  @Setter
  private String allowUrlRegex;

  @Column(columnDefinition = "default '' COMMENT ''")
  @Getter
  @Setter
  private String allowSignatureRegex;

  @Column(columnDefinition = "default '' COMMENT ''")
  @Getter
  @Setter
  private String denyUrlRegex;

  @Column(columnDefinition = "default '' COMMENT ''")
  @Getter
  @Setter
  private String denySignatureRegex;

  public boolean check(String url, String signature) {
    boolean allow = false;
    boolean deny = true;
    if (!String0.isNullOrEmpty(url)) {
      if (String0.isNullOrEmpty(getAllowUrlRegex())) {
        allow = allow || true;
      } else {
        allow = allow || Pattern.matches(getAllowUrlRegex(), url);
      }

      if (String0.isNullOrEmpty(getDenyUrlRegex())) {
        deny = deny && false;
      } else {
        deny = deny && Pattern.matches(getDenyUrlRegex(), url);
      }
    }
    if (!String0.isNullOrEmpty(signature)) {
      if (String0.isNullOrEmpty(getAllowSignatureRegex())) {
        allow = allow || true;
      } else {
        allow = allow || Pattern.matches(getAllowSignatureRegex(), signature);
      }

      if (String0.isNullOrEmpty(getDenySignatureRegex())) {
        deny = deny && false;
      } else {
        deny = deny && Pattern.matches(getDenySignatureRegex(), signature);
      }
    }
    return deny && allow;
  }

  //@see sktest.roc.rr.cfg.RrCfg.test4ApiAccessEntity
  public abstract <T extends ApiAccessEntity> Class<T> entityClass();
}
