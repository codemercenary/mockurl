package com.junit.mockurl.rule;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface TestUrls {
	TestUrl[] value();
}
