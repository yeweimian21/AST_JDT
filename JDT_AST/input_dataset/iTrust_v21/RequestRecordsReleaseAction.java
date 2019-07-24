package edu.ncsu.csc.itrust.action;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import edu.ncsu.csc.itrust.beans.HospitalBean;
import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.beans.RecordsReleaseBean;
import edu.ncsu.csc.itrust.beans.forms.RecordsReleaseForm;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.HospitalsDAO;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.dao.mysql.RecordsReleaseDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.validate.RecordsReleaseFormValidator;

public class RequestRecordsReleaseAction {
	
	/** Error message for if there is a database exception*/
	public static final String DB_ERROR = "Error: There was an error in the database";
	/** Error message for if there is no digital signature on a request form*/
	public static final String SIGNATURE_ERROR = "Error: Digital signature does not match name on record";
	/** Success message for if the request form is successfully submitted */
	public static final String SUCCESS_MESSAGE = "Request successfully sent";
	
	/** RecordsReleaseDAO object for working with record releases in the database*/
	private RecordsReleaseDAO rrDAO;
	/** PatientDAO for working with patient objects in the database*/
	private PatientDAO patDAO;
	/** HospitalsDAO for getting hospital objects from the database*/
	private HospitalsDAO hosDAO;
	
	/** RecordsReleaseFormValidator to validate records release forms*/
	private RecordsReleaseFormValidator validator = new RecordsReleaseFormValidator();
	/** Long for storing the patient's mid */
	private long pid;
	
	/**
	 * Constructor for RequestRecordsReleaseAction. Gets and initializes all necessary DAOs for
	 * working with requesting a patient's records.
	 */
	public RequestRecordsReleaseAction(DAOFactory factory, long pid) {
		rrDAO = new RecordsReleaseDAO(factory);
		patDAO = factory.getPatientDAO();
		hosDAO = factory.getHospitalsDAO();
		
		this.pid = pid;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getPatientName() {
		String name = "";
		try {
			name = patDAO.getName(pid);
		} catch (DBException e1) {
			e1.printStackTrace();
		} catch (ITrustException e2) {
			e2.printStackTrace();
		}
		
		return name;
	}
	
	public String getHospitalName(String hospitalID) {
		String name = "";
		try {
			HospitalBean hospital = hosDAO.getHospital(hospitalID);
			if (hospital != null)
				name = hospital.getHospitalName();
		} catch (DBException e1) {
			e1.printStackTrace();
		}
		
		return name;
	}
	
	/**
	 * 
	 * @return
	 */
	public String addRecordsRelease(RecordsReleaseForm form) {
		RecordsReleaseBean release;
		try {
			//Validate the form
			validator.validate(form);
			//Check that there is a digital signature
			if (!form.getDigitalSignature())
				return SIGNATURE_ERROR;
			//Transfer the form to a RecordsReleaseBean object
			release = transferForm(form);
			//Add the bean to the dao
			rrDAO.addRecordsRelease(release);
		} catch (FormValidationException e1) {
			String errorMsg = "";
			//Check that there is a digital signature
			if (!form.getDigitalSignature())
				errorMsg = ", " + SIGNATURE_ERROR.substring(SIGNATURE_ERROR.indexOf(' ') + 1);
			//If a form validation exception is thrown, indicate the release could not be added
			return e1.getMessage() + errorMsg;
		} catch (DBException e2) {
			//If a DBException is thrown, indicate the release could not be added
			return DB_ERROR;
		}
		
		//Indicate that the release request was successfully added 
		return SUCCESS_MESSAGE;
	}
	
	public List<RecordsReleaseBean> getAllPatientReleaseRequests() {
		List<RecordsReleaseBean> releases = new ArrayList<RecordsReleaseBean>();
		
		try {
			releases = rrDAO.getAllRecordsReleasesByPid(pid);
		} catch (DBException e) {
			e.printStackTrace();
		}
		
		return releases;
	}
	
	public List<HospitalBean> getAllPatientHospitals() {
		List<HospitalBean> hospitals = new ArrayList<HospitalBean>();
		try {
			//Get all hospitals where the patient has visited
			hospitals = hosDAO.getAllPatientHospitals(pid);
		} catch (DBException e) {
			//If a DBException is thrown, print a stack trace and return an empty list
			e.printStackTrace();
		}
		
		return hospitals;
	}
	
	private RecordsReleaseBean transferForm(RecordsReleaseForm form) {
		RecordsReleaseBean release = new RecordsReleaseBean();
		
		//Set the date to the current time
		release.setDateRequested(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		//Set the pid
		release.setPid(pid);
		//Set the hospital id
		release.setReleaseHospitalID(form.getReleaseHospitalID());
		//Set the recipient hospital name and address
		release.setRecHospitalName(form.getRecipientHospitalName());
		release.setRecHospitalAddress(form.getRecipientHospitalAddress());
		//Set the receiving doctor's name 
		release.setDocFirstName(form.getRecipientFirstName());
		release.setDocLastName(form.getRecipientLastName());
		//Set the receiving doctor's phone and email
		release.setDocEmail(form.getRecipientEmail());
		release.setDocPhone(form.getRecipientPhone());
		//Set the justification comment
		release.setJustification(form.getRequestJustification());
		//Set the status of the request to pending
		release.setStatus(0);
		
		return release;
		
	}
	
	/**
	 * Returns a list of PatientBeans of all patients the currently logged in patient represents
	 * 
	 * @return a list of PatientBeans of all patients the currently logged in patient represents
	 */
	public List<PatientBean> getRepresented() {
		List<PatientBean> represented = new ArrayList<PatientBean>();
		try {
			represented = patDAO.getRepresented(pid);
		} catch (DBException e) {
			//If a DBException occurs print a stack trace and return an empty list
			e.printStackTrace();
		}
		return represented;
	}
	
	/**
	 * Returns a list of PatientBeans of all patients the currently logged in patient represents and are a dependent
	 * 
	 * @return a list of PatientBeans of all patients the currently logged in patient represents and are a dependent
	 */
	public List<PatientBean> getDependents() {
		List<PatientBean> dependents = new ArrayList<PatientBean>();
		try {
			dependents = patDAO.getDependents(pid);
		} catch (DBException e) {
			//If a DBException occurs print a stack trace and return an empty list
			e.printStackTrace();
		}
		
		return dependents;
	}
}
