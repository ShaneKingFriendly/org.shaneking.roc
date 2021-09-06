package org.shaneking.roc.zero.simple;

import org.shaneking.roc.zero.cache.RocZeroCache;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(prefix = "sk.roc.zero.simple.cache", value = "enabled", matchIfMissing = false)
public class SimpleRocZeroCache implements RocZeroCache {
}
