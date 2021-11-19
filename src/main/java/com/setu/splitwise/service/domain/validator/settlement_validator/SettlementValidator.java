package com.setu.splitwise.service.domain.validator.settlement_validator;

import com.setu.splitwise.exception.ServerException;
import com.setu.splitwise.model.request.CreateSettlementRequest;
import com.setu.splitwise.service.domain.validator.Validator;

public abstract class SettlementValidator implements Validator<CreateSettlementRequest> {

  protected SettlementValidator successor;

  @Override
  public void validate(CreateSettlementRequest request) throws ServerException {
    if (successor != null) {
      successor.validate(request);
    }
  }

}
