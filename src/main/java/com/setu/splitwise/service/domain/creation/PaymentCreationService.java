package com.setu.splitwise.service.domain.creation;

import com.setu.splitwise.exception.ServerException;
import com.setu.splitwise.model.persistence.Payment;
import com.setu.splitwise.model.request.CreateSettlementRequest;
import com.setu.splitwise.service.domain.validator.settlement_validator.SettlementBasicDetailsValidator;
import com.setu.splitwise.service.domain.validator.settlement_validator.SettlementValidator;
import com.setu.splitwise.service.manager.PaymentManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentCreationService implements CreationService<Payment, CreateSettlementRequest> {

  private final SettlementValidator settlementValidator;

  @Autowired
  private PaymentManager paymentManager;

  @Autowired
  public PaymentCreationService(SettlementBasicDetailsValidator settlementBasicDetailsValidator) {
    this.settlementValidator = settlementBasicDetailsValidator;
  }

  @Override
  public Payment create(CreateSettlementRequest request) throws ServerException {
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
    return payment;
  }

  private void validate(CreateSettlementRequest request) throws ServerException {
    settlementValidator.validate(request);
  }

}
