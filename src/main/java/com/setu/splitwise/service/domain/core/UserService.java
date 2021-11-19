package com.setu.splitwise.service.domain.core;

import com.setu.splitwise.constant.Message;
import com.setu.splitwise.exception.ServerException;
import com.setu.splitwise.model.persistence.User;
import com.setu.splitwise.model.response.ExpenseOverview;
import com.setu.splitwise.model.response.ExpenseOverview.Expense;
import com.setu.splitwise.service.domain.aggregator.ExpenseOverviewService;
import com.setu.splitwise.service.manager.UserManager;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private UserManager userManager;

  @Autowired
  private ExpenseOverviewService expenseOverviewService;

  public void saveAll(List<User> users) {
    userManager.saveAll(users);
  }

  public void validate(Long userId) throws ServerException {
    if (!checkIfUserIdsExist(Collections.singletonList(userId))) {
      throw new ServerException(HttpStatus.BAD_REQUEST, Message.INVALID_USER_IDS);
    }
  }

  public boolean checkIfUserIdsExist(List<Long> userIds) {
    return userManager.checkUserIdsExist(userIds);
  }

  public ExpenseOverview getExpenseOverview(Long userId) throws ServerException {
    List<Expense> totalExpenses = expenseOverviewService.getOverview(userId);
    return ExpenseOverview
        .builder()
        .expenses(totalExpenses)
        .build();
  }

}
