package com.setu.splitwise.service.domain.split_strategy;

import com.setu.splitwise.model.persistence.Transaction;
import com.setu.splitwise.model.request.CreateSplitRequest;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class PercentageSplitStrategy implements SplitStrategy {

  @Override
  public Set<Transaction> createSplitTransactions(CreateSplitRequest createSplitRequest) {
    return null;
  }
}
