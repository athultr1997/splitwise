package com.setu.splitwise.service.domain;

import com.setu.splitwise.constant.Message;
import com.setu.splitwise.enums.SettlementType;
import com.setu.splitwise.exception.ServerException;
import com.setu.splitwise.model.persistence.Bill;
import com.setu.splitwise.model.persistence.Split;
import com.setu.splitwise.model.persistence.Transaction;
import com.setu.splitwise.model.request.CreateSplitRequest;
import com.setu.splitwise.model.response.CreateSplitResponse;
import com.setu.splitwise.service.domain.settlement_strategy.SettlementStrategy;
import com.setu.splitwise.service.domain.settlement_strategy.SettlementStrategyFactory;
import com.setu.splitwise.service.domain.split_strategy.SplitStrategy;
import com.setu.splitwise.service.domain.split_strategy.SplitStrategyFactory;
import com.setu.splitwise.service.manager.SplitManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class SplitService {

  private static final Logger logger = LoggerFactory.getLogger(SplitService.class);

  @Autowired
  private SplitManager splitManager;

  @Autowired
  private UserService userService;

  @Autowired
  private SplitStrategyFactory splitStrategyFactory;

  @Autowired
  private SettlementStrategyFactory settlementStrategyFactory;

  public CreateSplitResponse createSplit(CreateSplitRequest request) throws ServerException {
    //TODO check users are valid
    //TODO check amount distribution is valid

    List<Long> userIds = request.getAllUserIds();
    if (!userService.checkUserIdsExist(userIds)) {
      logger.error("Invalid userIds: {}", userIds);
      throw new ServerException(HttpStatus.BAD_REQUEST, Message.INVALID_USER_IDS);
    }
    LocalDateTime currentTime = LocalDateTime.now();
    Split split = Split
        .builder()
        .title(request.getTitle())
        .note(request.getNote())
        .amount(request.getAmount())
        .currency(request.getCurrency())
        .createdAt(currentTime)
        .updatedAt(currentTime)
        .build();
    SplitStrategy splitStrategy = splitStrategyFactory.getSplitStrategy(request.getSplitType());
    Set<Transaction> transactions = splitStrategy.createSplitTransactions(request);
    split.setTransactions(transactions);
    SettlementStrategy settlementStrategy = settlementStrategyFactory
        .getSettlementStrategy(SettlementType.DEFAULT);
    Set<Bill> bills = settlementStrategy.settle(split.getTransactions());
    split.setBills(bills);
    splitManager.save(split);
    return CreateSplitResponse
        .builder()
        .id(split.getId())
        .build();
  }

}
