package edu.ncsu.csc.itrust.unit.action;

import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

import edu.ncsu.csc.itrust.action.AddExerciseEntryAction;
import edu.ncsu.csc.itrust.action.DeleteExerciseEntryAction;
import edu.ncsu.csc.itrust.action.ViewExerciseEntryAction;
import edu.ncsu.csc.itrust.beans.ExerciseEntryBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

/**
 * Tests that a user can add and delete entries in his
 * exercise diary. Entries are known by their id.
 *
 */
public class DeleteExerciseEntryActionTest extends TestCase {

	private DeleteExerciseEntryAction action;
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
	 * Tests that an appropriate message is displayed if a user
	 * tries to delete from an empty diary (it will be the same
	 * as trying to delete with the wrong id)
	 */
	@Test
	public void testDeleteInvalidID() {
		action = new DeleteExerciseEntryAction(factory, 1);
		viewAction = new ViewExerciseEntryAction(factory, 1);
		try {
			List<ExerciseEntryBean> diary = viewAction.getDiary(1);
			assertEquals(2, diary.size());
			int numDeleted = action.deleteEntry(1);
			assertEquals(0, numDeleted);
		} catch (ITrustException e) {
			fail("Unexpected exception");
		}
	}
	
	/**
	 * Tests that a user can delete a exercise entry he just added.
	 */
	@Test
	public void testNewEntry() {
		action = new DeleteExerciseEntryAction(factory, 1);
		viewAction = new ViewExerciseEntryAction(factory, 1);
		addAction = new AddExerciseEntryAction(factory, 1);
		try {
			addAction.addEntry(exerciseBean);
			ExerciseEntryBean bean = viewAction.getDiary(1).get(0);
			assertEquals(3, viewAction.getDiary(1).size());
			int numDeleted = action.deleteEntry(bean.getEntryID());
			assertEquals(1, numDeleted);
			assertEquals(2, viewAction.getDiary(1).size());
		} catch (Exception e) {
			fail("Unexpected exception");
		}
	}
	
	/**
	 * Tests that a user cannot delete a exercise entry from another user.
	 */
	@Test
	public void testDeleteFromOtherUser() {
		action = new DeleteExerciseEntryAction(factory, 2);
		
		addAction = new AddExerciseEntryAction(factory, 1);
		viewAction = new ViewExerciseEntryAction(factory, 1);
		try {
			addAction.addEntry(exerciseBean);
			addAction.addEntry(exerciseBean);
			
			assertEquals(4, viewAction.getDiary(1).size());
			long id = viewAction.getDiary(1).get(0).getEntryID();
			int numDeleted = action.deleteEntry(id);
			assertEquals(0, numDeleted);
			assertEquals(4, viewAction.getDiary(1).size());
		} catch (Exception e) {
			fail("Unexpected exception");
		}
	}
	
	/**
	 * Tests deleting from an evil database.
	 */
	@Test
	public void testEvilDelete() {
		EvilDAOFactory evil = new EvilDAOFactory(0);
		action = new DeleteExerciseEntryAction(evil, 1);
		try {
			action.deleteEntry(2);
			fail("Using evil factory. Should have failed");
		} catch (ITrustException e) {
			assertTrue(e.getMessage().contains("Error deleting entry from "
					+ "Exercise Diary"));
		}
	}

}
