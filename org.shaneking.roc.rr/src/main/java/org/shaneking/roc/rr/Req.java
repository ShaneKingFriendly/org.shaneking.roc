package org.shaneking.roc.rr;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.shaneking.ling.rr.ReqMsg;

@Accessors(chain = true)
@ToString
public class Req<I> {
  @Getter
  @Setter
  private String cno;//ChannelNo
  @Getter
  @Setter
  private String tkn;//Token, for one request on secret key scenario.
  @Getter
  @Setter
  private String mvc;//Message Verification Code
  @Getter
  @Setter
  private String enc;//ciphertext of msg
  @Getter
  @Setter
  private ReqMsg<I> msg;

  @Getter
  @Schema(hidden = true)
  @Setter
  private Ctx ctx;

  public static <I> Req<I> build() {
    return new Req<I>();
  }

  public Ctx gnnCtx() {
    if (getCtx() == null) {
      setCtx(new Ctx());
    }
    return getCtx();
  }
}
