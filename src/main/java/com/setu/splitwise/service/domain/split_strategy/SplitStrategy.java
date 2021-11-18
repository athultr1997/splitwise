package com.setu.splitwise.service.domain.split_strategy;

import com.setu.splitwise.exception.ServerException;
import com.setu.splitwise.model.persistence.Transaction;
import com.setu.splitwise.model.request.CreateSplitRequest;
import java.util.Set;

public interface SplitStrategy {

  Set<Transaction> createSplitTransactions(CreateSplitRequest createSplitRequest) throws ServerException;

}
