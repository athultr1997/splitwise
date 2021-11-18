package com.setu.splitwise.constant;

public class Message {

  public static final String INVALID_USER_IDS = "userIds are invalid";

  public static final String INVALID_AMOUNT = "amount is invalid";

  private Message() {
    throw new IllegalStateException("Message is a utility class");
  }

}
