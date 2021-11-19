package com.setu.splitwise.service.domain;

import com.setu.splitwise.constant.Message;
import com.setu.splitwise.enums.BalanceType;
import com.setu.splitwise.exception.ServerException;
import com.setu.splitwise.model.persistence.Payment;
import com.setu.splitwise.model.persistence.Split;
import com.setu.splitwise.model.persistence.Transaction;
import com.setu.splitwise.model.persistence.User;
import com.setu.splitwise.model.response.ExpenseOverview;
import com.setu.splitwise.model.response.ExpenseOverview.Expense;
import com.setu.splitwise.service.manager.UserManager;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private static final Logger logger = LoggerFactory.getLogger(UserService.class);

  @Autowired
  private UserManager userManager;

  @Autowired
  private SplitService splitService;

  @Autowired
  private PaymentService paymentService;

  public void saveAll(List<User> users) {
    userManager.saveAll(users);
  }

  public boolean checkUserIdsExist(List<Long> userIds) {
    return userManager.checkUserIdsExist(userIds);
  }

  public ExpenseOverview getExpenseOverview(Long userId) throws ServerException {
    validate(userId);
    Set<Split> splits = splitService.findAllSplitsInvolvingUserId(userId);
    if (CollectionUtils.isEmpty(splits)) {
      logger.info("splits involved by user: {} is empty", userId);
    }
    List<Expense> expenses = splits.stream()
        .map(split -> Expense
            .builder()
            .createdAt(split.getCreatedAt())
            .title(split.getTitle())
            .description(createDescription(split))
            .additionalInformation(createAdditionalInformation(userId, split))
            .build())
        .collect(Collectors.toList());
    Set<Payment> payments = paymentService.getPaymentsInvolvingUserId(userId);
    if (CollectionUtils.isEmpty(payments)) {
      logger.info("payments involved by user: {} is empty", userId);
    }
    List<Expense> expenses1 = payments.stream()
        .map(payment -> Expense
            .builder()
            .createdAt(payment.getCreatedAt())
            .title(createTitle(payment))
            .build())
        .collect(Collectors.toList());
    List<Expense> expenses2 = Stream.concat(expenses.stream(), expenses1.stream())
        .sorted(Comparator.comparing(Expense::getCreatedAt)).collect(Collectors.toList());
    return ExpenseOverview
        .builder()
        .expenses(expenses2)
        .build();
  }

  // TODO move to validation service
  public void validate(Long userId) throws ServerException {
    if (!checkUserIdsExist(Collections.singletonList(userId))) {
      throw new ServerException(HttpStatus.BAD_REQUEST, Message.INVALID_USER_IDS);
    }
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
