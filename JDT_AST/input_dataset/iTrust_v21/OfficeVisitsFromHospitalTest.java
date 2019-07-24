package edu.ncsu.csc.itrust.unit.dao.officevisit;

import java.util.List;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.OfficeVisitBean;
import edu.ncsu.csc.itrust.dao.mysql.OfficeVisitDAO;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class OfficeVisitsFromHospitalTest extends TestCase {
	private OfficeVisitDAO ovDAO = TestDAOFactory.getTestInstance().getOfficeVisitDAO();
	
	private TestDataGenerator gen;
	
	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.patient1();
		gen.patient2();
		gen.patient3();
	}
	
	public void testGetOfficeVisitsFromHospital() throws Exception {
	
		List<OfficeVisitBean> hospitalVisits = ovDAO.getOfficeVisitsFromHospital("1");
		assertEquals(9, hospitalVisits.size());
		assertEquals(959, hospitalVisits.get(0).getID());
		assertEquals(958, hospitalVisits.get(1).getID());
		assertEquals(957, hospitalVisits.get(2).getID());
		assertEquals(952, hospitalVisits.get(7).getID());
		assertEquals("1", hospitalVisits.get(0).getHospitalID());
		assertEquals("1", hospitalVisits.get(7).getHospitalID());
		assertEquals(2, hospitalVisits.get(0).getPatientID());
		assertEquals(9000000000L, hospitalVisits.get(0).getHcpID());
		
		
	}

}
