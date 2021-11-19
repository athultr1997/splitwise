package com.setu.splitwise.service.domain;

import com.setu.splitwise.constant.Message;
import com.setu.splitwise.exception.ServerException;
import com.setu.splitwise.model.persistence.Split;
import com.setu.splitwise.model.persistence.Transaction;
import com.setu.splitwise.model.request.CreateSplitRequest;
import com.setu.splitwise.model.response.CreateSplitResponse;
import com.setu.splitwise.model.response.GetSplitResponse;
import com.setu.splitwise.model.response.GetSplitResponse.SplitDistribution;
import com.setu.splitwise.service.domain.creation.SplitCreationService;
import com.setu.splitwise.service.manager.SplitManager;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class SplitService {

  @Autowired
  private SplitManager splitManager;

  @Autowired
  private SplitCreationService splitCreationService;

  public CreateSplitResponse createSplit(CreateSplitRequest request) throws ServerException {
    Split split = splitCreationService.create(request);
    return CreateSplitResponse
        .builder()
        .id(split.getId())
        .build();
  }

  public GetSplitResponse getSplitDetails(Long splitId) throws ServerException {
    Split split = splitManager.getById(splitId);
    if (split == null) {
      throw new ServerException(HttpStatus.NOT_FOUND, Message.RESOURCE_NOT_FOUND);
    }
    return GetSplitResponse
        .builder()
        .id(split.getId())
        .createdBy(split.getCreatedBy())
        .title(split.getTitle())
        .note(split.getNote())
        .amount(split.getAmount())
        .currency(split.getCurrency())
        .createdAt(split.getCreatedAt())
        .updatedAt(split.getUpdatedAt())
        .splitDistributions(constructSplitDistribution(split.getTransactions()))
        .build();
  }

  private List<SplitDistribution> constructSplitDistribution(Set<Transaction> transactions) {
    if (CollectionUtils.isEmpty(transactions)) {
      return Collections.emptyList();
    }
    return transactions.stream()
        .map(transaction -> SplitDistribution
            .builder()
            .userId(transaction.getUserId())
            .paid(transaction.getPaid())
            .owed(transaction.getOwed())
            .build())
        .collect(Collectors.toList());
  }

  public Set<Split> findAllSplitsInvolvingUserId(Long userId) {
    return splitManager.findAllInvolvingUserId(userId);
  }

}
