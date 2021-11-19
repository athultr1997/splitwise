package com.setu.splitwise.service.domain.validator.settlement_validator;

import com.setu.splitwise.constant.Message;
import com.setu.splitwise.exception.ServerException;
import com.setu.splitwise.model.request.CreateSettlementRequest;
import com.setu.splitwise.service.domain.core.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class SettlementUserDetailsValidator extends SettlementValidator {

  @Autowired
  private UserService userService;

  @Autowired
  public SettlementUserDetailsValidator(SettlementAmountValidator settlementAmountValidator) {
    this.successor = settlementAmountValidator;
  }

  public void validate(CreateSettlementRequest request) throws ServerException {
    List<Long> userIds = request.getAllUserIds();
    if (userIds.size() < 2 || !userService.checkIfUserIdsExist(userIds)) {
      throw new ServerException(HttpStatus.BAD_REQUEST, Message.INVALID_USER_IDS);
    }
    super.validate(request);
  }

}
