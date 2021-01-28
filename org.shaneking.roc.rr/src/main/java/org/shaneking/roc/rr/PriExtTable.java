package org.shaneking.roc.rr;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.shaneking.ling.persistence.sql.Pagination;

@Accessors(chain = true)
@ToString
public class PriExtTable {
  @Getter
  @Setter
  private Pagination pagination;
}
