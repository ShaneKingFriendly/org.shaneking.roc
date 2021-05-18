package org.shaneking.roc.zero.hello;

import org.shaneking.roc.zero.cache.RocZeroCache;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(prefix = "sk.roc.zero.hello.cache", value = "enabled")
public class HelloRocZeroCache implements RocZeroCache {
}
