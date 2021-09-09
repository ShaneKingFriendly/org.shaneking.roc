package org.shaneking.roc.persistence.annotation;

import org.shaneking.ling.zero.lang.String0;

import java.lang.annotation.*;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface EntityCacheEvict {
  int clsIdx() default 0;

  boolean empty() default false;

  int pKeyIdx() default -1;///begin 0, for parameter

  String pKeyPath() default String0.EMPTY;//a.b.c, for parameter
}
