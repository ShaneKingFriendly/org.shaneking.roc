package sktest.roc.zero.cache.trans;


import org.shaneking.ling.zero.cache.ZeroCache;
import org.shaneking.roc.zero.cache.aspectj.CacheTransactionAspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

@Component
@EnableTransactionManagement(order = CacheTransactionAspect.ORDER - 500)
public class CacheTrans {

  @Autowired
  private ZeroCache cache;

  @Transactional(rollbackFor = Exception.class)
  public void test(boolean throwException) {
    cache.set("k1", "v1");
    cache.hset("k2", "f2-1", "v2-1");
    cache.hset("k2", "f2-2", "v2-2");
    cache.get("k1");
    cache.hget("k2", "f2-1");
    cache.del("k1");
    cache.hdel("k2", "f2-2");

    cache.set("k1", "v1");
    cache.hset("k2", "f2-2", "v2-2");
    cache.get("k1");
    cache.hget("k2", "f2-1");

    if (throwException) {
      throw new RuntimeException();
    }
  }
}
