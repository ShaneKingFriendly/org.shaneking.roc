package org.shaneking.roc.persistence.dao;

import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.ling.persistence.sql.Keyword;
import org.shaneking.ling.persistence.sql.entity.IdEntity;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.ling.zero.lang.ZeroException;
import org.shaneking.ling.zero.persistence.Tuple;
import org.shaneking.roc.persistence.annotation.EntityCacheEvict;
import org.shaneking.roc.persistence.annotation.EntityCacheable;
import org.shaneking.roc.persistence.entity.CacheableEntity;
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

  public <T extends CacheableEntity> int add(@NonNull Class<T> cacheType, @NonNull T t) {
    Tuple.Pair<String, List<Object>> pair = t.insertSql();
    log.info(OM3.writeValueAsString(pair));
    return this.getJdbcTemplate().update(Tuple.getFirst(pair), Tuple.getSecond(pair).toArray());
  }

  public <T extends CacheableEntity> int cnt(@NonNull Class<T> cacheType, @NonNull T t) {
    Tuple.Pair<String, List<Object>> pair = t.selectCountSql();
    log.info(OM3.writeValueAsString(pair));
    return (int) this.getJdbcTemplate().queryForMap(Tuple.getFirst(pair), Tuple.getSecond(pair).toArray()).get(Keyword.COUNT_1_);
  }

  public <T extends CacheableEntity> String ids(@NonNull Class<T> cacheType, @NonNull T t) {
    Tuple.Pair<String, List<Object>> pair = t.selectIdsSql();
    log.info(OM3.writeValueAsString(pair));
    return String.valueOf(this.getJdbcTemplate().queryForMap(Tuple.getFirst(pair), Tuple.getSecond(pair).toArray()).get(Keyword.GROUP__CONCAT_ID_));
  }

  @EntityCacheEvict(pKeyIdx = 1, pKeyPath = IdEntity.FIELD__ID)
  public <T extends CacheableEntity> int delById(@NonNull Class<T> cacheType, @NonNull T t) {
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
  public <T extends CacheableEntity> int delById(@NonNull Class<T> cacheType, @NonNull T t, @NonNull String id) {
    try {
      if (String0.isNullOrEmpty(id)) {
        throw new ZeroException(OM3.p(cacheType, id));
      } else {
        t.forceWhereCondition(IdEntity.FIELD__ID).resetId(id);
        Tuple.Pair<String, List<Object>> pair = t.deleteSql();
        log.info(OM3.writeValueAsString(pair));
        return this.getJdbcTemplate().update(Tuple.getFirst(pair), Tuple.getSecond(pair).toArray());
      }
    } catch (Exception e) {
      throw new ZeroException(OM3.p(cacheType, id), e);
    }
  }

  @EntityCacheEvict(pKeyIdx = 2)
  public <T extends CacheableEntity> int delByIds(@NonNull Class<T> cacheType, @NonNull T t, @NonNull List<String> ids) {
    try {
      if (ids.size() == 0) {
        return 0;
      } else {
        t.forceWhereCondition(IdEntity.FIELD__ID).resetIds(ids);
        Tuple.Pair<String, List<Object>> pair = t.deleteSql();
        log.info(OM3.writeValueAsString(pair));
        return this.getJdbcTemplate().update(Tuple.getFirst(pair), Tuple.getSecond(pair).toArray());
      }
    } catch (Exception e) {
      throw new ZeroException(OM3.p(cacheType, t, ids), e);
    }
  }

  @EntityCacheEvict(pKeyIdx = 2)
  public <T extends CacheableEntity> int modByIdsVer(@NonNull Class<T> cacheType, @NonNull T t, @NonNull List<String> ids) {
    if (ids.size() == 0) {
      throw new ZeroException(OM3.p(cacheType, t, ids));
    } else {
      t.forceWhereCondition(IdEntity.FIELD__ID).resetIds(ids);
      Tuple.Pair<String, List<Object>> pair = t.updateSql();
      log.info(OM3.writeValueAsString(pair));
      return this.getJdbcTemplate().update(Tuple.getFirst(pair), Tuple.getSecond(pair).toArray());
    }
  }

  @EntityCacheEvict(pKeyIdx = 1, pKeyPath = IdEntity.FIELD__ID)
  public <T extends CacheableEntity> int modByIdVer(@NonNull Class<T> cacheType, @NonNull T t) {
    if (String0.isNullOrEmpty(t.getId())) {
      throw new ZeroException(OM3.p(cacheType, t));
    } else {
      Tuple.Pair<String, List<Object>> pair = t.updateSql();
      log.info(OM3.writeValueAsString(pair));
      return this.getJdbcTemplate().update(Tuple.getFirst(pair), Tuple.getSecond(pair).toArray());
    }
  }

  @EntityCacheable(rKeyPath = IdEntity.FIELD__ID)
  public <T extends CacheableEntity> List<T> lst(@NonNull Class<T> cacheType, @NonNull T t) {
    return lstWithoutCache(cacheType, t);
  }

  //can't with t. if add t parameter, cache will over
  @EntityCacheable(pKeyIdx = 2, rKeyPath = IdEntity.FIELD__ID)
  public <T extends CacheableEntity> List<T> lstByIds(@NonNull Class<T> cacheType, @NonNull T t, @NonNull List<String> ids) {
    try {
      t.forceWhereCondition(IdEntity.FIELD__ID).resetIds(ids);
      return lstWithoutCache(cacheType, t);
    } catch (Exception e) {
      throw new ZeroException(OM3.p(cacheType, ids), e);
    }
  }

  @EntityCacheable(rKeyPath = IdEntity.FIELD__ID)
  public <T extends CacheableEntity> T one(@NonNull Class<T> cacheType, @NonNull T t) {
    return oneWithoutCache(cacheType, t, false);
  }

  @EntityCacheable(rKeyPath = IdEntity.FIELD__ID)
  public <T extends CacheableEntity> T one(@NonNull Class<T> cacheType, @NonNull T t, boolean rtnNullIfNotEqualsOne) {
    return oneWithoutCache(cacheType, t, rtnNullIfNotEqualsOne);
  }

  @EntityCacheable(pKeyIdx = 2, rKeyPath = IdEntity.FIELD__ID)
  public <T extends CacheableEntity> T oneById(@NonNull Class<T> cacheType, @NonNull T t, @NonNull String id) {
    return oneByIdWithoutCache(cacheType, t, id, false);
  }

  @EntityCacheable(pKeyIdx = 2, rKeyPath = IdEntity.FIELD__ID)
  public <T extends CacheableEntity> T oneById(@NonNull Class<T> cacheType, @NonNull T t, @NonNull String id, boolean rtnNullIfNotEqualsOne) {
    return oneByIdWithoutCache(cacheType, t, id, rtnNullIfNotEqualsOne);
  }

  private <T extends CacheableEntity> List<T> lstWithoutCache(@NonNull Class<T> cacheType, @NonNull T t) {
    Tuple.Pair<String, List<Object>> pair = t.selectSql();
    log.info(OM3.writeValueAsString(pair));
    return this.getJdbcTemplate().query(Tuple.getFirst(pair), Tuple.getSecond(pair).toArray(), (resultSet, i) -> {
      try {
        T rst = cacheType.newInstance();
        rst.setSelectList(t.getSelectList());
        rst.mapRow(resultSet);
        return rst;
      } catch (Exception e) {
        throw new ZeroException(OM3.p(cacheType, t), e);
      }
    });
  }

  private <T extends CacheableEntity> T oneWithoutCache(@NonNull Class<T> cacheType, @NonNull T t, boolean rtnNullIfNotEqualsOne) {
    List<T> lst = this.lst(cacheType, t);
    if (lst.size() == 1) {
      return lst.get(0);
    } else {
      if (rtnNullIfNotEqualsOne) {
        return null;
      } else {
        throw new ZeroException(MessageFormat.format(FMT_RESULT_NOT_EQUALS_ONE, cacheType.getName(), OM3.writeValueAsString(t)));
      }
    }
  }

  private <T extends CacheableEntity> T oneByIdWithoutCache(@NonNull Class<T> cacheType, @NonNull T t, @NonNull String id, boolean rtnNullIfNotEqualsOne) {
    try {
      t.setId(id);
      return oneWithoutCache(cacheType, t, rtnNullIfNotEqualsOne);
    } catch (Exception e) {
      throw new ZeroException(OM3.p(cacheType, id, rtnNullIfNotEqualsOne), e);
    }
  }
}
