package edu.ncsu.csc.itrust.unit.action;

import java.util.List;

import org.junit.Test;

import edu.ncsu.csc.itrust.action.DesignateNutritionistAction;
import edu.ncsu.csc.itrust.action.ViewFoodEntryAction;
import edu.ncsu.csc.itrust.beans.PersonnelBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;

public class DesignateNutritionistActionTest extends TestCase {

	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen;
	private DesignateNutritionistAction action;
	
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.standardData();
	}
	
	protected void tearDown() throws Exception {
		gen.clearAllTables();
	}
	
	/**
	 * Patient Derek Morgan has Spencer Reid
	 * as his designated nutritionist and no entries in food table
	 */
	public void testDeleteNutritionist() {
		//spencer reid viewaction to make sure she can view it now
		ViewFoodEntryAction viewAction = 
				new ViewFoodEntryAction(factory, 9000000071L);
		try {
			assertEquals(0, viewAction.getDiary(333).size());
		} catch (ITrustException d) {
			fail("Failed viewing diary when able to");
		}
		action = new DesignateNutritionistAction(factory, 333);
		try {
			int updated = action.updateNutritionist(-1); //-1 means delete nutritionist
			assertEquals(1, updated);
		} catch (ITrustException d) {
			fail("Failed deleting nutritionist");
		}
		//now has no nutritionist, so reid should not be able to view it
		try {
			assertEquals(0, viewAction.getDiary(333).size());
		} catch (ITrustException d) {
			assertEquals("You do not have permission to view the Food"
					+ " Diary!", d.getMessage());
		}
	}
	
	/**
	 * Derek Morgan has Spencer Reid as nutritionist
	 * and chooses to make her his nutritionist
	 */
	public void testChangeNutritionistToSameNutritionist() {
		//spencer reid viewaction to make sure she can view it now
		ViewFoodEntryAction viewAction = 
				new ViewFoodEntryAction(factory, 9000000071L);
		try {
			assertEquals(0, viewAction.getDiary(333).size());
		} catch (ITrustException d) {
			fail("Failed viewing diary when able to");
		}
		action = new DesignateNutritionistAction(factory, 333);
		try {
			int updated = action.updateNutritionist(9000000071L); //same nutritionist
			assertEquals(-1, updated);
		} catch (ITrustException d) {
			fail("Failed deleting nutritionist");
		}
		//should still be able to view diary
		try {
			assertEquals(0, viewAction.getDiary(333).size());
		} catch (ITrustException d) {
			fail("Failed viewing diary");
		}
	}
	
	/**
	 * login as a patient without a designated nutritionist,
	 * and add one
	 */
	public void testAddNutritionist() {
		//spencer reid viewaction to make sure she can view it now
		ViewFoodEntryAction viewAction = 
				new ViewFoodEntryAction(factory, 9000000071L);
		try {
			assertEquals(0, viewAction.getDiary(1).size());
			fail("Can view food diary");
		} catch (ITrustException d) {
			assertEquals("You do not have permission to view the Food"
					+ " Diary!", d.getMessage());	
		}
		action = new DesignateNutritionistAction(factory, 1);
		try {
			int updated = action.updateNutritionist(9000000071L);
			assertEquals(1, updated);
		} catch (ITrustException d) {
			fail("Could not add HCP");
		}
		try {
			assertEquals(0, viewAction.getDiary(1).size());
		} catch (ITrustException d) {
			fail("Failed viewing diary");
		}
	}
	
	/**
	 * Try to add as a nutritionist a non HCP
	 */
	public void testNonHCP() {
		action = new DesignateNutritionistAction(factory, 333);
		try {
			int updated = action.updateNutritionist(333);//1 is a patient
			assertEquals(-2, updated);
		} catch (ITrustException d) {
			fail("Failed adding nonHCP");
		}
	}
	
	/**
	 * Try to add as nutritionist an hcp that isn't a nutritionist
	 */
	public void testAddNonNutritionist() {
		action = new DesignateNutritionistAction(factory, 333);
		try {
			int updated = action.updateNutritionist(9000000000L);
			assertEquals(-2, updated);
		} catch (ITrustException d) {
			fail("Added non nutritionist");
		}
	}
	
	/**
	 * Test that it returns the right number of nutritionist specialists.
	 */
	public void testNumberNutritionists() {
		action = new DesignateNutritionistAction(factory, 333);
		try {
			List<PersonnelBean> nutrition = action.getAllNutritionists();
			assertEquals(3, nutrition.size());
			//first one
			assertEquals("Spencer", nutrition.get(0).getFirstName());
			assertEquals(9000000071L, nutrition.get(0).getMID());
			//second one
			assertEquals("Ben", nutrition.get(1).getFirstName());
			assertEquals(9000000072L, nutrition.get(1).getMID());
			//third one
			assertEquals("Colonel", nutrition.get(2).getFirstName());
			assertEquals(9000000073L, nutrition.get(2).getMID());
		} catch (ITrustException d) {
			fail("Not correct nutritionists");
		}
	}
	
	/**
	 * Test switching to a new nutritionist
	 */
	public void testSwitchNutritionist() {
		//spencer reid viewaction to make sure she can view it now
		ViewFoodEntryAction viewAction = 
				new ViewFoodEntryAction(factory, 9000000071L);
		ViewFoodEntryAction viewAction2 = 
				new ViewFoodEntryAction(factory, 9000000072L);
		try {
			assertEquals(0, viewAction.getDiary(333).size());
		} catch (ITrustException d) {
			fail("Failed viewing diary when able to");
		}
		try {
			assertEquals(0, viewAction2.getDiary(333).size());
			fail("Unauthorized nutritionist");
		} catch (ITrustException d) {
			assertEquals("You do not have permission to view the "
					+ "Food Diary!", d.getMessage());
		}
		action = new DesignateNutritionistAction(factory, 333);
		try {
			//change nutritionist to ben matlock
			int updated = action.updateNutritionist(9000000072L);
			assertEquals(1, updated);
		} catch (ITrustException d) {
			fail("Failed switching nutritionist");
		}
		//now make sure matlock can see
		try {
			assertEquals(0, viewAction2.getDiary(333).size());
		} catch (ITrustException d) {
			fail("Failed viewing diary when able to");
		}
		try {
			assertEquals(0, viewAction.getDiary(333).size());
			fail("Unauthorized nutritionist");
		} catch (ITrustException d) {
			assertEquals("You do not have permission to view the "
					+ "Food Diary!", d.getMessage());
		} 
	}
	
	/**
	 * Test with an evil factory
	 */
	@Test
	public void testEvilFactoryGetAllNutritionists() {
		EvilDAOFactory evil = new EvilDAOFactory();
		action = new DesignateNutritionistAction(evil, 1);
		try {
			action.getAllNutritionists();
			fail("Working with evil factory");
		} catch (ITrustException d) {
			assertEquals("Error retrieving HCPs", d.getMessage());
		}		
	}
	
	/**
	 * Test with an evil factory
	 */
	@Test
	public void testEvilFactoryUpdateNutritionist() {
		EvilDAOFactory evil = new EvilDAOFactory();
		action = new DesignateNutritionistAction(evil, 1);
		try {
			action.updateNutritionist(33);
			fail("Working with evil factory");
		} catch (ITrustException d) {
			assertEquals("Error retrieving information", d.getMessage());
		}		
	}
	
	/**
	 * Test with an evil factory
	 */
	@Test
	public void testEvilFactoryGetDesignatedNutritionist() {
		EvilDAOFactory evil = new EvilDAOFactory();
		action = new DesignateNutritionistAction(evil, 1);
		try {
			action.getDesignatedNutritionist();
			fail("Working with evil factory");
		} catch (ITrustException d) {
			assertEquals("Error retrieving information", d.getMessage());
		}		
	}
}
