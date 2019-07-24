package edu.ncsu.csc.itrust.unit.bean;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.HCPLinkBean;

public class HCPLinkBeanTest extends TestCase {
	public void testGetDrug() throws Exception {
		HCPLinkBean hl = new HCPLinkBean();
		hl.setDrug("Penicillin");
		hl.setCode("blah");
		assertEquals("Penicillin", hl.getDrug());
		assertEquals("blah", hl.getCode());
	}
	
	public void testGetPresciberMID()
	{
		HCPLinkBean hl = new HCPLinkBean();
		hl.setPrescriberMID(900000000);
		assertEquals(900000000, hl.getPrescriberMID());
	}
	
	public void testChecked()
	{
		HCPLinkBean hl = new HCPLinkBean();
		hl.setChecked(true);
		assertTrue(hl.isChecked());
	}
}
