package edu.ncsu.csc.itrust.unit.action;

import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

import edu.ncsu.csc.itrust.action.ViewFoodEntryAction;
import edu.ncsu.csc.itrust.beans.FoodEntryBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

/**
 * Tests for viewing a patient's food diary
 *
 */
public class ViewFoodEntryActionTest extends TestCase {
	private ViewFoodEntryAction action;
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen;
	
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
	 * Log in as the HCP Spencer Reid who does have the specialty of
	 * nutritionist, so she should be able to view food entries. View
	 * the Food Entries for Patient Jennifer Jareau who has some
	 * food entries already in her diary.
	 */
	@Test
	public void testViewingFoodDiaryWithEntriesAsNutritionist() {
		action = new ViewFoodEntryAction(factory, 9000000071L);
		try {
			List<FoodEntryBean> foodDiary = action.getDiary(334);
			assertEquals(2, foodDiary.size());
			FoodEntryBean entry1 = foodDiary.get(0);
			FoodEntryBean entry2 = foodDiary.get(1);
			//now that we know we have 2 of them, make sure they are the 
			//right ones
			assertEquals("09/30/2012", entry1.getDateEatenStr().toString());
			assertEquals("Breakfast", entry1.getMealType().name());
			assertEquals("Hot dog", entry1.getFood());
			assertEquals(4.0, entry1.getServings(), .001);
			assertEquals(80.0, entry1.getCalories(), .001);
			assertEquals(5.0, entry1.getFatGrams(), .001);
			assertEquals(480.0, entry1.getMilligramsSodium(), .001);
			assertEquals(2.0, entry1.getCarbGrams(), .001);
			assertEquals(0.0, entry1.getFiberGrams(), .001);
			assertEquals(0.0, entry1.getSugarGrams(), .001);
			assertEquals(5.0, entry1.getProteinGrams(), .001);
			assertEquals(334, entry1.getPatientID());
			
			assertEquals("09/30/2012", entry2.getDateEatenStr().toString());
			assertEquals("Lunch", entry2.getMealType().name());
			assertEquals("Mango Passionfruit Juice", entry2.getFood());
			assertEquals(1.2, entry2.getServings(), .001);
			assertEquals(130.0, entry2.getCalories(), .001);
			assertEquals(0.0, entry2.getFatGrams(), .001);
			assertEquals(25.0, entry2.getMilligramsSodium(), .001);
			assertEquals(32.0, entry2.getCarbGrams(), .001);
			assertEquals(0.0, entry2.getFiberGrams(), .001);
			assertEquals(29.0, entry2.getSugarGrams(), .001);
			assertEquals(1.0, entry2.getProteinGrams(), .001);
			assertEquals(334, entry1.getPatientID());
			
			//now check the totals
			List<FoodEntryBean> totals = action.getDiaryTotals(334);
			assertEquals(1, totals.size());
			FoodEntryBean total = totals.get(0);
			assertEquals(476.0, total.getCalories(), .001);
			assertEquals(20.0, total.getFatGrams(), .001);
			assertEquals(1950.0, total.getMilligramsSodium(), .001);
			assertEquals(46.4, total.getCarbGrams(), .001);
			assertEquals(0.0, total.getFiberGrams(), .001);
			assertEquals(34.8, total.getSugarGrams(), .001);
			assertEquals(21.2, total.getProteinGrams(), .001);
			
		} catch (ITrustException e) {
			fail(e.getMessage());
		}
			
	}
	
	/**
	 * Test that HCP's that are not specialists in nutrition are unable to
	 * view Food Diary Entries.
	 */
	@Test
	public void testViewingFoodDiaryWithEntriesAsNonNutritionistHCP() {
		action = new ViewFoodEntryAction(factory, 9000000000L); 
		//log in as Doctor Kelly
		List<FoodEntryBean> foodDiary = null;
		try {
			foodDiary = action.getDiary(334); 
			fail("Non Nutritionist can view Food Diary");
		} catch (ITrustException d) {
			assertEquals("You do not have permission"
					+ " to view the Food Diary!", d.getMessage());
			assertNull(foodDiary);
		}
	}
	
	/**
	 * Test that a patient can view his food diary.
	 */
	@Test
	public void testViewingFoodDiaryAsPatient() {
		action = new ViewFoodEntryAction(factory, 334);
		try {
			List<FoodEntryBean> foodDiary = action.getDiary(334);
			assertEquals(2, foodDiary.size());
			FoodEntryBean entry1 = foodDiary.get(0);
			FoodEntryBean entry2 = foodDiary.get(1);
			//now that we know we have 2 of them, 
			//make sure they are the right ones
			assertEquals("09/30/2012", entry1.getDateEatenStr().toString());
			assertEquals("Breakfast", entry1.getMealType().name());
			assertEquals("Hot dog", entry1.getFood());
			assertEquals(4.0, entry1.getServings(), .001);
			assertEquals(80.0, entry1.getCalories(), .001);
			assertEquals(5.0, entry1.getFatGrams(), .001);
			assertEquals(480.0, entry1.getMilligramsSodium(), .001);
			assertEquals(2.0, entry1.getCarbGrams(), .001);
			assertEquals(0.0, entry1.getFiberGrams(), .001);
			assertEquals(0.0, entry1.getSugarGrams(), .001);
			assertEquals(5.0, entry1.getProteinGrams(), .001);
			assertEquals(334, entry1.getPatientID());
			
			assertEquals("09/30/2012", entry2.getDateEatenStr().toString());
			assertEquals("Lunch", entry2.getMealType().name());
			assertEquals("Mango Passionfruit Juice", entry2.getFood());
			assertEquals(1.2, entry2.getServings(), .001);
			assertEquals(130.0, entry2.getCalories(), .001);
			assertEquals(0.0, entry2.getFatGrams(), .001);
			assertEquals(25.0, entry2.getMilligramsSodium(), .001);
			assertEquals(32.0, entry2.getCarbGrams(), .001);
			assertEquals(0.0, entry2.getFiberGrams(), .001);
			assertEquals(29.0, entry2.getSugarGrams(), .001);
			assertEquals(1.0, entry2.getProteinGrams(), .001);
			assertEquals(334, entry1.getPatientID());
			
			//now check the totals
			List<FoodEntryBean> totals = action.getDiaryTotals(334);
			assertEquals(1, totals.size());
			FoodEntryBean total = totals.get(0);
			assertEquals(476.0, total.getCalories(), .001);
			assertEquals(20.0, total.getFatGrams(), .001);
			assertEquals(1950.0, total.getMilligramsSodium(), .001);
			assertEquals(46.4, total.getCarbGrams(), .001);
			assertEquals(0.0, total.getFiberGrams(), .001);
			assertEquals(34.8, total.getSugarGrams(), .001);
			assertEquals(21.2, total.getProteinGrams(), .001);
		} catch (ITrustException e) {
			fail(e.getMessage());
		}
	}
	
	/**
	 * Ensures a patient can still view his food diary even when it is empty.
	 */
	@Test
	public void testViewingEmptyFoodDiaryAsPatient() {
		action = new ViewFoodEntryAction(factory, 333);
		try {
			List<FoodEntryBean> foodDiary = action.getDiary(333);
			assertEquals(0, foodDiary.size());
		} catch (ITrustException e) {
			fail(e.getMessage());
		}
	}
	
	/**
	 * Ensures HCPs with specialty of nutritionist can still view a food diary
	 * even when the food diary is empty.
	 */
	@Test
	public void testViewingEmptyFoodDiaryAsHCP() {
		action = new ViewFoodEntryAction(factory, 9000000071L);
		try {
			List<FoodEntryBean> foodDiary = action.getDiary(333);
			assertEquals(0, foodDiary.size());
		} catch (ITrustException e) {
			fail(e.getMessage());
		}
	}
	
	/**
	 * Ensure patients can't view the food diary of other patients.
	 */
	@Test
	public void testViewFoodDiaryOfOtherPatient() {
		action = new ViewFoodEntryAction(factory, 333);
		try {
			action.getDiary(334);
			fail("You should not be able to view other patient's food diary.");
		} catch (ITrustException e) {
			assertEquals("You do not have permission to view the Food Diary!",
					e.getMessage());
		}
	}
	
	/**
	 * Test getting a bounded food diary
	 */
	@Test
	public void testBoundedFoodDiary() {
		//aaron has multiple days
		action = new ViewFoodEntryAction(factory, 335);
		try {
			List<FoodEntryBean> beans = 
					action.getBoundedDiary("04/13/2014", "04/13/2014", 335);
			assertEquals(1, beans.size());
			FoodEntryBean entry = beans.get(0);
			assertEquals("Snack", entry.getMealType().toString());
			assertEquals("Oreos", entry.getFood());
			assertEquals(53.0, entry.getServings(), .001);
			assertEquals(140.0, entry.getCalories(), .001);
			assertEquals(7.0, entry.getFatGrams(), .001);
			assertEquals(90.0, entry.getMilligramsSodium(), .001);
			assertEquals(21.0, entry.getCarbGrams(), .001);
			assertEquals(13.0, entry.getSugarGrams(), .001);
			assertEquals(1.0, entry.getFiberGrams(), .001);
			assertEquals(0.0, entry.getProteinGrams(), .001);
			
			//now get the totals
			List<FoodEntryBean> totals = 
					action.getBoundedDiaryTotals("04/13/2014", "04/13/2014", 335);
			assertEquals(1, totals.size());
			FoodEntryBean total = totals.get(0);
			assertEquals(7420.0, total.getCalories(), .001);
			assertEquals(371.0, total.getFatGrams(), .001);
			assertEquals(4770.0, total.getMilligramsSodium(), .001);
			assertEquals(1113.0, total.getCarbGrams(), .001);
			assertEquals(53.0, total.getFiberGrams(), .001);
			assertEquals(689.0, total.getSugarGrams(), .001);
			assertEquals(0.0, total.getProteinGrams(), .001);
		} catch (ITrustException d) {
			fail("Why the error?");
		} catch (FormValidationException d) {
			fail("No errors in dates");
		}
	}
	
	/**
	 * Test start date after end date
	 */
	@Test
	public void testStartAfterEnd() {
		action = new ViewFoodEntryAction(factory, 335);
		try {
			action.getBoundedDiary("12/12/2014", "12/10/2014", 335);
			fail("Start date after end date");
		} catch (ITrustException d) {
			fail("Why the error?");
		} catch (FormValidationException e) {
			assertEquals("This form has not been validated correctly. "
					+ "The following field are not properly filled in: "
					+ "[Start date must be before end date!]", 
					e.getMessage());
		}
	}
	
	/**
	 * Test start date after end date
	 */
	@Test
	public void testStartAfterEndTotals() {
		action = new ViewFoodEntryAction(factory, 335);
		try {
			action.getBoundedDiaryTotals("12/12/2014", "12/10/2014", 335);
			fail("Start date after end date");
		} catch (ITrustException d) {
			fail("Why the error?");
		} catch (FormValidationException e) {
			assertEquals("This form has not been validated correctly. "
					+ "The following field are not properly filled in: "
					+ "[Start date must be before end date!]", 
					e.getMessage());
		}
	}
	
	/**
	 * Ensure patients can't view the bounded food diary of other patients.
	 */
	@Test
	public void testViewFoodDiaryOfOtherPatientBounded() {
		action = new ViewFoodEntryAction(factory, 333);
		try {
			action.getBoundedDiary("12/12/2014", "12/12/2014", 334);
			fail("You should not be able to view other patient's food diary.");
		} catch (ITrustException e) {
			assertEquals("You do not have permission to view the Food Diary!",
					e.getMessage());
		} catch (FormValidationException d) {
			fail("Not your food diary");
		}
	}
	
	/**
	 * Ensure patients can't view the bounded food diary of other patients.
	 */
	@Test
	public void testViewFoodDiaryOfOtherPatientBoundedTotals() {
		action = new ViewFoodEntryAction(factory, 333);
		try {
			action.getBoundedDiaryTotals("12/12/2014", "12/12/2014", 334);
			fail("You should not be able to view other patient's food diary.");
		} catch (ITrustException e) {
			assertEquals("You do not have permission to view the Food Diary!",
					e.getMessage());
		} catch (FormValidationException d) {
			fail("Not your food diary");
		}
	}
	
	/**
	 * Test bounded diary with bad dates
	 */
	@Test
	public void testFoodDiaryBadDates() {
		action = new ViewFoodEntryAction(factory, 1);
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
	 * Test bounded diary totals with bad dates
	 */
	@Test
	public void testFoodDiaryBadDatesTotals() {
		action = new ViewFoodEntryAction(factory, 1);
		try {
			action.getBoundedDiaryTotals("", "", 1);
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
	 * Try to view it as a nutritionist that isn't designated
	 */
	@Test
	public void testNonDesignatedNutritionist() {
		action = new ViewFoodEntryAction(factory, 9000000071L);
		try {
			action.getDiary(1);
			fail("Not his designated nutritionist");
		} catch (ITrustException e) {
			assertEquals("You do not have permission to view the Food Diary!",
					e.getMessage());
		}
	}
	
	/**
	 * Try to view it as a nutritionist that isn't designated
	 */
	@Test
	public void testNonDesignatedNutritionistTotals() {
		action = new ViewFoodEntryAction(factory, 9000000071L);
		try {
			action.getDiaryTotals(1);
			fail("Not his designated nutritionist");
		} catch (ITrustException e) {
			assertEquals("You do not have permission to view the Food Diary!",
					e.getMessage());
		}
	}
	
	/**
	 * Try to view it as a nutritionist that isn't designated
	 */
	@Test
	public void testNonDesignatedNutritionistBounded() {
		action = new ViewFoodEntryAction(factory, 9000000071L);
		try {
			action.getBoundedDiary("02/02/2014", "02/02/2014", 1);
			fail("Not his designated nutritionist");
		} catch (ITrustException e) {
			assertEquals("You do not have permission to view the Food Diary!",
					e.getMessage());
		} catch (FormValidationException d) {
			fail(d.getMessage());
		}
	}
	
	/**
	 * Try to view it as a nutritionist that isn't designated
	 */
	@Test
	public void testNonDesignatedNutritionistBoundedTotals() {
		action = new ViewFoodEntryAction(factory, 9000000071L);
		try {
			action.getBoundedDiaryTotals("02/02/2014", "02/02/2014", 1);
			fail("Not his designated nutritionist");
		} catch (ITrustException e) {
			assertEquals("You do not have permission to view the Food Diary!",
					e.getMessage());
		} catch (FormValidationException d) {
			fail(d.getMessage());
		}
	}
	
	/**
	 * Test with evil factory
	 */
	@Test
	public void testEvilFactoryGetDiary() {
		EvilDAOFactory evil = new EvilDAOFactory();
		action = new ViewFoodEntryAction(evil, 1);
		try {
			action.getDiary(1);
			fail("Working with evil factory");
		} catch (ITrustException d) {
			assertEquals("Error retrieving Food Diary", d.getMessage());
		}
	}
	
	/**
	 * Test with evil factory
	 */
	@Test
	public void testEvilFactoryGetDiaryTotals() {
		EvilDAOFactory evil = new EvilDAOFactory();
		action = new ViewFoodEntryAction(evil, 1);
		try {
			action.getDiaryTotals(1);
			fail("Working with evil factory");
		} catch (ITrustException d) {
			assertEquals("Error retrieving Food Diary", d.getMessage());
		}
	}
	
	/**
	 * Test with evil factory
	 */
	@Test
	public void testEvilFactoryGetBoundedDiary() {
		EvilDAOFactory evil = new EvilDAOFactory();
		action = new ViewFoodEntryAction(evil, 1);
		try {
			action.getBoundedDiary("", "", 1);
			fail("Working with evil factory");
		} catch (ITrustException d) {
			assertEquals("Error retrieving Food Diary", d.getMessage());
		} catch (FormValidationException e) {
			fail(e.getMessage());
		}
	}
	
	/**
	 * Test with evil factory
	 */
	@Test
	public void testEvilFactoryGetBoundedDiaryTotals() {
		EvilDAOFactory evil = new EvilDAOFactory();
		action = new ViewFoodEntryAction(evil, 1);
		try {
			action.getBoundedDiaryTotals("", "", 1);
			fail("Working with evil factory");
		} catch (ITrustException d) {
			assertEquals("Error retrieving Food Diary", d.getMessage());
		} catch (FormValidationException e) {
			fail(e.getMessage());
		}
	}
}
