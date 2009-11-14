package org.jintegration;

import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;


public class CheckManualRunPropertyRule implements MethodRule {

	@Override
	public Statement apply(final Statement base, final FrameworkMethod method,
			final Object target) {

        ((AbstractIntegrationTest)target).setCurrentTestName(target.getClass().getName()+"."+method.getName());
		return new Statement() {

			@Override
			public void evaluate() throws Throwable {
				if ("true".equalsIgnoreCase(System.getProperty("runManualTests"))) {
					base.evaluate();
				} else {
					System.out.println(target.getClass().getName() + "."
							+ method.getName()
							+ "() not run. (pass -DrunManualTests=true to enable)");
				}

			}
		};
	}

}
