package org.shaneking.roc.rr.annotation;

import java.lang.annotation.*;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RrCache {
  int reqParamIdx() default 0;

  int cacheSeconds() default 180;//180s
}
