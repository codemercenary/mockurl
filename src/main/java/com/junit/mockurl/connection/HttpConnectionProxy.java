package com.junit.mockurl.connection;

import java.io.*;
import java.net.*;
import java.security.Permission;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.input.TeeInputStream;
import org.apache.commons.io.output.TeeOutputStream;

/**
 *
 */
public class HttpConnectionProxy extends HttpURLConnection {
	protected final HttpURLConnection conn;
	
	public HttpConnectionProxy(URL url, HttpURLConnection conn) {
		super(url);
		this.conn = conn;
	}
	
	@Override
	public void connect() throws IOException {
		conn.connect();
	}

	@Override
	public boolean usingProxy() {
		return conn.usingProxy();
	}

	@Override
	public void disconnect() {
		conn.disconnect();
	}

	@Override
	public OutputStream getOutputStream() throws IOException {
		return conn.getOutputStream();
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return conn.getInputStream();
	}

	@Override
	public InputStream getErrorStream() {
		return conn.getErrorStream();
	}

	@Override
	public String getHeaderFieldKey(int n) {
		return conn.getHeaderFieldKey(n);
	}

	@Override
	public void setFixedLengthStreamingMode(int contentLength) {
		conn.setFixedLengthStreamingMode(contentLength);
	}

	@Override
	public void setFixedLengthStreamingMode(long contentLength) {
		conn.setFixedLengthStreamingMode(contentLength);
	}

	@Override
	public void setChunkedStreamingMode(int chunklen) {
		conn.setChunkedStreamingMode(chunklen);
	}

	@Override
	public String getHeaderField(int n) {
		return conn.getHeaderField(n);
	}

	@Override
	public void setInstanceFollowRedirects(boolean followRedirects) {
		conn.setInstanceFollowRedirects(followRedirects);
	}

	@Override
	public boolean getInstanceFollowRedirects() {
		return conn.getInstanceFollowRedirects();
	}

	@Override
	public void setRequestMethod(String method) throws ProtocolException {
		conn.setRequestMethod(method);
	}

	@Override
	public String getRequestMethod() {
		return conn.getRequestMethod();
	}

	@Override
	public int getResponseCode() throws IOException {
		return conn.getResponseCode();
	}

	@Override
	public String getResponseMessage() throws IOException {
		return conn.getResponseMessage();
	}

	@Override
	public long getHeaderFieldDate(String name, long Default) {
		return conn.getHeaderFieldDate(name, Default);
	}

	@Override
	public Permission getPermission() throws IOException {
		return conn.getPermission();
	}
	
	@Override
    public void setConnectTimeout(int timeout) {
		conn.setConnectTimeout(timeout);
    }

	@Override
    public int getConnectTimeout() {
        return conn.getConnectTimeout();
    }

	@Override
    public void setReadTimeout(int timeout) {
        conn.setReadTimeout(timeout);
    }

	@Override
    public int getReadTimeout() {
        return conn.getReadTimeout();
    }

	@Override
    public URL getURL() {
        return conn.getURL();
    }

	@Override
    public int getContentLength() {
		return conn.getContentLength();
    }

	@Override
    public long getContentLengthLong() {
        return conn.getContentLengthLong();
    }

	@Override
    public String getContentType() {
        return conn.getContentType();
    }

	@Override
    public String getContentEncoding() {
        return conn.getContentEncoding();
    }

	@Override
    public long getExpiration() {
        return conn.getExpiration();
    }

	@Override
    public long getDate() {
        return conn.getDate();
    }

	@Override
    public long getLastModified() {
        return conn.getLastModified();
    }

	@Override
    public String getHeaderField(String name) {
        return conn.getHeaderField(name);
    }

	@Override
    public Map<String,List<String>> getHeaderFields() {
        return conn.getHeaderFields();
    }

	@Override
    public int getHeaderFieldInt(String name, int Default) {
		return conn.getHeaderFieldInt(name, Default);
    }

	@Override
    public long getHeaderFieldLong(String name, long Default) {
		return conn.getHeaderFieldLong(name, Default);
    }

	@Override
    public Object getContent() throws IOException {
		return conn.getContent();
    }

	@Override
    public Object getContent(Class[] classes) throws IOException {
		return conn.getContent(classes);
    }

	@Override
    public String toString() {
        return conn.toString();
    }

	@Override
    public void setDoInput(boolean doinput) {
		conn.setDoInput(doinput);
    }

	@Override
    public boolean getDoInput() {
        return conn.getDoInput();
    }
	
	@Override
	public void setDoOutput(boolean dooutput) {
		conn.setDoOutput(dooutput);
	}
	
	@Override
	public boolean getDoOutput() {
		return conn.getDoOutput();
	}

	@Override
    public void setAllowUserInteraction(boolean allowuserinteraction) {
		conn.setAllowUserInteraction(allowuserinteraction);
    }

	@Override
    public boolean getAllowUserInteraction() {
        return conn.getAllowUserInteraction();
    }

	@Override
    public void setUseCaches(boolean usecaches) {
		conn.setUseCaches(usecaches);
    }

	@Override
    public boolean getUseCaches() {
        return conn.getUseCaches();
    }

	@Override
    public void setIfModifiedSince(long ifmodifiedsince) {
		conn.setIfModifiedSince(ifmodifiedsince);
    }

	@Override
    public long getIfModifiedSince() {
        return conn.getIfModifiedSince();
    }

	@Override
    public boolean getDefaultUseCaches() {
        return conn.getDefaultUseCaches();
    }

	@Override
    public void setDefaultUseCaches(boolean defaultusecaches) {
        conn.setDefaultUseCaches(defaultusecaches);
    }

	@Override
    public void setRequestProperty(String key, String value) {
		conn.setRequestProperty(key, value);
    }

	@Override
    public void addRequestProperty(String key, String value) {
		conn.addRequestProperty(key, value);
    }

	@Override
    public String getRequestProperty(String key) {
		return conn.getRequestProperty(key);
    }

	@Override
    public Map<String,List<String>> getRequestProperties() {
		return conn.getRequestProperties();
    }
}
