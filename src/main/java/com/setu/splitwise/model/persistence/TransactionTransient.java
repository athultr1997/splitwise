package com.setu.splitwise.model.persistence;

import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Setter;

public class TransactionTransient {

  @Setter(AccessLevel.PUBLIC)
  protected BigDecimal amountToBeSettled;

}
