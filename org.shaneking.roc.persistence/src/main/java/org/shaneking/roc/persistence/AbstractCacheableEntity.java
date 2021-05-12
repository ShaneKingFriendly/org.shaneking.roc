package org.shaneking.roc.persistence;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.shaneking.ling.persistence.Condition;
import org.shaneking.ling.persistence.entity.sql.AbstractDialectSqlEntity;
import org.shaneking.roc.persistence.entity.sql.UserEntities;

import javax.persistence.Transient;
import java.util.Map;

@Accessors(chain = true)
@ToString(callSuper = true)
public abstract class AbstractCacheableEntity extends AbstractDialectSqlEntity<Map<String, Condition>> implements CacheableEntities {
  @Getter
  @Setter
  @Transient
  private UserEntities lastModifyUser;

  public void srvHavingConditions(Map<String, Condition> conditionMap) {
    setHavingConditions(conditionMap);
  }

  public void srvWhereConditions(Map<String, Condition> conditionMap) {
    setWhereConditions(conditionMap);
  }
}
