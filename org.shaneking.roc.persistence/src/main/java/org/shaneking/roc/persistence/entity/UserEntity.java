package org.shaneking.roc.persistence.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.shaneking.ling.zero.crypto.Crypto0;
import org.shaneking.ling.zero.lang.String0;

import javax.persistence.Column;

@Accessors(chain = true)
@ToString(callSuper = true)
public abstract class UserEntity extends TenantedEntity {
  @Column(columnDefinition = "default '' COMMENT ''")
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

  public String getHaha() {
    if (String0.isNullOrEmpty(haha) || haha.startsWith(Crypto0.ENCRYPTED_PREFIX)) {
      return haha;
    } else {
      return Crypto0.ENCRYPTED_PREFIX + Crypto0.aesEncrypt(haha);
    }
  }

  //@see sktest.roc.rr.cfg.RrCfg.test4UserEntity
  public abstract <T extends UserEntity> Class<T> entityClass();
}
