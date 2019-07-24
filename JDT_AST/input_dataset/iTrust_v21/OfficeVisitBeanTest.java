package edu.ncsu.csc.itrust.unit.bean;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.OfficeVisitBean;

public class OfficeVisitBeanTest extends TestCase {
	
	public void testDateFailure() {
		OfficeVisitBean ov = new OfficeVisitBean();
		ov.setVisitDateStr("bad date");
		assertNull(ov.getVisitDate());
	}

}
