package com.setu.splitwise.scripts;

import com.setu.splitwise.model.persistence.User;
import com.setu.splitwise.service.domain.UserService;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class PopulateUserService {

  @Autowired
  private UserService userService;

  @EventListener(ApplicationReadyEvent.class)
  public void populate() {
    User user1 = User
        .builder()
        .username("django")
        .email("django@gmail.com")
        .build();
    User user2 = User
        .builder()
        .username("marty")
        .email("marty@gmail.com")
        .build();
    User user3 = User
        .builder()
        .username("naruto")
        .email("naruto@gmail.com")
        .build();
    User user4 = User
        .builder()
        .username("danjiro")
        .email("danjiro@gmail.com")
        .build();
    User user5 = User
        .builder()
        .username("alex")
        .email("alex@gmail.com")
        .build();
    List<User> users = Arrays.asList(user1, user2, user3, user4, user5);
    userService.saveAll(users);
  }

}
