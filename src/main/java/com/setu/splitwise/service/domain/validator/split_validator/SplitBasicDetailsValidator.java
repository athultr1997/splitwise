package com.setu.splitwise.service.domain.validator.split_validator;

import com.setu.splitwise.exception.ServerException;
import com.setu.splitwise.model.request.CreateSplitRequest;
import com.setu.splitwise.service.domain.UserService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class SplitBasicDetailsValidator extends SplitValidator {

  @Autowired
  private UserService userService;

  @Autowired
  public SplitBasicDetailsValidator(SplitDistributionValidator splitDistributionValidator) {
    this.successor = splitDistributionValidator;
  }

  @Override
  public void validate(CreateSplitRequest request) throws ServerException {
    if (request.getAmount() == null
        || request.getSplitType() == null
        || request.getCreatedBy() == null
        || CollectionUtils.isEmpty(request.getSplitDistributions())) {
      throw new ServerException(HttpStatus.BAD_REQUEST, "");
    }
    super.validate(request);
  }

}
