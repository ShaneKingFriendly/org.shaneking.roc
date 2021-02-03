package org.shaneking.roc.persistence.dao;

import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.ling.persistence.Tenanted;
import org.shaneking.ling.persistence.sql.Condition;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.ling.zero.lang.ZeroException;
import org.shaneking.ling.zero.util.List0;
import org.shaneking.roc.persistence.entity.TenantedEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
public class TenantedCacheableDao {
  @Autowired
  @Getter
  private CacheableDao cacheableDao;

  public static <T extends TenantedEntity> void protectInsert(@NonNull T t, String tenantIds) {
    if (!String0.isNullOrEmpty(tenantIds)) {
      t.setTenantId(tenantIds);
    }
  }

  public static <T extends TenantedEntity> void protectUpdate(@NonNull T t, String tenantIds) {
    protectInsert(t, tenantIds);
    if (!String0.isNullOrEmpty(tenantIds)) {
      t.forceWhereCondition(Tenanted.FIELD__TENANT_ID).resetVal(tenantIds);
    }
  }

  public static <T extends TenantedEntity> void protectSelect(@NonNull T t, String tenantIds) {
    if (!String0.isNullOrEmpty(tenantIds)) {
      List<String> tenantIdList = List0.newArrayList(tenantIds.split(String0.COMMA));
      Condition condition = t.forceWhereCondition(Tenanted.FIELD__TENANT_ID);
      if (tenantIdList.size() == 1) {
        condition.resetVal(tenantIdList.get(0));
      } else {
        condition.retainVal(tenantIdList);
      }
    }
  }

  //comment for idea compare
  public <T extends TenantedEntity> int add(Class<T> cacheType, T t, String tenantIds) {
    protectInsert(t, tenantIds);
    return this.getCacheableDao().add(cacheType, t);
  }

  public <T extends TenantedEntity> int cnt(Class<T> cacheType, T t, String tenantIds) {
    protectSelect(t, tenantIds);
    return this.getCacheableDao().cnt(cacheType, t);
  }

  public <T extends TenantedEntity> String ids(Class<T> cacheType, T t, String tenantIds) {
    protectSelect(t, tenantIds);
    return this.getCacheableDao().ids(cacheType, t);
  }

  public <T extends TenantedEntity> int delById(Class<T> cacheType, String id, String tenantIds) {
    try {
      return delById(cacheType, cacheType.newInstance(), id, tenantIds);
    } catch (Exception e) {
      throw new ZeroException(OM3.p(cacheType, id, tenantIds), e);
    }
  }

  public <T extends TenantedEntity> int delById(Class<T> cacheType, T t, String tenantIds) {
    protectUpdate(t, tenantIds);
    return this.getCacheableDao().delById(cacheType, t);
  }

  public <T extends TenantedEntity> int delById(Class<T> cacheType, T t, String id, String tenantIds) {
    protectUpdate(t, tenantIds);
    return this.getCacheableDao().delById(cacheType, t, id);
  }

  public <T extends TenantedEntity> int delByIds(Class<T> cacheType, List<String> ids, String tenantIds) {
    try {
      return delByIds(cacheType, cacheType.newInstance(), ids, tenantIds);
    } catch (Exception e) {
      throw new ZeroException(OM3.p(cacheType, ids, tenantIds), e);
    }
  }

  public <T extends TenantedEntity> int delByIds(Class<T> cacheType, T t, List<String> ids, String tenantIds) {
    protectUpdate(t, tenantIds);
    return this.getCacheableDao().delByIds(cacheType, t, ids);
  }

  public <T extends TenantedEntity> int modByIdsVer(Class<T> cacheType, T t, List<String> ids, String tenantIds) {
    protectUpdate(t, tenantIds);
    return this.getCacheableDao().modByIdsVer(cacheType, t, ids);
  }

  public <T extends TenantedEntity> int modByIdVer(Class<T> cacheType, T t, String tenantIds) {
    protectUpdate(t, tenantIds);
    return this.getCacheableDao().modByIdVer(cacheType, t);
  }

  public <T extends TenantedEntity> List<T> lst(Class<T> cacheType, T t, String tenantIds) {
    protectSelect(t, tenantIds);
    return this.getCacheableDao().lst(cacheType, t);
  }

  public <T extends TenantedEntity> List<T> lstByIds(Class<T> cacheType, List<String> ids, String tenantIds) {
    try {
      return lstByIds(cacheType, cacheType.newInstance(), ids, tenantIds);
    } catch (Exception e) {
      throw new ZeroException(OM3.p(cacheType, ids, tenantIds), e);
    }
  }

  public <T extends TenantedEntity> List<T> lstByIds(Class<T> cacheType, T t, List<String> ids, String tenantIds) {
    protectSelect(t, tenantIds);
    return this.getCacheableDao().lstByIds(cacheType, t, ids);
  }

  public <T extends TenantedEntity> T one(Class<T> cacheType, T t, String tenantIds) {
    protectSelect(t, tenantIds);
    return this.getCacheableDao().one(cacheType, t);
  }

  public <T extends TenantedEntity> T one(Class<T> cacheType, T t, boolean rtnNullIfNotEqualsOne, String tenantIds) {
    protectSelect(t, tenantIds);
    return this.getCacheableDao().one(cacheType, t, rtnNullIfNotEqualsOne);
  }

  public <T extends TenantedEntity> T oneById(Class<T> cacheType, String id, String tenantIds) {
    try {
      return oneById(cacheType, cacheType.newInstance(), id, tenantIds);
    } catch (Exception e) {
      throw new ZeroException(OM3.p(cacheType, id, tenantIds), e);
    }
  }

  public <T extends TenantedEntity> T oneById(Class<T> cacheType, T t, String id, String tenantIds) {
    protectSelect(t, tenantIds);
    return this.getCacheableDao().oneById(cacheType, t, id);
  }

  public <T extends TenantedEntity> T oneById(Class<T> cacheType, String id, boolean rtnNullIfNotEqualsOne, String tenantIds) {
    try {
      return oneById(cacheType, cacheType.newInstance(), id, rtnNullIfNotEqualsOne, tenantIds);
    } catch (Exception e) {
      throw new ZeroException(OM3.p(cacheType, id, rtnNullIfNotEqualsOne, tenantIds), e);
    }
  }

  public <T extends TenantedEntity> T oneById(Class<T> cacheType, T t, String id, boolean rtnNullIfNotEqualsOne, String tenantIds) {
    protectSelect(t, tenantIds);
    return this.getCacheableDao().oneById(cacheType, t, id, rtnNullIfNotEqualsOne);
  }
}
