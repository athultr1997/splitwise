package com.setu.splitwise.service.domain;

import com.setu.splitwise.model.persistence.User;
import com.setu.splitwise.service.manager.UserManager;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private UserManager userManager;

  public void saveAll(List<User> users) {
    userManager.saveAll(users);
  }

  public boolean checkUserIdsExist(List<Long> userIds) {
    return userManager.checkUserIdsExist(userIds);
  }

}
