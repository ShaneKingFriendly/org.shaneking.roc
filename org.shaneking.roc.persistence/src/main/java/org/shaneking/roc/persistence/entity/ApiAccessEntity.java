package org.shaneking.roc.persistence.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;

@Accessors(chain = true)
@ToString(callSuper = true)
public abstract class ApiAccessEntity extends CacheableEntity {
  @Column(columnDefinition = "default '' COMMENT ''")
  @Getter
  @Setter
  private String allowUrl;

  @Column(columnDefinition = "default '' COMMENT ''")
  @Getter
  @Setter
  private String allowSignature;

  @Column(columnDefinition = "default '' COMMENT ''")
  @Getter
  @Setter
  private String denyUrl;

  @Column(columnDefinition = "default '' COMMENT ''")
  @Getter
  @Setter
  private String denySignature;
}
