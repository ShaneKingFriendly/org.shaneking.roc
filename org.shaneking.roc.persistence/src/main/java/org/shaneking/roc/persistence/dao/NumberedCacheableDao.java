package org.shaneking.roc.persistence.dao;

import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.shaneking.ling.cache.StringCaches;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.ling.zero.lang.ZeroException;
import org.shaneking.roc.persistence.entity.NumberedEntities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.text.MessageFormat;

@ConditionalOnProperty(prefix = "sk.roc.persistence.dao.cache", value = "enabled")
@Repository
@Slf4j
public class NumberedCacheableDao {
  @Autowired
  @Getter
  private CacheableDao cacheableDao;
  @Autowired
  private StringCaches cache;
  @Value("${sk.roc.persistence.dao.cache.seconds:180}")
  private int cacheSeconds;

  public <T extends NumberedEntities> T oneByNo(@NonNull Class<T> cacheType, @NonNull String no) {
    return oneByNo(cacheType, no, true);
  }

  public <T extends NumberedEntities> T oneByNo(@NonNull Class<T> cacheType, @NonNull String no, boolean rtnNullIfNotEqualsOne) {
    T rtn = null;
    String key = String.join(String0.MORE, cacheType.getName(), no);
    String id = cache.get(key);
    if (String0.isNullOrEmpty(id)) {
      log.info(MessageFormat.format("{0} : {1}", StringCaches.ERR_CODE__CACHE_HIT_MISS, key));
      rtn = oneByNo(cacheType, no, rtnNullIfNotEqualsOne, key);
    } else {
      log.info(MessageFormat.format("{0} - {1} : {2}", StringCaches.ERR_CODE__CACHE_HIT_ALL, key, id));
      rtn = cacheableDao.oneById(cacheType, id, rtnNullIfNotEqualsOne);
      if (!eq(no, rtn)) {
        log.info(MessageFormat.format("{0} - {1} : {2}, {3}", StringCaches.ERR_CODE__CACHE_HIT_ALL, key, id, OM3.writeValueAsString(rtn)));
        cache.del(key);
        rtn = oneByNo(cacheType, no, rtnNullIfNotEqualsOne, key);
      }
    }
    return rtn;
  }

  private <T extends NumberedEntities> boolean eq(@NonNull String no, T t) {
    return t != null && no.equals(t.getNo());
  }

  private <T extends NumberedEntities> T oneByNo(@NonNull Class<T> cacheType, @NonNull String no, boolean rtnNullIfNotEqualsOne, String key) {
    T rtn = null;
    try {
      T one = cacheType.newInstance();
      one.setNo(no);
      rtn = cacheableDao.one(cacheType, one, rtnNullIfNotEqualsOne);
      if (rtn != null) {
        cache.set(key, cacheSeconds, rtn.getId());
      }
    } catch (Exception e) {
      log.error(OM3.lp(rtn, cacheType.getName(), no, rtnNullIfNotEqualsOne, key), e);
      if (!rtnNullIfNotEqualsOne) {
        throw new ZeroException(OM3.lp(rtn, cacheType, no, rtnNullIfNotEqualsOne, key), e);
      }
    }
    return rtn;
  }
}
