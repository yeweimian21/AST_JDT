package edu.ncsu.csc.itrust.action;

import java.util.Calendar;
import java.util.List;
import edu.ncsu.csc.itrust.action.base.OfficeVisitBaseAction;
import edu.ncsu.csc.itrust.beans.DiagnosisBean;
import edu.ncsu.csc.itrust.beans.HealthRecord;
import edu.ncsu.csc.itrust.beans.LabProcedureBean;
import edu.ncsu.csc.itrust.beans.OfficeVisitBean;
import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.beans.PrescriptionBean;
import edu.ncsu.csc.itrust.beans.ProcedureBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.DiagnosesDAO;
import edu.ncsu.csc.itrust.dao.mysql.HealthRecordsDAO;
import edu.ncsu.csc.itrust.dao.mysql.LabProcedureDAO;
import edu.ncsu.csc.itrust.dao.mysql.OfficeVisitDAO;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO;
import edu.ncsu.csc.itrust.dao.mysql.PrescriptionsDAO;
import edu.ncsu.csc.itrust.dao.mysql.ProceduresDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.Messages;

/**
 * Handles viewing the office visits, prescriptions, and HCP name for the given ovID Used by
 * viewOfficeVisit.jsp
 * 
 * 
 */
public class ViewOfficeVisitAction extends OfficeVisitBaseAction {
	
	/** The number of months in a year */
	public static final int MONTHS_IN_YEAR = 12;
	
	private OfficeVisitDAO ovDAO;
	private HealthRecordsDAO healthRecordsDAO;
	private PrescriptionsDAO prescriptionsDAO;
	private ProceduresDAO proceduresDAO;
	private LabProcedureDAO labProceduresDAO;
	private DiagnosesDAO diagnosesDAO;
	
	private PersonnelDAO personnelDAO;
	private PatientDAO patientDAO;

	/**
	 * Super class handles validating the loggedInMid and ovIDString
	 * 
	 * @param factory The DAOFactory used to create the DAOs used in this action.
	 * @param loggedInMID The MID of the person viewing their office visits. 
	 * @param ovIDString
	 *            The unique identifier of the office visit as a String.
	 * @throws ITrustException
	 */
	public ViewOfficeVisitAction(DAOFactory factory, long loggedInMID, String ovIDString)
			throws ITrustException {
		super(factory, String.valueOf(loggedInMID), ovIDString);
		this.patientDAO = factory.getPatientDAO();
		this.personnelDAO = factory.getPersonnelDAO();
		this.ovDAO = factory.getOfficeVisitDAO();
		
		healthRecordsDAO = factory.getHealthRecordsDAO();
		prescriptionsDAO = factory.getPrescriptionsDAO();
		proceduresDAO = factory.getProceduresDAO();
		labProceduresDAO = factory.getLabProcedureDAO();
		diagnosesDAO = factory.getDiagnosesDAO();
	}

	/**
	 * Super class handles validating the pidString and ovIDString. Usually used for representing a patient.
	 * 
	 * @param factory The DAOFactory used to create the DAOs used in this action.
	 * @param repPIDString The MID of the representative viewing the records.
	 * @param ovIDString
	 *            The unique identifier of the office visit as a String.
	 * @throws ITrustException
	 */
	public ViewOfficeVisitAction(DAOFactory factory, long loggedInMID, String repPIDString, String ovIDString)
			throws ITrustException {
		super(factory, repPIDString, ovIDString);
		this.personnelDAO = factory.getPersonnelDAO();
		this.patientDAO = factory.getPatientDAO();
		this.ovDAO = factory.getOfficeVisitDAO();
		
		healthRecordsDAO = factory.getHealthRecordsDAO();
		prescriptionsDAO = factory.getPrescriptionsDAO();
		proceduresDAO = factory.getProceduresDAO();
		labProceduresDAO = factory.getLabProcedureDAO();
		diagnosesDAO = factory.getDiagnosesDAO();
		
		checkRepresented(loggedInMID, repPIDString);
	}

	private void checkRepresented(long loggedInMID, String repPIDString) throws ITrustException {
		try {
			long repee = Long.valueOf(repPIDString);
			if (!patientDAO.represents(loggedInMID, repee))
				throw new ITrustException(
						Messages.getString("ViewOfficeVisitAction.0")); //$NON-NLS-1$
		} catch (NumberFormatException e) {
			throw new ITrustException(Messages.getString("ViewOfficeVisitAction.1")); //$NON-NLS-1$
		}
	}

	/**
	 * Returns the office visit as an OfficeVisitBean for the ovID that was initially passed to the
	 * constructor
	 * 
	 * @return the OfficeVisitBean for the ovID
	 * @throws ITrustException
	 */
	public OfficeVisitBean getOfficeVisit() throws ITrustException {
		return ovDAO.getOfficeVisit(ovID);
	}

	/**
	 * Returns the prescriptions associated with the ovID initially passed to the constructor
	 * 
	 * @return list of PrescriptionBeans for the ovID
	 * @throws DBException
	 */
	public List<PrescriptionBean> getPrescriptions() throws DBException {
		return prescriptionsDAO.getList(ovID);
	}

	public List<ProcedureBean> getAllProcedures() throws DBException {
		return proceduresDAO.getList(ovID);
	}

	public List<ProcedureBean> getProcedures() throws DBException {
		return proceduresDAO.getMedProceduresList(ovID);
	}
	
	public List<ProcedureBean> getImmunizations() throws DBException {
		return proceduresDAO.getImmunizationList(ovID);
	}
	
	public List<DiagnosisBean> getDiagnoses() throws DBException {
		return diagnosesDAO.getList(ovID);
	}
	
	public List<LabProcedureBean> getLabProcedures() throws DBException {
		return labProceduresDAO.getLabProceduresForPatientOV(ovID);
	}
	
	/**
	 * Returns the HealthRecord related to this office visit's ID
	 * 
	 * @return Health record specific to office visit ID
	 * @throws DBException
	 */
	public HealthRecord getHealthRecord() throws DBException {
		return healthRecordsDAO.getHealthRecordByOfficeVisit(ovID);
	}
	
	public void setViewed(List<LabProcedureBean> procs) throws DBException {
		for (LabProcedureBean b : procs) {
			b.setViewedByPatient(true);
			labProceduresDAO.markViewed(b);
		}
	}

	/**
	 * Returns the name of the HCP for the hcpID passed as a param
	 * 
	 * @param hcpID
	 * @return the name of the HCP
	 * @throws ITrustException
	 */
	public String getHCPName(long hcpID) throws ITrustException {
		String name = null;
		try {
			name = personnelDAO.getName(hcpID);
		} catch (ITrustException e) {
			
			name = e.getMessage();
		}
		return name;
			
	}
	
	/**
	 * Get patient's age in months by taking the office visit date and comparing it with the patient's
	 * date of birth. 
	 * 
	 * @return the patient's age in months
	 * @throws ITrustException 
	 */
	public int getPatientAgeInMonths() throws ITrustException {
		//Create int for patient's age in months
		int ageInMonths = 0;
		
		//Get the office visit date
		Calendar officeVisitDate = Calendar.getInstance();
		officeVisitDate.setTime(getOfficeVisit().getVisitDate());
				
		//Get the patient's birthdate
		Calendar birthDate = Calendar.getInstance();
		PatientBean patient = patientDAO.getPatient(pid);
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
