package org.shaneking.roc.rr;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@ToString
public class Req<O, R> {
  @Getter
  @Schema(hidden = true)
  @Setter
  private Ctx ctx;
  @Getter
  @Setter
  private String enc;
  @Getter
  @Setter
  private Pri<O, R> pri;
  @Getter
  @Setter
  private Pub pub;

  public static <O, R> Req<O, R> build() {
    return new Req<O, R>();
  }

  public static <O, R> Req<O, R> build(Pri<O, R> pri) {
    return new Req<O, R>().setPri(pri);
  }

  public static <O, R> Req<O, R> build(Pub pub) {
    return new Req<O, R>().setPub(pub);
  }

  public static <O, R> Req<O, R> build(Pub pub, Pri<O, R> pri) {
    return new Req<O, R>().setPub(pub).setPri(pri);
  }

  public static <O, R> Req<O, R> build(Pub pub, String enc) {
    return new Req<O, R>().setPub(pub).setEnc(enc);
  }

  public Ctx gnnCtx() {
    if (getCtx() == null) {
      setCtx(new Ctx());
    }
    return getCtx();
  }

  public Ctx detach() {
    Ctx rtn = ctx;
    ctx = null;
    return rtn;
  }

  public Req<O, R> attach(Ctx ctx) {
    this.ctx = ctx;
    return this;
  }
}
