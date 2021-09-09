package org.shaneking.roc.persistence.entity;

import org.shaneking.ling.zero.lang.String0;
import org.shaneking.roc.persistence.CacheableEntities;

import java.util.regex.Pattern;

public interface ApiAccessRegexEntities extends CacheableEntities {
  default boolean check(String url, String signature) {
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

  String getAllowSignatureRegex();

  <T extends ApiAccessRegexEntities> T setAllowSignatureRegex(String allowSignatureRegex);

  String getAllowUrlRegex();

  <T extends ApiAccessRegexEntities> T setAllowUrlRegex(String allowUrlRegex);

  String getDenySignatureRegex();

  <T extends ApiAccessRegexEntities> T setDenySignatureRegex(String denySignatureRegex);

  String getDenyUrlRegex();

  <T extends ApiAccessRegexEntities> T setDenyUrlRegex(String denyUrlRegex);
}
