package sktest.roc.persistence.util.concurrent.locks;

import org.junit.jupiter.api.Test;
import org.shaneking.ling.zero.util.UUID0;
import org.shaneking.ling.zero.util.concurrent.locks.DistributedLockable;
import org.shaneking.roc.persistence.util.concurrent.locks.DistributedDbLock;
import org.shaneking.roc.test.SKSpringUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.*;

class DistributedDbLockTest extends SKSpringUnit {
  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Test
  void test() {
    String lockKey = "sktest.roc.persistence.util.concurrent.locks.DistributedDbLockTest";

    DistributedLockable distributedLock = new DistributedDbLock(jdbcTemplate);
    distributedLock.lock(lockKey, 1, 1);

    DistributedLockable distributedLock2 = new DistributedDbLock(jdbcTemplate);
    distributedLock2.lock(lockKey, 1, 1);

    assertTrue(distributedLock.locked());
    assertFalse(distributedLock2.locked());
    assertThrows(IllegalArgumentException.class, () -> distributedLock.lock(UUID0.cUl33(), 1, 1));

    distributedLock2.unlock();
    distributedLock.unlock();
  }
}
