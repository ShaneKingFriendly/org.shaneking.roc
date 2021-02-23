package org.shaneking.roc.rr;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.shaneking.ling.persistence.Pagination;

@Accessors(chain = true)
@ToString
public class Tbl {
  @Getter
  @Setter
  private Pagination pagination;

  public Pagination gnnPagination() {
    if (getPagination() == null) {
      setPagination(new Pagination());
    }
    return getPagination();
  }
}
