package org.jintegration;


public interface UserFeedBack {
    String askUserInput(String message, String defaultValue);

    boolean hasTestPassed(String currentTestName);
}
