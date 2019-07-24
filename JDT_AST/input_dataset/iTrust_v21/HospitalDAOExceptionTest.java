package edu.ncsu.csc.itrust.unit.dao.hospital;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.HospitalBean;
import edu.ncsu.csc.itrust.dao.mysql.HospitalsDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;

public class HospitalDAOExceptionTest extends TestCase {
	private HospitalsDAO evilDAO = EvilDAOFactory.getEvilInstance().getHospitalsDAO();

	@Override
	protected void setUp() throws Exception {
	}

	public void testAddHospitalException() throws Exception {
		try {
			evilDAO.addHospital(new HospitalBean());
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	public void testAssignHospitalException() throws Exception {
		try {
			evilDAO.assignHospital(0L, "");
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	public void testGetAllHospitalsException() throws Exception {
		try {
			evilDAO.getAllHospitals();
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	public void testGetHospitalException() throws Exception {
		try {
			evilDAO.getHospital("");
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	public void testRemoveAllHospitalAssignmentsException() throws Exception {
		try {
			evilDAO.removeAllHospitalAssignmentsFrom(0L);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	public void testRemoveHospitalAssignmentException() throws Exception {
		try {
			evilDAO.removeHospitalAssignment(0L, "");
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	public void testUpdateHospitalException() throws Exception {
		try {
			evilDAO.updateHospital(new HospitalBean());
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}
}
