package com.setu.splitwise.service.domain.split_strategy;

import com.setu.splitwise.constant.Message;
import com.setu.splitwise.exception.ServerException;
import com.setu.splitwise.model.persistence.Transaction;
import com.setu.splitwise.model.request.CreateSplitRequest;
import com.setu.splitwise.model.request.CreateSplitRequest.SplitDistribution;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class AmountSplitStrategy implements SplitStrategy {

  @Override
  public Set<Transaction> createSplitTransactions(CreateSplitRequest createSplitRequest)
      throws ServerException {
    for (SplitDistribution splitDistribution : createSplitRequest.getSplitDistributions()) {
      if (splitDistribution == null) {
        throw new ServerException(HttpStatus.BAD_REQUEST, Message.INVALID_AMOUNT);
      }
    }
    return createSplitRequest.getSplitDistributions()
        .stream()
        .map(this::constructTransaction)
        .collect(Collectors.toSet());
  }

  private Transaction constructTransaction(SplitDistribution splitDistribution) {
    return Transaction
        .builder()
        .userId(splitDistribution.getUserId())
        .owed(splitDistribution.getSplitValue())
        .paid(splitDistribution.getAmountPaid())
        .build();
  }

}
