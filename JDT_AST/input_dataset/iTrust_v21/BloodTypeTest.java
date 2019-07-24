package edu.ncsu.csc.itrust.unit.enums;

import edu.ncsu.csc.itrust.enums.BloodType;
import junit.framework.TestCase;

public class BloodTypeTest extends TestCase {
	public void testParse() throws Exception {
		assertEquals(BloodType.ABNeg, BloodType.parse("AB-"));
		assertEquals(BloodType.ONeg, BloodType.parse("O-"));
		assertEquals(BloodType.NS, BloodType.parse("N/S"));
		assertEquals(BloodType.NS, BloodType.parse("non-existent ethnicity"));
	}
}
