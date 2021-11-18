package com.setu.splitwise.enums;

import java.math.BigDecimal;

public enum BalanceType {
  LEND,
  OWES,
  SETTLED;

  public static BalanceType findBalanceType(BigDecimal amount) {
    int result = amount.compareTo(BigDecimal.ZERO);
    switch (result) {
      case 1:
        return BalanceType.LEND;
      case -1:
        return BalanceType.OWES;
      case 0:
        return BalanceType.SETTLED;
      default:
        return null;
    }
  }

}
