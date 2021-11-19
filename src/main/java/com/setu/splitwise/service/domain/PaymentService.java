package com.setu.splitwise.service.domain;

import com.setu.splitwise.constant.Message;
import com.setu.splitwise.exception.ServerException;
import com.setu.splitwise.model.persistence.Payment;
import com.setu.splitwise.model.request.CreateSettlementRequest;
import com.setu.splitwise.model.response.CreateSettlementResponse;
import com.setu.splitwise.service.manager.PaymentManager;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

  @Autowired
  private PaymentManager paymentManager;

  @Autowired
  private UserService userService;

  public CreateSettlementResponse createSettlementPayment(
      CreateSettlementRequest request) throws ServerException {
    validate(request);
    Payment payment = Payment
        .builder()
        .createdBy(request.getCreatedBy())
        .debitFrom(request.getDebitFrom())
        .creditTo(request.getCreditTo())
        .amount(request.getAmount())
        .currency(request.getCurrency())
        .createdAt(request.getCreatedAt())
        .updatedAt(request.getUpdatedAt())
        .build();
    paymentManager.save(payment);
    return CreateSettlementResponse
        .builder()
        .id(payment.getId())
        .build();
  }

  private void validate(CreateSettlementRequest request) throws ServerException {
    if (request.getCreatedBy() == null
        || request.getCreditTo() == null
        || request.getDebitFrom() == null
        || request.getAmount() == null
        || request.getCurrency() == null
        || request.getCreatedAt() == null
        || request.getUpdatedAt() == null) {
      throw new ServerException(HttpStatus.BAD_REQUEST, Message.MANDATORY_PARAMETER_INVALID);
    }
    List<Long> userIds = request.getAllUserIds();
    if (request.getCreditTo().equals(request.getDebitFrom())
        || BigDecimal.ZERO.compareTo(request.getAmount()) > -1
        || userIds.size() < 2) {
      throw new ServerException(HttpStatus.BAD_REQUEST, Message.SETTLEMENT_CONTRAINTS_VIOLATED);
    }
    if (!userService.checkUserIdsExist(userIds)) {
      throw new ServerException(HttpStatus.BAD_REQUEST, Message.INVALID_USER_IDS);
    }
  }

}
