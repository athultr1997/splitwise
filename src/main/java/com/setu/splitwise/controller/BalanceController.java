package com.setu.splitwise.controller;

import com.setu.splitwise.exception.ServerException;
import com.setu.splitwise.model.response.GetBalanceResponse;
import com.setu.splitwise.service.domain.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BalanceController {

  @Autowired
  private BalanceService balanceService;

  /**
   * Do not have to show balances that are settled with a user. Only show the ones that are lend or
   * owed.
   *
   * @param userId
   * @return
   * @throws ServerException
   */
  @GetMapping(name = "balance_get", path = "/balance/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public GetBalanceResponse getBalance(@PathVariable String userId)
      throws ServerException {
    return balanceService.getBalance(userId);
  }


}
