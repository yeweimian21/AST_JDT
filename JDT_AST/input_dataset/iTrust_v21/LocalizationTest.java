package edu.ncsu.csc.itrust.unit;

import java.util.Locale;

import edu.ncsu.csc.itrust.Localization;
import junit.framework.TestCase;



public class LocalizationTest extends TestCase{

	public void testLocalization() {
		Locale l = Localization.instance().getCurrentLocale();
		assert(l != null);
	}

	
	
}
