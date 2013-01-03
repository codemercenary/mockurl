package com.junit.mockurl.connection;

import com.junit.mockurl.RecordedConversation;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 */
public class CannedHttpURLConnection extends MockHttpConnection {
	private final Logger logger = Logger.getLogger(CannedHttpURLConnection.class.getName());
	private final String response;
	
	public CannedHttpURLConnection(URL url, String response) {
		super(url);
		this.response = response;
	}
	
	@Override
	public String getResponse() {
		return response;
	}
}
