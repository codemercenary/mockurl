package com.junit.mockurl.rule;

import com.junit.mockurl.MockUrlFactory;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 *
 */
public abstract class RedirectRuleBase<T extends RedirectRuleBase> implements TestRule {
	private boolean enabled = true;
	protected boolean verbose = false;
	
	/**
	 * Disables this rule.  A convenience, prevents commenting from being needed.
	 * @return This, for chaining
	 */
	public T disable() {
		enabled = false;
		return (T)this;
	}
	
	/**
	 * Makes this rule 
	 * @return 
	 */
	public T verbose() {
		verbose = true;
		return (T)this;
	}
	
	@Override
	public Statement apply(Statement base, Description description) {
		if(!enabled)
			return base;
		
		final Statement subStatement = doApply(base, description);
		return new Statement() {
			@Override
			public void evaluate() throws Throwable {
				MockUrlFactory.setVerbose(verbose);
				subStatement.evaluate();
				if(verbose)
					MockUrlFactory.setVerbose(false);
			}
		};
	}
	
	/**
	 * Performs the actual application, called only when the rule is enabled.
	 * @param base
	 * @param description
	 * @return 
	 */
	public abstract Statement doApply(Statement base, Description description);
}
