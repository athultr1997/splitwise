package com.setu.splitwise.controller;

import com.setu.splitwise.exception.ServerException;
import com.setu.splitwise.model.request.CreateSplitRequest;
import com.setu.splitwise.model.response.CreateSplitResponse;
import com.setu.splitwise.service.domain.SplitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SplitWiseController {

  @Autowired
  private SplitService splitService;

  @PostMapping(name = "split_create", path = "/split", produces = MediaType.APPLICATION_JSON_VALUE)
  public CreateSplitResponse createSplit(@RequestBody CreateSplitRequest request)
      throws ServerException {
    return splitService.createSplit(request);
  }

}
