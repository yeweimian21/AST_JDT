package edu.ncsu.csc.itrust.unit.dao.labprocedure;

import edu.ncsu.csc.itrust.beans.LabProcedureBean;
import edu.ncsu.csc.itrust.dao.mysql.LabProcedureDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;
import junit.framework.TestCase;

public class LabPocedureDAOExceptionTest extends TestCase {
	private LabProcedureDAO dao = EvilDAOFactory.getEvilInstance().getLabProcedureDAO();

	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testGetLabProceduresForPatient() {
		try {
			dao.getLabProceduresForPatient(1);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	public void testGetLabProceduresForPatientForNextMonth() {
		try {
			dao.getLabProceduresForPatientForNextMonth(1);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	public void testGetLabProcedure() {
		try {
			dao.getLabProcedure(1);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	public void testGetAllLabProceduresDate() {
		try {
			dao.getAllLabProceduresDate(9000000000L);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	public void testGetAllLabProceduresForDocOVLongLong() {
		try {
			dao.getAllLabProceduresForDocOV(9000000000L, 1);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	public void testGetAllLabProceduresForDocOVLong() {
		try {
			dao.getAllLabProceduresForDocOV(9000000000L);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	public void testGetAllLabProcedures() {
		try {
			dao.getAllLabProcedures();
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	public void testGetLabProceduresForLHCPForNextMonth() {
		try {
			dao.getLabProceduresForLHCPForNextMonth(9000000000L);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	public void testAddLabProcedure() {
		LabProcedureBean bean = new LabProcedureBean();
		bean.setPid(1);
		try {
			dao.addLabProcedure(bean);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	public void testUpdateLabProcedure() {
		LabProcedureBean bean = new LabProcedureBean();
		bean.setPid(1);
		try {
			dao.updateLabProcedure(bean);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	public void testMarkViewed() {
		LabProcedureBean bean = new LabProcedureBean();
		bean.setPid(1);
		try {
			dao.markViewed(bean);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}
	
	public void testGetAllLabProceduresLOINC() {
		try {
			dao.getAllLabProceduresLOINC(9000000000L);
			fail("DBException should have been thrown");
		} catch(DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
		
	}

	/**
	 * testGetAllLabProceduresLOINC2
	 */
	public void testGetAllLabProceduresLOINC2() {
		try {
			dao.getAllLabProceduresLOINC(22, "10763-1");
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	/**
	 * testUpdateRights
	 */
	public void testUpdateRights() {
		LabProcedureBean bean = new LabProcedureBean();
		bean.setPid(1);
		try {
			dao.updateRights(bean);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	/**
	 * testRemoveLabProcedure
	 */
	public void testRemoveLabProcedure() {
		try {
			dao.removeLabProcedure(1);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	/**
	 * testGetLabProceduresInTranistForLabTech
	 */
	public void testGetLabProceduresInTransitForLabTech() {
		try {
			dao.getLabProceduresInTransitForLabTech(1);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	/**
	 * testGetLabProceduresReceivedForLabTech
	 */
	public void testGetLabProceduresReceivedForLabTech() {
		try {
			dao.getLabProceduresReceivedForLabTech(1);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	/**
	 * testGetLanProceduresTestingForLabTech
	 */
	public void testGetLabProceduresTestingForLabTech() {
		try {
			dao.getLabProceduresTestingForLabTech(1);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	/**
	 * testSubmiteTestResults
	 */
	public void testSubmitTestResults() {
		try {
			dao.submitTestResults(1, "", "", "", "");
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	/**
	 * testSubmitReceivedLP
	 */
	public void testSubmitReceivedLP() {
		try {
			dao.submitReceivedLP(1);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	/**
	 * testSetLPToTesting
	 */
	public void testSetLPToTesting() {
		try {
			dao.setLPToTesting(1);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

}
