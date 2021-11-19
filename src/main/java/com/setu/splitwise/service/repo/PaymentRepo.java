package com.setu.splitwise.service.repo;

import com.setu.splitwise.model.persistence.Payment;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepo extends JpaRepository<Payment, Long> {

  Set<Payment> findAllByDebitFromOrCreditTo(Long debitFrom, Long creditTo);

}
