package com.setu.splitwise.service.domain.validator.split_validator;

import com.setu.splitwise.constant.Message;
import com.setu.splitwise.exception.ServerException;
import com.setu.splitwise.model.request.CreateSplitRequest;
import com.setu.splitwise.service.domain.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class SplitUserValidator extends SplitValidator {

  @Autowired
  private UserService userService;

  @Autowired
  public SplitUserValidator() {
    this.successor = null;
  }

  @Override
  public void validate(CreateSplitRequest request) throws ServerException {
    List<Long> userIds = request.getAllUserIds();
    if (!userService.checkUserIdsExist(userIds)) {
      throw new ServerException(HttpStatus.BAD_REQUEST, Message.INVALID_USER_IDS);
    }
    super.validate(request);
  }

}
