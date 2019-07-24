package edu.ncsu.csc.itrust.unit.dao.patient;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;

public class PatientDAOExceptionTest extends TestCase {
	private PatientDAO evilDAO = EvilDAOFactory.getEvilInstance().getPatientDAO();

	@Override
	protected void setUp() throws Exception {
	}

	public void testAddEmptyPatientException() throws Exception {
		try {
			evilDAO.addEmptyPatient();
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	public void testAddRepException() throws Exception {
		try {
			evilDAO.addRepresentative(0L, 0L);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	public void testCheckDeclaredHCPException() throws Exception {
		try {
			evilDAO.checkDeclaredHCP(0L, 0L);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	public void testCheckPatientExistsException() throws Exception {
		try {
			evilDAO.checkPatientExists(0L);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	public void testDeclareHCPException() throws Exception {
		try {
			evilDAO.declareHCP(0L, 0L);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	public void testEditPatientException() throws Exception {
		try {
			evilDAO.editPatient(new PatientBean(), 9000000003L);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	public void testGetDeclaredHCPsException() throws Exception {
		try {
			evilDAO.getDeclaredHCPs(0L);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals("pid cannot be 0", e.getSQLException().getMessage());
		}
	}

	public void testGetDiagnosesException() throws Exception {
		try {
			evilDAO.getDiagnoses(0L);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals("pid cannot be 0", e.getSQLException().getMessage());
		}
	}

	public void testGetNameException() throws Exception {
		try {
			evilDAO.getName(0L);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	public void testGetPatientException() throws Exception {
		try {
			evilDAO.getPatient(0L);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	public void testGetRepresentedException() throws Exception {
		try {
			evilDAO.getRepresented(0L);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	public void testRemoveRepresentativeException() throws Exception {
		try {
			evilDAO.removeRepresentative(0L, 0L);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	public void testRepresentsException() throws Exception {
		try {
			evilDAO.represents(0L, 0L);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	public void testUndeclareHCPException() throws Exception {
		try {
			evilDAO.undeclareHCP(0L, 0L);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}
	
	public void testExpiredPrescriptionException() throws Exception {
		try {
			evilDAO.getExpiredPrescriptions(0L);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals("pid cannot be 0", e.getSQLException().getMessage());
		}
	}
	
	public void testExpiredPrescriptionException2() throws Exception {
		try {
			evilDAO.getExpiredPrescriptions(1);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}
	
	public void testGetCurrentPrescriptions() throws Exception {
		try {
			evilDAO.getExpiredPrescriptions(1);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}
}
