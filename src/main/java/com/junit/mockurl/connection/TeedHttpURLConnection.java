package com.junit.mockurl.connection;

import com.junit.mockurl.MockUrlFactory;
import com.junit.mockurl.ConversationRecord;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.commons.io.input.TeeInputStream;
import org.apache.commons.io.output.TeeOutputStream;

/**
 *
 */
public class TeedHttpURLConnection extends HttpConnectionProxy {
	final ConversationRecord.Conversation convo;

	public TeedHttpURLConnection(URL url) throws IOException {
		super(
			url,
			(HttpURLConnection)
			new URL(
				url.getProtocol() + "-notee",
				url.getHost(),
				url.getPort(),
				url.getFile()
			).openConnection()
		);
		convo = MockUrlFactory.getConvoLogger(url).next();
	}

	@Override
	public OutputStream getOutputStream() throws IOException {
		return new TeeOutputStream(super.getOutputStream(), convo.getRequest());
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return new TeeInputStream(super.getInputStream(), convo.getResponse());
	}

	@Override
	public InputStream getErrorStream() {
		return new TeeInputStream(super.getErrorStream(), convo.getResponseErr());
	}
}
