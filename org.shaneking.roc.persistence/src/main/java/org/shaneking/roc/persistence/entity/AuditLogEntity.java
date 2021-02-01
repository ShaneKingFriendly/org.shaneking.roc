package org.shaneking.roc.persistence.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Lob;

@Accessors(chain = true)
@ToString(callSuper = true)
public abstract class AuditLogEntity extends ChannelizedTenantedEntity {
  @Column(length = 40, columnDefinition = "default '' COMMENT ''")
  @Getter
  @Setter
  private String tracingId;

  @Column(length = 20, columnDefinition = "default '' COMMENT ''")
  @Getter
  @Setter
  private String reqDatetime;

  @Column(columnDefinition = "default '' COMMENT ''")
  @Getter
  @Setter
  private String reqIps;

  @Column(length = 40, columnDefinition = "default '' COMMENT ''")
  @Getter
  @Setter
  private String reqUserId;

  @Column(columnDefinition = "default '' COMMENT ''")
  @Getter
  @Lob
  @Setter
  private String reqJsonStrRaw;

  @Column(columnDefinition = "default '' COMMENT ''")
  @Getter
  @Lob
  @Setter
  private String reqJsonStr;

  @Column(columnDefinition = "default '' COMMENT ''")
  @Getter
  @Setter
  private String reqUrl;

  @Column(columnDefinition = "default '' COMMENT ''")
  @Getter
  @Setter
  private String reqSignature;

  @Column(length = 1, columnDefinition = "default '' COMMENT ''")
  @Getter
  @Setter
  private String cached;//Y|N

  @Column(columnDefinition = "default '' COMMENT ''")
  @Getter
  @Lob
  @Setter
  private String respJsonStr;

  @Column(columnDefinition = "default '' COMMENT ''")
  @Getter
  @Lob
  @Setter
  private String respJsonStrCtx;

  @Column(columnDefinition = "default '' COMMENT ''")
  @Getter
  @Lob
  @Setter
  private String respJsonStrRaw;

  @Column(columnDefinition = "default '' COMMENT ''")
  @Getter
  @Setter
  private String respIps;

  @Column(length = 20, columnDefinition = "default '' COMMENT ''")
  @Getter
  @Setter
  private String respDatetime;

  //@see sktest.roc.rr.cfg.RrCfg.helloAuditLogEntity
  public abstract <T extends AuditLogEntity> Class<T> entityClass();
}
