package org.shaneking.roc.persistence.annotation;

import org.shaneking.ling.zero.lang.String0;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface EntityCacheable {
  int clsIdx() default 0;

  int pKeyIdx() default -1;//begin 0, for parameter

  String pKeyPath() default String0.EMPTY;//a.b.c, for parameter

  String rKeyPath();//a.b.c, for return
}
