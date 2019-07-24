package edu.ncsu.csc.itrust.unit.action;

import java.util.List;

import org.junit.Test;

import edu.ncsu.csc.itrust.action.AddFoodEntryAction;
import edu.ncsu.csc.itrust.action.EditFoodEntryAction;
import edu.ncsu.csc.itrust.action.ViewFoodEntryAction;
import edu.ncsu.csc.itrust.beans.FoodEntryBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;

/**
 * Tests different abilites for editing a food entry.
 *
 */
public class EditFoodEntryActionTest extends TestCase {
	
	private EditFoodEntryAction action;
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
	 * Tests that nothing happens when a user tries to udpate
	 * a nonexistent food entry. Derek Morgan (333) has no entries.
	 */
	@Test
	public void testEditNoEntry() {
		action = new EditFoodEntryAction(factory, 333);
		viewAction = new ViewFoodEntryAction(factory, 333);
		try {
			foodBean.setEntryID(100);
			assertEquals(0, action.editEntry(foodBean));
		} catch (ITrustException e) {
			fail(e.getMessage());
		} catch (FormValidationException d) {
			fail(d.getMessage());
		}
	}
	
	/**
	 * Tests that a user can edit his own food entries.
	 */
	@Test
	public void testEditExistingEntry() {
		action = new EditFoodEntryAction(factory, 334);
		viewAction = new ViewFoodEntryAction(factory, 334);
		try {
			List<FoodEntryBean> foodDiary = viewAction.getDiary(334);
			assertEquals(2, foodDiary.size());
			FoodEntryBean firstEntry = foodDiary.get(0);
			assertEquals("Breakfast", firstEntry.getMealType().toString());
			firstEntry.setMealType("Dinner");
			int numUpdated = action.editEntry(firstEntry);
			assertEquals(1, numUpdated);
			assertEquals(2, viewAction.getDiary(334).size());
			firstEntry = viewAction.getDiary(334).get(0);
			assertEquals("Dinner", firstEntry.getMealType().toString());
		} catch (ITrustException d) {
			fail(d.getMessage());
		} catch (FormValidationException e) {
			fail(e.getMessage());
		}
		
	}
	
	/**
	 * Tests that users cannot edit other people food entries
	 */
	@Test
	public void testOtherUsersEntry() {
		action = new EditFoodEntryAction(factory, 333);
		viewAction = new ViewFoodEntryAction(factory, 334);
		try {
			List<FoodEntryBean> foodDiary = viewAction.getDiary(334);
			assertEquals(2, foodDiary.size());
			FoodEntryBean firstEntry = foodDiary.get(0);
			assertEquals("Breakfast", firstEntry.getMealType().toString());
			firstEntry.setMealType("Dinner");
			int numUpdated = action.editEntry(firstEntry);
			assertEquals(-1, numUpdated);
			assertEquals(2, viewAction.getDiary(334).size());
			firstEntry = viewAction.getDiary(334).get(0);
			assertEquals("Breakfast", firstEntry.getMealType().getName());
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
		action = new EditFoodEntryAction(factory, 333);
		viewAction = new ViewFoodEntryAction(factory, 333);
		addAction = new AddFoodEntryAction(factory, 333);
		try {
			addAction.addEntry(foodBean);
			assertEquals(1, viewAction.getDiary(333).size());
			FoodEntryBean bean = viewAction.getDiary(333).get(0);
			assertEquals("12/12/2012", bean.getDateEatenStr());
			assertEquals("Breakfast", bean.getMealType().getName());
			assertEquals("Ice Cream", bean.getFood());
			assertEquals(2.0, bean.getServings(), .001);
			assertEquals(30.5, bean.getCalories(), .001);
			assertEquals(4.0, bean.getFatGrams(), .001);
			assertEquals(2.0, bean.getMilligramsSodium(), .001);
			assertEquals(6.0, bean.getCarbGrams(), .001);
			assertEquals(1.0, bean.getSugarGrams(), .001);
			assertEquals(4.3, bean.getFiberGrams(), .001);
			assertEquals(3.5, bean.getProteinGrams(), .001);
			
			bean.setDateEatenStr("01/01/2013");
			bean.setMealType("Dinner");
			bean.setFood("Hot dog");
			bean.setServings(1.0);
			bean.setCalories(10.0);
			bean.setFatGrams(3.0);
			bean.setMilligramsSodium(100.0);
			bean.setCarbGrams(10.0);
			bean.setSugarGrams(11.0);
			bean.setFiberGrams(1.0);
			bean.setProteinGrams(3.0);
			
			action.editEntry(bean);
			assertEquals(1, viewAction.getDiary(333).size());
			bean = viewAction.getDiary(333).get(0);
			assertEquals("01/01/2013", bean.getDateEatenStr());
			assertEquals("Dinner", bean.getMealType().getName());
			assertEquals("Hot dog", bean.getFood());
			assertEquals(1.0, bean.getServings(), .001);
			assertEquals(10.0, bean.getCalories(), .001);
			assertEquals(3.0, bean.getFatGrams(), .001);
			assertEquals(100.0, bean.getMilligramsSodium(), .001);
			assertEquals(10.0, bean.getCarbGrams(), .001);
			assertEquals(11.0, bean.getSugarGrams(), .001);
			assertEquals(1.0, bean.getFiberGrams(), .001);
			assertEquals(3.0, bean.getProteinGrams(), .001);
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
		action = new EditFoodEntryAction(factory, 334);
		viewAction = new ViewFoodEntryAction(factory, 334);
		foodBean.setServings(0.0);
		try {
			FoodEntryBean bean = viewAction.getDiary(334).get(0);
			assertEquals(4.0, bean.getServings());
			bean.setServings(0.0);
			action.editEntry(bean);
			fail("Invalid number of servings");
		} catch (FormValidationException d) {
			assertTrue(d.getMessage().contains("Number of Servings must be "
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
		action = new EditFoodEntryAction(evil, 334);
		viewAction = new ViewFoodEntryAction(factory, 334);
		try {
			FoodEntryBean bean = viewAction.getDiary(334).get(0);
			bean.setCalories(100.0);
			action.editEntry(bean);
			fail("Using evil factory. Should have failed");
		} catch (ITrustException e) {
			assertTrue(e.getMessage().contains("Error updating entry from "
					+ "Food Diary"));
		} catch (FormValidationException d) {
			fail(d.getMessage());
		}
	}
	

}
