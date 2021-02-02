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
    boolean urlAllow = false;
    boolean urlDeny = false;
    boolean signatureAllow = false;
    boolean signatureDeny = false;
    if (!String0.isNullOrEmpty(url)) {
      if (String0.isNullOrEmpty(getAllowUrlRegex())) {
        urlAllow = urlAllow || true;
      } else {
        urlAllow = urlAllow || Pattern.matches(getAllowUrlRegex(), url);
      }
      if (String0.isNullOrEmpty(getDenyUrlRegex())) {
        urlDeny = urlDeny || false;
      } else {
        urlDeny = urlDeny || Pattern.matches(getDenyUrlRegex(), url);
      }
    }
    if (!String0.isNullOrEmpty(signature)) {
      if (String0.isNullOrEmpty(getAllowSignatureRegex())) {
        signatureAllow = signatureAllow || true;
      } else {
        signatureAllow = signatureAllow || Pattern.matches(getAllowSignatureRegex(), signature);
      }
      if (String0.isNullOrEmpty(getDenySignatureRegex())) {
        signatureDeny = signatureDeny || false;
      } else {
        signatureDeny = signatureDeny || Pattern.matches(getDenySignatureRegex(), signature);
      }
    }
    return (urlAllow || signatureAllow) && !(urlDeny || signatureDeny);
  }

  //@see sktest.roc.rr.cfg.RrCfg.helloApiAccessEntity
  public abstract <T extends ApiAccessEntity> Class<T> entityClass();
}
