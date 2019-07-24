package edu.ncsu.csc.itrust.unit.dao.exerciseEntry;

import java.io.IOException;
import java.sql.SQLException;

import junit.framework.TestCase;

import org.junit.Test;

import edu.ncsu.csc.itrust.beans.ExerciseEntryBean;
import edu.ncsu.csc.itrust.dao.mysql.ExerciseEntryDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;

/**
 * Tests that the dao throws exceptions when created with bad factory
 *
 */
public class ExerciseEntryDAOTest extends TestCase {
	
	private TestDataGenerator gen = new TestDataGenerator();
	private EvilDAOFactory evil;
	private ExerciseEntryDAO exerciseDAO;
	
	
	/**
	 * Clears all of the tables, sets the standard data and includes 
	 * the data for use cases 68 and 69
	 */
	@Override
	public void setUp() throws SQLException, IOException {
		gen.clearAllTables();
		gen.standardData();
		evil = new EvilDAOFactory(0);
		exerciseDAO = new ExerciseEntryDAO(evil);
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
	public void testGetExerciseDiaryWithEvilDAO() {
		try {
			exerciseDAO.getPatientExerciseDiary(1);
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
	public void testAddToExerciseDiaryWithEvilDAO() {
		try {
			ExerciseEntryBean bean = new ExerciseEntryBean();
			bean.setStrDate("12/12/2012");
			bean.setExerciseType("Weight Training");
			bean.setStrName("Bench Press");
			bean.setCaloriesBurned(150);
			bean.setHoursWorked(1);
			bean.setNumSets(3);
			bean.setNumReps(10);
			exerciseDAO.addExerciseEntry(bean);
			fail("Should have thrown an exception");
		} catch (DBException e) {
			assertEquals("A database exception has occurred. Please see the "
					+ "log in the console for stacktrace", e.getMessage());
		} catch (ITrustException d) {
			fail("Should not have been an ITrustException");
		}
	}
}
