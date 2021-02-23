package org.shaneking.roc.persistence;

import lombok.NonNull;
import org.shaneking.ling.persistence.Condition;
import org.shaneking.ling.persistence.entity.sql.IdAdtVerSqlEntities;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.ling.zero.util.Map0;
import org.shaneking.roc.persistence.entity.sql.UserEntities;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public interface CacheableEntities extends IdAdtVerSqlEntities {

  Map<String, Condition> getHavingConditions();

  void srvHavingConditions(Map<String, Condition> conditionMap);

  Map<String, Condition> getWhereConditions();

  void srvWhereConditions(Map<String, Condition> conditionMap);

  ///
  @Override
  default @NonNull List<Condition> findHavingConditions(@NonNull String fieldName) {
    Map<String, Condition> conditionMap = this.getHavingConditions();
    if (conditionMap == null) {
      conditionMap = Map0.newHashMap();
      this.srvHavingConditions(conditionMap);
    }
    return conditionMap.keySet().parallelStream().filter(Objects::nonNull).filter(s -> s.equals(fieldName) || s.startsWith(fieldName + String0.UNDERLINE + String0.UNDERLINE)).map(s -> this.getHavingConditions().get(s)).collect(Collectors.toList());
  }

  @Override
  default @NonNull List<Condition> findWhereConditions(@NonNull String fieldName) {
    Map<String, Condition> conditionMap = this.getWhereConditions();
    if (conditionMap == null) {
      conditionMap = Map0.newHashMap();
      this.srvWhereConditions(conditionMap);
    }
    return conditionMap.keySet().parallelStream().filter(Objects::nonNull).filter(s -> s.equals(fieldName) || s.startsWith(fieldName + String0.UNDERLINE + String0.UNDERLINE)).map(s -> this.getWhereConditions().get(s)).collect(Collectors.toList());
  }

  default Condition forceCondition(@NonNull Map<String, Condition> conditionMap, @NonNull String field) {
    Condition condition = conditionMap.get(field);
    if (condition == null) {
      condition = new Condition();
      conditionMap.put(field, condition);
    }
    return condition;
  }

  default Condition forceHavingCondition(@NonNull String field) {
    Map<String, Condition> conditionMap = this.getHavingConditions();
    if (conditionMap == null) {
      conditionMap = Map0.newHashMap();
      this.srvHavingConditions(conditionMap);
    }
    return forceCondition(conditionMap, field);
  }

  default Condition forceWhereCondition(@NonNull String field) {
    Map<String, Condition> conditionMap = this.getWhereConditions();
    if (conditionMap == null) {
      conditionMap = Map0.newHashMap();
      this.srvWhereConditions(conditionMap);
    }
    return forceCondition(conditionMap, field);
  }

  ///
  UserEntities getLastModifyUser();

  <T extends CacheableEntities> T setLastModifyUser(UserEntities userEntity);
}
