package com.setu.splitwise.service.domain.validator.split_validator;

import com.setu.splitwise.exception.ServerException;
import com.setu.splitwise.model.request.CreateSplitRequest;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class SplitDistributionValidator extends SplitValidator {

  @Autowired
  public SplitDistributionValidator(SplitUserValidator splitUserValidator) {
    this.successor = splitUserValidator;
  }

  @Override
  public void validate(CreateSplitRequest request) throws ServerException {
    BigDecimal totalAmountPaid = BigDecimal.ZERO;
    for (CreateSplitRequest.SplitDistribution splitDistribution : request.getSplitDistributions()) {
      totalAmountPaid = totalAmountPaid.add(splitDistribution.getAmountPaid());
      if (splitDistribution.getUserId() == null) {
        throw new ServerException(HttpStatus.BAD_REQUEST, "");
      }
    }
    if (!totalAmountPaid.equals(request.getAmount())) {
      throw new ServerException(HttpStatus.BAD_REQUEST, "amount mismatch");
    }
    super.validate(request);
  }

}
