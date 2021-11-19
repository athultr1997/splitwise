package com.setu.splitwise.service.domain.settlement_strategy;

import com.setu.splitwise.exception.ServerException;
import com.setu.splitwise.model.persistence.Bill;
import com.setu.splitwise.model.persistence.Transaction;
import java.util.Set;

public interface SettlementStrategy {

  Set<Bill> settle(Set<Transaction> transactions) throws ServerException;

}
