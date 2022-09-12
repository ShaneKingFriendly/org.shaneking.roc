package org.shaneking.roc.zero.lang;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

@Slf4j
public class Object0s {
  public static <T> T copy(@NonNull T t) {
    T rtn = null;
    try {
      rtn = (T) t.getClass().newInstance();
      BeanUtils.copyProperties(t, rtn);
    } catch (Exception e) {
      log.error(t.toString(), e);
    }
    return rtn;
  }
}
