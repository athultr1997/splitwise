package com.setu.splitwise.service.domain.creation;

import com.setu.splitwise.exception.ServerException;

public interface CreationService<X, T> {

  X create(T t) throws ServerException;

}
