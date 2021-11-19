package com.setu.splitwise.service.domain.creation;

import com.setu.splitwise.enums.SettlementType;
import com.setu.splitwise.exception.ServerException;
import com.setu.splitwise.model.persistence.Bill;
import com.setu.splitwise.model.persistence.Split;
import com.setu.splitwise.model.persistence.Transaction;
import com.setu.splitwise.model.request.CreateSplitRequest;
import com.setu.splitwise.service.domain.settlement_strategy.SettlementStrategy;
import com.setu.splitwise.service.domain.settlement_strategy.SettlementStrategyFactory;
import com.setu.splitwise.service.domain.split_strategy.SplitStrategy;
import com.setu.splitwise.service.domain.split_strategy.SplitStrategyFactory;
import com.setu.splitwise.service.domain.validator.split_validator.SplitBasicDetailsValidator;
import com.setu.splitwise.service.domain.validator.split_validator.SplitValidator;
import com.setu.splitwise.service.manager.SplitManager;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SplitCreationService implements CreationService<Split, CreateSplitRequest> {

  private final SplitValidator splitValidator;

  @Autowired
  private SplitStrategyFactory splitStrategyFactory;

  @Autowired
  private SettlementStrategyFactory settlementStrategyFactory;

  @Autowired
  private SplitManager splitManager;

  @Autowired
  public SplitCreationService(SplitBasicDetailsValidator splitBasicDetailsValidator) {
    splitValidator = splitBasicDetailsValidator;
  }

  @Override
  public Split create(CreateSplitRequest request) throws ServerException {
    validate(request);
    Split split = createSplitWithBasicInfo(request);
    Set<Transaction> transactions = constructTransactions(request);
    split.setTransactions(transactions);
    Set<Bill> bills = constructBills(split);
    split.setBills(bills);
    splitManager.save(split);
    return split;
  }

  private void validate(CreateSplitRequest request) throws ServerException {
    splitValidator.validate(request);
  }

  private Split createSplitWithBasicInfo(CreateSplitRequest request) {
    return Split
        .builder()
        .createdBy(request.getCreatedBy())
        .title(request.getTitle())
        .note(request.getNote())
        .amount(request.getAmount())
        .currency(request.getCurrency())
        .createdAt(request.getCreatedAt())
        .updatedAt(request.getUpdatedAt())
        .build();
  }

  /**
   * Strategy pattern is being used for creating transactions using different algorithms.
   *
   * @param request
   * @return
   * @throws ServerException
   */
  private Set<Transaction> constructTransactions(CreateSplitRequest request)
      throws ServerException {
    SplitStrategy splitStrategy = splitStrategyFactory.getSplitStrategy(request.getSplitType());
    return splitStrategy.createSplitTransactions(request);
  }

  /**
   * Strategy pattern is being used for creating bills.
   *
   * @param split
   * @return
   * @throws ServerException
   */
  private Set<Bill> constructBills(Split split) throws ServerException {
    SettlementStrategy settlementStrategy = settlementStrategyFactory
        .getSettlementStrategy(SettlementType.DEFAULT);
    return settlementStrategy.settle(split.getTransactions());
  }


}
