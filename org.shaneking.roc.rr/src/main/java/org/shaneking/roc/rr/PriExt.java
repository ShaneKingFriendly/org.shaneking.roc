package org.shaneking.roc.rr;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.shaneking.ling.persistence.sql.Pagination;

@Accessors(chain = true)
@ToString
public class PriExt {
  @Getter
  @Setter
  private ObjectNode jon;//json object node
  @Getter
  @Setter
  private Pagination pagination;
  @Getter
  @Setter
  private String userId;
}
