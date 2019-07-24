package edu.ncsu.csc.itrust.unit.dao.foodEntry;

import java.io.IOException;
import java.sql.SQLException;

import junit.framework.TestCase;

import org.junit.Test;

import edu.ncsu.csc.itrust.beans.FoodEntryBean;
import edu.ncsu.csc.itrust.dao.mysql.FoodEntryDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;

/**
 * Tests that the dao throws exceptions when created with bad factory
 *
 */
public class FoodEntryDAOTest extends TestCase {
	
	private TestDataGenerator gen = new TestDataGenerator();
	private EvilDAOFactory evil;
	private FoodEntryDAO foodDAO;
	
	
	/**
	 * Clears all of the tables, sets the standard data and includes 
	 * the data for use cases 68 and 69
	 */
	@Override
	public void setUp() throws SQLException, IOException {
		gen.clearAllTables();
		gen.standardData();
		//gen.uc68(); //uc68 is now a part of standardData
		evil = new EvilDAOFactory(0);
		foodDAO = new FoodEntryDAO(evil);
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
	public void testGetFoodDiaryWithEvilDAO() {
		try {
			foodDAO.getPatientFoodDiary(333);
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
	public void testAddToFoodDiaryWithEvilDAO() {
		try {
			FoodEntryBean bean = new FoodEntryBean();
			bean.setDateEatenStr("12/12/2012");
			bean.setMealType("Lunch");
			bean.setFood("Ice Cream");
			bean.setCalories(5);
			bean.setCarbGrams(3);
			bean.setFatGrams(3);
			bean.setFiberGrams(3);
			bean.setMilligramsSodium(3);
			bean.setProteinGrams(3);
			bean.setServings(3);
			bean.setSugarGrams(3);
			foodDAO.addFoodEntry(bean);
			fail("Should have thrown an exception");
		} catch (DBException e) {
			assertEquals("A database exception has occurred. Please see the "
					+ "log in the console for stacktrace", e.getMessage());
		} catch (ITrustException d) {
			fail("Should not have been an ITrustException");
		}
	}
}
