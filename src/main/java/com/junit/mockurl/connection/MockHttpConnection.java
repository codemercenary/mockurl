package com.junit.mockurl.connection;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import org.apache.commons.io.input.CharSequenceInputStream;
import org.apache.commons.io.output.NullOutputStream;

/**
 *
 */
public abstract class MockHttpConnection extends java.net.HttpURLConnection {
	public MockHttpConnection(URL url) {
		super(url);
	}

	@Override
	public void connect() {
	}

	@Override
	public void disconnect() {
	}

	@Override
	public boolean usingProxy() {
		return false;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return new CharSequenceInputStream(getResponse(), Charset.defaultCharset());
	}

	@Override
	public OutputStream getOutputStream() {
		return new NullOutputStream();
	}
	
	/**
	 * Obtains the response to be sent to the client for this request.
	 * 
	 * @return The response to be obtained
	 */
	public abstract String getResponse();
}
