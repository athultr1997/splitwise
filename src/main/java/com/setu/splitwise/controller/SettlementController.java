package com.setu.splitwise.controller;

import com.setu.splitwise.exception.ServerException;
import com.setu.splitwise.model.request.CreateSettlementRequest;
import com.setu.splitwise.model.response.CreateSettlementResponse;
import com.setu.splitwise.service.domain.core.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SettlementController {

  @Autowired
  private PaymentService paymentService;

  @PostMapping(name = "settlement_create", path = "/settlement", produces = MediaType.APPLICATION_JSON_VALUE)
  public CreateSettlementResponse createSettlement(@RequestBody CreateSettlementRequest request)
      throws ServerException {
    return paymentService.createSettlementPayment(request);
  }

}
