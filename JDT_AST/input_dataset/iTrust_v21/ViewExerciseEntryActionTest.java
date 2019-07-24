package edu.ncsu.csc.itrust.unit.action;

import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

import edu.ncsu.csc.itrust.action.ViewExerciseEntryAction;
import edu.ncsu.csc.itrust.beans.ExerciseEntryBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

/**
 * Tests for viewing a patient's exercise diary
 *
 */
public class ViewExerciseEntryActionTest extends TestCase {
	private ViewExerciseEntryAction action;
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen;
	private EvilDAOFactory evil = new EvilDAOFactory();
	
	/**
	 * Clears all of the tables, gets the standard data, 
	 * and includes the data for use cases 68 and 69
	 */
	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.standardData();
	}
	
	/**
	 * Clears all of the tables
	 */
	@Override
	protected void tearDown() throws Exception {
		gen.clearAllTables();
	}
	/**
	 * Log in as the HCP Duyu Ivanlyft who does have the specialty of
	 * trainer, so she should be able to view exercise entries. View
	 * the Exercise Entries for Patient Random Person who has some
	 * exercise entries already in their diary.
	 */
	@Test
	public void testViewingExerciseDiaryWithEntriesAsTrainer() {
		action = new ViewExerciseEntryAction(factory, 9000000081L);
		try {
			List<ExerciseEntryBean> exerciseDiary = action.getDiary(1);
			assertEquals(2, exerciseDiary.size());
			ExerciseEntryBean entry1 = exerciseDiary.get(0);
			ExerciseEntryBean entry2 = exerciseDiary.get(1);
			//now that we know we have 2 of them, make sure they are the 
			//right ones
			assertEquals("12/14/2012", entry1.getStrDate().toString());
			assertEquals("Weights", entry1.getExerciseType().name());
			assertEquals("Bench Press", entry1.getStrName());
			assertEquals(0.5, entry1.getHoursWorked());
			assertEquals(50, entry1.getCaloriesBurned());
			assertEquals(1, entry1.getPatientID());
			
			assertEquals("12/12/2012", entry2.getStrDate().toString());
			assertEquals("Cardio", entry2.getExerciseType().name());
			assertEquals("Running", entry2.getStrName());
			assertEquals(1.0, entry2.getHoursWorked());
			assertEquals(100, entry2.getCaloriesBurned());
			assertEquals(1, entry1.getPatientID());
			
			//now check the totals
			List<ExerciseEntryBean> totals = action.getDiaryTotals(1);
			assertEquals(2, totals.size());
			ExerciseEntryBean total = totals.get(0);
			assertEquals(.5, total.getHoursWorked(), .0001);
			assertEquals(50, total.getCaloriesBurned());
			ExerciseEntryBean total2 = totals.get(1);
			assertEquals(1.0, total2.getHoursWorked(), .0001);
			assertEquals(100, total2.getCaloriesBurned());
		} catch (ITrustException e) {
			fail(e.getMessage());
		}
			
	}
	
	/**
	 * Test that HCP's that are not specialists in nutrition are unable to
	 * view Exercise Diary Entries.
	 */
	@Test
	public void testViewingExerciseDiaryWithEntriesAsNonTrainerHCP() {
		action = new ViewExerciseEntryAction(factory, 9000000000L); 
		//log in as Doctor Kelly
		List<ExerciseEntryBean> exerciseDiary = null;
		try {
			exerciseDiary = action.getDiary(1); 
			fail("Non Trainer can view Exercise Diary");
		} catch (ITrustException d) {
			assertEquals("You do not have permission"
					+ " to view the Exercise Diary!", d.getMessage());
			assertNull(exerciseDiary);
		}
	}
	
	/**
	 * Test that a patient can view their exercise diary.
	 */
	@Test
	public void testViewingExerciseDiaryAsPatient() {
		action = new ViewExerciseEntryAction(factory, 1);
		try {
			List<ExerciseEntryBean> exerciseDiary = action.getDiary(1);
			assertEquals(2, exerciseDiary.size());
			ExerciseEntryBean entry1 = exerciseDiary.get(0);
			ExerciseEntryBean entry2 = exerciseDiary.get(1);
			//now that we know we have 2 of them, 
			//make sure they are the right ones
			assertEquals("12/14/2012", entry1.getStrDate().toString());
			assertEquals("Weights", entry1.getExerciseType().name());
			assertEquals("Bench Press", entry1.getStrName());
			assertEquals(0.5, entry1.getHoursWorked());
			assertEquals(50, entry1.getCaloriesBurned());
			assertEquals(1, entry1.getPatientID());
			
			assertEquals("12/12/2012", entry2.getStrDate().toString());
			assertEquals("Cardio", entry2.getExerciseType().name());
			assertEquals("Running", entry2.getStrName());
			assertEquals(1.0, entry2.getHoursWorked());
			assertEquals(100, entry2.getCaloriesBurned());
			assertEquals(1, entry1.getPatientID());
			
			//now check the totals
			List<ExerciseEntryBean> totals = action.getDiaryTotals(1);
			assertEquals(2, totals.size());
			ExerciseEntryBean total = totals.get(0);
			assertEquals(.5, total.getHoursWorked(), .0001);
			assertEquals(50, total.getCaloriesBurned());
			ExerciseEntryBean total2 = totals.get(1);
			assertEquals(1.0, total2.getHoursWorked(), .0001);
			assertEquals(100, total2.getCaloriesBurned());
		} catch (ITrustException e) {
			fail(e.getMessage());
		}
	}
	
	/**
	 * Ensures a patient can still view their exercise diary even when it is empty.
	 */
	@Test
	public void testViewingEmptyExerciseDiaryAsPatient() {
		action = new ViewExerciseEntryAction(factory, 2);
		try {
			List<ExerciseEntryBean> exerciseDiary = action.getDiary(2);
			assertEquals(0, exerciseDiary.size());
		} catch (ITrustException e) {
			fail(e.getMessage());
		}
	}
	
	/**
	 * Ensures HCPs with specialty of trainer can still view a exercise diary
	 * even when the exercise diary is empty.
	 */
	@Test
	public void testViewingEmptyExerciseDiaryAsHCP() {
		action = new ViewExerciseEntryAction(factory, 9000000081L);
		try {
			List<ExerciseEntryBean> exerciseDiary = action.getDiary(2);
			assertEquals(0, exerciseDiary.size());
		} catch (ITrustException e) {
			fail(e.getMessage());
		}
	}
	
	/**
	 * Ensure patients can't view the exercise diary of other patients.
	 */
	@Test
	public void testViewExerciseDiaryOfOtherPatient() {
		action = new ViewExerciseEntryAction(factory, 1);
		try {
			action.getDiary(2);
			fail("You should not be able to view other patient's exercise diary.");
		} catch (ITrustException e) {
			assertEquals("You do not have permission to view the Exercise Diary!",
					e.getMessage());
		}
	}
	
	/**
	 * Testing that we can get the diary when we are searching for range of dates
	 */
	@Test
	public void testViewExerciseDiaryDateRange() {
		action = new ViewExerciseEntryAction(factory, 1);
		try {
			List<ExerciseEntryBean> exerciseDiary = action.getBoundedDiary("12/12/2012", "12/12/2012", 1);
			assertEquals(1, exerciseDiary.size());
			ExerciseEntryBean entry1 = exerciseDiary.get(0);
			//now that we know we have 1 of them, make sure they are the 
			//right ones
			assertEquals("12/12/2012", entry1.getStrDate().toString());
			assertEquals("Cardio", entry1.getExerciseType().name());
			assertEquals("Running", entry1.getStrName());
			assertEquals(1.0, entry1.getHoursWorked());
			assertEquals(100, entry1.getCaloriesBurned());
			assertEquals(1, entry1.getPatientID());
			
			
			//now check the totals
			List<ExerciseEntryBean> totals = action.getBoundedDiaryTotals("12/12/2012", "12/12/2012", 1);
			assertEquals(1, totals.size());
			ExerciseEntryBean total = totals.get(0);
			assertEquals(1.0, total.getHoursWorked(), .001);
			assertEquals(100, total.getCaloriesBurned());
		} catch (ITrustException e) {
			fail(e.getMessage());
		} catch (FormValidationException d) {
			fail("Do not want an exception");
		}
	}
	
	/**
	 * Test bounded diary with bad dates
	 */
	@Test
	public void testExerciseDiaryBadDates() {
		action = new ViewExerciseEntryAction(factory, 1);
		try {
			action.getBoundedDiary("", "", 1);
			fail("Bad dates");
		} catch (FormValidationException e) {
			assertEquals("This form has not been validated correctly. "
					+ "The following field are not properly "
					+ "filled in: [Enter dates in MM/dd/yyyy]", 
					e.getMessage());
		} catch (ITrustException d) {
			fail("Wanted bad dates");
		}
	}
	
	/**
	 * Test with a bad factory
	 */
	@Test
	public void testExerciseDiaryBadFactoryBoundedDates() {
		action = new ViewExerciseEntryAction(evil, 1);
		try {
			action.getBoundedDiary("", "", 1);
		} catch (ITrustException d) {
			assertEquals("Error retrieving Exercise Diary", d.getMessage());
		} catch (FormValidationException e) {
			fail("Made it with evil factory");
		}
	}
	
	/**
	 * Test with a bad factory
	 */
	@Test
	public void testExerciseDiaryBadFactoryBoundedDatesTotals() {
		action = new ViewExerciseEntryAction(evil, 1);
		try {
			action.getBoundedDiaryTotals("", "", 1);
		} catch (ITrustException d) {
			assertEquals("Error retrieving Exercise Diary", d.getMessage());
		} catch (FormValidationException e) {
			fail("Made it with evil factory");
		}
	}
	
	/**
	 * Test with a bad factory
	 */
	@Test
	public void testExerciseDiaryBadFactory() {
		action = new ViewExerciseEntryAction(evil, 1);
		try {
			action.getDiary(1);
		} catch (ITrustException d) {
			assertEquals("Error retrieving Exercise Diary", d.getMessage());
		}
	}
	
	/**
	 * Test with a bad factory
	 */
	@Test
	public void testExerciseDiaryBadFactoryTotals() {
		action = new ViewExerciseEntryAction(evil, 1);
		try {
			action.getDiaryTotals(1);
		} catch (ITrustException d) {
			assertEquals("Error retrieving Exercise Diary", d.getMessage());
		}
	}
	
	/**
	 * Try to get a bounded exercise diary of other patient.
	 */
	@Test
	public void testBoundedDiaryOtherPatient() {
		action = new ViewExerciseEntryAction(factory, 1);
		try {
			action.getBoundedDiary("", "", 2);
			fail("You should not be able to view other patient's exercise diary.");
		} catch (ITrustException e) {
			assertEquals("You do not have permission to view the Exercise Diary!",
					e.getMessage());
		} catch (FormValidationException d) {
			fail("Wrong patient");
		}
	}
	
	/**
	 * Try to get a bounded exercise diary totals of other patient.
	 */
	@Test
	public void testBoundedDiaryTotalsOtherPatient() {
		action = new ViewExerciseEntryAction(factory, 1);
		try {
			action.getBoundedDiaryTotals("", "", 2);
			fail("You should not be able to view other patient's exercise diary.");
		} catch (ITrustException e) {
			assertEquals("You do not have permission to view the Exercise Diary!",
					e.getMessage());
		} catch (FormValidationException d) {
			fail("Wrong patient");
		}
	}
	
	/**
	 * Enter in start date after end date
	 */
	@Test
	public void testStartAfterEnd() {
		action = new ViewExerciseEntryAction(factory, 1);
		try {
			action.getBoundedDiary("12/12/2012", "12/10/2012", 1);
			fail("Wrong date order");
		} catch (FormValidationException d) {
			assertEquals("This form has not been validated correctly. "
					+ "The following field are not properly filled in: "
					+ "[Start date must be before end date!]", 
					d.getMessage());
		} catch (ITrustException e) {
			fail("Wrong ordering of dates");
		}
	}
	
	/**
	 * Enter in start date after end date for the totals
	 */
	@Test
	public void testStartAfterEndTotals() {
		action = new ViewExerciseEntryAction(factory, 1);
		try {
			action.getBoundedDiaryTotals("12/12/2012", "12/10/2012", 1);
			fail("Wrong date order");
		} catch (FormValidationException d) {
			assertEquals("This form has not been validated correctly. "
					+ "The following field are not properly filled in: "
					+ "[Start date must be before end date!]", 
					d.getMessage());
		} catch (ITrustException e) {
			fail("Wrong ordering of dates");
		}
	}

}
