package com.junit.mockurl;

import com.junit.mockurl.connection.CannedHttpURLConnection;
import com.junit.mockurl.connection.TeedHttpURLConnection;
import java.io.IOException;
import java.net.*;
import java.util.HashSet;
import java.util.Set;

/**
 *
 */
public class TeedHttpsStreamHandler extends sun.net.www.protocol.https.Handler {
	public boolean isTeeing = false;
	
	@Override
	protected URLConnection openConnection(URL url) throws IOException {
		try {
			// Is the URL specifically one that we are canning?
			RecordedConversation convo = MockUrlFactory.getRecordedConveration(url);
			return
				convo != null ?
				new CannedHttpURLConnection(url, convo.next()) :
				isTeeing ?
				new TeedHttpURLConnection(url) :
				super.openConnection(url);
		} catch(MalformedURLException ex) {
			throw new RuntimeException(ex);
		}
	}
}
