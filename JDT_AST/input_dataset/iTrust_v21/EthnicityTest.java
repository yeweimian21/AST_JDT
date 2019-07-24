package edu.ncsu.csc.itrust.unit.enums;

import edu.ncsu.csc.itrust.enums.Ethnicity;
import junit.framework.TestCase;

public class EthnicityTest extends TestCase {
	public void testParse() throws Exception {
		assertEquals(Ethnicity.Caucasian, Ethnicity.parse("Caucasian"));
		assertEquals(Ethnicity.AfricanAmerican, Ethnicity.parse("African American"));
		assertEquals(Ethnicity.NotSpecified, Ethnicity.parse("Not Specified"));
		assertEquals(Ethnicity.NotSpecified, Ethnicity.parse("non-existent ethnicity"));
	}
}
