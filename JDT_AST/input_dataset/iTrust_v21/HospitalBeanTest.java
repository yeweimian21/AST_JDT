package edu.ncsu.csc.itrust.unit.bean;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.HospitalBean;

/**
 */
public class HospitalBeanTest extends TestCase {

	/**
	 * testHospitalBean
	 * @throws Exception
	 */
	public void testHospitalBean() throws Exception {
		HospitalBean h = new HospitalBean();
		h.setHospitalID("id");
		h.setHospitalName("name");
		assertEquals("id", h.getHospitalID());
		assertEquals("name", h.getHospitalName());
		assertEquals(42, h.hashCode());
	}
	
	/**
	 * testFullConstrcutor
	 * @throws Exception
	 */
	public void testFullConstructor() throws Exception {
		HospitalBean h = new HospitalBean("id", "name", "address", "city", "ST", "12345-6789");
		assertEquals("id", h.getHospitalID());
		assertEquals("name", h.getHospitalName());
		assertEquals("address", h.getHospitalAddress());
		assertEquals("city", h.getHospitalCity());
		assertEquals("ST", h.getHospitalState());
		assertEquals("12345-6789", h.getHospitalZip());
		assertEquals(42, h.hashCode());
	}
}
