package com.setu.splitwise.service.domain.validator;

import com.setu.splitwise.exception.ServerException;

public interface Validator<T> {

  void validate(T t) throws ServerException;

}
