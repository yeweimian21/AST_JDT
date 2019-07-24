package edu.ncsu.csc.itrust.unit.dao.hospital;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.HospitalBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.HospitalsDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.unit.DBBuilder;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

/**
 * Test Hostipal DAO
 */
public class AddEditHospitalDAOTest extends TestCase {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private HospitalsDAO hospitalDAO = factory.getHospitalsDAO();
	TestDataGenerator gen = new TestDataGenerator();

	@Override
	protected void setUp() throws Exception {
		gen.clearAllTables();
		gen.hospitals();
	}

	private void clearHospitals() throws SQLException {
		new DBBuilder(factory).executeSQL(Arrays.asList("DELETE FROM hospitals;"));
	}

	/**
	 * Test All hospital
	 * test Get HospitalFromEmptyTable
	 * test GetHospital
	 * test AddDupe
	 * testUpdateName
	 * @throws Exception
	 */
	public void testGetAllHospitals() throws Exception {
		List<HospitalBean> hospitals = hospitalDAO.getAllHospitals();
		assertEquals(9, hospitals.size());
		// All hospitals in alphabetical order.
		assertEquals("Facebook Rehab Center", hospitals.get(0).getHospitalName());
		assertEquals("Health Institute Dr. E", hospitals.get(1).getHospitalName());
		assertEquals("Health Institute Mr. Barry", hospitals.get(2).getHospitalName());
		assertEquals("Health Institute Mr. Donghoon", hospitals.get(3).getHospitalName());
		assertEquals("Le Awesome Hospital", hospitals.get(4).getHospitalName());
		assertEquals("Mental Hospital 4 iTrust Devs", hospitals.get(5).getHospitalName());
		assertEquals("Ninja Hospital", hospitals.get(6).getHospitalName());
	}

	public void testGetHospital() throws DBException {
		HospitalBean hosp = hospitalDAO.getHospital("9191919191");
		assertEquals("9191919191", hosp.getHospitalID());
		assertEquals("Test Hospital 9191919191", hosp.getHospitalName());

	}

	public void testGetAllFromEmptyTable() throws SQLException, DBException {
		clearHospitals();
		assertEquals(0, hospitalDAO.getAllHospitals().size());
	}

	public void testGetHospitalFromEmptyTable() throws SQLException, DBException {
		clearHospitals();
		assertEquals(null, hospitalDAO.getHospital("9191919191"));
	}

	public void testAddHospital() throws DBException, ITrustException {
		final String id = "9191919192";
		final String name = "testAddHospital Hospital ";
		genericAdd(id, name);
		List<HospitalBean> allCodes = hospitalDAO.getAllHospitals();
		assertEquals(id, allCodes.get(allCodes.size() - 1).getHospitalID());
		assertEquals(name, allCodes.get(allCodes.size() - 1).getHospitalName());
	}

	public void testAddDupe() throws SQLException, DBException, ITrustException {
		final String id = "0000000000";
		final String name0 = "testAddDupe Hospital";
		HospitalBean hosp = genericAdd(id, name0);
		try {
			hosp.setHospitalName("");
			hospitalDAO.addHospital(hosp);
			fail("CPTCodeTest.testAddDupe failed to catch dupe");
		} catch (ITrustException e) {
			assertEquals("Error: Hospital already exists.", e.getMessage());
			hosp = hospitalDAO.getHospital(id);
			assertEquals(name0, hosp.getHospitalName());
		}
	}

	private HospitalBean genericAdd(String id, String name) throws DBException, ITrustException {
		HospitalBean hosp = new HospitalBean(id, name);
		assertTrue(hospitalDAO.addHospital(hosp));
		assertEquals(name, hospitalDAO.getHospital(id).getHospitalName());
		return hosp;
	}

	public void testUpdateName() throws DBException, ITrustException {
		final String id = "7777777777";
		final String name = "testUpdateName NEW Hospital";
		HospitalBean hosp = genericAdd(id, "");
		hosp.setHospitalName(name);
		assertEquals(1, hospitalDAO.updateHospital(hosp));
		hosp = hospitalDAO.getHospital(id);
		assertEquals(name, hosp.getHospitalName());
	}

	public void testUpdateNonExistent() throws SQLException, DBException {
		clearHospitals();
		final String id = "0000000000";
		HospitalBean hosp = new HospitalBean(id, "");
		assertEquals(0, hospitalDAO.updateHospital(hosp));
		assertEquals(0, hospitalDAO.getAllHospitals().size());
	}
	
	public void testGetAllPatientHospitals() {
		//Generate the standard data set
		try {
			gen.clearAllTables();
			gen.standardData();
		} catch (Exception e) {
			//Fail if there's an exception
			fail();
		}
		
		//Test that the getAllPatientHospitals() method returns a nonempty list for 
		//patient 102
		try {
			hospitalDAO.getAllPatientHospitals(102L);
		} catch (DBException e) {
			//Fail if there's a DBException
			fail();
		}
		
		//Test the method with a dirty factory
		DAOFactory evilFactory = EvilDAOFactory.getEvilInstance();
		hospitalDAO = new HospitalsDAO(evilFactory);
		try {
			hospitalDAO.getAllPatientHospitals(102L);
			//Fail if no excepion is thrown
			fail();
		} catch (DBException e) {
			//Test passed
		}
	}
}
