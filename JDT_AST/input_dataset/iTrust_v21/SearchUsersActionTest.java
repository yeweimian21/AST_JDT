package edu.ncsu.csc.itrust.unit.action;

import java.util.List;

import edu.ncsu.csc.itrust.action.EditPatientAction;
import edu.ncsu.csc.itrust.action.SearchUsersAction;
import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.beans.PersonnelBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;

/**
 * SearchUsersActionTest
 */
public class SearchUsersActionTest extends TestCase {
	private TestDataGenerator gen = new TestDataGenerator();
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private DAOFactory evil = EvilDAOFactory.getEvilInstance();
	@Override
	public void setUp() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		gen.patientDeactivate();
	}
	
	/**
	 * testSearchForPatienstWithName
	 */
	public void testSearchForPatientsWithName(){
		SearchUsersAction act = new SearchUsersAction(factory, 9000000000L);
		List<PatientBean> patients = act.searchForPatientsWithName("Random", "Person");
		assertEquals("Random Person", patients.get(0).getFullName());
	}
	
	/**
	 * testSearchForPatietnsWithName2
	 */
	public void testSearchForPatientsWithName2(){
		SearchUsersAction act = new SearchUsersAction(factory, 9000000003L);
		List<PatientBean> patient = act.searchForPatientsWithName("Andy", "Programmer");
		assertEquals("Andy Programmer", patient.get(0).getFullName());
	}
	
	/**
	 * testSearchForPatientsWithName3
	 */
	public void testSearchForPatientsWithName3(){
		SearchUsersAction act = new SearchUsersAction(factory, 9000000003L);
		List<PatientBean> patient = act.searchForPatientsWithName("", "");
		assertEquals(0, patient.size());
	}
	
	/**
	 * testFuzzySearchForPatient1
	 */
	public void testFuzzySearchForPatient1(){
		SearchUsersAction act = new SearchUsersAction(factory, 9000000003L);
		List<PatientBean> patient = act.fuzzySearchForPatients("Andy");
		assertEquals("Andy Programmer", patient.get(0).getFullName());
	}
	
	/**
	 * testFuzzySearchForPatient2
	 */
	public void testFuzzySearchForPatient2(){
		SearchUsersAction act = new SearchUsersAction(factory, 9000000003L);
		List<PatientBean> patient = act.fuzzySearchForPatients("nd grammer");
		assertEquals("Andy Programmer", patient.get(0).getFullName());
	}
	
	/**
	 * testFuzzySearchForPatient3
	 */
	public void testFuzzySearchForPatient3(){
		SearchUsersAction act = new SearchUsersAction(factory, 9000000003L);
		List<PatientBean> patient = act.fuzzySearchForPatients("2");
		assertEquals("Andy Programmer", patient.get(0).getFullName());
	}
	
	public void testFuzzySearchForPatientDeactivated(){
		SearchUsersAction act = new SearchUsersAction(factory, 9000000003L);
		List<PatientBean> patient = act.fuzzySearchForPatients("314159");
		assertEquals(patient.size(), 0);
	}
	
	/**
	 * testFuzzySearchForPatientDeactivatedOverride
	 */
	public void testFuzzySearchForPatientDeactivatedOverride(){
		SearchUsersAction act = new SearchUsersAction(factory, 9000000003L);
		List<PatientBean> patient = act.fuzzySearchForPatients("314159", true);
		assertEquals("Fake Baby", patient.get(0).getFullName());
	}
	
	/**
	 * testSearchForPersonnelWithName
	 */
	public void testSearchForPersonnelWithName(){
		SearchUsersAction act = new SearchUsersAction(factory, 9000000000L);
		List<PersonnelBean> personnel = act.searchForPersonnelWithName("Kelly", "Doctor");
		assertEquals("Kelly Doctor", personnel.get(0).getFullName());
	}
	
	/**
	 * testSearchForPersonnelWithName2
	 */
	public void testSearchForPersonnelWithName2(){
		SearchUsersAction act = new SearchUsersAction(factory, 9000000003L);
		List<PersonnelBean> personnel = act.searchForPersonnelWithName("", "");
		assertEquals(0, personnel.size());
	}
	
	/**
	 * testSearchForPersonnelWithName3
	 */
	public void testSearchForPersonnelWithName3(){
		SearchUsersAction act = new SearchUsersAction(evil, 2L);
		List<PersonnelBean> personnel = act.searchForPersonnelWithName("null", "null");
		assertEquals(null, personnel);
	}
	
	
	
	@Override
	public void tearDown() throws Exception {
	}
	
	/**
	 * testZeroPatients
	 * @throws Exception
	 */
	public void testZeroPatients() throws Exception {
		SearchUsersAction action = new SearchUsersAction(factory, 9990000000L);
		List<PatientBean> patients = action.searchForPatientsWithName("A", "B");
		assertNotNull(patients);
		assertEquals(0, patients.size());
	}
	
	public void testDeactivated() throws Exception{
		SearchUsersAction action = new SearchUsersAction(factory, 9990000000L);
		EditPatientAction dis = new EditPatientAction(factory, 1L, "1");
		dis.deactivate();
		List<PatientBean> deact = action.getDeactivated();
		assertEquals(2, deact.size());
		assertNotNull(deact.get(0).getDateOfDeactivationStr());
		assertNotNull(deact.get(1).getDateOfDeactivationStr());
	}

	/**
	 * testFuzzySearchForExpert
	 */
	public void testFuzzySearchForExpert(){
		SearchUsersAction act = new SearchUsersAction(factory, 9000000003L);
		List<PersonnelBean> patient = act.fuzzySearchForExperts("Andy");
		assertEquals(0, patient.size());
		patient = act.fuzzySearchForExperts("Gandal");
		assertEquals(1, patient.size());
		assertEquals("Gandalf", patient.get(0).getFirstName());
	}
}
