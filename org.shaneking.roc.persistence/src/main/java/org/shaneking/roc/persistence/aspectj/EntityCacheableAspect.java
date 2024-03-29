package org.shaneking.roc.persistence.aspectj;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.ling.zero.annotation.ZeroAnnotation;
import org.shaneking.ling.zero.cache.ZeroCache;
import org.shaneking.ling.zero.lang.Object0;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.ling.zero.text.MF0;
import org.shaneking.roc.persistence.annotation.EntityCacheable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Aspect
@Component
@ConditionalOnProperty(prefix = "sk.roc.persistence.entity.cache", value = "enabled", matchIfMissing = true)
@Slf4j
public class EntityCacheableAspect {
  @Value("${sk.roc.persistence.entity.cache.enabled:true}")
  private boolean enabled;
  @Autowired(required = false)
  private ZeroCache cache;

  @Around("pointcut() && @annotation(entityCacheable)")
  public Object around(ProceedingJoinPoint pjp, EntityCacheable entityCacheable) throws Throwable {
    Object rtn = null;
    List<Object> rtnList = null;
    List<Object> argList = null;
    if (enabled && cache != null) {
      if (pjp.getArgs().length > entityCacheable.clsIdx() && pjp.getArgs()[entityCacheable.clsIdx()] instanceof Class) {
        Class clazz = (Class) pjp.getArgs()[entityCacheable.clsIdx()];
        try {
          if (entityCacheable.pKeyIdx() > -1) {
            Object pKeyObj = pjp.getArgs()[entityCacheable.pKeyIdx()];
            if (pKeyObj instanceof List) {
              //org.shaneking.roc.persistence.dao.CacheableDao.lstByIds
              List<String> pKeyList = String0.isNullOrEmpty(entityCacheable.pKeyPath()) ? (List<String>) pKeyObj : ((List<Object>) pKeyObj).stream().map(o -> String.valueOf(Object0.gs(o, entityCacheable.pKeyPath()))).filter(s -> !String0.isNullOrEmpty(s)).collect(Collectors.toList());
              List<String> cachedList = cache.hmget(clazz.getName(), pKeyList.toArray(new String[0]));
              if (cachedList.size() > 0) {
                //compile error
//              rtnList = cachedList.stream().filter(s -> !Strings.isNullOrEmpty(s)).map(s -> OM3.readValue(s, clazz, true)).filter(o -> o != null).collect(Collectors.toList());
                rtnList = cachedList.stream().filter(s -> !String0.isNullOrEmpty(s)).collect(ArrayList::new, (a, s) -> {
                  Object o = OM3.readValue(s, clazz, true);
                  if (o != null) {
                    a.add(o);
                  }
                }, ArrayList::addAll);
                Set<String> cachedKeySet = rtnList.stream().map(o -> String.valueOf(Object0.gs(o, entityCacheable.rKeyPath()))).filter(s -> !String0.isNullOrEmpty(s)).collect(Collectors.toSet());
                argList = ((List<Object>) pKeyObj).stream().filter(o -> {
                  String k = String.valueOf(String0.isNullOrEmpty(entityCacheable.pKeyPath()) ? o : Object0.gs(o, entityCacheable.pKeyPath()));
                  return !cachedKeySet.contains(k);
                }).collect(Collectors.toList());
              }
            } else {
              //org.shaneking.roc.persistence.dao.CacheableDao.oneById(java.lang.Class<T>, java.lang.String)
              //org.shaneking.roc.persistence.dao.CacheableDao.oneById(java.lang.Class<T>, java.lang.String, boolean)
              String k = String.valueOf(String0.isNullOrEmpty(entityCacheable.pKeyPath()) ? pKeyObj : Object0.gs(pKeyObj, entityCacheable.pKeyPath()));
              if (String0.isNull2Empty(k)) {
                log.warn(MF0.fmt("{0} - {1}", pjp.getSignature().toLongString(), ZeroAnnotation.ERR_CODE__ANNOTATION_SETTING_ERROR));
              } else {
                String cached = cache.hget(clazz.getName(), k);
                if (!String0.isNullOrEmpty(cached)) {
                  log.info(MF0.fmt("{0} - {1} : {2}", clazz.getName(), ZeroCache.ERR_CODE__CACHE_HIT_ALL, cached));
                  rtn = OM3.readValue(cached, clazz, true);
                }
              }
            }
          }
        } catch (Throwable e) {
          ///ignore exception : if exception then proceed
          log.error(String.valueOf(clazz), e);
        }
        if (rtn == null) {
          if (argList != null && argList.size() == 0) {
            log.info(MF0.fmt("{0} - {1}({2}) : {3}", clazz.getName(), ZeroCache.ERR_CODE__CACHE_HIT_ALL, rtnList.size(), OM3.writeValueAsString(rtnList)));
            rtn = rtnList;
          } else {
            if (argList != null && argList.size() > 0 && entityCacheable.pKeyIdx() > -1) {
              log.info(MF0.fmt("{0} - {1}({2}) : {3}", clazz.getName(), ZeroCache.ERR_CODE__CACHE_HIT_PART, rtnList.size(), OM3.writeValueAsString(rtnList)));
              pjp.getArgs()[entityCacheable.pKeyIdx()] = argList;
              rtn = pjp.proceed(pjp.getArgs());
            } else {
              if (entityCacheable.pKeyIdx() > -1) {
                log.warn(MF0.fmt("{0} - {1}", clazz.getName(), ZeroCache.ERR_CODE__CACHE_HIT_MISS));
              }
              rtn = pjp.proceed();
            }
            if (rtn != null) {
              if (rtn instanceof List) {
                List<Object> rstList = (List<Object>) rtn;
                if (rstList.size() > 0) {
                  cache.hmset(clazz.getName(), rstList.stream().collect(HashMap::new, (a, o) -> {
                    String k = String.valueOf(Object0.gs(o, entityCacheable.rKeyPath()));
                    if (!String0.isNullOrEmpty(k)) {
                      a.put(k, OM3.writeValueAsString(o));
                    }
                  }, HashMap::putAll));
                }
                if (rtnList != null) {
                  rtnList.addAll(rstList);
                } else {
                  rtnList = rstList;
                }
                rtn = rtnList;
              } else {
                String k = String.valueOf(Object0.gs(rtn, entityCacheable.rKeyPath()));
                if (!String0.isNullOrEmpty(k)) {
                  cache.hset(clazz.getName(), k, OM3.writeValueAsString(rtn));
                }
              }
            } else {
              rtn = rtnList;
            }
          }
        }
      } else {
        log.warn(MF0.fmt("{0} - {1}", pjp.getSignature().toLongString(), ZeroAnnotation.ERR_CODE__ANNOTATION_SETTING_ERROR));
        rtn = pjp.proceed();
      }
    } else {
      rtn = pjp.proceed();
    }
    return rtn;
  }

  @Pointcut("execution(@org.shaneking.roc.persistence.annotation.EntityCacheable * *..*.*(..))")
  private void pointcut() {
  }
}
