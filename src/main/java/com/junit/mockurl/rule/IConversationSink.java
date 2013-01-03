package com.junit.mockurl.rule;

import com.junit.mockurl.ConversationRecord;

/**
 *
 */
public interface IConversationSink {
	public void notify(ConversationRecord conversation);
}
