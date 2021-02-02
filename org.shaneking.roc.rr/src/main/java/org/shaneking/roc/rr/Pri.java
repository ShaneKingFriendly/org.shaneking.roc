package org.shaneking.roc.rr;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * Pri is trans object wrapper
 * <p>
 * Mapping for: https://github.com/ShaneKing/sk-user
 */
@Accessors(chain = true)
@ToString
public class Pri<O, R> {
  @Getter
  @Setter
  private Ext ext;//extend settings like table config
  @Getter
  @Setter
  private O obj;//main object content
  @Getter
  @Setter
  private R rtn;//return, just response, don't request

  public static <O, R> Pri<O, R> build() {
    return new Pri<O, R>();
  }

  //some like no request parameter
  public static <O, R> Pri<O, R> build(R rtn) {
    return new Pri<O, R>().setRtn(rtn);
  }

  public static <O, R> Pri<O, R> build(R rtn, O obj) {
    return new Pri<O, R>().setRtn(rtn).setObj(obj);
  }

  public static <O, R> Pri<O, R> build(R rtn, O obj, Ext ext) {
    return new Pri<O, R>().setRtn(rtn).setObj(obj).setExt(ext);
  }

  public Ext gnnExt() {
    if (ext == null) {
      ext = new Ext();
    }
    return ext;
  }
}
