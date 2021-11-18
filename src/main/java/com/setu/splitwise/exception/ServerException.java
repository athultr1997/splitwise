package com.setu.splitwise.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
public class ServerException extends Exception {

  private static final long serialVersionUID = -7478839340745881381L;

  private final HttpStatus statusCode;

  private final String message;

}
