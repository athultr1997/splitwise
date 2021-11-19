package com.setu.splitwise.constant;

public class Message {

  public static final String INVALID_USER_IDS = "userIds are invalid";

  public static final String INVALID_AMOUNT = "amount is invalid";

  public static final String MANDATORY_PARAMETER_INVALID = "one or more mandatory parameters are invalid";

  public static final String SETTLEMENT_CONTRAINTS_VIOLATED = "one or more constraints are violated";

  private Message() {
    throw new IllegalStateException("Message is a utility class");
  }

}
