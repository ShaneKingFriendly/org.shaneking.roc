package sktest.roc.persistence.aspectj;

import lombok.NonNull;
import org.shaneking.roc.persistence.aspectj.EntityCacheAbstractWrapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class EntityCacheAbstractWrapperTest extends EntityCacheAbstractWrapper {

  @Override
  public Boolean del(@NonNull String key) {
    return null;
  }

  @Override
  public Long hdel(@NonNull String key, @NonNull String... fields) {
    return null;
  }

  @Override
  public String hget(@NonNull String key, @NonNull String field) {
    return null;
  }

  @Override
  public @NonNull List<String> hmget(@NonNull String key, @NonNull String... fields) {
    return null;
  }

  @Override
  public void hmset(@NonNull String key, @NonNull Map<String, String> map) {

  }

  @Override
  public void hset(@NonNull String key, @NonNull String field, @NonNull String value) {

  }
}
