package org.shaneking.roc.persistence.dao;

import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.shaneking.ling.persistence.Channelized;
import org.shaneking.ling.persistence.sql.Condition;
import org.shaneking.ling.zero.lang.String0;
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
  private CacheableDao cacheableDao;

  public static <T extends ChannelizedEntity> void protectInsert(@NonNull T t, String channelIds) {
    if (!String0.isNullOrEmpty(channelIds)) {
      t.setChannelId(channelIds);
    }
  }

  public static <T extends ChannelizedEntity> void protectUpdate(@NonNull T t, String channelIds) {
    protectInsert(t, channelIds);
    if (!String0.isNullOrEmpty(channelIds)) {
      t.forceWhereCondition(Channelized.FIELD__CHANNEL_ID).resetId(channelIds);
    }
  }

  public static <T extends ChannelizedEntity> void protectSelect(@NonNull T t, String channelIds) {
    if (!String0.isNullOrEmpty(channelIds)) {
      List<String> ChannelIdList = List0.newArrayList(channelIds.split(String0.COMMA));
      Condition condition = t.forceWhereCondition(Channelized.FIELD__CHANNEL_ID);
      if (ChannelIdList.size() == 1) {
        condition.resetId(ChannelIdList.get(0));
      } else {
        condition.retainIds(ChannelIdList);
      }
    }
  }

  public <T extends ChannelizedEntity> int add(Class<T> cacheType, T t, String channelIds) {
    protectInsert(t, channelIds);
    return this.getCacheableDao().add(cacheType, t);
  }

  public <T extends ChannelizedEntity> int cnt(Class<T> cacheType, T t, String channelIds) {
    protectSelect(t, channelIds);
    return this.getCacheableDao().cnt(cacheType, t);
  }

  public <T extends ChannelizedEntity> String ids(Class<T> cacheType, T t, String channelIds) {
    protectSelect(t, channelIds);
    return this.getCacheableDao().ids(cacheType, t);
  }

  public <T extends ChannelizedEntity> int delById(Class<T> cacheType, T t, String channelIds) {
    protectUpdate(t, channelIds);
    return this.getCacheableDao().delById(cacheType, t);
  }

  public <T extends ChannelizedEntity> int delById(Class<T> cacheType, T t, String id, String channelIds) {
    protectUpdate(t, channelIds);
    return this.getCacheableDao().delById(cacheType, t, id);
  }

  public <T extends ChannelizedEntity> int delByIds(Class<T> cacheType, T t, List<String> ids, String channelIds) {
    protectUpdate(t, channelIds);
    return this.getCacheableDao().delByIds(cacheType, t, ids);
  }

  public <T extends ChannelizedEntity> int modByIdsVer(Class<T> cacheType, T t, List<String> ids, String channelIds) {
    protectUpdate(t, channelIds);
    return this.getCacheableDao().modByIdsVer(cacheType, t, ids);
  }

  public <T extends ChannelizedEntity> int modByIdVer(Class<T> cacheType, T t, String channelIds) {
    protectUpdate(t, channelIds);
    return this.getCacheableDao().modByIdVer(cacheType, t);
  }

  public <T extends ChannelizedEntity> List<T> lst(Class<T> cacheType, T t, String channelIds) {
    protectSelect(t, channelIds);
    return this.getCacheableDao().lst(cacheType, t);
  }

  public <T extends ChannelizedEntity> List<T> lstByIds(Class<T> cacheType, T t, List<String> ids, String channelIds) {
    protectSelect(t, channelIds);
    return this.getCacheableDao().lstByIds(cacheType, t, ids);
  }

  public <T extends ChannelizedEntity> T one(Class<T> cacheType, T t, String channelIds) {
    protectSelect(t, channelIds);
    return this.getCacheableDao().one(cacheType, t);
  }

  public <T extends ChannelizedEntity> T one(Class<T> cacheType, T t, boolean rtnNullIfNotEqualsOne, String channelIds) {
    protectSelect(t, channelIds);
    return this.getCacheableDao().one(cacheType, t, rtnNullIfNotEqualsOne);
  }

  public <T extends ChannelizedEntity> T oneById(Class<T> cacheType, T t, String id, String channelIds) {
    protectSelect(t, channelIds);
    return this.getCacheableDao().oneById(cacheType, t, id);
  }

  public <T extends ChannelizedEntity> T oneById(Class<T> cacheType, T t, String id, boolean rtnNullIfNotEqualsOne, String channelIds) {
    protectSelect(t, channelIds);
    return this.getCacheableDao().oneById(cacheType, t, id, rtnNullIfNotEqualsOne);
  }
}
