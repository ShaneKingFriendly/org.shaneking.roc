package org.shaneking.roc.persistence.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.shaneking.ling.persistence.sql.Condition;
import org.shaneking.ling.persistence.sql.entity.IdAdtVerEntity;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.ling.zero.util.Map0;

import javax.persistence.Transient;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Accessors(chain = true)
@ToString(callSuper = true)
public abstract class CacheableEntity extends IdAdtVerEntity<Map<String, Condition>> {
  @Getter
  @Setter
  @Transient
  private UserEntity lastModifyUser;

  @Override
  public @NonNull List<Condition> findHavingConditions(@NonNull String fieldName) {
    Map<String, Condition> conditionMap = this.getHavingConditions();
    if (conditionMap == null) {
      conditionMap = Map0.newHashMap();
      this.setHavingConditions(conditionMap);
    }
    return conditionMap.keySet().parallelStream().filter(Objects::nonNull).filter(s -> s.equals(fieldName) || s.startsWith(fieldName + String0.UNDERLINE + String0.UNDERLINE)).map(s -> this.getHavingConditions().get(s)).collect(Collectors.toList());
  }

  @Override
  public @NonNull List<Condition> findWhereConditions(@NonNull String fieldName) {
    Map<String, Condition> conditionMap = this.getWhereConditions();
    if (conditionMap == null) {
      conditionMap = Map0.newHashMap();
      this.setWhereConditions(conditionMap);
    }
    return conditionMap.keySet().parallelStream().filter(Objects::nonNull).filter(s -> s.equals(fieldName) || s.startsWith(fieldName + String0.UNDERLINE + String0.UNDERLINE)).map(s -> this.getWhereConditions().get(s)).collect(Collectors.toList());
  }

  public Condition forceCondition(@NonNull Map<String, Condition> conditionMap, @NonNull String field) {
    Condition condition = conditionMap.get(field);
    if (condition == null) {
      condition = new Condition();
      conditionMap.put(field, condition);
    }
    return condition;
  }

  public Condition forceHavingCondition(@NonNull String field) {
    Map<String, Condition> conditionMap = this.getHavingConditions();
    if (conditionMap == null) {
      conditionMap = Map0.newHashMap();
      this.setHavingConditions(conditionMap);
    }
    return forceCondition(conditionMap, field);
  }

  public Condition forceWhereCondition(@NonNull String field) {
    Map<String, Condition> conditionMap = this.getWhereConditions();
    if (conditionMap == null) {
      conditionMap = Map0.newHashMap();
      this.setWhereConditions(conditionMap);
    }
    return forceCondition(conditionMap, field);
  }
}
