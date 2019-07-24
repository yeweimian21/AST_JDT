package edu.ncsu.csc.itrust.unit.dao.phr;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.HealthRecord;
import edu.ncsu.csc.itrust.dao.mysql.HealthRecordsDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;

public class PHRDAOExceptionTest extends TestCase {
	private HealthRecordsDAO evilDAO = EvilDAOFactory.getEvilInstance().getHealthRecordsDAO();

	@Override
	protected void setUp() throws Exception {
	}

	public void testAddException() throws Exception {
		try {
			evilDAO.add(new HealthRecord());
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	public void testGetException() throws Exception {
		try {
			evilDAO.getAllHealthRecords(0L);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}
}
