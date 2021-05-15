package org.shaneking.roc.zero.hello;

import org.shaneking.ling.zero.cache.ZeroCache;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(prefix = "sk.roc.zero.hello.cache", value = "enabled")
public class HelloZeroCache implements ZeroCache {
}
