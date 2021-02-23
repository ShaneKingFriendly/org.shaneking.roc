package org.shaneking.roc.persistence.dao;

import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.ling.persistence.Condition;
import org.shaneking.ling.persistence.Keyword;
import org.shaneking.ling.persistence.entity.Identified;
import org.shaneking.ling.persistence.entity.sql.Channelized;
import org.shaneking.ling.persistence.entity.sql.Tenanted;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.ling.zero.lang.ZeroException;
import org.shaneking.ling.zero.persistence.Tuple;
import org.shaneking.ling.zero.util.List0;
import org.shaneking.roc.persistence.CacheableEntities;
import org.shaneking.roc.persistence.annotation.EntityCacheEvict;
import org.shaneking.roc.persistence.annotation.EntityCacheable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.text.MessageFormat;
import java.util.List;

@Repository
@Slf4j
public class CacheableDao {
  public static final String FMT_RESULT_NOT_EQUALS_ONE = "Result not equals one : {0} = {1}.";

  @Autowired
  @Getter
  private JdbcTemplate jdbcTemplate;

  //protectChannelInsert
  public static <T extends CacheableEntities> T pci(@NonNull T t, String channelIds) {
    if (t instanceof Channelized) {
      if (!String0.isNullOrEmpty(channelIds)) {
        ((Channelized) t).setChannelId(channelIds);
      }
    }
    return t;
  }

  //protectChannelUpdate
  public static <T extends CacheableEntities> T pcu(@NonNull T t, String channelIds) {
    if (t instanceof Channelized) {
      pci(t, channelIds);
      if (!String0.isNullOrEmpty(channelIds)) {
        t.forceWhereCondition(Channelized.FIELD__CHANNEL_ID).resetVal(channelIds);
      }
    }
    return t;
  }

  //protectChannelSelect
  public static <T extends CacheableEntities> T pcs(@NonNull T t, String channelIds) {
    if (t instanceof Channelized) {
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
    return t;
  }

  //protectTenantInsert
  public static <T extends CacheableEntities> T pti(@NonNull T t, String tenantIds) {
    if (t instanceof Tenanted) {
      if (!String0.isNullOrEmpty(tenantIds)) {
        ((Tenanted) t).setTenantId(tenantIds);
      }
    }
    return t;
  }

  //protectTenantUpdate
  public static <T extends CacheableEntities> T ptu(@NonNull T t, String tenantIds) {
    if (t instanceof Tenanted) {
      pti(t, tenantIds);
      if (!String0.isNullOrEmpty(tenantIds)) {
        t.forceWhereCondition(Tenanted.FIELD__TENANT_ID).resetVal(tenantIds);
      }
    }
    return t;
  }

  //protectTenantSelect
  public static <T extends CacheableEntities> T pts(@NonNull T t, String tenantIds) {
    if (t instanceof Tenanted) {
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
    return t;
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
    return Long.parseLong(this.getJdbcTemplate().queryForMap(Tuple.getFirst(pair), Tuple.getSecond(pair).toArray()).get(Keyword.COUNT_1_).toString());
  }

  public <T extends CacheableEntities> String ids(@NonNull Class<T> cacheType, @NonNull T t) {
    Tuple.Pair<String, List<Object>> pair = t.selectIdsSql();
    log.info(OM3.writeValueAsString(pair));
    return String.valueOf(this.getJdbcTemplate().queryForMap(Tuple.getFirst(pair), Tuple.getSecond(pair).toArray()).get(Keyword.GROUP__CONCAT_ID_));
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

  @EntityCacheEvict(pKeyIdx = 1, pKeyPath = Identified.FIELD__ID)
  public <T extends CacheableEntities> int delById(@NonNull Class<T> cacheType, @NonNull T t) {
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

  @EntityCacheEvict(pKeyIdx = 1)
  public <T extends CacheableEntities> int delById(@NonNull Class<T> cacheType, @NonNull String id) {
    try {
      return delById(cacheType, cacheType.newInstance(), id);///https://shaneking.org/2019/11/16/aop-invalid-for-inner-calling-in-class/
    } catch (Exception e) {
      throw new ZeroException(OM3.p(cacheType, id), e);
    }
  }

  @EntityCacheEvict(pKeyIdx = 2)
  public <T extends CacheableEntities> int delById(@NonNull Class<T> cacheType, @NonNull T t, @NonNull String id) {
    try {
      if (String0.isNullOrEmpty(id)) {
        throw new ZeroException(OM3.p(cacheType, t, id));
      } else {
        return delByIds(cacheType, t, List0.newArrayList(id));
      }
    } catch (Exception e) {
      throw new ZeroException(OM3.p(cacheType, t, id), e);
    }
  }

  @EntityCacheEvict(pKeyIdx = 1)
  public <T extends CacheableEntities> int delByIds(@NonNull Class<T> cacheType, @NonNull List<String> ids) {
    try {
      return delByIds(cacheType, cacheType.newInstance(), ids);///https://shaneking.org/2019/11/16/aop-invalid-for-inner-calling-in-class/
    } catch (Exception e) {
      throw new ZeroException(OM3.p(cacheType, ids), e);
    }
  }

  @EntityCacheEvict(pKeyIdx = 2)
  public <T extends CacheableEntities> int delByIds(@NonNull Class<T> cacheType, @NonNull T t, @NonNull List<String> ids) {
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

  @EntityCacheEvict(pKeyIdx = 2)
  public <T extends CacheableEntities> int modByIdsVer(@NonNull Class<T> cacheType, @NonNull T t, @NonNull List<String> ids) {
    if (ids.size() == 0) {
      throw new ZeroException(OM3.p(cacheType, t, ids));
    } else {
      t.forceWhereCondition(Identified.FIELD__ID).resetVal(ids);
      Tuple.Pair<String, List<Object>> pair = t.updateSql();
      log.info(OM3.writeValueAsString(pair));
      return this.getJdbcTemplate().update(Tuple.getFirst(pair), Tuple.getSecond(pair).toArray());
    }
  }

  @EntityCacheEvict(pKeyIdx = 1, pKeyPath = Identified.FIELD__ID)
  public <T extends CacheableEntities> int modByIdVer(@NonNull Class<T> cacheType, @NonNull T t) {
    if (String0.isNullOrEmpty(t.getId())) {
      throw new ZeroException(OM3.p(cacheType, t));
    } else {
      Tuple.Pair<String, List<Object>> pair = t.updateSql();
      log.info(OM3.writeValueAsString(pair));
      return this.getJdbcTemplate().update(Tuple.getFirst(pair), Tuple.getSecond(pair).toArray());
    }
  }

  @EntityCacheable(rKeyPath = Identified.FIELD__ID)
  public <T extends CacheableEntities> List<T> lst(@NonNull Class<T> cacheType, @NonNull T t) {
    Tuple.Pair<String, List<Object>> pair = t.selectSql();
    log.info(OM3.writeValueAsString(pair));
    return this.getJdbcTemplate().query(Tuple.getFirst(pair), Tuple.getSecond(pair).toArray(), (resultSet, i) -> {
      try {
        T rst = cacheType.newInstance();
        rst.srvSelectList(t.getSelectList());
        rst.mapRow(resultSet);
        return rst;
      } catch (Exception e) {
        throw new ZeroException(OM3.p(cacheType, t), e);
      }
    });
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

  ///can't with t. if add t parameter, cache will over
  ///for example: (UserEntity.class, {name:ShaneKing}, [1,2,3])
  @EntityCacheable(rKeyPath = Identified.FIELD__ID)
  public <T extends CacheableEntities> List<T> lstByIds(@NonNull Class<T> cacheType, @NonNull T t, @NonNull List<String> ids) {
    try {
      t.forceWhereCondition(Identified.FIELD__ID).resetVal(ids);
      return lst(cacheType, t);///https://shaneking.org/2019/11/16/aop-invalid-for-inner-calling-in-class/
    } catch (Exception e) {
      throw new ZeroException(OM3.p(cacheType, t, ids), e);
    }
  }

  @EntityCacheable(rKeyPath = Identified.FIELD__ID)
  public <T extends CacheableEntities> T one(@NonNull Class<T> cacheType, @NonNull T t) {
    return one(cacheType, t, false);///https://shaneking.org/2019/11/16/aop-invalid-for-inner-calling-in-class/
  }

  @EntityCacheable(rKeyPath = Identified.FIELD__ID)
  public <T extends CacheableEntities> T one(@NonNull Class<T> cacheType, @NonNull T t, boolean rtnNullIfNotEqualsOne) {
    List<T> lst = this.lst(cacheType, t);
    if (lst.size() == 1) {
      return lst.get(0);
    } else {
      if (rtnNullIfNotEqualsOne) {
        log.warn(OM3.lp(lst, cacheType.getName(), t, rtnNullIfNotEqualsOne));
        return null;
      } else {
        throw new ZeroException(MessageFormat.format(FMT_RESULT_NOT_EQUALS_ONE, cacheType.getName(), OM3.writeValueAsString(t)));
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
      return one(cacheType, t, rtnNullIfNotEqualsOne);
    } catch (Exception e) {
      throw new ZeroException(OM3.p(cacheType, t, id, rtnNullIfNotEqualsOne), e);
    }
  }
}
