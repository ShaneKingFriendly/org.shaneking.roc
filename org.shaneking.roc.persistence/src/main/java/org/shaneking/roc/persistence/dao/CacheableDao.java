package org.shaneking.roc.persistence.dao;

import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.ling.persistence.CacheableEntities;
import org.shaneking.ling.persistence.Condition;
import org.shaneking.ling.persistence.Keyword;
import org.shaneking.ling.persistence.entity.Identified;
import org.shaneking.ling.persistence.entity.sql.Channelized;
import org.shaneking.ling.persistence.entity.sql.Tenanted;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.ling.zero.lang.ZeroException;
import org.shaneking.ling.zero.persistence.Tuple;
import org.shaneking.ling.zero.text.MF0;
import org.shaneking.ling.zero.util.List0;
import org.shaneking.roc.persistence.annotation.EntityCacheEvict;
import org.shaneking.roc.persistence.annotation.EntityCacheable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
public class CacheableDao {
  public static final String FMT__DUPLICATE_RECORD = "Duplicate {0} records of {1} by `{2}`";
  public static final String FMT__NOT_FOUND = "Not found {0} by `{1}`";

  @Autowired(required = false)
  @Getter
  private JdbcTemplate jdbcTemplate;

  //protectChannelInsert
  public static <T extends CacheableEntities> T pci(@NonNull T t, List<String> channelIds) {
    if (t instanceof Channelized) {
      if (channelIds != null && channelIds.size() == 1) {
        ((Channelized) t).setChannelId(channelIds.get(0));
      }
    }
    return t;
  }

  public static <T extends CacheableEntities> T pci(@NonNull T t, String channelId) {
    return pci(t, List0.newArrayList(channelId));
  }

  //protectChannelSelect
  public static <T extends CacheableEntities> T pcs(@NonNull T t, List<String> channelIds) {
    if (t instanceof Channelized) {
      if (channelIds != null && channelIds.size() > 0) {
        Condition condition = t.forceWhereCondition(Channelized.FIELD__CHANNEL_ID);
        if (channelIds.size() == 1) {
          condition.resetVal(channelIds.get(0));
        } else {
          condition.retainVal(channelIds);
        }
      }
    }
    return t;
  }

  public static <T extends CacheableEntities> T pcs(@NonNull T t, String channelId) {
    return pcs(t, List0.newArrayList(channelId));
  }

  //protectChannelUpdate
  public static <T extends CacheableEntities> T pcu(@NonNull T t, List<String> channelIds) {
    if (t instanceof Channelized) {
      pci(t, channelIds);
      if (channelIds != null && channelIds.size() > 0) {
        t.forceWhereCondition(Channelized.FIELD__CHANNEL_ID).resetVal(channelIds);
      }
    }
    return t;
  }

  public static <T extends CacheableEntities> T pcu(@NonNull T t, String channelId) {
    return pcu(t, List0.newArrayList(channelId));
  }

  //protectTenantInsert
  public static <T extends CacheableEntities> T pti(@NonNull T t, List<String> tenantIds) {
    if (t instanceof Tenanted) {
      if (tenantIds != null && tenantIds.size() == 1) {
        ((Tenanted) t).setTenantId(tenantIds.get(0));
      }
    }
    return t;
  }

  public static <T extends CacheableEntities> T pti(@NonNull T t, String tenantId) {
    return pti(t, List0.newArrayList(tenantId));
  }

  //protectTenantSelect
  public static <T extends CacheableEntities> T pts(@NonNull T t, List<String> tenantIds) {
    if (t instanceof Tenanted) {
      if (tenantIds != null && tenantIds.size() > 0) {
        Condition condition = t.forceWhereCondition(Tenanted.FIELD__TENANT_ID);
        if (tenantIds.size() == 1) {
          condition.resetVal(tenantIds.get(0));
        } else {
          condition.retainVal(tenantIds);
        }
      }
    }
    return t;
  }

  public static <T extends CacheableEntities> T pts(@NonNull T t, String tenantId) {
    return pts(t, List0.newArrayList(tenantId));
  }

  //protectTenantUpdate
  public static <T extends CacheableEntities> T ptu(@NonNull T t, List<String> tenantIds) {
    if (t instanceof Tenanted) {
      pti(t, tenantIds);
      if (tenantIds != null && tenantIds.size() > 0) {
        t.forceWhereCondition(Tenanted.FIELD__TENANT_ID).resetVal(tenantIds);
      }
    }
    return t;
  }

  public static <T extends CacheableEntities> T ptu(@NonNull T t, String tenantId) {
    return ptu(t, List0.newArrayList(tenantId));
  }

  //comment for idea compare
  public <T extends CacheableEntities> int add(@NonNull Class<T> cacheType, @NonNull T t) {
    Tuple.Pair<String, List<Object>> pair = t.insertSql();
    log.info(OM3.writeValueAsString(pair));
    return this.getJdbcTemplate().update(Tuple.getFirst(pair), Tuple.getSecond(pair).toArray());
  }

  public <T extends CacheableEntities> long cnt(@NonNull Class<T> cacheType, @NonNull T t) {
    Tuple.Pair<String, List<Object>> pair = t.selectCountSql();
    log.info(OM3.writeValueAsString(pair));
    return Long.parseLong(this.getJdbcTemplate().queryForMap(Tuple.getFirst(pair), Tuple.getSecond(pair).toArray()).get(Keyword.parenthesis(Keyword.FN__COUNT, 1)).toString());
  }

  ///show variables like 'group_concat_max_len'; -- default 1024 chars
  ///SET GLOBAL group_concat_max_len = 41861160; -- need set by root, 1023*1023*40
  @Deprecated
  public <T extends CacheableEntities> String ids(@NonNull Class<T> cacheType, @NonNull T t) {
    Tuple.Pair<String, List<Object>> pair = t.selectIdsSql();
    log.info(OM3.writeValueAsString(pair));
    return String.valueOf(this.getJdbcTemplate().queryForMap(Tuple.getFirst(pair), Tuple.getSecond(pair).toArray()).get(Keyword.parenthesis(Keyword.FN__GROUP_CONCAT, Identified.FIELD__ID)));
  }

  @EntityCacheable(rKeyPath = Identified.FIELD__ID)
  public <T extends CacheableEntities> List<T> lst(@NonNull Class<T> cacheType, @NonNull T t) {
    Tuple.Pair<String, List<Object>> pair = t.selectSql();
    log.info(OM3.writeValueAsString(pair));
    return this.getJdbcTemplate().query(Tuple.getFirst(pair), (resultSet, i) -> {
      try {
        T rst = cacheType.newInstance();
        rst.srvSelectList(t.getSelectList());
        rst.mapRow(resultSet);
        return rst;
      } catch (Exception e) {
        throw new ZeroException(OM3.p(cacheType, t), e);
      }
    }, Tuple.getSecond(pair).toArray());
  }

  @EntityCacheable(pKeyIdx = 1, rKeyPath = Identified.FIELD__ID)
  public <T extends CacheableEntities> List<T> lstByIds(@NonNull Class<T> cacheType, @NonNull List<String> ids) {
    try {
      T t = cacheType.newInstance();
      t.forceWhereCondition(Identified.FIELD__ID).resetVal(ids);
      return lst(cacheType, t);///https://shaneking.org/2019/11/16/aop-invalid-for-inner-calling-in-class/
    } catch (Exception e) {
      throw new ZeroException(OM3.p(cacheType, ids), e);
    }
  }

  ///if add `t` parameter, can't set `pKeyIdx = 2`. because cache will over, for example: (UserExample.class, {name:ShaneKing}, [1,2,3])
  @EntityCacheable(rKeyPath = Identified.FIELD__ID)
  public <T extends CacheableEntities> List<T> lstByIds(@NonNull Class<T> cacheType, @NonNull T t, @NonNull List<String> ids) {
    try {
      t.forceWhereCondition(Identified.FIELD__ID).resetVal(ids);
      return lst(cacheType, t);///https://shaneking.org/2019/11/16/aop-invalid-for-inner-calling-in-class/
    } catch (Exception e) {
      throw new ZeroException(OM3.p(cacheType, t, ids), e);
    }
  }

  public <T extends CacheableEntities> List<String> lstIds(@NonNull Class<T> cacheType, @NonNull T t) {
    Tuple.Pair<List<String>, List<Object>> pair = t.selectSql(List0.newArrayList(Identified.COLUMN__ID), List0.newArrayList());
    log.info(OM3.writeValueAsString(pair));
    return this.getJdbcTemplate().query(String.join(String0.BLANK, Tuple.getFirst(pair)), (resultSet, i) -> resultSet.getString(Identified.COLUMN__ID), Tuple.getSecond(pair).toArray());
  }

  ///!BeCareful: if no whereConditions, will update all
  @EntityCacheEvict(empty = true)
  public <T extends CacheableEntities> int mod(@NonNull Class<T> cacheType, @NonNull T t) {
    try {
      Tuple.Pair<String, List<Object>> pair = t.updateSql();
      log.info(OM3.writeValueAsString(pair));
      return this.getJdbcTemplate().update(Tuple.getFirst(pair), Tuple.getSecond(pair).toArray());
    } catch (Exception e) {
      throw new ZeroException(OM3.p(cacheType, t), e);
    }
  }

  @EntityCacheEvict(pKeyIdx = 2)
  public <T extends CacheableEntities> int modByIdsVer(@NonNull Class<T> cacheType, @NonNull T t, @NonNull List<String> ids) {
    if (ids.size() == 0) {
      throw new ZeroException(OM3.p(cacheType, t, ids));
    } else {
      t.forceWhereCondition(Identified.FIELD__ID).resetVal(ids);
      return mod(cacheType, t);///https://shaneking.org/2019/11/16/aop-invalid-for-inner-calling-in-class/
    }
  }

  @EntityCacheEvict(pKeyIdx = 1, pKeyPath = Identified.FIELD__ID)
  public <T extends CacheableEntities> int modByIdVer(@NonNull Class<T> cacheType, @NonNull T t) {
    if (String0.isNullOrEmpty(t.getId())) {
      throw new ZeroException(OM3.p(cacheType, t));
    } else {
      return mod(cacheType, t);///https://shaneking.org/2019/11/16/aop-invalid-for-inner-calling-in-class/
    }
  }

  @EntityCacheable(rKeyPath = Identified.FIELD__ID)
  public <T extends CacheableEntities> T one(@NonNull Class<T> cacheType, @NonNull T t) {
    return one(cacheType, t, false);///https://shaneking.org/2019/11/16/aop-invalid-for-inner-calling-in-class/
  }

  @EntityCacheable(rKeyPath = Identified.FIELD__ID)
  public <T extends CacheableEntities> T one(@NonNull Class<T> cacheType, @NonNull T t, boolean rtnNullIfNotEqualsOne) {
    List<T> lst = lst(cacheType, t);///https://shaneking.org/2019/11/16/aop-invalid-for-inner-calling-in-class/
    if (lst.size() == 1) {
      return lst.get(0);
    } else {
      if (rtnNullIfNotEqualsOne) {
        log.warn(OM3.lp(lst, cacheType.getName(), t, rtnNullIfNotEqualsOne));
        return null;
      } else {
        if (lst.size() == 0) {
          throw new ZeroException(MF0.fmt(FMT__NOT_FOUND, cacheType.getName(), OM3.writeValueAsString(t)));
        } else {
          throw new ZeroException(MF0.fmt(FMT__DUPLICATE_RECORD, lst.size(), cacheType.getName(), OM3.writeValueAsString(t)));
        }
      }
    }
  }

  @EntityCacheable(pKeyIdx = 1, rKeyPath = Identified.FIELD__ID)
  public <T extends CacheableEntities> T oneById(@NonNull Class<T> cacheType, @NonNull String id) {
    try {
      return oneById(cacheType, cacheType.newInstance(), id, false);///https://shaneking.org/2019/11/16/aop-invalid-for-inner-calling-in-class/
    } catch (Exception e) {
      throw new ZeroException(OM3.p(cacheType, id), e);
    }
  }

  @EntityCacheable(pKeyIdx = 1, rKeyPath = Identified.FIELD__ID)
  public <T extends CacheableEntities> T oneById(@NonNull Class<T> cacheType, @NonNull String id, boolean rtnNullIfNotEqualsOne) {
    try {
      return oneById(cacheType, cacheType.newInstance(), id, rtnNullIfNotEqualsOne);///https://shaneking.org/2019/11/16/aop-invalid-for-inner-calling-in-class/
    } catch (Exception e) {
      throw new ZeroException(OM3.p(cacheType, id, rtnNullIfNotEqualsOne), e);
    }
  }

  @EntityCacheable(rKeyPath = Identified.FIELD__ID)
  public <T extends CacheableEntities> T oneById(@NonNull Class<T> cacheType, @NonNull T t, @NonNull String id) {
    return oneById(cacheType, t, id, false);///https://shaneking.org/2019/11/16/aop-invalid-for-inner-calling-in-class/
  }

  @EntityCacheable(rKeyPath = Identified.FIELD__ID)
  public <T extends CacheableEntities> T oneById(@NonNull Class<T> cacheType, @NonNull T t, @NonNull String id, boolean rtnNullIfNotEqualsOne) {
    try {
      t.setId(id);
      return one(cacheType, t, rtnNullIfNotEqualsOne);///https://shaneking.org/2019/11/16/aop-invalid-for-inner-calling-in-class/
    } catch (Exception e) {
      throw new ZeroException(OM3.p(cacheType, t, id, rtnNullIfNotEqualsOne), e);
    }
  }

  @EntityCacheEvict(empty = true)
  public <T extends CacheableEntities> int rmv(@NonNull Class<T> cacheType, @NonNull T t) {
    try {
      Tuple.Pair<String, List<Object>> pair = t.deleteSql();
      log.info(OM3.writeValueAsString(pair));
      return this.getJdbcTemplate().update(Tuple.getFirst(pair), Tuple.getSecond(pair).toArray());
    } catch (Exception e) {
      throw new ZeroException(OM3.p(cacheType, t), e);
    }
  }

  @EntityCacheEvict(pKeyIdx = 1)
  public <T extends CacheableEntities> int rmvById(@NonNull Class<T> cacheType, @NonNull String id) {
    try {
      return rmvById(cacheType, cacheType.newInstance(), id);///https://shaneking.org/2019/11/16/aop-invalid-for-inner-calling-in-class/
    } catch (Exception e) {
      throw new ZeroException(OM3.p(cacheType, id), e);
    }
  }

  @EntityCacheEvict(pKeyIdx = 1, pKeyPath = Identified.FIELD__ID)
  public <T extends CacheableEntities> int rmvById(@NonNull Class<T> cacheType, @NonNull T t) {
    try {
      if (String0.isNullOrEmpty(t.getId())) {
        throw new ZeroException(OM3.p(cacheType, t));
      } else {
        Tuple.Pair<String, List<Object>> pair = t.deleteSql();
        log.info(OM3.writeValueAsString(pair));
        return this.getJdbcTemplate().update(Tuple.getFirst(pair), Tuple.getSecond(pair).toArray());
      }
    } catch (Exception e) {
      throw new ZeroException(OM3.p(cacheType, t), e);
    }
  }

  @EntityCacheEvict(pKeyIdx = 2)
  public <T extends CacheableEntities> int rmvById(@NonNull Class<T> cacheType, @NonNull T t, @NonNull String id) {
    try {
      if (String0.isNullOrEmpty(id)) {
        throw new ZeroException(OM3.p(cacheType, t, id));
      } else {
        return rmvByIds(cacheType, t, List0.newArrayList(id));///https://shaneking.org/2019/11/16/aop-invalid-for-inner-calling-in-class/
      }
    } catch (Exception e) {
      throw new ZeroException(OM3.p(cacheType, t, id), e);
    }
  }

  @EntityCacheEvict(pKeyIdx = 1)
  public <T extends CacheableEntities> int rmvByIds(@NonNull Class<T> cacheType, @NonNull List<String> ids) {
    try {
      return rmvByIds(cacheType, cacheType.newInstance(), ids);///https://shaneking.org/2019/11/16/aop-invalid-for-inner-calling-in-class/
    } catch (Exception e) {
      throw new ZeroException(OM3.p(cacheType, ids), e);
    }
  }

  @EntityCacheEvict(pKeyIdx = 2)
  public <T extends CacheableEntities> int rmvByIds(@NonNull Class<T> cacheType, @NonNull T t, @NonNull List<String> ids) {
    try {
      if (ids.size() == 0) {
        return 0;
      } else {
        t.forceWhereCondition(Identified.FIELD__ID).resetVal(ids);
        Tuple.Pair<String, List<Object>> pair = t.deleteSql();
        log.info(OM3.writeValueAsString(pair));
        return this.getJdbcTemplate().update(Tuple.getFirst(pair), Tuple.getSecond(pair).toArray());
      }
    } catch (Exception e) {
      throw new ZeroException(OM3.p(cacheType, t, ids), e);
    }
  }
}
