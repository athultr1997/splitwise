package com.setu.splitwise.service.manager;

import com.setu.splitwise.model.persistence.Bill;
import com.setu.splitwise.service.repo.BillRepo;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillManager {

  @Autowired
  private BillRepo billRepo;

  public Set<Bill> getBillsInvolvingUserId(Long userId) {
    return billRepo.findAllByDebitFromOrCreditTo(userId, userId);
  }

}
