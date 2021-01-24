package org.shaneking.roc.persistence.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;

@Accessors(chain = true)
@ToString(callSuper = true)
public abstract class UserEntity extends CacheableEntity {
  @Column(columnDefinition = "default '' COMMENT ''")
  @Getter
  @Setter
  private String haha;

  @Column(length = 20, columnDefinition = "default '' COMMENT ''")
  @Getter
  @Setter
  private String mobile;

  @Column(length = 30, columnDefinition = "default '' COMMENT ''")
  @Getter
  @Setter
  private String name;

  @Column(length = 40, columnDefinition = "default '' COMMENT ''")
  @Getter
  @Setter
  private String email;
}
