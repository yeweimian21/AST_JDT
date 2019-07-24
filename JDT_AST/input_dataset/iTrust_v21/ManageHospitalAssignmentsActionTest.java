package edu.ncsu.csc.itrust.unit.action;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.action.ManageHospitalAssignmentsAction;
import edu.ncsu.csc.itrust.beans.HospitalBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

/**
 * ManageHospitalAssignmentsActionTest
 */
public class ManageHospitalAssignmentsActionTest extends TestCase {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private DAOFactory evil = EvilDAOFactory.getEvilInstance();
	private ManageHospitalAssignmentsAction action;
	private ManageHospitalAssignmentsAction ltAction;
	private TestDataGenerator gen = new TestDataGenerator();
	private final static long performingAdmin = 9000000001L;
	private final static long hcp0 = 9000000000L;
	private final static long lt0 = 5000000000L;
	private final static String hosp0 = "1";
	private final static String hosp1 = "9191919191";
	private final static String hosp2 = "8181818181";

	@Override
	protected void setUp() throws Exception {
		gen.clearAllTables();
		gen.hcp0();
		gen.admin1();
		gen.hospitals();
		gen.clearHospitalAssignments();
		gen.ltData0();
		action = new ManageHospitalAssignmentsAction(factory, performingAdmin);
		ltAction = new ManageHospitalAssignmentsAction(factory, lt0);
	}

	private String doAssignment() throws ITrustException {
		return action.assignHCPToHospital("" + hcp0, hosp0);
	}

	private String doAssignment(String hospitalID) throws ITrustException {
		return action.assignHCPToHospital("" + hcp0, hospitalID);
	}

	/**
	 * testAssignHCPToHospital
	 * @throws ITrustException
	 */
	public void testAssignHCPToHospital() throws ITrustException {
		assertEquals("HCP successfully assigned.", doAssignment());
		List<HospitalBean> h = action.getAssignedHospitals("" + hcp0);
		assertEquals(1, h.size());
		assertEquals("1", h.get(0).getHospitalID());
	}

	/**
	 * testAssignHCPToHospitalEvil
	 */
	public void testAssignHCPToHospitalEvil() {
		action = new ManageHospitalAssignmentsAction(evil, performingAdmin);
		try {
			action.getAssignedHospitals("" + hcp0);
			fail();
		} catch (ITrustException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getExtendedMessage());
		}

	}

	/**
	 * testAssignDuplicate
	 * @throws ITrustException
	 */
	public void testAssignDuplicate() throws ITrustException {
		assertEquals("HCP successfully assigned.", doAssignment());
		try {
			doAssignment();
			fail("testAssignDuplicate failed: assignHCPToHospital should have thrown exception");
		} catch (ITrustException e) {
			assertEquals("HCP 9000000000 already assigned to hospital 1", e.getMessage());
		}
	}

	/**
	 * testRemovePersonnelAssignmentToHospital
	 * @throws ITrustException
	 */
	public void testRemovePersonnelAssignmentToHospital() throws ITrustException {
		doAssignment();
		assertEquals("HCP successfully unassigned", action.removeHCPAssignmentToHospital("" + hcp0, hosp0));
		assertEquals(0, action.getAssignedHospitals("" + hcp0).size());
	}

	/**
	 * testRemoveNonAssigned
	 * @throws ITrustException
	 */
	public void testRemoveNonAssigned() throws ITrustException {
		assertEquals("HCP not unassigned", action.removeHCPAssignmentToHospital("" + hcp0, hosp0));
	}

	/**
	 * testRemoveAll
	 * @throws ITrustException
	 * @throws Exception
	 */
	public void testRemoveAll() throws ITrustException, Exception {
		doAssignment();
		doAssignment(hosp1);
		doAssignment(hosp2);
		assertEquals(3, action.getAssignedHospitals("" + hcp0).size());
		assertEquals(3, action.removeAllAssignmentsFromHCP("" + hcp0));
		assertEquals(0, action.getAssignedHospitals("" + hcp0).size());
	}

	/**
	 * testRemoveAllEvil
	 */
	public void testRemoveAllEvil() {
		action = new ManageHospitalAssignmentsAction(evil, performingAdmin);
		try {
			action.removeAllAssignmentsFromHCP("" + hcp0);
			fail();
		} catch (ITrustException e) {
			//TODO
		}

	}

	/**
	 * testRemoveAllUnassigned
	 * @throws ITrustException
	 */
	public void testRemovaAllUnassigned() throws ITrustException {
		assertEquals(0, action.removeAllAssignmentsFromHCP("" + hcp0));
	}

	/**
	 * testCheckHCPIDBadMID
	 */
	public void testCheckHCPIDBadMID() {
		try {
			action.checkHCPID("90000000001");
			fail();
		} catch (ITrustException e) {
			//TODO
		}
	}

	/**
	 * testCheckHCPID
	 * @throws ITrustException
	 */
	public void testCheckHCPID() throws ITrustException {
		assertEquals(9000000000L, action.checkHCPID("9000000000"));
	}

	/**
	 * testCheckHCPIDStringMID
	 */
	public void testCheckHCPIDStringMID() {
		try {
			action.checkHCPID("f");
			fail();
		} catch (ITrustException e) {
			//TODO
		}
	}

	/**
	 * testCheckHCPIDEvil
	 */
	public void testCheckHCPIDEvil() {
		try {
			action = new ManageHospitalAssignmentsAction(evil, performingAdmin);
			action.checkHCPID("9000000000");
			fail();
		} catch (ITrustException e) {
			//TODO
		}
	}

	/**
	 * testGetAvailableHospitals
	 * @throws ITrustException
	 */
	public void testGetAvailableHospitals() throws ITrustException {
		assertSame(9, action.getAvailableHospitals("9000000000").size());
	}

	/**
	 * testGetAvailableHospitalsBadMID
	 */
	public void testGetAvailableHospitalsBadMID() {
		try {
			action.getAvailableHospitals("f");
			fail();
		} catch (ITrustException e) {
			//TODO
		}
	}

	/**
	 * testGetAssignedHospitalsBadMID
	 */
	public void testGetAssignedHospitalsBadMID() {
		try {
			action.getAssignedHospitals("f");
			fail();
		} catch (ITrustException e) {
			//TODO
		}
	}

	/**
	 * testAssignHCPToHospitalBadID
	 */
	public void testAssignHCPToHospitalBadID() {
		try {
			action.assignHCPToHospital("f", "1");
			fail();
		} catch (ITrustException e) {
			//TODO
		}
	}

	/**
	 * testRemoveHCPtoHospitalBADID
	 */
	public void testRemoveHCPtoHospitalBadID() {
		try {
			action.removeHCPAssignmentToHospital("f", "1");
			fail();
		} catch (ITrustException e) {
			//TODO
		}
	}

	/**
	 * testRemoveHCPAssignmentsBadID
	 */
	public void testRemoveHCPAssignmentsBadID() {
		try {
			action.removeAllAssignmentsFromHCP("l");
			fail();
		} catch (ITrustException e) {
			//TODO
		}
	}
	
	/**
	 * New Method to check and make sure LTs only have 1 hospital
	 * 
	 * @throws ITrustException
	 * @throws IOException
	 * @throws SQLException
	 */
	public void testCheckLTHospital() throws ITrustException, IOException, SQLException {
		assertTrue(ltAction.checkLTHospital("5000000001"));
		gen.clearHospitalAssignments();
		assertFalse(ltAction.checkLTHospital("5000000001"));
		
		
	}

	/**
	 * This method checks to make sure checkLTHospital method can correctly handle
	 * and illegal MID.
	 */
	public void testCheckLTIDStringMID() {
		try {
			assertFalse(ltAction.checkLTHospital("ABCD"));
			fail();
		} catch (ITrustException e) {
			//TODO
		}
	}

}
