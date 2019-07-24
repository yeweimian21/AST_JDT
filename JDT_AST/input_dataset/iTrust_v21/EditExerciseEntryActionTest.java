package edu.ncsu.csc.itrust.unit.action;

import java.util.List;

import org.junit.Test;

import edu.ncsu.csc.itrust.action.AddExerciseEntryAction;
import edu.ncsu.csc.itrust.action.EditExerciseEntryAction;
import edu.ncsu.csc.itrust.action.ViewExerciseEntryAction;
import edu.ncsu.csc.itrust.beans.ExerciseEntryBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;

/**
 * Tests different abilities for editing a exercise entry.
 *
 */
public class EditExerciseEntryActionTest extends TestCase {
	
	private EditExerciseEntryAction action;
	private ViewExerciseEntryAction viewAction;
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen;
	private ExerciseEntryBean exerciseBean;
	private AddExerciseEntryAction addAction;
	
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
		exerciseBean.setPatientID(1);
	}
	
	/**
	 * Clears all of the tables.
	 */
	protected void tearDown() throws Exception {
		gen.clearAllTables();
	}
	
	/**
	 * Tests that nothing happens when a user tries to udpate
	 * a nonexistent exercise entry. Random Person (1) has no entries.
	 */
	@Test
	public void testEditNoEntry() {
		action = new EditExerciseEntryAction(factory, 1);
		viewAction = new ViewExerciseEntryAction(factory, 1);
		try {
			exerciseBean.setEntryID(100);
			assertEquals(0, action.editEntry(exerciseBean));
		} catch (ITrustException e) {
			fail(e.getMessage());
		} catch (FormValidationException d) {
			fail(d.getMessage());
		}
	}
	
	/**
	 * Tests that a user can edit their own exercise entries.
	 */
	@Test
	public void testEditExistingEntry() {
		addAction = new AddExerciseEntryAction(factory, 1);
		action = new EditExerciseEntryAction(factory, 1);
		viewAction = new ViewExerciseEntryAction(factory, 1);
		try {
			addAction.addEntry(exerciseBean);
			
			List<ExerciseEntryBean> exerciseDiary = viewAction.getDiary(1);
			assertEquals(3, exerciseDiary.size());
			ExerciseEntryBean entry = exerciseDiary.get(0);
			assertEquals("Bench Press", entry.getStrName().toString());
			entry.setStrName("Bicep Curls");
			int numUpdated = action.editEntry(entry);
			assertEquals(1, numUpdated);
			assertEquals(3, viewAction.getDiary(1).size());
			entry = viewAction.getDiary(1).get(0);
			assertEquals("Bicep Curls", entry.getStrName());
		} catch (ITrustException d) {
			fail(d.getMessage());
		} catch (FormValidationException e) {
			fail(e.getMessage());
		}
		
	}
	
	/**
	 * Tests that users cannot edit other people exercise entries
	 */
	@Test
	public void testOtherUsersEntry() {
		addAction = new AddExerciseEntryAction(factory, 1);
		action = new EditExerciseEntryAction(factory, 2);
		viewAction = new ViewExerciseEntryAction(factory, 1);
		try {
			addAction.addEntry(exerciseBean);
			
			List<ExerciseEntryBean> exerciseDiary = viewAction.getDiary(1);
			assertEquals(3, exerciseDiary.size());
			ExerciseEntryBean firstEntry = exerciseDiary.get(0);
			assertEquals("Bench Press", firstEntry.getStrName().toString());
			firstEntry.setStrName("Bicep Curls");
			int numUpdated = action.editEntry(firstEntry);
			assertEquals(-1, numUpdated);
			assertEquals(3, viewAction.getDiary(1).size());
			firstEntry = viewAction.getDiary(1).get(0);
			assertEquals("Bench Press", firstEntry.getStrName());
		} catch (ITrustException d) {
			fail(d.getMessage());
		} catch (FormValidationException e) {
			fail(e.getMessage());
		}
	}
	
	/**
	 * Test that you can add an entry and then turn around and edit it.
	 */
	@Test
	public void testAddEditEntry() {
		addAction = new AddExerciseEntryAction(factory, 1);
		action = new EditExerciseEntryAction(factory, 1);
		viewAction = new ViewExerciseEntryAction(factory, 1);
		try {
			addAction.addEntry(exerciseBean);
			
			assertEquals(3, viewAction.getDiary(1).size());
			ExerciseEntryBean bean = viewAction.getDiary(1).get(0);
			assertEquals("12/14/2012", bean.getStrDate());
			assertEquals("Weight Training", bean.getExerciseType().getName());
			assertEquals("Bench Press", bean.getStrName());
			assertEquals(0.5, bean.getHoursWorked());
			assertEquals(50, bean.getCaloriesBurned());
			
			bean.setStrDate("12/11/2012");
			bean.setExerciseType("Cardio");
			bean.setStrName("Running");
			bean.setHoursWorked(2.0);
			bean.setCaloriesBurned(100);
			action.editEntry(bean);
			
			assertEquals(3, viewAction.getDiary(1).size());
			
			bean = viewAction.getDiary(1).get(2);
			assertEquals("12/11/2012", bean.getStrDate());
			assertEquals("Cardio", bean.getExerciseType().getName());
			assertEquals("Running", bean.getStrName());
			assertEquals(2.0, bean.getHoursWorked());
			assertEquals(100, bean.getCaloriesBurned());
		} catch (FormValidationException e) {
			fail(e.getMessage());
		} catch (ITrustException d) {
			fail(d.getMessage());
		}
	}
	
	/**
	 * Tests that the information included still has to be correct and pass
	 * the same validation as adding a new one
	 */
	@Test
	public void testEditInvalidServings() {
		addAction = new AddExerciseEntryAction(factory, 1);
		action = new EditExerciseEntryAction(factory, 1);
		viewAction = new ViewExerciseEntryAction(factory, 1);
		exerciseBean.setHoursWorked(2.0);
		try {
			addAction.addEntry(exerciseBean);
			
			ExerciseEntryBean bean = viewAction.getDiary(1).get(0);
			assertEquals(0.5, bean.getHoursWorked());
			bean.setHoursWorked(-2.0);
			action.editEntry(bean);
			fail("Invalid number of hours");
		} catch (FormValidationException d) {
			assertTrue(d.getMessage().contains("Number of Hours must be "
					+ "greater than 0"));
		} catch (ITrustException e) {
			fail(e.getMessage());
		}
	}
	
	/**
	 * Test with an evil factory
	 */
	@Test
	public void testEvilFactory() {
		EvilDAOFactory evil = new EvilDAOFactory(0);
		addAction = new AddExerciseEntryAction(factory, 1);
		action = new EditExerciseEntryAction(evil, 1);
		viewAction = new ViewExerciseEntryAction(factory, 1);
		try {
			addAction.addEntry(exerciseBean);
			
			ExerciseEntryBean bean = viewAction.getDiary(1).get(0);
			bean.setHoursWorked(2.0);
			action.editEntry(bean);
			fail("Using evil factory. Should have failed");
		} catch (ITrustException e) {
			assertTrue(e.getMessage().contains("Error updating entry from "
					+ "Exercise Diary"));
		} catch (FormValidationException d) {
			fail(d.getMessage());
		}
	}
	

}
