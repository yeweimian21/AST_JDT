package edu.ncsu.csc.itrust.unit.bean;

import edu.ncsu.csc.itrust.beans.HCPVisitBean;
import junit.framework.TestCase;

public class HCPVisitBeanTest extends TestCase {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testHashCode() {
		HCPVisitBean a = new HCPVisitBean();
		a.setDesignated(true);
		a.setHCPAddr("abc");
		a.setHCPMID(1);
		a.setHCPName("My HCP");
		a.setHCPSpecialty("Tree Surgeon");
		a.setOVDate("The day before tomorrow.");
		
		assertEquals(a.hashCode(), a.hashCode());

		a.setDesignated(false);
		
		assertEquals(a.hashCode(), a.hashCode());
	}

	public void testEqualsObject() {
		HCPVisitBean a = new HCPVisitBean();
		a.setDesignated(true);
		a.setHCPAddr("abc");
		a.setHCPMID(1);
		a.setHCPName("My HCP");
		a.setHCPSpecialty("Tree Surgeon");
		a.setOVDate("The day before tomorrow.");
		
		assertTrue(a.equals(a));
		
		HCPVisitBean b = new HCPVisitBean();
		b.setDesignated(true);
		b.setHCPAddr("abc");
		b.setHCPMID(1);
		b.setHCPName("My HCP");
		b.setHCPSpecialty("Tree Surgeon");
		b.setOVDate("The day before tomorrow.");
		
		assertTrue(b.equals(a));
		
		// The order of the following statements is important.
		b.setOVDate("The day after yesterday");
		assertFalse(b.equals(a));
		
		b.setHCPSpecialty("Love Doctor");
		assertFalse(b.equals(a));
		
		b.setHCPName("The best HCP");
		assertFalse(b.equals(a));
		
		b.setHCPAddr("Elm Street");
		assertFalse(b.equals(a));
		
		assertFalse(b.equals("string"));
	}
	
	public void testGetHCPSpecialty() {
		HCPVisitBean b = new HCPVisitBean();
		assertEquals(b.getHCPSpecialty(), "");
		b.setHCPSpecialty("Pillow Fluffer");
		assertEquals(b.getHCPSpecialty(), "Pillow Fluffer");
	}
	
	public void testGetHCPAddr() {
		HCPVisitBean b = new HCPVisitBean();
		assertEquals(b.getHCPAddr(), "");
		b.setHCPAddr("Western Blvd");
		assertEquals(b.getHCPAddr(), "Western Blvd");
	}
	
	public void testGetOVDate() {
		HCPVisitBean b = new HCPVisitBean();
		assertEquals(b.getOVDate(), "");
		b.setOVDate("01/01/2001");
		assertEquals(b.getOVDate(), "01/01/2001");
	}
	
	public void testIsDesignated() {
		HCPVisitBean b = new HCPVisitBean();
		assertFalse(b.isDesignated());
		b.setDesignated(true);
		assertTrue(b.isDesignated());
	}

}
