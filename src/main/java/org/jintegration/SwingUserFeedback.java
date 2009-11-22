package org.jintegration;

import javax.swing.*;
import java.util.concurrent.*;

public class SwingUserFeedback implements UserFeedBack {
     private static final int USERFEEDBACK_TIMEOUT_SECONDS = 60;
    
    @Override
    public String askUserInput(final String message, final String defaultValue) {
        Callable callable= new Callable() {
            public Object call() throws Exception {
                return javax.swing.JOptionPane.showInputDialog(message,defaultValue);
            }
        };

        return executeAsynchronous(callable,defaultValue);
    }

    @Override
    public boolean hasTestPassed(String currentTestName) {
       Callable askIfTestPassedCallable= new AskIfTestPassedCallable(currentTestName);
        String success=executeAsynchronous(askIfTestPassedCallable,null);
        return "true".equals(success);
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
     private class AskIfTestPassedCallable implements Callable {
         private String currentTestName;

         public AskIfTestPassedCallable(String currentTestName) {
             this.currentTestName = currentTestName;
         }

         public Object call() throws Exception {
            int popupResult = JOptionPane.showConfirmDialog(null, "Manual Test : " + currentTestName + "\n Did it pass ?", "Manual test result ",
                    JOptionPane.YES_NO_OPTION);
            if (popupResult==JOptionPane.OK_OPTION)
                return "true";
            else return "false";
        }
    }
}
