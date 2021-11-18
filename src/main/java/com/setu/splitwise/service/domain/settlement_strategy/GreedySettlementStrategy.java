package com.setu.splitwise.service.domain.settlement_strategy;

import com.setu.splitwise.model.persistence.Bill;
import com.setu.splitwise.model.persistence.Transaction;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class GreedySettlementStrategy implements SettlementStrategy {

  @Override
  public Set<Bill> settle(Set<Transaction> transactions) {
    Set<Bill> bills = new HashSet<>();
    settleHelper(transactions, bills);
    return bills;
  }

  private void settleHelper(Set<Transaction> transactions, Set<Bill> bills) {
    Transaction maxCredit = Collections.max(transactions,
        Comparator.comparing(Transaction::getAmountToSettle));
    Transaction maxDebit = Collections.min(transactions,
        Comparator.comparing(Transaction::getAmountToSettle));
    if ((BigDecimal.ZERO.equals(maxCredit.getAmountToSettle())
        && BigDecimal.ZERO.equals(maxDebit.getAmountToSettle())
        || maxCredit.equals(maxDebit))) {
      return;
    }
    BigDecimal min = maxCredit.getAmountToSettle().min(maxDebit.getAmountToSettle().negate());
    maxCredit.setAmountToBeSettled(maxCredit.getAmountToSettle().subtract(min));
    maxDebit.setAmountToBeSettled(maxDebit.getAmountToSettle().add(min));
    Bill bill = Bill
        .builder()
        .amount(min)
        .debitFrom(maxDebit.getUserId())
        .creditTo(maxCredit.getUserId())
        .build();
    bills.add(bill);
    settleHelper(transactions, bills);
  }

}
