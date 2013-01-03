package com.junit.mockurl.rule;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation, for use with the UrlRedirectRule, which signifies that requests for a particular
 * URL should be redirected to a described destination resource.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface TestUrl {
	/**
	 * @return The source URL to be redirected
	 */
	public String source();
	
	/**
	 * @return The name of the resource, relative to the test class, to be used as the destination
	 */
	public String dest();
}
