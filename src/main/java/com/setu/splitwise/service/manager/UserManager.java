package com.setu.splitwise.service.manager;

import com.setu.splitwise.model.persistence.User;
import com.setu.splitwise.service.repo.UserRepository;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserManager {

  @Autowired
  private UserRepository userRepository;

  public void save(User user) {
    userRepository.save(user);
  }

  public void saveAll(List<User> users) {
    userRepository.saveAll(users);
  }

  public boolean checkUserIdsExist(List<Long> userIds) {
    if (CollectionUtils.isEmpty(userIds)) {
      return false;
    }
    return userRepository.existsByIdIn(userIds);
  }

}
