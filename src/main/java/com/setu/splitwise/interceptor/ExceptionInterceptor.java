package com.setu.splitwise.interceptor;

import com.setu.splitwise.exception.ServerException;
import com.setu.splitwise.model.response.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ExceptionInterceptor {

  private static final Logger logger = LoggerFactory.getLogger(ExceptionInterceptor.class);

  @ExceptionHandler({ServerException.class, Exception.class})
  public final ResponseEntity<ErrorResponse> handleException(Exception ex, WebRequest request) {
    if (ex instanceof ServerException) {
      ServerException serverException = (ServerException) ex;
      return handleError(serverException);
    }
    ServerException serverException = new ServerException(HttpStatus.INTERNAL_SERVER_ERROR,
        "Some issue with server");
    return handleError(serverException);
  }

  private ResponseEntity<ErrorResponse> handleError(ServerException serverException) {
    ErrorResponse errorResponse = new ErrorResponse(serverException.getMessage());
    HttpHeaders headers = new HttpHeaders();
    logger.error(serverException.getMessage(), serverException);
    return new ResponseEntity<>(errorResponse, headers, serverException.getStatusCode());
  }

}
