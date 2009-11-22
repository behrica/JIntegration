package org.jintegration;


import junit.framework.AssertionFailedError;
import org.junit.Rule;

public abstract class AbstractIntegrationTest {

    
	@Rule
	public CheckManualRunPropertyRule checkManulRunProperty = new CheckManualRunPropertyRule();

    private String currentTestName;
    private UserFeedBack userFeedback = new SwingUserFeedback();

    protected void assertManualTestPassed() {
        if (!userFeedback.hasTestPassed(currentTestName))
            throw new AssertionFailedError("User said test had failed.");
    }

    protected String askUserInput(final String message, final String defaultValue) {
        return userFeedback.askUserInput(message,defaultValue);
    }

    public void setCurrentTestName(String currentTestName) {
        this.currentTestName=currentTestName;
    }

}
