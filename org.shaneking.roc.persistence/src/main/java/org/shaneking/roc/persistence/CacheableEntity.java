package org.shaneking.roc.persistence;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.shaneking.ling.persistence.Condition;
import org.shaneking.ling.persistence.entity.sql.IdAdtVerSqlEntitiesTemplate;
import org.shaneking.roc.persistence.entity.UserEntity;

import javax.persistence.Transient;
import java.util.Map;

@Accessors(chain = true)
@ToString(callSuper = true)
public abstract class CacheableEntity extends IdAdtVerSqlEntitiesTemplate<Map<String, Condition>> implements CacheableEntities {
  @Getter
  @Setter
  @Transient
  private UserEntity lastModifyUser;

  public void srvHavingConditions(Map<String, Condition> conditionMap) {
    setHavingConditions(conditionMap);
  }

  public void srvWhereConditions(Map<String, Condition> conditionMap) {
    setWhereConditions(conditionMap);
  }
}
