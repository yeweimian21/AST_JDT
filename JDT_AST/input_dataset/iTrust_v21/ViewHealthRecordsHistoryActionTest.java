package edu.ncsu.csc.itrust.unit.action;

import java.util.List;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.action.ViewHealthRecordsHistoryAction;
import edu.ncsu.csc.itrust.beans.CDCStatsBean;
import edu.ncsu.csc.itrust.beans.HealthRecord;
import edu.ncsu.csc.itrust.beans.NormalBean;
import edu.ncsu.csc.itrust.beans.OfficeVisitBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.CDCBmiStatsDAO;
import edu.ncsu.csc.itrust.dao.mysql.CDCHeadCircStatsDAO;
import edu.ncsu.csc.itrust.dao.mysql.CDCHeightStatsDAO;
import edu.ncsu.csc.itrust.dao.mysql.CDCWeightStatsDAO;
import edu.ncsu.csc.itrust.dao.mysql.HealthRecordsDAO;
import edu.ncsu.csc.itrust.dao.mysql.NormalDAO;
import edu.ncsu.csc.itrust.dao.mysql.OfficeVisitDAO;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class ViewHealthRecordsHistoryActionTest extends TestCase{
	
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen = new TestDataGenerator();
	private ViewHealthRecordsHistoryAction action;

	@Override
	protected void setUp() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		
		action = new ViewHealthRecordsHistoryAction(factory, "102", 9000000001L);
	}
	
	public void testGetPatientID() throws Exception{			
		action = new ViewHealthRecordsHistoryAction(factory, "101", 101L);
		long pid = action.getPatientID();
		assertEquals(101, pid);		
	}
	
	public void testGetPatientName() throws Exception{
		action = new ViewHealthRecordsHistoryAction(factory, "101", 101L);
		String patientName = action.getPatientName();
		assertEquals("Brynn McClain", patientName);		
		
	}
	
	public void testGetAllPatientHealthRecords() throws Exception{
		OfficeVisitDAO ovDAO = factory.getOfficeVisitDAO();		
		OfficeVisitBean ovBean = new OfficeVisitBean(453);
		ovBean.setPatientID(101L);
		ovBean.setERIncident(false);
		ovBean.setHcpID(9000000000L);
		ovBean.setHospitalID("1");
		ovBean.setNotes("");
		ovBean.setVisitDateStr("09/30/2013");
		long ovID = ovDAO.add(ovBean);
		
		HealthRecordsDAO hrDAO = factory.getHealthRecordsDAO();
		HealthRecord hr = new HealthRecord();
		hr.setPatientID(101L);
		hr.setHeight(20.1);
		hr.setWeight(22.3);
		hr.setHeadCircumference(36.2);
		hr.setHouseholdSmokingStatus(1);
		hr.setOfficeVisitID(ovID);
		hr.setOfficeVisitDateStr("10/01/2013");
		hrDAO.add(hr);
		
		action = new ViewHealthRecordsHistoryAction(factory, "101", 101L);
		List<HealthRecord> testHR = action.getAllPatientHealthRecords();
		assertEquals(hr.getPatientID(), testHR.get(0).getPatientID());
		assertEquals(hr.getHeight(), testHR.get(0).getHeight());
		assertEquals(hr.getWeight(), testHR.get(0).getWeight());
		assertEquals(hr.getHeadCircumference(), testHR.get(0).getHeadCircumference());
		assertEquals(hr.getOfficeVisitID(), testHR.get(0).getOfficeVisitID());
		assertEquals(hr.getVisitDateStr(), testHR.get(0).getVisitDateStr());
		
	}
	
	public void testCalculateAgeInMonthsAtOfficeVisit() throws Exception{
		OfficeVisitDAO ovDAO = factory.getOfficeVisitDAO();
		OfficeVisitBean ovBean = new OfficeVisitBean(454);
		ovBean.setPatientID(101L);
		ovBean.setERIncident(false);
		ovBean.setHcpID(9000000000L);
		ovBean.setHospitalID("1");
		ovBean.setNotes("");
		ovBean.setVisitDateStr("09/30/2013");
		long ovID = ovDAO.add(ovBean);
		
		action = new ViewHealthRecordsHistoryAction(factory, "101", 101L);
		
		assertEquals(4, action.calculateAgeInMonthsAtOfficeVisit(ovID));
	}
	
	public void testGetPercentile() throws DBException {
		NormalDAO normalDAO = new NormalDAO(factory);
		
		double leastPercent = 0;
		double maxPercent = 100;
		double tooLowScore =  -999;
		double tooHighScore = 999;
		assertEquals(maxPercent, action.getPercentile(tooHighScore));
		assertEquals(leastPercent, action.getPercentile(tooLowScore));
		
		double fiftyPercent = 50;
		double midScore = 0;
		assertEquals(fiftyPercent, action.getPercentile(midScore));
		
		//Check all values contained in the normal bean
		double testScore = 2.00;
		NormalBean normalValue = normalDAO.getNormal(testScore);
		double testPercent = Double.parseDouble(String.format("%.2f", normalValue.get_00() * 100));
		assertEquals(testPercent, action.getPercentile(testScore));
		testPercent = Double.parseDouble(String.format("%.2f", normalValue.get_01() * 100));
		assertEquals(testPercent, action.getPercentile(testScore + .01));
		testPercent = Double.parseDouble(String.format("%.2f", normalValue.get_02() * 100));
		assertEquals(testPercent, action.getPercentile(testScore + .02));
		testPercent = Double.parseDouble(String.format("%.2f", normalValue.get_03() * 100));
		assertEquals(testPercent, action.getPercentile(testScore + .03));
		testPercent = Double.parseDouble(String.format("%.2f", normalValue.get_04() * 100));
		assertEquals(testPercent, action.getPercentile(testScore + .04));
		testPercent = Double.parseDouble(String.format("%.2f", normalValue.get_05() * 100));
		assertEquals(testPercent, action.getPercentile(testScore + .05));
		testPercent = Double.parseDouble(String.format("%.2f", normalValue.get_06() * 100));
		assertEquals(testPercent, action.getPercentile(testScore + .06));
		testPercent = Double.parseDouble(String.format("%.2f", normalValue.get_07() * 100));
		assertEquals(testPercent, action.getPercentile(testScore + .07));
		testPercent = Double.parseDouble(String.format("%.2f", normalValue.get_08() * 100));
		assertEquals(testPercent, action.getPercentile(testScore + .08));
		testPercent = Double.parseDouble(String.format("%.2f", normalValue.get_09() * 100));
		assertEquals(testPercent, action.getPercentile(testScore + .09));
	}
	
	public void testGetWeightZScore() throws ITrustException {
		List<HealthRecord> records = action.getAllPatientHealthRecords();
		HealthRecord testRecord = records.get(0);
		CDCWeightStatsDAO cdcStatsDAO = new CDCWeightStatsDAO(factory);
		PatientDAO patientDAO = new PatientDAO(factory);
		
		//Get the weight associated with the health record and convert into kg
		double weight = testRecord.getWeight() / 2.2046;
		//Get the patient's gender
		int sex = 0;
		if (patientDAO.getPatient(102L).getGender().toString().equals("Male")) {
			sex = 1;
		} else {
			sex = 2;
		}
		//Get the patient's age
		float age = 0;
		if (testRecord.getVisitDate().equals(patientDAO.getPatient(102L).getDateOfBirth())) {
			//If the patient's birthdate equals the office visit date, 
			//the patient's age is 0 months
			age = (float) 0.0;
		} else {
			//Otherwise add 0.5 to the patient's age in months
			age = (float) (action.calculateAgeInMonthsAtOfficeVisit(testRecord.getOfficeVisitID()) + 0.5);
		}
		CDCStatsBean testBean = cdcStatsDAO.getCDCStats(sex, age);
		
		//Get the L, M, and S values
		double L = testBean.getL();
		double M = testBean.getM();
		double S = testBean.getS();
		
		//Calculate the z score
		double testScore;
		if (L == 0.0) {
			//If L is 0 then calculate the z score differently
			testScore = Math.log(weight / M) / S;
		} else {
			testScore = (Math.pow((weight / M), L) - 1) / (L * S);
		} 
		testScore = Double.parseDouble(String.format("%.2f", testScore));
		
		assertEquals(testScore, action.getWeightZScore(testRecord));
	}

	public void testGetHeightZScore() throws ITrustException {
		List<HealthRecord> records = action.getAllPatientHealthRecords();
		HealthRecord testRecord = records.get(0);
		CDCHeightStatsDAO cdcStatsDAO = new CDCHeightStatsDAO(factory);
		PatientDAO patientDAO = new PatientDAO(factory);
		
		//Get the height associated with the health record and convert into cm
		double height = testRecord.getHeight() / 0.3937;
		//Get the patient's gender
		int sex = 0;
		if (patientDAO.getPatient(102L).getGender().toString().equals("Male")) {
			sex = 1;
		} else {
			sex = 2;
		}
		//Get the patient's age
		float age = 0;
		if (testRecord.getVisitDate().equals(patientDAO.getPatient(102L).getDateOfBirth())) {
			//If the patient's birthdate equals the office visit date, 
			//the patient's age is 0 months
			age = (float) 0.0;
		} else {
			//Otherwise add 0.5 to the patient's age in months
			age = (float) (action.calculateAgeInMonthsAtOfficeVisit(testRecord.getOfficeVisitID()) + 0.5);
		}
		CDCStatsBean testBean = cdcStatsDAO.getCDCStats(sex, age);
		
		//Get the L, M, and S values
		double L = testBean.getL();
		double M = testBean.getM();
		double S = testBean.getS();
		
		//Calculate the z score
		double testScore;
		if (L == 0.0) {
			//If L is 0 then calculate the z score differently
			testScore = Math.log(height / M) / S;
		} else {
			testScore = (Math.pow((height / M), L) - 1) / (L * S);
		} 
		testScore = Double.parseDouble(String.format("%.2f", testScore));
		
		assertEquals(testScore, action.getHeightZScore(testRecord));
	}
	
	public void testGetHeadCircZScore() throws ITrustException {
		List<HealthRecord> records = action.getAllPatientHealthRecords();
		HealthRecord testRecord = records.get(0);
		CDCHeadCircStatsDAO cdcStatsDAO = new CDCHeadCircStatsDAO(factory);
		PatientDAO patientDAO = new PatientDAO(factory);
		
		//Get the head circumference associated with the health record and convert into cm
		double headCirc = testRecord.getHeadCircumference() / 0.3937;
		//Get the patient's gender
		int sex = 0;
		if (patientDAO.getPatient(102L).getGender().toString().equals("Male")) {
			sex = 1;
		} else {
			sex = 2;
		}
		//Get the patient's age
		float age = 0;
		if (testRecord.getVisitDate().equals(patientDAO.getPatient(102L).getDateOfBirth())) {
			//If the patient's birthdate equals the office visit date, 
			//the patient's age is 0 months
			age = (float) 0.0;
		} else {
			//Otherwise add 0.5 to the patient's age in months
			age = (float) (action.calculateAgeInMonthsAtOfficeVisit(testRecord.getOfficeVisitID()) + 0.5);
		}
		CDCStatsBean testBean = cdcStatsDAO.getCDCStats(sex, age);
		
		//Get the L, M, and S values
		double L = testBean.getL();
		double M = testBean.getM();
		double S = testBean.getS();
		
		//Calculate the z score
		double testScore;
		if (L == 0.0) {
			//If L is 0 then calculate the z score differently
			testScore = Math.log(headCirc / M) / S;
		} else {
			testScore = (Math.pow((headCirc / M), L) - 1) / (L * S);
		} 
		testScore = Double.parseDouble(String.format("%.2f", testScore));
		
		assertEquals(testScore, action.getHeadCircZScore(testRecord));
	}
	
	public void testGetBmiZScore() throws ITrustException {
		List<HealthRecord> records = action.getAllPatientHealthRecords();
		HealthRecord testRecord = records.get(0);
		CDCBmiStatsDAO cdcStatsDAO = new CDCBmiStatsDAO(factory);
		PatientDAO patientDAO = new PatientDAO(factory);
		
		//Get the bmi associated with the health record
		double bmi = testRecord.getBodyMassIndex();
		//Get the patient's gender
		int sex = 0;
		if (patientDAO.getPatient(102L).getGender().toString().equals("Male")) {
			sex = 1;
		} else {
			sex = 2;
		}
		//Get the patient's age
		float age = 0;
		if (testRecord.getVisitDate().equals(patientDAO.getPatient(102L).getDateOfBirth())) {
			//If the patient's birthdate equals the office visit date, 
			//the patient's age is 0 months
			age = (float) 0.0;
		} else {
			//Otherwise add 0.5 to the patient's age in months
			age = (float) (action.calculateAgeInMonthsAtOfficeVisit(testRecord.getOfficeVisitID()) + 0.5);
		}
		CDCStatsBean testBean = cdcStatsDAO.getCDCStats(sex, age);
		
		//Get the L, M, and S values
		double L = testBean.getL();
		double M = testBean.getM();
		double S = testBean.getS();
		
		//Calculate the z score
		double testScore;
		if (L == 0.0) {
			//If L is 0 then calculate the z score differently
			testScore = Math.log(bmi / M) / S;
		} else {
			testScore = (Math.pow((bmi / M), L) - 1) / (L * S);
		} 
		testScore = Double.parseDouble(String.format("%.2f", testScore));
		
		assertEquals(testScore, action.getBMIZScore(testRecord));
	}
}




