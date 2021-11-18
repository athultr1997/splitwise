package com.setu.splitwise.service.domain.settlement_strategy;

import com.setu.splitwise.enums.SettlementType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SettlementStrategyFactory {

  @Autowired
  private GreedySettlementStrategy greedySettlementStrategy;

  public SettlementStrategy getSettlementStrategy(SettlementType settlementType) {
    if (settlementType != SettlementType.DEFAULT) {
      return null;
    }
    return greedySettlementStrategy;
  }

}
