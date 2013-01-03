package com.junit.mockurl.rule;

import com.junit.mockurl.ConversationRecord;
import com.junit.mockurl.MockUrlFactory;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * Allows a user to monitor all of the teed conversations recorded during a test.  The tee rule
 * will automatically start teeing before each test run, and stop it afterwards.  Conversations
 * recorded during the test are automatically flushed when the test is completed.
 */
public class UrlTeeRule extends RedirectRuleBase<UrlTeeRule> {
	public static class SystemOutSink implements IConversationSink {
		private static final Logger logger = Logger.getLogger(SystemOutSink.class.getName());
		
		@Override
		public void notify(ConversationRecord conversation) {
			try {
				conversation.seralize(System.out);
			} catch(IOException ex) {
				logger.log(Level.SEVERE, null, ex);
			}
		}
	}
	
	private final IConversationSink[] listeners;
	
	/**
	 * Constructs a new rule with the specified listeners.
	 * 
	 * @param listeners 
	 */
	public UrlTeeRule(IConversationSink... listeners) {
		this.listeners = listeners;
	}
	
	private void setup() {
		MockUrlFactory.startTee();
	}
	
	private void notifyListeners() {
		MockUrlFactory.stopTee();
		for(ConversationRecord record : MockUrlFactory.loggers.values())
			for(IConversationSink listener : listeners)
				listener.notify(record);
		MockUrlFactory.loggers.clear();
	}
	
	@Override
	public Statement doApply(final Statement base, Description description) {
		return new Statement() {
			@Override
			public void evaluate() throws Throwable {
				setup();
				try {
					base.evaluate();
				} finally {
					notifyListeners();
				}
			}
		};
	}
}
