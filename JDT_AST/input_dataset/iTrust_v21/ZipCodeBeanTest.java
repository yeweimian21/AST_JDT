package edu.ncsu.csc.itrust.unit.bean;

import edu.ncsu.csc.itrust.beans.ZipCodeBean;
import edu.ncsu.csc.itrust.enums.State;
import junit.framework.TestCase;

/**
 * Test for ZipCodeBean
 */
public class ZipCodeBeanTest extends TestCase
{
	/**
	 * Test for a bean.
	 */
	public  void testBean()
	{
		ZipCodeBean bean = new ZipCodeBean();
		bean.setCity("Wake Forest");
		bean.setFullState("North Carolina");
		bean.setState(State.NC);
		bean.setLatitude("69.5");
		bean.setLongitude("35.5");
		bean.setZip("27587");
		
		assertTrue(bean.getCity().equals("Wake Forest"));
		assertTrue(bean.getFullState().equals("North Carolina"));
		assertTrue(bean.getLatitude().equals("69.5"));
		assertTrue(bean.getLongitude().equals("35.5"));
		assertTrue(bean.getZip().equals("27587"));
		assertTrue(bean.getState() == State.NC);
	}
}
