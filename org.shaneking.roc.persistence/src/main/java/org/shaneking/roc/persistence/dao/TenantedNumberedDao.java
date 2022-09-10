package org.shaneking.roc.persistence.dao;

import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.ling.persistence.CacheableEntities;
import org.shaneking.ling.persistence.entity.sql.TenantedNumberedUniIdx;
import org.shaneking.ling.zero.cache.ZeroCache;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.ling.zero.lang.ZeroException;
import org.shaneking.ling.zero.text.MF0;
import org.shaneking.ling.zero.util.List0;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class TenantedNumberedDao {
  @Value("${sk.roc.persistence.dao.cache.seconds:180}")
  private int cacheSeconds;
  @Autowired
  @Getter
  private CacheableDao cacheableDao;
  @Autowired(required = false)
  private ZeroCache cache;

  public <T extends CacheableEntities> T oneByNo(@NonNull Class<T> cacheType, @NonNull String no, @NonNull String tenantId) {
    return oneByNo(cacheType, no, tenantId, true);
  }

  public <T extends CacheableEntities> T oneByNo(@NonNull Class<T> cacheType, @NonNull String no, @NonNull String tenantId, boolean rtnNullIfNotEqualsOne) {
    T rtn = null;
    String key = String.join(String0.MORE, cacheType.getName(), tenantId, no);
    String id = cache == null ? null : cache.get(key);
    if (String0.isNullOrEmpty(id)) {
      log.info(MF0.fmt("{0} : {1}", ZeroCache.ERR_CODE__CACHE_HIT_MISS, key));
      rtn = oneByNo(cacheType, no, tenantId, rtnNullIfNotEqualsOne, key);
    } else {
      log.info(MF0.fmt("{0} - {1} : {2}", ZeroCache.ERR_CODE__CACHE_HIT_ALL, key, id));
      rtn = cacheableDao.oneById(cacheType, id, rtnNullIfNotEqualsOne);
      if (!eq(no, tenantId, rtn)) {
        log.info(MF0.fmt("{0} - {1} : {2}, {3}", ZeroCache.ERR_CODE__CACHE_HIT_ALL, key, id, OM3.writeValueAsString(rtn)));
        if (cache != null) {
          cache.del(key);
        }
        rtn = oneByNo(cacheType, no, tenantId, rtnNullIfNotEqualsOne, key);
      }
    }
    return rtn;
  }

  private <T extends CacheableEntities> boolean eq(@NonNull String no, @NonNull String tenantId, T t) {
    return t != null && no.equals(t.getNo()) && tenantId.equals(((TenantedNumberedUniIdx) t).getTenantId());
  }

  private <T extends CacheableEntities> T oneByNo(@NonNull Class<T> cacheType, @NonNull String no, @NonNull String tenantId, boolean rtnNullIfNotEqualsOne, String key) {
    T rtn = null;
    try {
      T one = cacheType.newInstance();
      assert one instanceof TenantedNumberedUniIdx;
      one.setNo(no);
      rtn = cacheableDao.one(cacheType, CacheableDao.pts(one, List0.newArrayList(tenantId)), rtnNullIfNotEqualsOne);
      if (rtn != null && cache != null) {
        cache.set(key, cacheSeconds, rtn.getId());
      }
    } catch (Exception e) {
      log.error(OM3.lp(rtn, cacheType.getName(), no, tenantId, rtnNullIfNotEqualsOne, key), e);
      if (!rtnNullIfNotEqualsOne) {
        throw new ZeroException(OM3.lp(rtn, cacheType, no, tenantId, rtnNullIfNotEqualsOne, key), e);
      }
    }
    return rtn;
  }
}
