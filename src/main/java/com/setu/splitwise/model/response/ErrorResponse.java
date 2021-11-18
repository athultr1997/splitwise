package com.setu.splitwise.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse implements Response {

  private static final long serialVersionUID = -1350286753434022565L;

  private String message;

}
