package org.jintegration;

import org.junit.Test;


public class ATest extends AbstractIntegrationTest{

    @Test
    public void test() {
        String url=askUserInput("url","http://default");
        assertManualTestPassed();

    }
}
