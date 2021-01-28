package org.shaneking.roc.rr;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@ToString
public class PriExt {
  @Getter
  @Setter
  private ObjectNode jon;//json object node
  @Setter
  private PriExtTable table;
  @Getter
  @Setter
  private String userId;

  public PriExtTable getTable() {
    if (table == null) {
      table = new PriExtTable();
    }
    return table;
  }
}
