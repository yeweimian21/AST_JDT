package edu.ncsu.csc.itrust.unit.dao.hospital;

import java.util.List;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.HospitalBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.HospitalsDAO;
import edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class AssignHospitalDAOTest extends TestCase {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private HospitalsDAO hosDAO = factory.getHospitalsDAO();
	private PersonnelDAO personnelDAO = factory.getPersonnelDAO();

	@Override
	protected void setUp() throws Exception {
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.hospitals();
		gen.hcp0();
	}

	public void testPersonnelHospitals() throws Exception {
		List<HospitalBean> hospitals = personnelDAO.getHospitals(9000000000L);
		assertEquals(2, hospitals.size());
		assertEquals("8181818181", hospitals.get(0).getHospitalID());
		assertEquals("9191919191", hospitals.get(1).getHospitalID());
		List<HospitalBean> hospList = hosDAO.getHospitalsAssignedToPhysician(9000000000L);
		assertEquals(2, hospList.size());
	}

	public void testAssignNewHospital() throws Exception {
		assertEquals(2, personnelDAO.getHospitals(9000000000L).size());
		hosDAO.assignHospital(9000000000L, "1");
		assertEquals(3, personnelDAO.getHospitals(9000000000L).size());
	}

	public void testDoItTwice() throws Exception {
		hosDAO.assignHospital(9000000000L, "1");
		try {
			hosDAO.assignHospital(9000000000L, "1");
			fail("Exception should have been thrown");
		} catch (ITrustException e) {
			assertEquals("HCP 9000000000 already assigned to hospital 1", e.getMessage());
		}
	}
	
	
	
	
}
