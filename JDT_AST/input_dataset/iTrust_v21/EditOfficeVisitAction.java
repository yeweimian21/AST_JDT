package edu.ncsu.csc.itrust.action;

import java.util.Arrays;
import java.util.List;
import edu.ncsu.csc.itrust.action.base.EditOfficeVisitBaseAction;
import edu.ncsu.csc.itrust.beans.Email;
import edu.ncsu.csc.itrust.beans.HospitalBean;
import edu.ncsu.csc.itrust.beans.OfficeVisitBean;
import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.beans.forms.EditOfficeVisitForm;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.HospitalsDAO;
import edu.ncsu.csc.itrust.dao.mysql.OfficeVisitDAO;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.validate.EditOfficeVisitValidator;

/**
 * Edits the office visits of a patient Used by editOfficeVisit.jsp.  This 
 * exists in two states: saved and unsaved.  If unsaved, data cannot be saved 
 * to sub actions (if this is attempted, exceptions will be raised).  Once it 
 * is saved, however, the sub actions can be saved.  
 * 
 *  
 * 
 */
public class EditOfficeVisitAction extends EditOfficeVisitBaseAction {
	
	private EditOfficeVisitValidator validator = new EditOfficeVisitValidator();
	private PersonnelDAO personnelDAO;
	private HospitalsDAO hospitalDAO;
	private OfficeVisitDAO ovDAO;
	private PatientDAO patDAO;
	
	private EditHealthHistoryAction healthRecordAction;
	private EditPrescriptionsAction prescriptionsAction;
	private EditProceduresAction proceduresAction;
	private EditImmunizationsAction immunizationsAction;
	private EditDiagnosesAction diagnosesAction;
	private EditLabProceduresAction labProceduresAction;
	private EditPatientInstructionsAction patientInstructionsAction;
	private EditReferralsAction referralsAction;
	
	private EventLoggingAction loggingAction;
	
	private long loggedInMID;


	/**
	 * Patient id and office visit id validated by super class
	 * 
	 * @param factory The DAOFactory to be used in creating the DAOs for this action.
	 * @param loggedInMID The MID of the user who is authorizing this action.
	 * @param pidString The patient who this action is performed on.
	 * @param ovIDString The ID of the office visit in play.
	 * @throws ITrustException
	 */
	public EditOfficeVisitAction(DAOFactory factory, long loggedInMID, String pidString, String ovIDString)
			throws ITrustException {
		super(factory, loggedInMID, pidString, ovIDString);
		pid = Long.parseLong(pidString);
		ovDAO = factory.getOfficeVisitDAO();
		this.personnelDAO = factory.getPersonnelDAO();
		this.hospitalDAO = factory.getHospitalsDAO();
		this.patDAO = factory.getPatientDAO();
		
		this.healthRecordAction = new EditHealthHistoryAction(factory, loggedInMID, pidString, ovIDString);
		this.prescriptionsAction = new EditPrescriptionsAction(factory, loggedInMID, pidString, ovIDString);
		this.proceduresAction = new EditProceduresAction(factory, loggedInMID, pidString, ovIDString);
		this.immunizationsAction = new EditImmunizationsAction(factory, loggedInMID, pidString, ovIDString);
		this.diagnosesAction = new EditDiagnosesAction(factory, loggedInMID, pidString, ovIDString);
		this.labProceduresAction = new EditLabProceduresAction(factory, loggedInMID, pidString, ovIDString);
		this.patientInstructionsAction = new EditPatientInstructionsAction(factory, loggedInMID, pidString, ovIDString);
		this.referralsAction = new EditReferralsAction(factory, loggedInMID, pidString, ovIDString);
		
		this.loggingAction = new EventLoggingAction(factory);
		
		this.loggedInMID = loggedInMID;
	}
	
	/**
	 * Create an OfficeVisitAction that is not yet associated with an actual 
	 * office visit.  When update() is called, it will be saved.  Until then, 
	 * any attempt to save health records, prescriptions, procedures, lab procedures, 
	 * immunizations, or diagnoses will raise an exception.
	 * @param factory
	 * @param loggedInMID
	 * @param pidString
	 * @throws ITrustException
	 */
	public EditOfficeVisitAction(DAOFactory factory, long loggedInMID, String pidString)
			throws ITrustException {
		super(factory, loggedInMID, pidString);
		pid = Long.parseLong(pidString);
		ovDAO = factory.getOfficeVisitDAO();
		this.personnelDAO = factory.getPersonnelDAO();
		this.hospitalDAO = factory.getHospitalsDAO();
		this.patDAO = factory.getPatientDAO();
		
		this.healthRecordAction = new EditHealthHistoryAction(factory, loggedInMID, pidString);
		this.prescriptionsAction = new EditPrescriptionsAction(factory, loggedInMID, pidString); 
		this.proceduresAction = new EditProceduresAction(factory, loggedInMID, pidString);
		this.immunizationsAction = new EditImmunizationsAction(factory, loggedInMID, pidString);
		this.diagnosesAction = new EditDiagnosesAction(factory, loggedInMID, pidString);
		this.labProceduresAction = new EditLabProceduresAction(factory, loggedInMID, pidString);
		this.patientInstructionsAction = new EditPatientInstructionsAction(factory, loggedInMID, pidString);
		this.referralsAction = new EditReferralsAction(factory, loggedInMID, pidString);

		this.loggingAction = new EventLoggingAction(factory);
		
		this.loggedInMID = loggedInMID;
	}
	
	/**
	 * Used to update the sub actions once a office visit is saved.  Until this 
	 * is called, attempting to save sub actions will raise an exception.
	 * @throws ITrustException
	 */
	private void reinitializeSubActions() throws ITrustException {
		if (isUnsaved()) {
			throw new ITrustException("Cannot initalize EditOfficeVisit sub actions.  No ovID is present.");
		}
		DAOFactory factory = getFactory();
		String pidString = ""+getPid();
		String ovIDString = ""+getOvID();
		
		healthRecordAction = new EditHealthHistoryAction(factory, loggedInMID, pidString, ovIDString);
		prescriptionsAction = new EditPrescriptionsAction(factory, loggedInMID, pidString, ovIDString);
		proceduresAction = new EditProceduresAction(factory, loggedInMID, pidString, ovIDString);
		immunizationsAction = new EditImmunizationsAction(factory, loggedInMID, pidString, ovIDString);
		diagnosesAction = new EditDiagnosesAction(factory, loggedInMID, pidString, ovIDString);
		labProceduresAction = new EditLabProceduresAction(factory, loggedInMID, pidString, ovIDString);
		patientInstructionsAction = new EditPatientInstructionsAction(factory, loggedInMID, pidString, ovIDString);
		referralsAction = new EditReferralsAction(factory, loggedInMID, pidString, ovIDString);
	}

	/**
	 * Returns the office visit bean for the office visit
	 * 
	 * @return the OfficeVisitBean of the office visit
	 * @throws ITrustException
	 */
	public OfficeVisitBean getOfficeVisit() throws ITrustException {
		return getBean();
	}
	
	/**
	 * Gets and returns the associated patient's health history record.
	 * @return The EditHealthHistoryACtoin sub action associated with this office visit.
	 * @throws ITrustException
	 */
	public EditHealthHistoryAction healthRecord() throws ITrustException {
		return healthRecordAction;
	}
	/**
	 * @return The EditPrescriptionsAction sub action associated with this office visit.
	 * @throws ITrustException
	 */
	public EditPrescriptionsAction prescriptions() throws ITrustException {
		return prescriptionsAction;
	}
	/**
	 * @return The EditProceduresAction sub action associated with this office visit.
	 * @throws ITrustException
	 */
	public EditProceduresAction procedures() throws ITrustException {
		return proceduresAction;
	}
	/**
	 * @return The EditImmunizationsAction sub action associated with this office visit.
	 * @throws ITrustException
	 */
	public EditImmunizationsAction immunizations() throws ITrustException {
		return immunizationsAction;
	}
	/**
	 * @return The EditDiagnosesAction sub action associated with this office visit.
	 * @throws ITrustException
	 */
	public EditDiagnosesAction diagnoses() throws ITrustException {
		return diagnosesAction;
	}
	/**
	 * @return The EditLabProceduresAction sub action associated with this office visit.
	 * @throws ITrustException
	 */
	public EditLabProceduresAction labProcedures() throws ITrustException {
		return labProceduresAction;
	}
	
	public EditPatientInstructionsAction patientInstructions() throws ITrustException {
		return patientInstructionsAction;
	}
	
	public EditReferralsAction referrals() throws ITrustException {
		return referralsAction;
	}
	

	/**
	 * This is a list of all hospitals, ordered by the office visit's hcp FIRST
	 * 
	 * @param hcpID
	 * @return
	 * @throws ITrustException
	 */
	public List<HospitalBean> getHospitals() throws ITrustException {
		List<HospitalBean> hcpsHospitals = personnelDAO.getHospitals(getHcpid());
		List<HospitalBean> allHospitals = hospitalDAO.getAllHospitals();
		return combineLists(hcpsHospitals, allHospitals);
	}

	
	/**
	 * Combines two lists of hospitals
	 * 
	 * @param hcpsHospitals hospitals the HCP is assigned to
	 * @param allHospitals all hospitals
	 * @return the combined list
	 */
	private List<HospitalBean> combineLists(List<HospitalBean> hcpsHospitals, List<HospitalBean> allHospitals) {
		for (HospitalBean hos : allHospitals) {
			if (!hcpsHospitals.contains(hos))
				hcpsHospitals.add(hos);
		}
		return hcpsHospitals;
	}

	/**
	 * Updates the office visit with information from the form passed in.  If 
	 * the office visit has not yet been saved, calling this method will save 
	 * it as well as make the sub actions able to be saved.
	 * 
	 * @param form
	 *            information to update
	 * @return "success" or exception's message
	 * @throws FormValidationException
	 */
	public String updateInformation(EditOfficeVisitForm form, boolean isERIncident) throws FormValidationException {
		String confirm = "";
		try {
			updateOv(form, isERIncident);
			confirm = "success";
			return confirm;
		} catch (ITrustException e) {
			
			return e.getMessage();
		}
	}
	
	/**
	 * Helper that logs an office visit billing event.  The associated patient id, HCP 
	 * id, and office visit id are automatically included.
	 * added with UC60
	 * @param trans Transaction type for the log.
	 * @throws DBException
	 */
	public void logOfficeVisitBillingEvent(TransactionType trans) throws DBException {
		loggingAction.logEvent(trans, loggedInMID, getPid(), "Office visit " +  getOvID() + "billed");
	}
	
	/**
	 * Helper that logs an office visit event.  The associated patient id, HCP 
	 * id, and office visit id are automatically included.
	 * @param trans Transaction type for the log.
	 * @throws DBException
	 */
	public void logOfficeVisitEvent(TransactionType trans) throws DBException {
		loggingAction.logEvent(trans, loggedInMID, getPid(), "Office visit ID: " + getOvID());
	}
	
	/**
	 * 
	 * Sends e-mail regarding the prescribed dangerous drug.
	 * 
	 * @param hcpID HCP the prescription is made by
	 * @param patID ID of the patient prescription is for
	 * @param problem The allergy and/or interaction that is the problem
	 * @return the sent e-mail
	 * @throws DBException
	 */	
	public Email makeEmailApp(long hcpID, String patID, String problem) throws DBException, ITrustException {
		PatientBean p = patDAO.getPatient(Long.parseLong(patID));
		String hcpName = personnelDAO.getName(hcpID);
		Email email = new Email();
		email.setFrom("no-reply@itrust.com");
		email.setToList(Arrays.asList(p.getEmail()));
		email.setSubject("HCP has prescribed you a potentially dangerous medication");
		email.setBody(String
				.format(
					"%s has prescribed a medication that you are allergic to or that has a known interaction with a drug you are currently taking. %s",
					hcpName, problem));
		return email;
	}

	/**
	 * Updates the office visit.
	 * 
	 * @param form form with all the information
	 * @throws DBException
	 * @throws FormValidationException
	 */
	private void updateOv(EditOfficeVisitForm form, boolean isERIncident) throws DBException, FormValidationException, ITrustException {
		validator.validate(form);
		OfficeVisitBean ov = getBean();
		ov.setNotes(form.getNotes());
		ov.setVisitDateStr(form.getVisitDate());
		ov.setHcpID(Long.valueOf(form.getHcpID()));
		ov.setPatientID(Long.valueOf(form.getPatientID()));
		ov.setHospitalID(form.getHospitalID());
		ov.setERIncident(isERIncident);
		ov.setAppointmentType(form.getApptType());
		ov.setBilled(form.getIsBilled()); //UC60
		updateBean(ov);
	}
	
	
	/**
	 * @return The OfficeVisitBean associated with this office visit, or if it 
	 * has not been saved, a default OfficeVisitBean with the HCP id and 
	 * patient id filled in.
	 * @throws DBException
	 */
	private OfficeVisitBean getBean() throws DBException {
		if (isUnsaved()) {
			OfficeVisitBean b = new OfficeVisitBean();
			b.setHcpID(getHcpid());
			b.setPatientID(getPid());
			return b;
		} else {
			return ovDAO.getOfficeVisit(ovID);
		}
	}
	
	/**
	 * Update the office visit with the given data.  If the office visit has 
	 * not yet been saved, this will save it and reinitialize the sub actions.
	 * @param bean The data with which to update the office visit.
	 * @throws DBException
	 * @throws ITrustException
	 */
	private void updateBean(OfficeVisitBean bean) throws DBException, ITrustException {
		if (isUnsaved()) {
			ovID = ovDAO.add(bean);
			reinitializeSubActions();
		} else {
			ovDAO.update(bean);
		}
	}

}
