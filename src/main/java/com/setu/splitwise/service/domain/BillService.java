package com.setu.splitwise.service.domain;

import com.setu.splitwise.model.persistence.Bill;
import com.setu.splitwise.service.manager.BillManager;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillService {

  @Autowired
  private BillManager billManager;

  public Set<Bill> getBillsInvolvingUserId(Long userId) {
    return billManager.getBillsInvolvingUserId(userId);
  }

}
