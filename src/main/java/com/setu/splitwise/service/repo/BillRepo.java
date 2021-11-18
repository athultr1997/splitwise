package com.setu.splitwise.service.repo;

import com.setu.splitwise.model.persistence.Bill;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillRepo extends JpaRepository<Bill, Long> {

  Set<Bill> findAllByDebitFromOrCreditTo(Long debitFrom, Long creditTo);

}
