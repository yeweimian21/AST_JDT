/**
 * 
 */
package edu.ncsu.csc.itrust.unit.action;

import edu.ncsu.csc.itrust.action.EditPHRAction;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;

/**
 * 
 * This test class test EditPHRAction, specifically adding allergies.
 * 
 */
public class EditPHRActionTestTwo extends TestCase {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen;
	private EditPHRAction actionPHR;
	private String result;
		
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.standardData();
	}

	/**
	 * Test method for {@link edu.ncsu.csc.itrust.action.EditPHRAction#updateAllergies(long, java.lang.String)}.
	 * 
	 * 
	 * This method tests that a warning is shown for a future prescription.
	 * 
	 * @throws Exception
	 */
	public final void testUpdateAllergiesFutRX() throws Exception {
		actionPHR = new EditPHRAction(factory, 9000000000L, "100"); //HCP Kelly Doctor, patient Anakin Skywalker
		//result = actionPHR.updateAllergies(100L, "483012382"); //Adds M-minene
		result = actionPHR.updateAllergies(100L, "Midichlominene"); //Adds M-minene
		assertTrue(result.contains("is currently")); //Should return warning
	}

	/**
	 * Test method for {@link edu.ncsu.csc.itrust.action.EditPHRAction#updateAllergies(long, java.lang.String)}.
	 * 
	 * 
	 * This method tests that no warning is shown.
	 * 
	 * @throws Exception
	 */
	public final void testUpdateAllergiesNoRX() throws Exception {
		actionPHR = new EditPHRAction(factory, 9000000000L, "100"); //HCP Kelly Doctor, patient Trend Setter
		//result = actionPHR.updateAllergies(100L, "483013420"); //Adds M-maxene
		result = actionPHR.updateAllergies(100L, "Midichlomaxene"); //Adds M-maxene
		assertTrue(result.contains("Allergy Added")); //Should return no warning
	}

	/**
	 * Test method for {@link edu.ncsu.csc.itrust.action.EditPHRAction#updateAllergies(long, java.lang.String)}.
	 * 
	 * This method tests that a warning is shown when adding a duplicate allergy.
	 * 
	 * @throws Exception
	 */
	public final void testUpdateAllergiesDuplicateAllergy() throws Exception {
		actionPHR = new EditPHRAction(factory, 9000000000L, "100"); //HCP Kelly Doctor, patient Trend Setter
		//actionPHR.updateAllergies(100L, "483013420"); //Adds M-maxene
		//result = actionPHR.updateAllergies(100L, "483013420"); //Adds M-maxene again
		actionPHR.updateAllergies(100L, "Midichlomaxene"); //Adds M-maxene
		result = actionPHR.updateAllergies(100L, "midichlomaxene"); //Adds M-maxene again (lowercase)
		assertTrue(result.contains("has already")); //Should return warning
	}


	
	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
