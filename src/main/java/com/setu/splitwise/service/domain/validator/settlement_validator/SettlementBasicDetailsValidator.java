package com.setu.splitwise.service.domain.validator.settlement_validator;

import com.setu.splitwise.constant.Message;
import com.setu.splitwise.exception.ServerException;
import com.setu.splitwise.model.request.CreateSettlementRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class SettlementBasicDetailsValidator extends SettlementValidator {

  @Autowired
  public SettlementBasicDetailsValidator(
      SettlementUserDetailsValidator settlementUserDetailsValidator) {
    this.successor = settlementUserDetailsValidator;
  }

  public void validate(CreateSettlementRequest request) throws ServerException {
    if (request.getCreatedBy() == null
        || request.getCreditTo() == null
        || request.getDebitFrom() == null
        || request.getAmount() == null
        || request.getCurrency() == null
        || request.getCreatedAt() == null
        || request.getUpdatedAt() == null) {
      throw new ServerException(HttpStatus.BAD_REQUEST, Message.MANDATORY_PARAMETER_INVALID);
    }
    super.validate(request);
  }

}
