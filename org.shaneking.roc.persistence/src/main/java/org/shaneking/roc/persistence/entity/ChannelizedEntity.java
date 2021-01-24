package org.shaneking.roc.persistence.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.shaneking.ling.persistence.Channelized;

import javax.persistence.Column;

@Accessors(chain = true)
@ToString(callSuper = true)
public abstract class ChannelizedEntity extends CacheableEntity implements Channelized {
  @Column(length = 40, columnDefinition = "default '' COMMENT ''")
  @Getter
  @Setter
  private String channelId;
}
