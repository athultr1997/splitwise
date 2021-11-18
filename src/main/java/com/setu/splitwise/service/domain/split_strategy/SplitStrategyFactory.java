package com.setu.splitwise.service.domain.split_strategy;

import com.setu.splitwise.enums.SplitType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SplitStrategyFactory {

  @Autowired
  private EqualSplitStrategy equalSplitStrategy;

  @Autowired
  private AmountSplitStrategy amountSplitStrategy;

  @Autowired
  private PercentageSplitStrategy percentageSplitStrategy;

  public SplitStrategy getSplitStrategy(SplitType splitType) {
    if (splitType == null) {
      return null;
    }
    switch (splitType) {
      case EQUAL:
        return equalSplitStrategy;
      case AMOUNT:
        return amountSplitStrategy;
      case PERCENTAGE:
        return percentageSplitStrategy;
      default:
        return null;
    }
  }

}
