package com.setu.splitwise.service.domain.core;

import com.setu.splitwise.exception.ServerException;
import com.setu.splitwise.model.persistence.Payment;
import com.setu.splitwise.model.request.CreateSettlementRequest;
import com.setu.splitwise.model.response.CreateSettlementResponse;
import com.setu.splitwise.service.domain.creation.PaymentCreationService;
import com.setu.splitwise.service.manager.PaymentManager;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

  @Autowired
  private PaymentManager paymentManager;

  @Autowired
  private PaymentCreationService paymentCreationService;

  public Set<Payment> getPaymentsInvolvingUserId(Long userId) {
    return paymentManager.getPaymentsInvolvingUserId(userId);
  }

  public CreateSettlementResponse createSettlementPayment(
      CreateSettlementRequest request) throws ServerException {
    Payment payment = paymentCreationService.create(request);
    return CreateSettlementResponse
        .builder()
        .id(payment.getId())
        .build();
  }

}
