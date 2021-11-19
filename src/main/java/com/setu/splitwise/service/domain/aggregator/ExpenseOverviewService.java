package com.setu.splitwise.service.domain.aggregator;

import com.setu.splitwise.enums.BalanceType;
import com.setu.splitwise.exception.ServerException;
import com.setu.splitwise.model.persistence.Payment;
import com.setu.splitwise.model.persistence.Split;
import com.setu.splitwise.model.persistence.Transaction;
import com.setu.splitwise.model.response.ExpenseOverview.Expense;
import com.setu.splitwise.service.domain.core.PaymentService;
import com.setu.splitwise.service.domain.core.SplitService;
import com.setu.splitwise.service.domain.core.UserService;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpenseOverviewService implements AggregatorService<List<Expense>, Long> {

  private static final Logger logger = LoggerFactory.getLogger(ExpenseOverviewService.class);

  @Autowired
  private UserService userService;

  @Autowired
  private SplitService splitService;

  @Autowired
  private PaymentService paymentService;

  @Override
  public List<Expense> getOverview(Long userId) throws ServerException {
    userService.validate(userId);
    List<Expense> splitExpenses = constructSplitExpenses(userId);
    List<Expense> paymentExpenses = constructPaymentExpenses(userId);
    return merge(splitExpenses, paymentExpenses);
  }

  private List<Expense> constructSplitExpenses(Long userId) {
    Set<Split> splits = splitService.findAllSplitsInvolvingUserId(userId);
    if (CollectionUtils.isEmpty(splits)) {
      logger.info("splits involved by user: {} is empty", userId);
    }
    return splits.stream()
        .map(split -> Expense
            .builder()
            .createdAt(split.getCreatedAt())
            .title(split.getTitle())
            .description(createDescription(split))
            .additionalInformation(createAdditionalInformation(userId, split))
            .build())
        .collect(Collectors.toList());
  }

  private List<Expense> constructPaymentExpenses(Long userId) {
    Set<Payment> payments = paymentService.getPaymentsInvolvingUserId(userId);
    if (CollectionUtils.isEmpty(payments)) {
      logger.info("payments involved by user: {} is empty", userId);
    }
    return payments.stream()
        .map(payment -> Expense
            .builder()
            .createdAt(payment.getCreatedAt())
            .title(createTitle(payment))
            .build())
        .collect(Collectors.toList());
  }

  private List<Expense> merge(List<Expense> splitExpenses, List<Expense> paymentExpenses) {
    return Stream.concat(splitExpenses.stream(), paymentExpenses.stream())
        .sorted(Comparator.comparing(Expense::getCreatedAt).reversed())
        .collect(Collectors.toList());
  }

  private String createDescription(Split split) {
    return String
        .format("%d people paid %s%s", split.getNumberOfPeoplePaid(), split.getCurrencySymbol(),
            split.getAmount());
  }

  private String createTitle(Payment payment) {
    return String.format("%s paid %s %s%s", payment.getDebitFrom(), payment.getCreditTo(),
        payment.getCurrencySymbol(), payment.getAmount());
  }

  private String createAdditionalInformation(Long userId, Split split) {
    Transaction transaction = split.getTransactionByUserId(userId);
    BigDecimal amountToSettle = transaction.getAmountToSettle();
    BalanceType balanceType = BalanceType.findBalanceType(amountToSettle);
    if (BalanceType.SETTLED.equals(balanceType)) {
      return "no balance";
    }
    return String
        .format("you %s %s%s", balanceType, split.getCurrencySymbol(), amountToSettle.abs());
  }

}
