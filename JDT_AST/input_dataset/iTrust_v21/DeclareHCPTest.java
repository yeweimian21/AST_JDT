package edu.ncsu.csc.itrust.unit.dao.patient;

import java.util.List;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.PersonnelBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class DeclareHCPTest extends TestCase {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private PatientDAO patientDAO = factory.getPatientDAO();
	private TestDataGenerator gen;

	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.hcp3(); // 3 is declared, 0 is not
		gen.patient2();
		gen.hcp0();
	}

	public void testGetDeclaredHCPs() throws Exception {
		List<PersonnelBean> hcps = patientDAO.getDeclaredHCPs(2L);
		assertEquals(1, hcps.size());
		assertEquals(9000000003L, hcps.get(0).getMID());
		assertEquals("Gandalf Stormcrow", hcps.get(0).getFullName());
	}

	public void testDeclareHCP() throws Exception {
		assertEquals(1, patientDAO.getDeclaredHCPs(2L).size());
		patientDAO.declareHCP(2L, 9000000000L);
		assertEquals(2, patientDAO.getDeclaredHCPs(2L).size());
	}

	public void testDeclareDuplicateHCP() throws Exception {
		patientDAO.declareHCP(2L, 9000000000L);
		try {
			patientDAO.declareHCP(2L, 9000000000L);
			fail("Exception should have been thrown");
		} catch (ITrustException e) {
			assertEquals("HCP 9000000000 has already been declared for patient 2", e.getMessage());
		}
	}

	public void testUnDeclareHCP() throws Exception {
		assertEquals(1, patientDAO.getDeclaredHCPs(2L).size());
		patientDAO.undeclareHCP(2L, 9000000003L);
		assertEquals(0, patientDAO.getDeclaredHCPs(2L).size());
	}

	public void testUnDeclareNotDeclaredHCP() throws Exception {
		assertEquals(1, patientDAO.getDeclaredHCPs(2L).size());
		boolean confirm = patientDAO.undeclareHCP(2L, 9000000000L);
		assertFalse(confirm);
		assertEquals(1, patientDAO.getDeclaredHCPs(2L).size());
	}
	
	public void testCheckDeclared() throws Exception {
		assertTrue(patientDAO.checkDeclaredHCP(2L, 9000000003L));
		assertFalse(patientDAO.checkDeclaredHCP(2L, 9000000000L));
	}
}
