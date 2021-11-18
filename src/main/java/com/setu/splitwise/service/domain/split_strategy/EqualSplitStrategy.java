package com.setu.splitwise.service.domain.split_strategy;

import com.setu.splitwise.model.persistence.Transaction;
import com.setu.splitwise.model.request.CreateSplitRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class EqualSplitStrategy implements SplitStrategy {

  @Override
  public Set<Transaction> createSplitTransactions(CreateSplitRequest createSplitRequest) {
    BigDecimal totalAmount = createSplitRequest.getAmount();
    int splitSize = createSplitRequest.getSplitSize();
    BigDecimal amountOwed = totalAmount
        .divide(new BigDecimal(splitSize), 2, RoundingMode.HALF_EVEN);
    return createSplitRequest.getSplitDistributions()
        .stream()
        .map(splitDistribution -> Transaction
            .builder()
            .userId(splitDistribution.getUserId())
            .owed(amountOwed)
            .paid(splitDistribution.getAmountPaid())
            .build())
        .collect(Collectors.toSet());
  }
}
