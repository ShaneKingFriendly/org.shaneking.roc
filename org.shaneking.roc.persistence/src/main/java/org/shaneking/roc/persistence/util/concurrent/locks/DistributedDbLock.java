package org.shaneking.roc.persistence.util.concurrent.locks;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.ling.zero.text.MF0;
import org.shaneking.ling.zero.util.UUID0;
import org.shaneking.ling.zero.util.concurrent.locks.DistributedLockable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;

/*
create table if not exists `skrp_distributed_locks` (
  `lock_key` char(40) not null,
  `lock_status` varchar(40) default 'N',
  `client_id` varchar(40) default '',
  `time_millis` long default 0,
  primary key (`lock_key`)
);
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Slf4j
public class DistributedDbLock implements DistributedLockable {
  public static final String DEFAULT_LOCK_TBL = "skrp_distributed_locks";
  @Getter
  private long timeMillis = System.currentTimeMillis();
  @Getter
  private String clientId;
  @Getter
  private String lockKey;

  @Autowired
  private JdbcTemplate jdbcTemplate;

  public DistributedDbLock() {
    this.clientId = UUID0.cUl33();
  }

  public DistributedDbLock(JdbcTemplate jdbcTemplate) {
    this();
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public void close() throws Exception {
    jdbcTemplate.update(MF0.fmt("update {0} set lock_status = 'N' where lock_status = 'Y' and lock_key = '{1}' and client_id = '{2}'", DEFAULT_LOCK_TBL, this.lockKey, this.clientId));
    this.lockKey = null;
  }

  @Override
  public DistributedDbLock lock(String lockKey, int tryTimes, int intervalSeconds) {
    this.lockKey = String0.nullOrEmptyTo(this.lockKey, lockKey);
    if (Objects.equals(this.lockKey, lockKey)) {
      try {
        if (!locked()) {
          this.clientId = UUID0.cUl33();
          int times = 0;
          timeMillis = System.currentTimeMillis();
          while (jdbcTemplate.update(MF0.fmt("update {0} set lock_status = 'Y', client_id = '{2}', time_millis = {3} where lock_status = 'N' and lock_key = '{1}'"
            , DEFAULT_LOCK_TBL, this.lockKey, this.clientId, String.valueOf(this.timeMillis))) == 0 && times++ < tryTimes) {
            Thread.sleep(intervalSeconds * 1000);
            timeMillis = System.currentTimeMillis();
          }
        }
      } catch (Exception e) {
        log.error(this.lockKey, e);
        unlock();
      }
      return this;
    } else {
      throw new IllegalArgumentException(OM3.lp(this.lockKey, lockKey));
    }
  }

  @Override
  public boolean locked() {
    if (jdbcTemplate.queryForList(MF0.fmt("select lock_key from {0} where lock_key = '{1}'", DEFAULT_LOCK_TBL, this.lockKey)).size() == 0) {
      jdbcTemplate.update(MF0.fmt("insert into {0} (lock_key,lock_status) select '{1}','N'", DEFAULT_LOCK_TBL, this.lockKey));
    }
    return jdbcTemplate.queryForList(MF0.fmt("select client_id from {0} where lock_status = 'Y' and lock_key = '{1}' and client_id = '{2}'", DEFAULT_LOCK_TBL, this.lockKey, this.clientId)).size() > 0;
  }

  @Override
  public void unlock() {
    try {
      close();
    } catch (Exception e) {
      log.error(OM3.p(this.lockKey, this.clientId), e);
    }
  }
}
