package com.junit.mockurl;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.IOUtils;

/**
 * A record of a collection of conversations on the same URL
 */
public class ConversationRecord implements Serializable {
	public class Conversation {
		private ByteArrayOutputStream request;
		private ByteArrayOutputStream response;
		private ByteArrayOutputStream responseErr;
		
		public final int i = conversations.size();
		
		public ByteArrayOutputStream getRequest() {
			if(request == null)
				request = new ByteArrayOutputStream();
			return request;
		}

		public ByteArrayOutputStream getResponse() {
			if(response == null)
				response = new ByteArrayOutputStream();
			return response;
		}

		public ByteArrayOutputStream getResponseErr() {
			if(responseErr == null)
				responseErr = new ByteArrayOutputStream();
			return responseErr;
		}
		
		public void serialize(OutputStream os) throws IOException {
			if(request != null) {
				IOUtils.write("<<< REQUEST " + i + "\n", os);
				IOUtils.write(request.toByteArray(), os);
				IOUtils.write("\n\n", os);
			}
			if(response != null) {
				IOUtils.write(">>> RESPONSE " + i + "\n", os);
				IOUtils.write(response.toByteArray(), os);
				IOUtils.write("\n\n", os);
			}
			if(responseErr != null) {
				IOUtils.write(">>> RESPONSE-ERR " + i + "\n", os);
				IOUtils.write(responseErr.toByteArray(), os);
				IOUtils.write("\n\n", os);
			}
		}
	}
	
	private final URL url;
	private final List<Conversation> conversations = new ArrayList<>();
	
	public ConversationRecord(URL url) {
		this.url = url;
	}
	
	public Conversation next() {
		final Conversation retVal = new Conversation();
		conversations.add(retVal);
		return retVal;
	}
	
	public void seralize(OutputStream os) throws IOException {
		for(Conversation conversation : conversations)
			conversation.serialize(os);
	}
}
