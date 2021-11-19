package com.setu.splitwise.service.domain.core;

import com.setu.splitwise.exception.ServerException;
import com.setu.splitwise.model.persistence.Split;
import com.setu.splitwise.model.request.CreateSplitRequest;
import com.setu.splitwise.model.response.CreateSplitResponse;
import com.setu.splitwise.model.response.GetSplitResponse;
import com.setu.splitwise.service.domain.creation.SplitCreationService;
import com.setu.splitwise.service.domain.show.SplitGetService;
import com.setu.splitwise.service.manager.SplitManager;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SplitService {

  @Autowired
  private SplitManager splitManager;

  @Autowired
  private SplitCreationService splitCreationService;

  @Autowired
  private SplitGetService splitGetService;

  public CreateSplitResponse createSplit(CreateSplitRequest request) throws ServerException {
    Split split = splitCreationService.create(request);
    return CreateSplitResponse
        .builder()
        .id(split.getId())
        .build();
  }

  public GetSplitResponse getSplitDetails(Long splitId) throws ServerException {
    return splitGetService.get(splitId);
  }

  public Set<Split> findAllSplitsInvolvingUserId(Long userId) {
    return splitManager.findAllInvolvingUserId(userId);
  }

}
