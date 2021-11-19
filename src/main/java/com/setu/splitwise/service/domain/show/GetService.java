package com.setu.splitwise.service.domain.show;

import com.setu.splitwise.exception.ServerException;

public interface GetService<X, T> {

  X get(T t) throws ServerException;

}
