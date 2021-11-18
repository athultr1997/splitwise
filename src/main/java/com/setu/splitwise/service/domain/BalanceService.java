package com.setu.splitwise.service.domain;

import com.setu.splitwise.enums.BalanceType;
import com.setu.splitwise.model.persistence.Bill;
import com.setu.splitwise.model.response.GetBalanceResponse;
import com.setu.splitwise.model.response.GetBalanceResponse.BalanceDistribution;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BalanceService {

  @Autowired
  private UserService userService;

  @Autowired
  private BillService billService;

  public GetBalanceResponse getBalance(String userIdAsString) {
    // TODO userId check
    // TODO subtract payments
    Long userId = Long.valueOf(userIdAsString);
    Set<Bill> bills = billService.getBillsInvolvingUserId(userId);
    Map<Long, BigDecimal> userIdToAmountMap = new HashMap<>(10);
    BigDecimal totalAmount = BigDecimal.ZERO;
    BigDecimal amount;
    for (Bill bill : bills) {
      if (userId.equals(bill.getCreditTo())) {
        amount = userIdToAmountMap.getOrDefault(bill.getDebitFrom(), BigDecimal.ZERO);
        totalAmount = totalAmount.add(bill.getAmount());
        userIdToAmountMap.put(bill.getDebitFrom(), amount.add(bill.getAmount()));
      } else {
        amount = userIdToAmountMap.getOrDefault(bill.getCreditTo(), BigDecimal.ZERO);
        totalAmount = totalAmount.subtract(bill.getAmount());
        userIdToAmountMap.put(bill.getCreditTo(), amount.subtract(bill.getAmount()));
      }
    }
    return GetBalanceResponse
        .builder()
        .amount(totalAmount.abs())
        .balanceType(BalanceType.findBalanceType(totalAmount))
        .balanceDistributions(constructBalanceDistributions(userIdToAmountMap))
        .build();
  }

  private List<BalanceDistribution> constructBalanceDistributions(
      Map<Long, BigDecimal> userIdToAmountMap) {
    List<BalanceDistribution> balanceDistributions = new ArrayList<>();
    for (Entry<Long, BigDecimal> e : userIdToAmountMap.entrySet()) {
      BalanceType balanceType = BalanceType.findBalanceType(e.getValue());
      if (!BalanceType.SETTLED.equals(balanceType)) {
        BalanceDistribution balanceDistribution = BalanceDistribution
            .builder()
            .withUserId(e.getKey())
            .amount(e.getValue().abs())
            .balanceType(balanceType)
            .build();
        balanceDistributions.add(balanceDistribution);
      }
    }
    return balanceDistributions;
  }

}
