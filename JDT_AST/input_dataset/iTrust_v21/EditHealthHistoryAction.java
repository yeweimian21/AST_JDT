package edu.ncsu.csc.itrust.action;

import java.util.Calendar;
import java.util.List;
import edu.ncsu.csc.itrust.action.base.EditOfficeVisitBaseAction;
import edu.ncsu.csc.itrust.beans.HealthRecord;
import edu.ncsu.csc.itrust.beans.OfficeVisitBean;
import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.beans.forms.HealthRecordForm;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.AuthDAO;
import edu.ncsu.csc.itrust.dao.mysql.HealthRecordsDAO;
import edu.ncsu.csc.itrust.dao.mysql.OfficeVisitDAO;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.validate.HealthRecordFormValidator;

/**
 * Edits the health history of a patient, used by viewBasicHealth.jsp
 * 
 * 
 */
public class EditHealthHistoryAction extends EditOfficeVisitBaseAction {
	
	/** The number of months in a year */
	public static final int MONTHS_IN_YEAR = 12;
	/** The number of months in 3 years */
	public static final int MONTHS_IN_THREE_YEARS = 36;
	/** The number of months in 12 years */
	public static final int MONTHS_IN_TWELVE_YEARS = 144;
	
	private HealthRecordsDAO hrDAO;
	private AuthDAO authDAO;
	private PatientDAO patDAO;
	private OfficeVisitDAO ovDAO;
	private int patientAge;
	private HealthRecordFormValidator validator = new HealthRecordFormValidator();
	
	private EventLoggingAction loggingAction;

	/**
	 * The patient ID is validated by the superclass
	 * 
	 * @param factory The DAOFactory which will be used to generate the DAOs used for this action.
	 * @param loggedInMID The user authorizing this action.
	 * @param pidString The patient (or other user) who is being edited.
	 * @throws ITrustException
	 */
	public EditHealthHistoryAction(DAOFactory factory, long loggedInMID, String pidString)
			throws ITrustException {
		super(factory, loggedInMID, pidString);
		
		init(factory);
	}
	
	/**
	 * 
	 * @param factory
	 * @param hcpid
	 * @param pidString
	 * @param ovIDString
	 * @throws ITrustException
	 */	
	public EditHealthHistoryAction(DAOFactory factory, long loggedInMID, String pidString, String ovIDString) throws ITrustException {
		super(factory, loggedInMID, pidString, ovIDString);
		
		init(factory);
	}
	
	private void init(DAOFactory factory){
		hrDAO = factory.getHealthRecordsDAO();
		authDAO = factory.getAuthDAO();
		patDAO = factory.getPatientDAO();
		ovDAO = factory.getOfficeVisitDAO();
		
		loggingAction = new EventLoggingAction(factory);		
	}
	
	/**
	 * returns the patient name
	 * 
	 * @return patient name
	 * @throws DBException
	 * @throws ITrustException
	 */
	public String getPatientName() throws DBException, ITrustException {
		return authDAO.getUserName(pid);
	}
	
	/**
	 * Sets patient age for form validation when adding a new health record
	 * 
	 * @param patientAge Age passed in from the EditOVBasicHealth.jsp
	 */
	public void setPatientAge(int patientAge){
		this.patientAge = patientAge;
	}

	/**
	 * Adds a health record for the given patient
	 * 
	 * @param pid  The patient record who is being edited.
	 * @param hr  The filled out health record form to be added.
	 * @param ovID  The office visit id of the office visit from where the health record is added from
	 * @return message - "Information Recorded" or exception's message
	 * @throws FormValidationException
	 */
	public String addHealthRecord(long pid, HealthRecordForm hr, long ovID) throws FormValidationException,
			ITrustException {	
		
		HealthRecord record;
		
		if(patientAge < MONTHS_IN_THREE_YEARS){
			validator.validateBaby(hr);
			record = transferFormBaby(pid, hr);
		}
		else if(patientAge < MONTHS_IN_TWELVE_YEARS){
			validator.validateYouth(hr);
			record = transferFormYouth(pid, hr);
		}
		else{
			validator.validate(hr);
			record = transferForm(pid, hr);
		}
		
		record.setOfficeVisitID(ovID);
		
		OfficeVisitBean ov = ovDAO.getOfficeVisit(ovID);
		record.setOfficeVisitDateStr(ov.getVisitDateStr());
		
		//If there is already an existing health record with the specified office visit ID
		if (getHealthRecordByOfficeVisit(ovID) != null) {
			//Remove the health record before adding a new one
			hrDAO.remove(record);
			loggingAction.logEvent(TransactionType.EDIT_BASIC_HEALTH_METRICS, super.getHcpid(), pid, "");
		} else {
			loggingAction.logEvent(TransactionType.CREATE_BASIC_HEALTH_METRICS, super.getHcpid(), pid, "");
		}
		hrDAO.add(record);
		return "Information Recorded";
	}
	
	
	/**
	 * Moves the information from the form to a HealthRecord for a baby
	 * 
	 * @param pid Patient of interest
	 * @param form Form to be translated
	 * @return A HealthRecord containing all the information in the form
	 * @throws FormValidationException
	 */
	private HealthRecord transferFormBaby(long pid, HealthRecordForm form) throws FormValidationException{
		HealthRecord record = new HealthRecord();
		record.setPatientID(pid);
		record.setPersonnelID(super.getHcpid());
		record.setHeight(Double.valueOf(form.getHeight()));
		record.setWeight(Double.valueOf(form.getWeight()));
		record.setHeadCircumference(Double.valueOf(form.getHeadCircumference()));
		record.setHouseholdSmokingStatus(Integer.valueOf(form.getHouseholdSmokingStatus()));
		
		return record;
	}
	
	/**
	 * Moves the information from the form to a HealthRecord for a youth
	 * 
	 * @param pid Patient of interest
	 * @param form Form to be translated
	 * @return A HealthRecord containing all the information in the form
	 * @throws FormValidationException
	 */
	private HealthRecord transferFormYouth(long pid, HealthRecordForm form) throws FormValidationException{
		HealthRecord record = new HealthRecord();
		record.setPatientID(pid);
		record.setPersonnelID(super.getHcpid());
		record.setBloodPressureD(Integer.valueOf(form.getBloodPressureD()));
		record.setBloodPressureN(Integer.valueOf(form.getBloodPressureN()));
		record.setHeight(Double.valueOf(form.getHeight()));
		record.setWeight(Double.valueOf(form.getWeight()));
		record.setHouseholdSmokingStatus(Integer.valueOf(form.getHouseholdSmokingStatus()));
		
		return record;
	}

	
	/**
	 * Moves the information from the form to a HealthRecord for an adult
	 * 
	 * @param pid Patient of interest
	 * @param form Form to be translated
	 * @return A HealthRecord containing all the information in the form
	 * @throws FormValidationException
	 */
	private HealthRecord transferForm(long pid, HealthRecordForm form) throws FormValidationException {
		HealthRecord record = new HealthRecord();
		record.setPatientID(pid);
		record.setPersonnelID(super.getHcpid());
		record.setBloodPressureD(Integer.valueOf(form.getBloodPressureD()));
		record.setBloodPressureN(Integer.valueOf(form.getBloodPressureN()));
		record.setCholesterolHDL(Integer.valueOf(form.getCholesterolHDL()));
		record.setCholesterolLDL(Integer.valueOf(form.getCholesterolLDL()));
		record.setCholesterolTri(Integer.valueOf(form.getCholesterolTri()));
		if (record.getTotalCholesterol() < 100 || record.getTotalCholesterol() > 600)
			throw new FormValidationException("Total cholesterol must be in [100,600]");
		record.setHeight(Double.valueOf(form.getHeight()));
		record.setWeight(Double.valueOf(form.getWeight()));
		record.setSmoker(Integer.valueOf(form.getIsSmoker()));
		record.setHouseholdSmokingStatus(Integer.valueOf(form.getHouseholdSmokingStatus()));
		return record;
	}

	/**
	 * Returns a list of all HealthRecords for the given patient
	 * 
	 * @param pid  The ID of the patient to look up
	 * @return list of HealthRecords
	 * @throws ITrustException
	 */
	public List<HealthRecord> getAllHealthRecords(long pid) throws ITrustException {
		loggingAction.logEvent(TransactionType.VIEW_BASIC_HEALTH_METRICS, super.getHcpid(), pid, "");
		return hrDAO.getAllHealthRecords(pid);
	}
	
	
	/**
	 * Returns a HealthRecord related to a specific office visit ID
	 * 
	 * @param ovID The office visit ID containing the health record of interest
	 * @return Health record specific to office visit ID
	 * @throws DBException
	 */
	public HealthRecord getHealthRecordByOfficeVisit(long ovID) throws DBException {
		return hrDAO.getHealthRecordByOfficeVisit(ovID);
	}
	
	/**
	 * 
	 * Removes a specific HealthRecord by office visit ID
	 * 
	 * @param hr The HealthRecord to be removed
	 * @return A boolean indicating whether the removal was successful (true) or unsuccessful (false)
	 * @throws DBException
	 */
	public boolean removeHealthRecord(HealthRecord hr) throws DBException {
		loggingAction.logEvent(TransactionType.REMOVE_BASIC_HEALTH_METRICS, super.getHcpid(), pid, "");
		return hrDAO.remove(hr);
	}
	
	/**
	 * Get patient's age in months by taking an office visit date and comparing it with the patient's
	 * date of birth. 
	 * 
	 * @param officeVisitDate The date of the office visit
	 * @return the patient's age in months
	 * @throws DBException
	 */
	public int getPatientAgeInMonths(Calendar officeVisitDate) throws DBException {
		//Create int for patient's age in months
		int ageInMonths = 0;
		
		//Get the patient's birthdate
		Calendar birthDate = Calendar.getInstance();
		PatientBean patient = patDAO.getPatient(pid);
		birthDate.setTime(patient.getDateOfBirth());
		
		//Split the patient's birthdate into day, month, and year
		int birthDay = birthDate.get(Calendar.DAY_OF_MONTH);
		int birthMonth = birthDate.get(Calendar.MONTH) + 1;
		int birthYear = birthDate.get(Calendar.YEAR);
		//Split the office visit date into day month and year
		int visitDay = officeVisitDate.get(Calendar.DAY_OF_MONTH);
		int visitMonth = officeVisitDate.get(Calendar.MONTH) + 1;
		int visitYear = officeVisitDate.get(Calendar.YEAR);
		
		//Calculate the year, month, and day differences
		int yearDiff = visitYear - birthYear;
		int monthDiff = visitMonth - birthMonth;
		int dayDiff = visitDay - birthDay;
		
		//Get the patient's age in months by multiplying the year difference by 12
		//and adding the month difference
		ageInMonths = yearDiff * MONTHS_IN_YEAR + monthDiff;
		
		//If the day difference is negative, subtract a month from the age
		if (dayDiff < 0) {
			ageInMonths -= 1;
		}
		
		//Return the age in months
		return ageInMonths;
	}
	
}
