package com.setu.splitwise.service.manager;

import com.setu.splitwise.model.persistence.Payment;
import com.setu.splitwise.service.repo.PaymentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentManager {

  @Autowired
  private PaymentRepo paymentRepo;

  public void save(Payment payment) {
    paymentRepo.save(payment);
  }

}
