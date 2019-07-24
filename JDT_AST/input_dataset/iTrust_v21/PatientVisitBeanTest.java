package edu.ncsu.csc.itrust.unit.bean;

import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.beans.PatientVisitBean;
import junit.framework.TestCase;

/**
 */
public class PatientVisitBeanTest extends TestCase {
	
	PatientVisitBean p1;
	PatientBean pat;

	/**
	 * setUP
	 */
	protected void setUp() throws Exception {
		p1 = new PatientVisitBean();
		pat = new PatientBean();
	}
	
	/**
	 * testOVDate
	 */
	public void testOVDate(){
		p1.setLastOVDate("date");
		assertEquals("date", p1.getLastOVDate());
	}
	
	/**
	 * testPatientName
	 */
	public void testPatientName(){
		p1.setPatientName("name");
		assertEquals("name", p1.getPatientName());
	}
	
	/**
	 * testAddress
	 */
	public void testAddress(){
		p1.setAddress1("Address 1");
		p1.setAddress2("Address 2");
		
		assertEquals("Address 1", p1.getAddress1());
		assertEquals("Address 2", p1.getAddress2());
	}
	
	/**
	 * testPatient
	 */
	public void testPatient(){
		p1.setPatient(pat);
		
		assertEquals(pat, p1.getPatient());
		
	}

}
