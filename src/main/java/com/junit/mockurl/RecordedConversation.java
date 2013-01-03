package com.junit.mockurl;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;

/**
 *
 */
public class RecordedConversation {
	private static final Logger logger = Logger.getLogger(MockUrlFactory.class.getName());
	public final List<String> requests = new ArrayList<>();
	public final List<String> responses = new ArrayList<>();
	
	private int nextResponse = 0;
	
	/**
	 * Parses the passed canned conversation list
	 * @param is
	 * @throws IOException 
	 */
	public RecordedConversation(InputStream is) throws IOException {
		StringBuilder curBldr = null;
		List<StringBuilder> requestBuilders = new ArrayList<>();
		List<StringBuilder> responseBuilders = new ArrayList<>();
		
		for(String responseLine : IOUtils.readLines(is))
			if(responseLine.startsWith("<<< REQUEST")) {
				curBldr = new StringBuilder();
				requestBuilders.add(curBldr);
			} else if(responseLine.startsWith(">>> RESPONSE")) {
				curBldr = new StringBuilder();
				responseBuilders.add(curBldr);
			} else
				curBldr.append(responseLine).append("\n");
		
		for(StringBuilder bldr : responseBuilders)
			responses.add(bldr.toString());
	}
	
	/**
	 * Obtains the next response in this recorded conversation
	 * @return The response
	 */
	public String next() {
		logger.log(Level.INFO, "Returning response {0}", nextResponse);
		return nextResponse < responses.size() ? responses.get(nextResponse++) : "";
	}
}
