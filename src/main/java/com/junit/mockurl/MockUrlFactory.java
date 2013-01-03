package com.junit.mockurl;

import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 */
public class MockUrlFactory {
	public static final Map<URL, ConversationRecord> loggers = new HashMap<>();
	
	private static final TeedHttpStreamHandler httpTeedHandler = new TeedHttpStreamHandler();
	private static final TeedHttpsStreamHandler httpsTeedHandler = new TeedHttpsStreamHandler();
	
	private static final URLStreamHandler httpHandler = new sun.net.www.protocol.http.Handler();
	private static final URLStreamHandler httpsHandler = new sun.net.www.protocol.https.Handler();
	
	private static final Map<URL, RecordedConversation> conversations = new HashMap<>();
	private static final Logger logger = Logger.getLogger(MockUrlFactory.class.getName());
	private static final Level defaultLevel = Level.WARNING;
	
	static {
		URL.setURLStreamHandlerFactory(
			new URLStreamHandlerFactory() {
				@Override
				public URLStreamHandler createURLStreamHandler(final String protocol) {
					switch(protocol) {
					case "http":
						return httpTeedHandler;
					case "https":
						return httpsTeedHandler;
					case "http-notee":
						return httpHandler;
					case "https-notee":
						return httpsHandler;
					}
					return null;
				}
			}
		);
		logger.setLevel(defaultLevel);
	}
	
	/**
	 * Causes requests for the specified URL to be sourced from the specified resource.
	 * 
	 * @param url The web URL to be redirected
	 * @param resourceUrl The resource containing a sequence of responses to be sent back
	 * @return A modified URL that, when opened with openConnection, will behave like an HTTP
	 * connection returning a static file.
	 */
	public static void canUrl(URL can, URL resourceUrl) throws MalformedURLException, IOException {
		try(InputStream is = resourceUrl.openStream()) {
			conversations.put(
				can,
				new RecordedConversation(is)
			);
		}
	}
	
	public static void clearCannedUrls() {
		conversations.clear();
	}
	
	public static RecordedConversation getRecordedConveration(URL name) {
		return conversations.get(name);
	}
	
	/**
	 * Creates a conversation logger for the specified URL
	 * @param url The URL on which a logger is to be created
	 * @return The logger
	 * @throws MalformedURLException 
	 */
	public static ConversationRecord getConvoLogger(URL url) throws MalformedURLException {
		switch(url.getProtocol())
		{
		case "http":
		case "https":
			ConversationRecord retVal = loggers.get(url);
			if(retVal == null) {
				retVal = new ConversationRecord(url);
				loggers.put(url, retVal);
			}
			return retVal;
		}
		throw new MalformedURLException("Cannot create a teed URL for any protocol except http and https");
	}
	
	/**
	 * Starts teeing in all threads in this JVM should be teed.  All conversations recovered during
	 * this operation will wind up being copied to the loggers instance, obtainable by calling
	 * getLoggers.
	 */
	public static void startTee() {
		httpTeedHandler.isTeeing = true;
		httpsTeedHandler.isTeeing = true;
	}
	
	/**
	 * Stops teeing connections in this JVM, returning the conversations as
	 * logged so far.
	 */
	public static void stopTee() {
		httpTeedHandler.isTeeing = false;
		httpsTeedHandler.isTeeing = false;
	}
	
	/**
	 * @return A map of requested URLs to the conversations logged from those URLs.
	 */
	public static Map<URL, ConversationRecord> getLoggers() {
		return loggers;
	}
	
	/**
	 * @return True if the factory is currently verbose
	 */
	public static boolean isVerbose() {
		return logger.getLevel() != defaultLevel;
	}
	
	/**
	 * Sets the verbosity flag for logging purposes.
	 * @param verbose 
	 */
	public static void setVerbose(boolean verbose) {
		logger.setLevel(verbose ? Level.INFO : defaultLevel);
	}
}
