package com.setu.splitwise.service.domain.validator.settlement_validator;

import com.setu.splitwise.constant.Message;
import com.setu.splitwise.exception.ServerException;
import com.setu.splitwise.model.request.CreateSettlementRequest;
import java.math.BigDecimal;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class SettlementAmountValidator extends SettlementValidator {

  public SettlementAmountValidator() {
    this.successor = null;
  }

  public void validate(CreateSettlementRequest request) throws ServerException {
    if (request.getCreditTo().equals(request.getDebitFrom())
        || BigDecimal.ZERO.compareTo(request.getAmount()) > -1) {
      throw new ServerException(HttpStatus.BAD_REQUEST, Message.SETTLEMENT_CONSTRAINTS_VIOLATED);
    }
    super.validate(request);
  }

}
