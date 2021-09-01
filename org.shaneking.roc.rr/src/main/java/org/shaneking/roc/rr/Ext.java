package org.shaneking.roc.rr;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@ToString
public class Ext {
  @Getter
  @Setter
  private String dsz;//DateTimeSssZone
  @Getter
  @Setter
  private ObjectNode jon;//json object node
  @Getter
  @Setter
  private Tbl tbl;
  @Getter
  @Setter
  private String userNo;

  public Tbl gnnTbl() {
    if (getTbl() == null) {
      setTbl(new Tbl());
    }
    return getTbl();
  }
}
