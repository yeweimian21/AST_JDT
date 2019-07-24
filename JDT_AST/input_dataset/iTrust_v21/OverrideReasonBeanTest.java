package edu.ncsu.csc.itrust.unit.bean;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.OverrideReasonBean;

public class OverrideReasonBeanTest extends TestCase {

	private OverrideReasonBean orb;

	public void testPrescriptionEquals() throws Exception {
		orb = new OverrideReasonBean();
		orb.setDescription("blah");
		orb.setORCode("00001");
		orb.setID(1);
		orb.setPresID(2);
		
		OverrideReasonBean orb2 = new OverrideReasonBean();
		orb2.setDescription("blah");
		orb2.setORCode("00001");
		orb2.setID(1);
		orb2.setPresID(2);
		assertTrue(orb2.equals(orb));
	}
}
