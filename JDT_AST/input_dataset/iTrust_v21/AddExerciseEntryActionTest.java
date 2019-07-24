package edu.ncsu.csc.itrust.unit.action;

import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

import edu.ncsu.csc.itrust.action.AddExerciseEntryAction;
import edu.ncsu.csc.itrust.action.ViewExerciseEntryAction;
import edu.ncsu.csc.itrust.beans.ExerciseEntryBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

/**
 * Tests adding exercise entries to the exercise diary
 *
 */
public class AddExerciseEntryActionTest extends TestCase {

	private AddExerciseEntryAction action;
	private ViewExerciseEntryAction viewAction;
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen;
	private ExerciseEntryBean exerciseBean;
	private EvilDAOFactory evil = new EvilDAOFactory();
	
	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.standardData();	
		exerciseBean = new ExerciseEntryBean();
		exerciseBean.setStrDate("12/12/2012");
		exerciseBean.setExerciseType("Weight Training");
		exerciseBean.setStrName("Bench Press");
		exerciseBean.setCaloriesBurned(50);
		exerciseBean.setHoursWorked(1);
		exerciseBean.setNumSets(3);
		exerciseBean.setNumReps(10);
	}
	
	/**
	 * Clears all of the tables.
	 */
	protected void tearDown() throws Exception {
		gen.clearAllTables();
	}
	
	/**
	 * Tests that a patient can add a new exercise entry to an empty diary.
	 * Log in as the patient Random Person who has no prior exercise entries.
	 */
	@Test
	public void testAddNewExerciseEntryToEmptyDiary() {
		action = new AddExerciseEntryAction(factory, 1);
		viewAction = new ViewExerciseEntryAction(factory, 1);
		try {
			String result = action.addEntry(exerciseBean);
			assertEquals("Success: Bench Press for 12/12/2012 was added "
					+ "successfully!", result);
			try {
				List<ExerciseEntryBean> exerciseDiary = viewAction.getDiary(1);
				assertEquals(3, exerciseDiary.size());
				ExerciseEntryBean bean = exerciseDiary.get(0);
				assertEquals(81, bean.getEntryID());
				assertEquals("12/14/2012", bean.getStrDate().toString());
				assertEquals("Weights", bean.getExerciseType().name());
				assertEquals("Bench Press", bean.getStrName());
				assertEquals(50, bean.getCaloriesBurned());
				assertEquals(0.5, bean.getHoursWorked());
				assertEquals(3, bean.getNumSets());
				assertEquals(10, bean.getNumReps());
				assertEquals(1, bean.getPatientID());
				
				List<ExerciseEntryBean> exerciseTotals = 
						viewAction.getDiaryTotals(1);
				assertEquals(2, exerciseTotals.size());
				ExerciseEntryBean total = exerciseTotals.get(0);
				assertEquals("12/14/2012", total.getStrDate());
				assertEquals(50, total.getCaloriesBurned());
				assertEquals(0.5, total.getHoursWorked());
				ExerciseEntryBean total2 = exerciseTotals.get(1);
				assertEquals("12/12/2012", total2.getStrDate());
				assertEquals(150, total2.getCaloriesBurned());
				assertEquals(2.0, total2.getHoursWorked(), .001);
			} catch (ITrustException e) {
				fail(e.getMessage());
			}
		} catch (FormValidationException e) {
			fail(e.getMessage());
		}
	}
	
	/**
	 * Tests that a patient can add a new exercise entry to a diary that already
	 * has some entries in it.
	 */
	@Test
	public void testAddExerciseEntryToNonEmptyDiary() {
		action = new AddExerciseEntryAction(factory, 1);
		viewAction = new ViewExerciseEntryAction(factory, 1);
		
		String result;
		try {
			result = action.addEntry(exerciseBean);
			exerciseBean.setStrName("Bicep Curls");
			
			assertEquals(viewAction.getDiary(1).size(), 3);
		} catch (Exception e1) {
			fail(e1.getMessage());
		}
		try {
			result = action.addEntry(exerciseBean);
			assertEquals("Success: Bicep Curls for 12/12/2012 was added "
					+ "successfully!", result);
			try {
				List<ExerciseEntryBean> exerciseDiary = viewAction.getDiary(1);
				assertEquals(4, exerciseDiary.size());
				ExerciseEntryBean bean = exerciseDiary.get(0);
				assertEquals("12/14/2012", bean.getStrDate().toString());
				assertEquals("Weights", bean.getExerciseType().name());
				assertEquals("Bench Press", bean.getStrName());
				assertEquals(50, bean.getCaloriesBurned());
				assertEquals(0.5, bean.getHoursWorked());
				assertEquals(3, bean.getNumSets());
				assertEquals(10, bean.getNumReps());
				assertEquals(1, bean.getPatientID());
				
				ExerciseEntryBean bean2 = exerciseDiary.get(3);
				assertEquals("12/12/2012", bean2.getStrDate().toString());
				assertEquals("Weights", bean2.getExerciseType().name());
				assertEquals("Bicep Curls", bean2.getStrName());
				assertEquals(50, bean.getCaloriesBurned());
				assertEquals(0.5, bean.getHoursWorked());
				assertEquals(3, bean.getNumSets());
				assertEquals(10, bean.getNumReps());
				assertEquals(1, bean.getPatientID());
				
				List<ExerciseEntryBean> totals = 
						viewAction.getDiaryTotals(1);
				assertEquals(2, totals.size());
				ExerciseEntryBean prevTotal = totals.get(0);
				assertEquals("12/14/2012", prevTotal.getStrDate());
				assertEquals(50, prevTotal.getCaloriesBurned());
				assertEquals(0.5, prevTotal.getHoursWorked());
				ExerciseEntryBean total = totals.get(1);
				assertEquals("12/12/2012", total.getStrDate());
				assertEquals(200, total.getCaloriesBurned());
				assertEquals(3.0, total.getHoursWorked(), .001);
			} catch (ITrustException e) {
				fail(e.getMessage());
			}
		} catch (FormValidationException e) {
			fail(e.getMessage());
		}
	}
	
	/**
	 * Tests that dates must be entered in the correct format.
	 */
	@Test
	public void testExerciseEntryInvalidDateFormat() {
		action = new AddExerciseEntryAction(factory, 1);
		exerciseBean.setStrDate("2014/04/04");
		try {
			action.addEntry(exerciseBean);
			fail("Date is in the wrong format");
		} catch (FormValidationException e) {
			assertEquals("This form has not been validated correctly. "
					+ "The following field are not properly filled in: "
					+ "[Date Performed: MM/DD/YYYY]", e.getMessage());
		}
	}
	
	/**
	 * Tests that dates in the future cannot be entered in.
	 * Eventually this test will fail because I am hardcoding in the date.
	 * However, since it is in the future 100 years, I do not expect this to be
	 * a problem (I spent about 30 minutes trying to use the Calendar class to 
	 * add one day to today's date, but I could'nt get it to format correctly).
	 */
	@Test
	public void testExerciseEntryFutureDate() {
		action = new AddExerciseEntryAction(factory, 1);
		String date = "03/17/2115";
		exerciseBean.setStrDate(date);
		try {
			action.addEntry(exerciseBean);
			fail("Date is in the future");
		} catch (FormValidationException e) {
			assertTrue(e.getMessage().contains("The Date Performed must be before "
					+ "or on today's Date."));
		}
	}
	
	/**
	 * Ensures that only breakfast, lunch, dinner, or snack is allowed
	 * as the exercise type
	 */
	@Test
	public void testInvalidExerciseType() {
		action = new AddExerciseEntryAction(factory, 1);
		try {
			exerciseBean.setExerciseType("Brunch");
			action.addEntry(exerciseBean);
			fail("Invalid exercise type");
		} catch (IllegalArgumentException d) {
			assertEquals("Exercise Type Brunch does not exist", d.getMessage());
		} catch (FormValidationException e) {
			fail("Setting exercise type to brunch should have failed");
		}
	}
	
	/**
	 * Test that the name of the exercise cannot be null or empty
	 */
	@Test
	public void testNullOrEmptyExerciseName() {
		action = new AddExerciseEntryAction(factory, 1);
		try {
			exerciseBean.setExerciseType(null);
			action.addEntry(exerciseBean);
			fail("Exercise Name is null");
		} catch (IllegalArgumentException d) {
			assertEquals("Exercise Type null does not exist", d.getMessage());
			try {
				exerciseBean.setExerciseType("");
				action.addEntry(exerciseBean);
				fail("Exercise Name is empty");
			} catch (IllegalArgumentException dd) {
				assertEquals("Exercise Type  does not exist", dd.getMessage());
			} catch (FormValidationException e) {
				fail("Should have already been caught");
			}
		} catch (FormValidationException ee) {
			fail("Should have already been caught");
		}
	}
	
	/**
	 * Test that the number of hours worked must be a positive number
	 */
	@Test
	public void testNumHoursPositive() {
		action = new AddExerciseEntryAction(factory, 1);
		exerciseBean.setHoursWorked(0);
		try {
			action.addEntry(exerciseBean);
			fail("Hours are 0");
		} catch (FormValidationException e) {
			assertTrue(e.getMessage().contains("Number of Hours"));
			exerciseBean.setHoursWorked(-1);
			try {
				action.addEntry(exerciseBean);
				fail("Hours are negative");
			} catch (FormValidationException d) {
				assertTrue(d.getMessage().contains("Number of Hours"));
			}
		}
	}
	
	/**
	 * Test that the number of calories cannot be negative.
	 * @throws ITrustException 
	 */
	@Test
	public void testNumCaloriesNotNegative() throws ITrustException {
		action = new AddExerciseEntryAction(factory, 1);
		viewAction = new ViewExerciseEntryAction(factory, 1);
		exerciseBean.setCaloriesBurned(1);
		try {
			String result = action.addEntry(exerciseBean);
			assertEquals("Success: Bench Press for 12/12/2012 was added "
					+ "successfully!", result);
			ExerciseEntryBean bean = viewAction.getDiary(1).get(0);
			assertEquals(3, viewAction.getDiary(1).size());
			assertEquals(50, bean.getCaloriesBurned());
			//now try to make them negative
			exerciseBean.setCaloriesBurned(0);
			try {
				action.addEntry(exerciseBean);
				fail("Calories cannot be negative");
			} catch (FormValidationException d) {
				assertTrue(d.getMessage().contains("Calories"));
			}
		} catch (FormValidationException e) {
			fail("Number of calories can be > 0");
		}
	}
	
	/**
	 * Test with an evil factory
	 */
	@Test
	public void testEvilFactory() {
		action = new AddExerciseEntryAction(evil, 1);
		try {
			String result = action.addEntry(exerciseBean);
			assertEquals("A database exception has occurred. Please see "
					+ "the log in the console for stacktrace", result);
		} catch (FormValidationException d) {
			fail(d.getMessage());
		}
	}

}
