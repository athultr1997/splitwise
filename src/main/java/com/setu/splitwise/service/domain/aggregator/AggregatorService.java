package com.setu.splitwise.service.domain.aggregator;

import com.setu.splitwise.exception.ServerException;

public interface AggregatorService<X, T> {

  X getOverview(T t) throws ServerException;

}
