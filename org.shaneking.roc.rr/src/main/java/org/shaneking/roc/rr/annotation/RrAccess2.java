package org.shaneking.roc.rr.annotation;

import java.lang.annotation.*;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RrAccess2 {
  int reqParamIdx() default 0;
}
