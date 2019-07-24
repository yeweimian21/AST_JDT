package edu.ncsu.csc.itrust.unit.bean;

import edu.ncsu.csc.itrust.beans.LabelBean;
import junit.framework.TestCase;

/**
 * Test for LabelBean
 */
public class LabelBeanTest extends TestCase {
	/**
	 * Test for a bean.
	 */
	public  void testBean()
	{
		LabelBean bean = new LabelBean();
		bean.setEntryID(1);
		bean.setPatientID(1);
		bean.setLabelName("Test");
		bean.setLabelColor("#FFFFFF");
		
		assertTrue(bean.getEntryID() == (long) 1);
		assertTrue(bean.getPatientID() == (long) 1);
		assertTrue(bean.getLabelName().equals("Test"));
		assertTrue(bean.getLabelColor().equals("#FFFFFF"));
	}
}
