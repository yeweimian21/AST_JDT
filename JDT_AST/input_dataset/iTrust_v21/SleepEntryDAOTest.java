package edu.ncsu.csc.itrust.unit.dao.sleepEntry;

import java.io.IOException;
import java.sql.SQLException;

import junit.framework.TestCase;

import org.junit.Test;

import edu.ncsu.csc.itrust.beans.SleepEntryBean;
import edu.ncsu.csc.itrust.dao.mysql.SleepEntryDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;

/**
 * Tests that the dao throws exceptions when created with bad factory
 *
 */
public class SleepEntryDAOTest extends TestCase {
	
	private TestDataGenerator gen = new TestDataGenerator();
	private EvilDAOFactory evil;
	private SleepEntryDAO sleepDAO;
	
	
	/**
	 * Clears all of the tables, sets the standard data and includes 
	 * the data for use cases 68 and 69
	 */
	@Override
	public void setUp() throws SQLException, IOException {
		gen.clearAllTables();
		gen.standardData();
		evil = new EvilDAOFactory(0);
		sleepDAO = new SleepEntryDAO(evil);
	}
	
	/**
	 * Clears all of the tables
	 */
	@Override
	public void tearDown() throws Exception {
		gen.clearAllTables();
	}
	
	/**
	 * Testing when you are using an evil dao.
	 * looking for it to throw exceptions
	 */
	@Test
	public void testGetSleepDiaryWithEvilDAO() {
		try {
			sleepDAO.getPatientSleepDiary(1);
			fail("Should have thrown an exception");
		} catch (DBException e) {
			assertEquals("A database exception has occurred. Please see the "
					+ "log in the console for stacktrace", e.getMessage());
		}
	}

	/**
	 * Testing when you are using an evil dao.
	 * looking for it to throw exceptions
	 */
	@Test
	public void testAddToSleepDiaryWithEvilDAO() {
		try {
			SleepEntryBean bean = new SleepEntryBean();
			bean.setStrDate("12/12/2012");
			bean.setSleepType("Nap");
			bean.setHoursSlept(3);
			sleepDAO.addSleepEntry(bean);
			fail("Should have thrown an exception");
		} catch (DBException e) {
			assertEquals("A database exception has occurred. Please see the "
					+ "log in the console for stacktrace", e.getMessage());
		} catch (ITrustException d) {
			fail("Should not have been an ITrustException");
		}
	}
}
