package edu.ncsu.csc.itrust.selenium;

import org.openqa.selenium.htmlunit.HtmlUnitDriver;

/**
 * Custom implementation of an HtmlUnitDriver
 * that does not report js errors.
 */
public class Driver extends HtmlUnitDriver {
	/**
	 * Construct a new driver and disable script error reporting.
	 */
	public Driver() {
		super();
		getWebClient().setThrowExceptionOnScriptError(false);
	}
}
