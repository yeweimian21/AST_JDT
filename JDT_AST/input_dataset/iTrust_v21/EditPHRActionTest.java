package edu.ncsu.csc.itrust.unit.action;

import java.util.List;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.action.EditPHRAction;
import edu.ncsu.csc.itrust.action.ViewMyRecordsAction;
import edu.ncsu.csc.itrust.beans.AllergyBean;
import edu.ncsu.csc.itrust.beans.FamilyMemberBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.FamilyDAO;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class EditPHRActionTest extends TestCase {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen;
	private EditPHRAction action;
	private FamilyDAO famDAO = new FamilyDAO(factory);
	private List<FamilyMemberBean> fmbList = null;
	private FamilyMemberBean fmBean = null;

	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.standardData();
		
		fmbList = famDAO.getParents(5);
		fmBean = fmbList.get(0);
	}

	public void testConstructPoorly() throws Exception {
		try {
			action = new EditPHRAction(factory, 9000000000L, "500");
			fail("exception should have been thrown");
		} catch (ITrustException e) {
			assertEquals("Patient does not exist", e.getMessage());
		}
		// I know it's bad form to have two test in one, but it's so we don't have to redo the database
		try {
			action = new EditPHRAction(factory, 9000000000L, "<br />");
			fail("exception should have been thrown");
		} catch (ITrustException e) {
			assertEquals("Patient ID is not a number: &lt;br /&gt;", e.getMessage());
		}
	}

	public void testObtainInformation() throws Exception {
		action = new EditPHRAction(factory, 9000000000L, "2");
		// Check that the correct call was made - more thorough assertions are in the DAO tests
		assertEquals(2L, action.getPid());
		assertEquals(2L, action.getPatient().getMID());
		assertEquals(2, action.getAllergies().size());
		assertEquals(9, action.getFamily().size());
		assertEquals(2, action.getAllHealthRecords().size());
		assertEquals(12, action.getAllOfficeVisits().size());
		gen.clearTransactionLog();
		assertEquals(2, action.getDiseasesAtRisk().size());
		
		assertTrue(action.doesFamilyMemberHaveHighBP(fmBean));
		assertTrue(action.doesFamilyMemberHaveDiabetes(fmBean));
		assertTrue(action.isFamilyMemberSmoker(fmBean));
		assertFalse(action.doesFamilyMemberHaveCancer(fmBean));
		assertFalse(action.doesFamilyMemberHaveHighCholesterol(fmBean));
		assertFalse(action.doesFamilyMemberHaveHeartDisease(fmBean));
		assertTrue(action.getFamilyMemberCOD(fmBean).contains(""));
		assertEquals(5, new ViewMyRecordsAction(factory, 5L).getFamilyHistory().size());
		
	}

	public void testUpdateAllergies() throws Exception {
		action = new EditPHRAction(factory, 9000000000L, "2");
		action.updateAllergies(2L, "Midichlominene");
		List<AllergyBean> allergies = action.getAllergies();
		assertEquals(3, allergies.size());
		assertEquals("Midichlominene", allergies.get(0).getDescription());
	}
	
	public void testAllergyExistingPrescription() throws Exception {
		action = new EditPHRAction(factory, 9000000000L, "100");
		String result = action.updateAllergies(100L, "Midichlominene");
		assertTrue(result.contains("is currently prescribed to"));
	}
}
