package edu.ncsu.csc.itrust.unit.action;

import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

import edu.ncsu.csc.itrust.action.AddFoodEntryAction;
import edu.ncsu.csc.itrust.action.ViewFoodEntryAction;
import edu.ncsu.csc.itrust.beans.FoodEntryBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

/**
 * Tests adding food entries to the food diary
 *
 */
public class AddFoodEntryActionTest extends TestCase {

	private AddFoodEntryAction action;
	private ViewFoodEntryAction viewAction;
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen;
	private FoodEntryBean foodBean;
	
	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.standardData();	
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
	}
	
	/**
	 * Clears all of the tables.
	 */
	protected void tearDown() throws Exception {
		gen.clearAllTables();
	}
	
	/**
	 * Tests that a patient can add a new food entry to an empty diary.
	 * Log in as the patient Derek Morgan who has no prior food entries.
	 */
	@Test
	public void testAddNewFoodEntryToEmptyDiary() {
		action = new AddFoodEntryAction(factory, 333);
		viewAction = new ViewFoodEntryAction(factory, 333);
		try {
			String result = action.addEntry(foodBean);
			assertEquals("Success: Ice Cream for 12/12/2012 was added "
					+ "successfully!", result);
			try {
				List<FoodEntryBean> foodDiary = viewAction.getDiary(333);
				assertEquals(1, foodDiary.size());
				FoodEntryBean bean = foodDiary.get(0);
				assertEquals(36, bean.getEntryID());
				assertEquals("12/12/2012", bean.getDateEatenStr().toString());
				assertEquals("Breakfast", bean.getMealType().name());
				assertEquals("Ice Cream", bean.getFood());
				assertEquals(2.0, bean.getServings());
				assertEquals(30.5, bean.getCalories());
				assertEquals(333, bean.getPatientID());
				
				List<FoodEntryBean> foodTotals = 
						viewAction.getDiaryTotals(333);
				assertEquals(1, foodTotals.size());
				FoodEntryBean total = foodTotals.get(0);
				assertEquals("12/12/2012", total.getDateEatenStr());
				assertEquals(61.0, total.getCalories());
				assertEquals(8.0, total.getFatGrams());
				assertEquals(4.0, total.getMilligramsSodium());
				assertEquals(12.0, total.getCarbGrams());
				assertEquals(2.0, total.getSugarGrams());
				assertEquals(8.6, total.getFiberGrams());
				assertEquals(7.0, total.getProteinGrams());
			} catch (ITrustException e) {
				fail(e.getMessage());
			}
		} catch (FormValidationException e) {
			fail(e.getMessage());
		}
	}
	
	/**
	 * Tests that a patient can add a new food entry to a diary that already
	 * has some entries in it.
	 */
	@Test
	public void testAddFoodEntryToNonEmptyDiary() {
		action = new AddFoodEntryAction(factory, 334);
		viewAction = new ViewFoodEntryAction(factory, 334);
		try {
			assertEquals(viewAction.getDiary(334).size(), 2);
		} catch (ITrustException e1) {
			fail(e1.getMessage());
		}
		try {
			String result = action.addEntry(foodBean);
			assertEquals("Success: Ice Cream for 12/12/2012 was added "
					+ "successfully!", result);
			try {
				List<FoodEntryBean> foodDiary = viewAction.getDiary(334);
				assertEquals(3, foodDiary.size());
				FoodEntryBean bean = foodDiary.get(0);
				assertEquals("12/12/2012", bean.getDateEatenStr().toString());
				assertEquals("Breakfast", bean.getMealType().name());
				assertEquals("Ice Cream", bean.getFood());
				assertEquals(2.0, bean.getServings());
				assertEquals(30.5, bean.getCalories());
				assertEquals(334, bean.getPatientID());
				
				FoodEntryBean bean2 = foodDiary.get(1);
				assertEquals("09/30/2012", bean2.getDateEatenStr().toString());
				assertEquals("Breakfast", bean2.getMealType().name());
				assertEquals("Hot dog", bean2.getFood());
				assertEquals(4.0, bean2.getServings());
				assertEquals(80.0, bean2.getCalories());
				assertEquals(5.0, bean2.getFatGrams());
				assertEquals(480.0, bean2.getMilligramsSodium());
				assertEquals(2.0, bean2.getCarbGrams());
				assertEquals(0.0, bean2.getFiberGrams());
				assertEquals(0.0, bean2.getSugarGrams());
				assertEquals(5.0, bean2.getProteinGrams());
				assertEquals(334, bean2.getPatientID());
				
				List<FoodEntryBean> totals = 
						viewAction.getDiaryTotals(334);
				assertEquals(2, totals.size());
				FoodEntryBean prevTotal = totals.get(1);
				assertEquals(476.0, prevTotal.getCalories());
				assertEquals(20.0, prevTotal.getFatGrams());
				assertEquals(1950.0, prevTotal.getMilligramsSodium());
				assertEquals(46.4, prevTotal.getCarbGrams());
				assertEquals(0.0, prevTotal.getFiberGrams());
				assertEquals(34.8, prevTotal.getSugarGrams());
				assertEquals(21.2, prevTotal.getProteinGrams());
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
	public void testFoodEntryInvalidDateFormat() {
		action = new AddFoodEntryAction(factory, 333);
		foodBean.setDateEatenStr("2014/04/04");
		try {
			action.addEntry(foodBean);
			fail("Date is in the wrong format");
		} catch (FormValidationException e) {
			assertEquals("This form has not been validated correctly. "
					+ "The following field are not properly filled in: "
					+ "[Date Eaten: MM/DD/YYYY]", e.getMessage());
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
	public void testFoodEntryFutureDate() {
		action = new AddFoodEntryAction(factory, 333);
		String date = "03/17/2115";
		foodBean.setDateEatenStr(date);
		try {
			action.addEntry(foodBean);
			fail("Date is in the future");
		} catch (FormValidationException e) {
			assertTrue(e.getMessage().contains("The Date Eaten must be before "
					+ "or on today's Date."));
		}
	}
	
	/**
	 * Ensures that only breakfast, lunch, dinner, or snack is allowed
	 * as the meal type
	 */
	@Test
	public void testInvalidMealType() {
		action = new AddFoodEntryAction(factory, 333);
		try {
			foodBean.setMealType("Brunch");
			action.addEntry(foodBean);
			fail("Invalid meal type");
		} catch (IllegalArgumentException d) {
			assertEquals("Meal Type Brunch does not exist", d.getMessage());
		} catch (FormValidationException e) {
			fail("Setting meal type to brunch should have failed");
		}
	}
	
	/**
	 * Test that the name of the food cannot be null or empty
	 */
	@Test
	public void testNullOrEmptyFoodName() {
		action = new AddFoodEntryAction(factory, 333);
		try {
			foodBean.setMealType(null);
			action.addEntry(foodBean);
			fail("Food Name is null");
		} catch (IllegalArgumentException d) {
			assertEquals("Meal Type null does not exist", d.getMessage());
			try {
				foodBean.setMealType("");
				action.addEntry(foodBean);
				fail("Food Name is empty");
			} catch (IllegalArgumentException dd) {
				assertEquals("Meal Type  does not exist", dd.getMessage());
			} catch (FormValidationException e) {
				fail("Should have already been caught");
			}
		} catch (FormValidationException ee) {
			fail("Should have already been caught");
		}
	}
	
	/**
	 * Test that the number of servings must be a positive number
	 */
	@Test
	public void testNumServingsPositive() {
		action = new AddFoodEntryAction(factory, 333);
		foodBean.setServings(0);
		try {
			action.addEntry(foodBean);
			fail("Servings are 0");
		} catch (FormValidationException e) {
			assertTrue(e.getMessage().contains("Number of Servings"));
			foodBean.setServings(-1);
			try {
				action.addEntry(foodBean);
				fail("Servings are negative");
			} catch (FormValidationException d) {
				assertTrue(d.getMessage().contains("Number of Servings"));
			}
		}
	}
	
	/**
	 * Test that the number of calories cannot be negative.
	 * @throws ITrustException 
	 */
	@Test
	public void testNumCaloriesNotNegative() throws ITrustException {
		action = new AddFoodEntryAction(factory, 333);
		viewAction = new ViewFoodEntryAction(factory, 333);
		foodBean.setCalories(0);
		try {
			String result = action.addEntry(foodBean);
			assertEquals("Success: Ice Cream for 12/12/2012 was added "
					+ "successfully!", result);
			FoodEntryBean bean = viewAction.getDiary(333).get(0);
			assertEquals(1, viewAction.getDiary(333).size());
			assertEquals(0.0, bean.getCalories());
			//now try to make them negative
			foodBean.setCalories(-1);
			try {
				action.addEntry(foodBean);
				fail("Calories cannot be negative");
			} catch (FormValidationException d) {
				assertTrue(d.getMessage().contains("Calories per Serving"));
			}
		} catch (FormValidationException e) {
			fail("Number of calories can be 0");
		}
	}
	
	/**
	 * Tests that the grams of fat per serving cannot be negative
	 */
	@Test
	public void testNumGramsFatNotNegative() throws ITrustException {
		action = new AddFoodEntryAction(factory, 333);
		viewAction = new ViewFoodEntryAction(factory, 333);
		foodBean.setFatGrams(0);
		try {
			String result = action.addEntry(foodBean);
			assertEquals("Success: Ice Cream for 12/12/2012 was added "
					+ "successfully!", result);
			FoodEntryBean bean = viewAction.getDiary(333).get(0);
			assertEquals(1, viewAction.getDiary(333).size());
			assertEquals(0.0, bean.getFatGrams());
			//now try to make them negative
			foodBean.setFatGrams(-1);
			try {
				action.addEntry(foodBean);
				fail("Grams of Fat cannot be negative");
			} catch (FormValidationException d) {
				assertTrue(d.getMessage().contains("Grams of Fat "
								+ "per Serving"));
			}
		} catch (FormValidationException e) {
			fail("Number of fat grams can be 0");
		}
	}

	/**
	 * Test that the number of sodium cannot be negative.
	 * @throws ITrustException 
	 */
	@Test
	public void testNumSodiumNotNegative() throws ITrustException {
		action = new AddFoodEntryAction(factory, 333);
		viewAction = new ViewFoodEntryAction(factory, 333);
		foodBean.setMilligramsSodium(0);
		try {
			String result = action.addEntry(foodBean);
			assertEquals("Success: Ice Cream for 12/12/2012 was added "
					+ "successfully!", result);
			FoodEntryBean bean = viewAction.getDiary(333).get(0);
			assertEquals(1, viewAction.getDiary(333).size());
			assertEquals(0.0, bean.getMilligramsSodium());
			//now try to make them negative
			foodBean.setMilligramsSodium(-1);
			try {
				action.addEntry(foodBean);
				fail("Sodium cannot be negative");
			} catch (FormValidationException d) {
				assertTrue(d.getMessage().contains("Milligrams of Sodium"
						+ " per Serving"));
			}
		} catch (FormValidationException e) {
			fail("Number of sodium can be 0");
		}
	}
	
	/**
	 * Tests that the grams of fat per serving cannot be negative
	 */
	@Test
	public void testNumGramsCarbsNotNegative() throws ITrustException {
		action = new AddFoodEntryAction(factory, 333);
		viewAction = new ViewFoodEntryAction(factory, 333);
		foodBean.setCarbGrams(0);
		try {
			String result = action.addEntry(foodBean);
			assertEquals("Success: Ice Cream for 12/12/2012 was added "
					+ "successfully!", result);
			FoodEntryBean bean = viewAction.getDiary(333).get(0);
			assertEquals(1, viewAction.getDiary(333).size());
			assertEquals(0.0, bean.getCarbGrams());
			//now try to make them negative
			foodBean.setCarbGrams(-1);
			try {
				action.addEntry(foodBean);
				fail("Grams of Carbs cannot be negative");
			} catch (FormValidationException d) {
				assertTrue(d.getMessage().contains("Grams of Carbs "
								+ "per Serving"));
			}
		} catch (FormValidationException e) {
			fail("Number of carb grams can be 0");
		}
	}
	

	/**
	 * Test that the number of sugars cannot be negative.
	 * @throws ITrustException 
	 */
	@Test
	public void testNumSugarNotNegative() throws ITrustException {
		action = new AddFoodEntryAction(factory, 333);
		viewAction = new ViewFoodEntryAction(factory, 333);
		foodBean.setSugarGrams(0);
		try {
			String result = action.addEntry(foodBean);
			assertEquals("Success: Ice Cream for 12/12/2012 was added "
					+ "successfully!", result);
			FoodEntryBean bean = viewAction.getDiary(333).get(0);
			assertEquals(1, viewAction.getDiary(333).size());
			assertEquals(0.0, bean.getSugarGrams());
			//now try to make them negative
			foodBean.setSugarGrams(-1);
			try {
				action.addEntry(foodBean);
				fail("Sugar cannot be negative");
			} catch (FormValidationException d) {
				assertTrue(d.getMessage().contains("Grams of Sugars"
						+ " per Serving"));
			}
		} catch (FormValidationException e) {
			fail("Number of sugar can be 0");
		}
	}
	
	/**
	 * Tests that the grams of fiber per serving cannot be negative
	 */
	@Test
	public void testNumGramsFiberNotNegative() throws ITrustException {
		action = new AddFoodEntryAction(factory, 333);
		viewAction = new ViewFoodEntryAction(factory, 333);
		foodBean.setFiberGrams(0);
		try {
			String result = action.addEntry(foodBean);
			assertEquals("Success: Ice Cream for 12/12/2012 was added "
					+ "successfully!", result);
			FoodEntryBean bean = viewAction.getDiary(333).get(0);
			assertEquals(1, viewAction.getDiary(333).size());
			assertEquals(0.0, bean.getFiberGrams());
			//now try to make them negative
			foodBean.setFiberGrams(-1);
			try {
				action.addEntry(foodBean);
				fail("Grams of Fiber cannot be negative");
			} catch (FormValidationException d) {
				assertTrue(d.getMessage().contains("Grams of Fiber "
								+ "per Serving"));
			}
		} catch (FormValidationException e) {
			fail("Number of fiber grams can be 0");
		}
	}
	
	/**
	 * Tests that the grams of protein per serving cannot be negative
	 */
	@Test
	public void testNumGramsProteinNotNegative() throws ITrustException {
		action = new AddFoodEntryAction(factory, 333);
		viewAction = new ViewFoodEntryAction(factory, 333);
		foodBean.setProteinGrams(0);
		try {
			String result = action.addEntry(foodBean);
			assertEquals("Success: Ice Cream for 12/12/2012 was added "
					+ "successfully!", result);
			FoodEntryBean bean = viewAction.getDiary(333).get(0);
			assertEquals(1, viewAction.getDiary(333).size());
			assertEquals(0.0, bean.getProteinGrams());
			//now try to make them negative
			foodBean.setProteinGrams(-1);
			try {
				action.addEntry(foodBean);
				fail("Grams of Protein cannot be negative");
			} catch (FormValidationException d) {
				assertTrue(d.getMessage().contains("Grams of Protein "
								+ "per Serving"));
			}
		} catch (FormValidationException e) {
			fail("Number of protein grams can be 0");
		}
	}
	
	/**
	 * Test with an evil factory
	 */
	@Test
	public void testEvilFactory() {
		EvilDAOFactory evil = new EvilDAOFactory();
		action = new AddFoodEntryAction(evil, 333);
		try {
			String result = action.addEntry(foodBean);
			assertEquals("A database exception has occurred. Please see "
					+ "the log in the console for stacktrace", result);
		} catch (FormValidationException d) {
			fail(d.getMessage());
		}
		
	}


}
