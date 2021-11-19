package com.setu.splitwise.service.domain.settlement_strategy;

import com.setu.splitwise.constant.Message;
import com.setu.splitwise.exception.ServerException;
import com.setu.splitwise.model.persistence.Bill;
import com.setu.splitwise.model.persistence.Transaction;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class GreedySettlementStrategy implements SettlementStrategy {

  private static final Logger logger = LoggerFactory.getLogger(GreedySettlementStrategy.class);

  /**
   * Uses greedy algorithm and recursion. So need to monitor to avoid infinity loops. Threshold is
   * used for avoiding infinity loops.
   *
   * @param transactions
   * @return
   */
  @Override
  public Set<Bill> settle(Set<Transaction> transactions) throws ServerException {
    Set<Bill> bills = new HashSet<>();
    int count = 0;
    int threshold = 2 * transactions.size();
    settleHelper(transactions, bills, count, threshold);
    logger.info("GreedySettlementStrategy stats: count = {}, size = {}, threshold = {}", count,
        transactions.size(), threshold);
    return bills;
  }

  private void settleHelper(Set<Transaction> transactions, Set<Bill> bills, int count,
      int threshold) throws ServerException {
    if (count > threshold) {
      throw new ServerException(HttpStatus.INTERNAL_SERVER_ERROR,
          Message.GREEDY_SETTLEMENT_RECURSION_THRESHOLD_REACHED);
    }
    Transaction maxCredit = getMaxCreditTransaction(transactions);
    Transaction maxDebit = getMaxDebitTransaction(transactions);
    if (shouldStopRecursion(maxCredit, maxDebit)) {
      return;
    }
    BigDecimal minimum = findMinimum(maxCredit, maxDebit);
    maxCredit.setAmountToBeSettled(maxCredit.getAmountToSettle().subtract(minimum));
    maxDebit.setAmountToBeSettled(maxDebit.getAmountToSettle().add(minimum));
    Bill bill = constructBill(minimum, maxCredit, maxDebit);
    bills.add(bill);
    settleHelper(transactions, bills, count + 1, threshold);
  }

  private Bill constructBill(BigDecimal min, Transaction maxCredit, Transaction maxDebit) {
    return Bill
        .builder()
        .amount(min)
        .debitFrom(maxDebit.getUserId())
        .creditTo(maxCredit.getUserId())
        .build();
  }

  private Transaction getMaxCreditTransaction(Set<Transaction> transactions) {
    return Collections.max(transactions,
        Comparator.comparing(Transaction::getAmountToSettle));
  }

  private Transaction getMaxDebitTransaction(Set<Transaction> transactions) {
    return Collections.min(transactions,
        Comparator.comparing(Transaction::getAmountToSettle));
  }

  private BigDecimal findMinimum(Transaction maxCredit, Transaction maxDebit) {
    return maxCredit.getAmountToSettle().min(maxDebit.getAmountToSettle().negate());
  }

  private boolean shouldStopRecursion(Transaction maxCredit, Transaction maxDebit) {
    return (BigDecimal.ZERO.equals(maxCredit.getAmountToSettle())
        && BigDecimal.ZERO.equals(maxDebit.getAmountToSettle()))
        || maxCredit.equals(maxDebit);
  }


}
