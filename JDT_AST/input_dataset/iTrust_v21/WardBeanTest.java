package edu.ncsu.csc.itrust.unit.bean;

import edu.ncsu.csc.itrust.beans.WardBean;
import junit.framework.TestCase;

/**
 * WardBeanTest
 */
public class WardBeanTest extends TestCase {
	
	WardBean testBean;
	WardBean testBean2;
	WardBean testBean3;
	
	/**
	 * setUp
	 */
	public void setUp(){
		testBean = new WardBean(0, "", 0);
		testBean2 = new WardBean(0, "", 0);
		testBean3 = new WardBean(0, "", 0);
	}
	
	/**
	 * testWardID
	 */
	public void testWardID(){
		testBean.setWardID(1);
		
		assertEquals(testBean.getWardID(), 1L);
	}
	
	/**
	 * testSpecialty
	 */
	public void testSpecialty(){
		testBean.setRequiredSpecialty("special");
		
		assertEquals(testBean.getRequiredSpecialty(), "special");
	}
	
	/**
	 * testInHospital
	 */
	public void testInHospital(){
		testBean.setInHospital(1);
		
		assertEquals(testBean.getInHospital(), 1L);
	}
	
	/**
	 * testEquals
	 */
	public void testEquals(){
		assertTrue(testBean2.equals(testBean3));
	}

}
