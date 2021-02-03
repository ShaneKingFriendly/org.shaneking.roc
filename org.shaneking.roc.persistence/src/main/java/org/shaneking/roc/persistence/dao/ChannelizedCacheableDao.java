package org.shaneking.roc.persistence.dao;

import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.ling.persistence.Channelized;
import org.shaneking.ling.persistence.sql.Condition;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.ling.zero.lang.ZeroException;
import org.shaneking.ling.zero.util.List0;
import org.shaneking.roc.persistence.entity.ChannelizedEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
public class ChannelizedCacheableDao {
  @Autowired
  @Getter
  private TenantedCacheableDao cacheableDao;

  public static <T extends ChannelizedEntity> void protectInsert(@NonNull T t, String channelIds) {
    if (!String0.isNullOrEmpty(channelIds)) {
      t.setChannelId(channelIds);
    }
  }

  public static <T extends ChannelizedEntity> void protectUpdate(@NonNull T t, String channelIds) {
    protectInsert(t, channelIds);
    if (!String0.isNullOrEmpty(channelIds)) {
      t.forceWhereCondition(Channelized.FIELD__CHANNEL_ID).resetVal(channelIds);
    }
  }

  public static <T extends ChannelizedEntity> void protectSelect(@NonNull T t, String channelIds) {
    if (!String0.isNullOrEmpty(channelIds)) {
      List<String> ChannelIdList = List0.newArrayList(channelIds.split(String0.COMMA));
      Condition condition = t.forceWhereCondition(Channelized.FIELD__CHANNEL_ID);
      if (ChannelIdList.size() == 1) {
        condition.resetVal(ChannelIdList.get(0));
      } else {
        condition.retainVal(ChannelIdList);
      }
    }
  }

  //comment for idea compare
  public <T extends ChannelizedEntity> int add(Class<T> cacheType, T t, String channelIds, String tenantIds) {
    protectInsert(t, channelIds);
    return this.getCacheableDao().add(cacheType, t, tenantIds);
  }

  public <T extends ChannelizedEntity> int cnt(Class<T> cacheType, T t, String channelIds, String tenantIds) {
    protectSelect(t, channelIds);
    return this.getCacheableDao().cnt(cacheType, t, tenantIds);
  }

  public <T extends ChannelizedEntity> String ids(Class<T> cacheType, T t, String channelIds, String tenantIds) {
    protectSelect(t, channelIds);
    return this.getCacheableDao().ids(cacheType, t, tenantIds);
  }

  public <T extends ChannelizedEntity> int delById(Class<T> cacheType, String id, String channelIds, String tenantIds) {
    try {
      return delById(cacheType, cacheType.newInstance(), id, channelIds, tenantIds);
    } catch (Exception e) {
      throw new ZeroException(OM3.p(cacheType, id, channelIds, tenantIds), e);
    }
  }

  public <T extends ChannelizedEntity> int delById(Class<T> cacheType, T t, String channelIds, String tenantIds) {
    protectUpdate(t, channelIds);
    return this.getCacheableDao().delById(cacheType, t, tenantIds);
  }

  public <T extends ChannelizedEntity> int delById(Class<T> cacheType, T t, String id, String channelIds, String tenantIds) {
    protectUpdate(t, channelIds);
    return this.getCacheableDao().delById(cacheType, t, id, tenantIds);
  }

  public <T extends ChannelizedEntity> int delByIds(Class<T> cacheType, List<String> ids, String channelIds, String tenantIds) {
    try {
      return delByIds(cacheType, cacheType.newInstance(), ids, channelIds, tenantIds);
    } catch (Exception e) {
      throw new ZeroException(OM3.p(cacheType, ids, channelIds, tenantIds), e);
    }
  }

  public <T extends ChannelizedEntity> int delByIds(Class<T> cacheType, T t, List<String> ids, String channelIds, String tenantIds) {
    protectUpdate(t, channelIds);
    return this.getCacheableDao().delByIds(cacheType, t, ids, tenantIds);
  }

  public <T extends ChannelizedEntity> int modByIdsVer(Class<T> cacheType, T t, List<String> ids, String channelIds, String tenantIds) {
    protectUpdate(t, channelIds);
    return this.getCacheableDao().modByIdsVer(cacheType, t, ids, tenantIds);
  }

  public <T extends ChannelizedEntity> int modByIdVer(Class<T> cacheType, T t, String channelIds, String tenantIds) {
    protectUpdate(t, channelIds);
    return this.getCacheableDao().modByIdVer(cacheType, t, tenantIds);
  }

  public <T extends ChannelizedEntity> List<T> lst(Class<T> cacheType, T t, String channelIds, String tenantIds) {
    protectSelect(t, channelIds);
    return this.getCacheableDao().lst(cacheType, t, tenantIds);
  }

  public <T extends ChannelizedEntity> List<T> lstByIds(Class<T> cacheType, List<String> ids, String channelIds, String tenantIds) {
    try {
      return lstByIds(cacheType, cacheType.newInstance(), ids, channelIds, tenantIds);
    } catch (Exception e) {
      throw new ZeroException(OM3.p(cacheType, ids, channelIds, tenantIds), e);
    }
  }

  public <T extends ChannelizedEntity> List<T> lstByIds(Class<T> cacheType, T t, List<String> ids, String channelIds, String tenantIds) {
    protectSelect(t, channelIds);
    return this.getCacheableDao().lstByIds(cacheType, t, ids, tenantIds);
  }

  public <T extends ChannelizedEntity> T one(Class<T> cacheType, T t, String channelIds, String tenantIds) {
    protectSelect(t, channelIds);
    return this.getCacheableDao().one(cacheType, t, tenantIds);
  }

  public <T extends ChannelizedEntity> T one(Class<T> cacheType, T t, boolean rtnNullIfNotEqualsOne, String channelIds, String tenantIds) {
    protectSelect(t, channelIds);
    return this.getCacheableDao().one(cacheType, t, rtnNullIfNotEqualsOne, tenantIds);
  }

  public <T extends ChannelizedEntity> T oneById(Class<T> cacheType, String id, String channelIds, String tenantIds) {
    try {
      return oneById(cacheType, cacheType.newInstance(), id, channelIds, tenantIds);
    } catch (Exception e) {
      throw new ZeroException(OM3.p(cacheType, id, channelIds, tenantIds), e);
    }
  }

  public <T extends ChannelizedEntity> T oneById(Class<T> cacheType, T t, String id, String channelIds, String tenantIds) {
    protectSelect(t, channelIds);
    return this.getCacheableDao().oneById(cacheType, t, id, tenantIds);
  }

  public <T extends ChannelizedEntity> T oneById(Class<T> cacheType, String id, boolean rtnNullIfNotEqualsOne, String channelIds, String tenantIds) {
    try {
      return oneById(cacheType, cacheType.newInstance(), id, rtnNullIfNotEqualsOne, channelIds, tenantIds);
    } catch (Exception e) {
      throw new ZeroException(OM3.p(cacheType, id, rtnNullIfNotEqualsOne, channelIds, tenantIds), e);
    }
  }

  public <T extends ChannelizedEntity> T oneById(Class<T> cacheType, T t, String id, boolean rtnNullIfNotEqualsOne, String channelIds, String tenantIds) {
    protectSelect(t, channelIds);
    return this.getCacheableDao().oneById(cacheType, t, id, rtnNullIfNotEqualsOne, tenantIds);
  }
}
