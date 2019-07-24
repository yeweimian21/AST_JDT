package edu.ncsu.csc.itrust.unit.dao.allergies;

import java.util.List;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.AllergyBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.AllergyDAO;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class GetAllergiesTest extends TestCase {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private AllergyDAO allergyDAO = factory.getAllergyDAO();

	@Override
	protected void setUp() throws Exception {
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.patient2();
		gen.patient1();
	}

	public void testGetAllergiesFor1() throws Exception {
		assertEquals(0, allergyDAO.getAllergies(1L).size());
	}

	public void testGetAllergiesFor2() throws Exception {
		List<AllergyBean> allergies = allergyDAO.getAllergies(2L);
		assertEquals(2, allergies.size());
		assertEquals("Pollen", allergies.get(0).getDescription());
		assertEquals(2, allergies.get(0).getPatientID());
		assertEquals("664662530", allergies.get(1).getNDCode());
		assertEquals(2, allergies.get(1).getPatientID());
	}
}
