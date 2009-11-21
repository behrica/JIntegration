package org.jintegration;



import org.junit.Rule;

import javax.swing.*;
import java.util.concurrent.*;

public abstract class AbstractIntegrationTest {

    
	@Rule
	public CheckManualRunPropertyRule checkManulRunProperty = new CheckManualRunPropertyRule();

    private String currentTestName;
    private static final int USERFEEDBACK_TIMEOUT_SECONDS = 60;

    protected void assertManualTestPassed() {
        Callable askIfTestPassedCallable= new AskIfTestPassedCallable();
        executeAsynchronous(askIfTestPassedCallable,null);


    }

    protected String askUserInput(final String message, final String defaultValue) {
        Callable callable= new Callable() {
            public Object call() throws Exception {
                return javax.swing.JOptionPane.showInputDialog(message,defaultValue);
            }
        };

        return executeAsynchronous(callable,defaultValue);

    }

    private String executeAsynchronous(Callable callable,final String defaultValue) {
        Future<String> future= Executors.newSingleThreadExecutor().submit(callable);
        try {
            return future.get(USERFEEDBACK_TIMEOUT_SECONDS, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
           throw new RuntimeException("Getting user input failed: ",e);
        } catch (ExecutionException e) {
           throw new RuntimeException("Getting user input failed: ",e);
        } catch (TimeoutException e) {
          if (defaultValue!=null)
            return defaultValue;
          throw new RuntimeException("Getting user input timed out after 10s: ",e);
        }
    }

    public void setCurrentTestName(String currentTestName) {
        this.currentTestName=currentTestName;
    }

    private class AskIfTestPassedCallable implements Callable {

        public Object call() throws Exception {
            org.hamcrest.MatcherAssert.assertThat(JOptionPane.showConfirmDialog(null, "Manual Test : "+currentTestName+"\n Did it pass ?", "Manual test result ",
            JOptionPane.YES_NO_OPTION), org.hamcrest.Matchers.is(JOptionPane.OK_OPTION));
            return null;
        }
    }
}
