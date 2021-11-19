package com.setu.splitwise.controller;

import com.setu.splitwise.exception.ServerException;
import com.setu.splitwise.model.response.ExpenseOverview;
import com.setu.splitwise.service.domain.core.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  @Autowired
  private UserService userService;

  /**
   * The expenses should be sorted in decreasing order of creation time.
   *
   * @param userId
   * @return
   * @throws ServerException
   */
  @GetMapping(name = "user_expense_overview", path = "/user/expense_overview/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ExpenseOverview getSplit(@PathVariable Long userId)
      throws ServerException {
    return userService.getExpenseOverview(userId);
  }


}
