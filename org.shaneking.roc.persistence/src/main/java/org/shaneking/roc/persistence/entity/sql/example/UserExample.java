package org.shaneking.roc.persistence.entity.sql.example;

import com.github.liaochong.myexcel.core.annotation.ExcelColumn;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.shaneking.ling.zero.crypto.SKC1;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.roc.persistence.entity.TenantedEntity;
import org.shaneking.roc.persistence.entity.sql.UserEntities;

import javax.persistence.Column;

@Accessors(chain = true)
@ToString(callSuper = true)
public abstract class UserExample extends TenantedEntity implements UserEntities {
  @Column(length = 30, columnDefinition = "default '' COMMENT ''")
  @ExcelColumn
  @Getter
  @Setter
  private String name;

  @Column(columnDefinition = "default '' COMMENT ''")
  @ExcelColumn
  @Setter
  private String haha;

  @Column(length = 20, columnDefinition = "default '' COMMENT ''")
  @ExcelColumn
  @Getter
  @Setter
  private String mobile;

  @Column(length = 40, columnDefinition = "default '' COMMENT ''")
  @ExcelColumn
  @Getter
  @Setter
  private String email;

  public String getHaha() {
    if (String0.isNullOrEmpty(haha) || haha.startsWith(SKC1.ENCRYPTED_PREFIX)) {
      return haha;
    } else {
      return SKC1.ENCRYPTED_PREFIX + SKC1.encrypt(haha);
    }
  }
}
