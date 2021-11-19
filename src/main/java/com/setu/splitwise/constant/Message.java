package com.setu.splitwise.constant;

public class Message {

  public static final String INVALID_USER_IDS = "userIds are invalid";

  public static final String INVALID_AMOUNT = "amount is invalid";

  public static final String MANDATORY_PARAMETER_INVALID = "one or more mandatory parameters are invalid";

  public static final String SETTLEMENT_CONSTRAINTS_VIOLATED = "one or more constraints are violated";

  public static final String RESOURCE_NOT_FOUND = "resource not found";

  public static final String GREEDY_SETTLEMENT_RECURSION_THRESHOLD_REACHED = "recursion threshold reached for greedy settlement algorithm";

  private Message() {
    throw new IllegalStateException("Message is a utility class");
  }

}
