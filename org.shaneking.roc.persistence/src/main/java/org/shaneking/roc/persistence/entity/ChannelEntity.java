package org.shaneking.roc.persistence.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Transient;

@Accessors(chain = true)
@ToString(callSuper = true)
public abstract class ChannelEntity extends TenantedEntity {
  @Transient
  public static final String ERR_CODE__NEED_ENCODING = "API_ACCESS__NEED_ENCODING";

  @Transient
  public static final String TOKEN_VALUE_TYPE__SELF = "SELF";
  @Transient
  public static final String TOKEN_VALUE_TYPE__PROP = "PROP";

  @Column(length = 10, unique = true, columnDefinition = "default '' COMMENT ''")
  @Getter
  @Setter
  private String name;

  @Column(columnDefinition = "default '' COMMENT ''")
  @Getter
  @Setter
  private String description;

  @Column(columnDefinition = "default '' COMMENT ''")
  @Getter
  @Setter
  private String tokenValue;

  @Column(columnDefinition = "default '' COMMENT ''")
  @Getter
  @Setter
  private String tokenForce;

  @Column(columnDefinition = "default '' COMMENT ''")
  @Getter
  @Setter
  private String tokenAlgorithmType;

  @Column(columnDefinition = "default '' COMMENT ''")
  @Getter
  @Setter
  private String tokenValueType;

  //@see sktest.roc.rr.cfg.RrCfg.helloChannelEntity
  public abstract <T extends ChannelEntity> Class<T> entityClass();
}
