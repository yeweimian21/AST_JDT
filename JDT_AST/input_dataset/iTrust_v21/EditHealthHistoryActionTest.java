package edu.ncsu.csc.itrust.unit.action;

import java.util.Calendar;
import java.util.List;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.action.EditHealthHistoryAction;
import edu.ncsu.csc.itrust.beans.HealthRecord;
import edu.ncsu.csc.itrust.beans.forms.HealthRecordForm;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class EditHealthHistoryActionTest extends TestCase {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen;
	private EditHealthHistoryAction action;

	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.patient1();
		gen.patient2();
		gen.uc51();
		action = new EditHealthHistoryAction(factory, 9000000000L, "2");
	}
		
	public void testAddToEmptyRecordSimpleBaby() throws Exception {
		assertEquals(2, action.getAllHealthRecords(1L).size());
		HealthRecordForm hr = new HealthRecordForm();
		hr.setHeadCircumference("8.0");
		hr.setHouseholdSmokingStatus("1");
		hr.setHeight("36.0");
		hr.setWeight("25.0");
		action.setPatientAge(12);
		String confirm = action.addHealthRecord(1L, hr, 960);
		assertEquals("Information Recorded", confirm);
		List<HealthRecord> records = action.getAllHealthRecords(1L);
		assertEquals(3, records.size());
	}
	
	public void testAddToEmptyRecordSimpleYouth() throws Exception {
		assertEquals(2, action.getAllHealthRecords(1L).size());
		HealthRecordForm hr = new HealthRecordForm();
		hr.setBloodPressureN("999");
		hr.setBloodPressureD("999");
		hr.setHouseholdSmokingStatus("1");
		hr.setHeight("36.0");
		hr.setWeight("25.0");
		action.setPatientAge(136);
		String confirm = action.addHealthRecord(1L, hr, 960);
		assertEquals("Information Recorded", confirm);
		List<HealthRecord> records = action.getAllHealthRecords(1L);
		assertEquals(3, records.size());
	}

	public void testAddToEmptyRecordSimple() throws Exception {
		assertEquals(2, action.getAllHealthRecords(1L).size());
		HealthRecordForm hr = new HealthRecordForm();
		hr.setBloodPressureN("999");
		hr.setBloodPressureD("999");
		hr.setCholesterolHDL("50");
		hr.setCholesterolLDL("50");
		hr.setCholesterolTri("499");
		hr.setHeight("65.2");
		hr.setWeight("9999.9");
		hr.setIsSmoker("1");
		hr.setHouseholdSmokingStatus("1");
		action.setPatientAge(200);
		String confirm = action.addHealthRecord(1L, hr, 960);
		assertEquals("Information Recorded", confirm);
		List<HealthRecord> records = action.getAllHealthRecords(1L);
		assertEquals(3, records.size());
		// Note that we don't need to check the returned values here - see the DAO test, AddPHRTest
	}
	
	public void testAddRemoveHealthRecord() throws Exception {
		HealthRecordForm hr = new HealthRecordForm();
		hr.setHeadCircumference("8.0");
		hr.setHouseholdSmokingStatus("1");
		hr.setHeight("36.0");
		hr.setWeight("25.0");
		action.setPatientAge(12);		
		String confirm = action.addHealthRecord(1L, hr, 961);
		assertEquals("Information Recorded", confirm);
		List<HealthRecord> records = action.getAllHealthRecords(1L);
		assertEquals(3, records.size());
		HealthRecord rec = action.getHealthRecordByOfficeVisit(961);
		boolean result = action.removeHealthRecord(rec);
		assertEquals(true, result);
	}
	
	public void testGetPatientAgeBaby() throws Exception {
		Calendar ovDate = Calendar.getInstance();
		ovDate.set(2013, 5, 1); //Set to June 1st, 2013
		
		action = new EditHealthHistoryAction(factory, 9000000000L, "101"); //May 1st, 2013
		assertEquals(1, action.getPatientAgeInMonths(ovDate));
	}

	public void testTotalCholesterol() throws Exception {
		HealthRecordForm hr = new HealthRecordForm();
		hr.setBloodPressureN("999");
		hr.setBloodPressureD("999");
		hr.setCholesterolHDL("50");
		hr.setCholesterolLDL("50");
		hr.setCholesterolTri("500");
		hr.setHeight("65.2");
		hr.setWeight("9999.9");
		action.setPatientAge(200);
		action.addHealthRecord(2L, hr, 960);
		hr.setCholesterolTri("501");
		try {
			action.addHealthRecord(2L, hr, 1);
			fail("exception should have been thrown");
		} catch (FormValidationException e) {
			assertEquals("Total cholesterol must be in [100,600]", e.getErrorList().get(0));
			assertEquals(1, e.getErrorList().size());
		}
	}

	public void testPatientNameNull() throws Exception {
		assertNotNull(action.getPatientName());
	}

	public void testAddHealthRecordEvil() throws Exception {
		//Correct form data
		HealthRecordForm hr = new HealthRecordForm();
		hr.setBloodPressureN("999");
		hr.setBloodPressureD("999");
		hr.setCholesterolHDL("50");
		hr.setCholesterolLDL("50");
		hr.setCholesterolTri("499");
		hr.setHeight("65.2");
		hr.setWeight("9999.9");
		hr.setIsSmoker("1");
		action = new EditHealthHistoryAction(new EvilDAOFactory(1), 9000000000L, "2");
		action.setPatientAge(200);
		try {
			action.addHealthRecord(1L, hr, 0);
		} catch (ITrustException e) {
			DBException dbe = (DBException) e;
			assertEquals(EvilDAOFactory.MESSAGE, dbe.getSQLException().getMessage());
		}
	}
}
