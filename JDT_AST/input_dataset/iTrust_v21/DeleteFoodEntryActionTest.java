package edu.ncsu.csc.itrust.unit.action;

import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

import edu.ncsu.csc.itrust.action.AddFoodEntryAction;
import edu.ncsu.csc.itrust.action.DeleteFoodEntryAction;
import edu.ncsu.csc.itrust.action.ViewFoodEntryAction;
import edu.ncsu.csc.itrust.beans.FoodEntryBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

/**
 * Tests that a user can add and delete entries in his
 * food diary. Entries are known by their id.
 *
 */
public class DeleteFoodEntryActionTest extends TestCase {

	private DeleteFoodEntryAction action;
	private ViewFoodEntryAction viewAction;
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen;
	private FoodEntryBean foodBean;
	private AddFoodEntryAction addAction;
	
	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.standardData();	
		//gen.uc68(); //uc68 is now a part of standardData
		foodBean = new FoodEntryBean();
		foodBean.setDateEatenStr("12/12/2012");
		foodBean.setMealType("Breakfast");
		foodBean.setFood("Ice Cream");
		foodBean.setServings(2.0);
		foodBean.setCalories(30.5);
		foodBean.setFatGrams(4.0);
		foodBean.setMilligramsSodium(2.0);
		foodBean.setCarbGrams(6.0);
		foodBean.setSugarGrams(1.0);
		foodBean.setFiberGrams(4.3);
		foodBean.setProteinGrams(3.5);
		foodBean.setPatientID(333);
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
		action = new DeleteFoodEntryAction(factory, 333);
		viewAction = new ViewFoodEntryAction(factory, 333);
		try {
			List<FoodEntryBean> diary = viewAction.getDiary(333);
			assertEquals(0, diary.size());
			int numDeleted = action.deleteEntry(1);
			assertEquals(0, numDeleted);
		} catch (ITrustException e) {
			fail("Unexpected exception");
		}
	}
	
	/**
	 * Tests that a user can delete a food entry he just added.
	 */
	@Test
	public void testNewEntry() {
		action = new DeleteFoodEntryAction(factory, 333);
		viewAction = new ViewFoodEntryAction(factory, 333);
		addAction = new AddFoodEntryAction(factory, 333);
		try {
			addAction.addEntry(foodBean);
			FoodEntryBean bean = viewAction.getDiary(333).get(0);
			assertEquals(1, viewAction.getDiary(333).size());
			int numDeleted = action.deleteEntry(bean.getEntryID());
			assertEquals(1, numDeleted);
			assertEquals(0, viewAction.getDiary(333).size());
		} catch (ITrustException e) {
			fail("Unexpected exception");
		} catch (FormValidationException d) {
			fail("Unexpected exception");
		}
	}
	
	/**
	 * Tests that a user cannot delete a food entry from another user.
	 */
	@Test
	public void testDeleteFromOtherUser() {
		action = new DeleteFoodEntryAction(factory, 333);
		viewAction = new ViewFoodEntryAction(factory, 334);
		try {
			assertEquals(2, viewAction.getDiary(334).size());
			long id = viewAction.getDiary(334).get(0).getEntryID();
			int numDeleted = action.deleteEntry(id);
			assertEquals(0, numDeleted);
			assertEquals(2, viewAction.getDiary(334).size());
		} catch (ITrustException d) {
			fail("Unexpected exception");
		}
	}
	
	/**
	 * Tests deleting from an evil database.
	 */
	@Test
	public void testEvilDelete() {
		EvilDAOFactory evil = new EvilDAOFactory(0);
		action = new DeleteFoodEntryAction(evil, 333);
		try {
			action.deleteEntry(2);
			fail("Using evil factory. Should have failed");
		} catch (ITrustException e) {
			assertTrue(e.getMessage().contains("Error deleting entry from "
					+ "Food Diary"));
		}
	}

}
