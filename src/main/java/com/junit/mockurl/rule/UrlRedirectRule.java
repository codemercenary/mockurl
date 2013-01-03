package com.junit.mockurl.rule;

import com.junit.mockurl.MockUrlFactory;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * Causes the configured URLs to be redirected for the duration of the test
 */
public class UrlRedirectRule extends RedirectRuleBase<UrlRedirectRule> {
	private final Map<URL, URL> redirectSet = new HashMap<>();

	/**
	 * Adds a new URL to the redirect set
	 * @param source The URL to be redirected, as a string
	 * @param dest The destination URL for the redirection 
	 * @return This, for chaining
	 * @throws MalformedURLException 
	 */
	public UrlRedirectRule add(String source, URL dest) {
		try {
			redirectSet.put(new URL(source), dest);
		} catch(MalformedURLException ex) {
			throw new RuntimeException(ex);
		}
		return this;
	}
	
	private void add(Class<?> parent, TestUrl testUrl) throws MalformedURLException {
		add(
			testUrl.source(),
			parent.getResource(testUrl.dest())
		);
	}
	
	public void setup() throws MalformedURLException, IOException {
		for(Map.Entry<URL, URL> entry : redirectSet.entrySet())
			MockUrlFactory.canUrl(entry.getKey(), entry.getValue());
	}
	
	public void teardown() {
		MockUrlFactory.clearCannedUrls();
	}
	
	@Override
	public Statement doApply(final Statement base, Description description) {
		final Class<?> clazz = description.getTestClass();
		
		try {
			TestUrl testUrl = clazz.getAnnotation(TestUrl.class);
			if(testUrl != null)
				add(clazz, testUrl);

			TestUrls testUrls = clazz.getAnnotation(TestUrls.class);
			if(testUrls != null)
				for(TestUrl cur : testUrls.value())
					add(clazz, cur);
		} catch(MalformedURLException ex) {
			throw new RuntimeException(ex);
		}
		
		try {
			TestUrl testUrl = description.getAnnotation(TestUrl.class);
			if(testUrl != null)
				add(clazz, testUrl);

			TestUrls testUrls = description.getAnnotation(TestUrls.class);
			if(testUrls != null)
				for(TestUrl cur : testUrls.value())
					add(clazz, cur);
		} catch(MalformedURLException ex) {
			throw new RuntimeException(ex);
		}
		
		return new Statement() {
			@Override
			public void evaluate() throws Throwable {
				setup();
				try {
					base.evaluate();
				} finally {
					teardown();
				}
			}
		};
	}
}
