package edu.ncsu.csc.itrust.unit.bean;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.TelemedicineBean;

public class TelemedicineBeanTest extends TestCase {
	TelemedicineBean tBean;
	@Override
	protected void setUp() throws Exception {
		tBean = new TelemedicineBean();
	}

	public void testHeight() throws Exception {
		tBean.setHeightAllowed(true);
		assertTrue(tBean.isHeightAllowed());
	}
	
	public void testWeight() throws Exception {
		tBean.setWeightAllowed(false);
		assertTrue(!tBean.isWeightAllowed());
	}
	
	public void testGlucoseLevel() throws Exception {
		tBean.setGlucoseLevelAllowed(true);
		assertTrue(tBean.isGlucoseLevelAllowed());
	}
}
