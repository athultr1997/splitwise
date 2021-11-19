package com.setu.splitwise.service.domain.validator.split_validator;

import com.setu.splitwise.exception.ServerException;
import com.setu.splitwise.model.request.CreateSplitRequest;
import com.setu.splitwise.service.domain.validator.Validator;

public abstract class SplitValidator implements Validator<CreateSplitRequest> {

  protected SplitValidator successor;

  public void validate(CreateSplitRequest request) throws ServerException {
    if (successor != null) {
      successor.validate(request);
    }
  }

}
