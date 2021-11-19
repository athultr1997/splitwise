package com.setu.splitwise.service.manager;

import com.setu.splitwise.model.persistence.Payment;
import com.setu.splitwise.service.repo.PaymentRepo;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentManager {

  @Autowired
  private PaymentRepo paymentRepo;

  public void save(Payment payment) {
    paymentRepo.save(payment);
  }

  public Set<Payment> getPaymentsInvolvingUserId(Long userId) {
    return paymentRepo.findAllByDebitFromOrCreditTo(userId, userId);
  }
}
