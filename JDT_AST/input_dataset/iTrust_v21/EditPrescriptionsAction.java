package edu.ncsu.csc.itrust.action;

import java.text.SimpleDateFormat;
//import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import edu.ncsu.csc.itrust.EmailUtil;
import edu.ncsu.csc.itrust.action.base.EditOfficeVisitBaseAction;
import edu.ncsu.csc.itrust.beans.*;
import edu.ncsu.csc.itrust.beans.forms.EditPrescriptionsForm;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.*;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.PrescriptionFieldException;
import edu.ncsu.csc.itrust.exception.PrescriptionWarningException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.validate.EditPrescriptionsValidator;


/**
 * Edits a patient's prescription information.  Used by hcp-uap/editPrescription.jsp
 */
public class EditPrescriptionsAction extends EditOfficeVisitBaseAction {

	private PrescriptionsDAO psDAO;
	private NDCodesDAO medDAO;
	private DrugInteractionDAO interactionsDAO;
	private PrescriptionReportDAO rptDAO;
	private AllergyDAO allergyDAO;
	private PatientDAO patientDAO;
	private EmailUtil emailUtil;
	private PersonnelDAO personnelDAO;
	private PrescriptionOverrideDAO prescriptionOverrideDAO;

	private EventLoggingAction loggingAction;
	/**
	 * Creates a new action by initializing the office visit
	 * database access object.
	 * 
	 * @param factory
	 * @throws ITrustException
	 */
	public EditPrescriptionsAction(DAOFactory factory, long hcpid, 
								   String pidString, String ovIDString) 
		throws ITrustException {
		super(factory, hcpid, pidString, ovIDString);

		init(factory);
	}
	
	/**
	 * A prescription action that is part of an office visit that is not yet 
	 * saved.  All attempts to modify this action will throw exceptions.  Once 
	 * the office visit is saved, obtain a new EditPrescriptionsAction using 
	 * the four-argument constructor.  (This is done automatically by the 
	 * EditOfficeVisitAction class.)
	 * @param factory
	 * @param hcpid
	 * @param pidString
	 * @throws ITrustException
	 */
	public EditPrescriptionsAction(DAOFactory factory, long hcpid, 
			   String pidString) 
		throws ITrustException {
		super(factory, hcpid, pidString);
		init(factory);
		
	}
	
	private void init(DAOFactory factory){		
		psDAO = factory.getPrescriptionsDAO();
		medDAO = factory.getNDCodesDAO();
		interactionsDAO = factory.getDrugInteractionDAO();
		allergyDAO = factory.getAllergyDAO();
		rptDAO = factory.getPrescriptionReportDAO();
		patientDAO = factory.getPatientDAO();
		emailUtil = new EmailUtil(factory);
		personnelDAO = new PersonnelDAO(factory);
		prescriptionOverrideDAO = new PrescriptionOverrideDAO(factory);
		loggingAction = new EventLoggingAction(factory);
	}
	/**
	 * Checks the prescription bean for interactions, allergies, and legal 
	 * values.
	 * @param pres The prescription bean.
	 * @throws ITrustException
	 */
	private void checkForAllergiesAndInteractions(PrescriptionBean pres) throws ITrustException {
		List<String> warnings = checkInteraction(pres);
		warnings.addAll(checkAllergy(pres));
		if (!warnings.isEmpty()) {
			PatientBean patient = patientDAO.getPatient(getPid());
			PersonnelBean hcp = personnelDAO.getPersonnel(getHcpid());
			loggingAction.logEvent(TransactionType.OVERRIDE_INTERACTION_WARNING, hcp.getMID(), patient.getMID(), pres.getMedication().getNDCode()+
					" Override: "+pres.getReasons().toString());
			if(validOverride(pres)){
				emailUtil.sendEmail(makeEmail(pres, warnings));
			}else{
				throw new PrescriptionWarningException(warnings);
			}
		}
		
		if ("".equals(pres.getInstructions())) {
			throw new PrescriptionFieldException("Instructions are required.");
		}
	}
	
	/**
	 * Indicates if the prescription bean has a valid allergy/interaction 
	 * override.
	 */
	private boolean validOverride(PrescriptionBean pres) {
		return pres.getReasons() != null && pres.getReasons().size()>0;
	}
	
	/**
	 * Returns a string suitable for a user warning message that a 
	 * drug-interaction was detected.
	 */
	private String formatInteractionWarning(PrescriptionBean newPrescription, 
									  PrescriptionBean oldPrescription, 
									  DrugInteractionBean bean) 
	{
		String startDate = new SimpleDateFormat("MM/dd/yyyy").format(oldPrescription.getStartDate());
		String endDate = new SimpleDateFormat("MM/dd/yyyy").format(oldPrescription.getEndDate());
		return String.format("Currently Prescribed: %s. Start Date: %s, End Date: %s. Interactions: %s - %s. Description: %s", 
							 oldPrescription.getMedication().getDescription(),
							 startDate, endDate,
							 oldPrescription.getMedication().getDescription(),
							 newPrescription.getMedication().getDescription(),
							 bean.getDescription()
							 );
	}
	
	/**
	 * Returns a string suitable for a user warning message that a 
	 * drug-allergy warning was detected.
	 */
	private String formatAllergyWarning(AllergyBean bean) throws DBException {
		return "Allergy: " + bean.getDescription() + ". First Found: " + 
			new SimpleDateFormat("MM/dd/yyyy").format(bean.getFirstFound());
	}
    
	/**
	 * Checks to see if the given prescription has an interaction with any 
	 * other previously prescribed prescriptions.
	 * @param newPrescription
	 * @return A list of interaction warning messages.  The list is empty if no 
	 * 	interactions were detected.
	 * @throws ITrustException
	 */
	private List<String> checkInteraction(PrescriptionBean newPrescription) throws ITrustException {
		ArrayList<String> warnings = new ArrayList<String>();
		try {

			SimpleDateFormat needed = new SimpleDateFormat("yyyy/MM/dd");
			
			String startdate = needed.format(newPrescription.getStartDate());
			String enddate = needed.format(newPrescription.getEndDate());
			String drug = newPrescription.getMedication().getNDCode();
			
			startdate = startdate.replaceAll("/", "-");
			enddate = enddate.replaceAll("/", "-");
			List<PrescriptionReportBean> prBeanList = rptDAO.byDate(getPid(), startdate, enddate);
			List<DrugInteractionBean> dBeanList = interactionsDAO.getInteractions(drug);
		
			for (PrescriptionReportBean prBean : prBeanList) {
				String oldDrug = prBean.getPrescription().getMedication().getNDCode();
				PrescriptionBean oldPrescription = prBean.getPrescription();
				for (DrugInteractionBean dBean : dBeanList) {
					String intDrug1 = dBean.getFirstDrug();
					String intDrug2 = dBean.getSecondDrug();
					
					if (oldDrug.equals(intDrug1) && drug.equals(intDrug2)) {
						warnings.add(formatInteractionWarning(newPrescription, oldPrescription, dBean));
					} else if (oldDrug.equals(intDrug2) && drug.equals(intDrug1)) {
						warnings.add(formatInteractionWarning(newPrescription, oldPrescription, dBean));
					}
				}
			}
		} catch (DBException e) {
			
			throw new ITrustException(e.getMessage());
		}
		return warnings;
	}
	
	/**
	 * Checks to see if the patient is allergic to the given prescription.
	 * @return A list of allergy warnings.  The list is empty if no allergies 
	 * were detected.
	 * @throws ITrustException
	 * @throws PrescriptionWarningException
	 */
	private List<String> checkAllergy(PrescriptionBean pres) throws ITrustException, PrescriptionWarningException {
		ArrayList<String> warnings = new ArrayList<String>();
		try {
			List<AllergyBean> allergyList = allergyDAO.getAllergies(getPid());
			MedicationBean medBean = pres.getMedication();
			if (medBean != null) {
				String newDrug = medBean.getNDCode();
				for (AllergyBean allergyBean : allergyList) {
					//Allergy: Aspirin. First Found: 12/20/2008. 
					if (newDrug.equals(allergyBean.getNDCode())) {
						warnings.add(formatAllergyWarning(allergyBean));
					}
				}
			}
		} catch (DBException e){
			
			throw new ITrustException(e.getMessage());
		}
		return warnings;
	}
	

	/**
	 * Edits an existing prescription in the database.  If the office visit is 
	 * unsaved, this will throw an exception.
	 * 
	 * @param pres The prescription bean that has been changed.
	 * @throws ITrustException 
	 */
	public void editPrescription(PrescriptionBean pres) throws ITrustException {
		verifySaved();
		checkForAllergiesAndInteractions(pres);
		psDAO.edit(pres);
	}
	/**
	 * @return A list of all prescriptions for this office visit.  (If the 
	 * 	office visit is unsaved, this returns an empty list.)
	 * @throws DBException
	 */
	public List<PrescriptionBean> getPrescriptions() throws DBException {
		if (isUnsaved()) {
			return new ArrayList<PrescriptionBean>();
		} else {
			return psDAO.getList(getOvID());
		}
	}
	/**
	 * Add a prescription to this office visit.  If the office visit is 
	 * unsaved, this will throw an exception.
	 * @param pres
	 * @throws ITrustException
	 */
	public void addPrescription(PrescriptionBean pres) throws ITrustException {
		verifySaved();
		checkForAllergiesAndInteractions(pres);
		long medID = psDAO.add(pres);
		for(OverrideReasonBean reason : pres.getReasons()){
			reason.setPresID(medID);
			prescriptionOverrideDAO.add(reason);
		}
	}
	/**
	 * Delete a prescription from this office visit.  If the office visit is 
	 * unsaved, this will throw an exception.
	 * @param pres
	 * @throws DBException
	 * @throws ITrustException
	 */
	public void deletePrescription(PrescriptionBean pres) throws DBException, ITrustException {
		verifySaved();
		psDAO.remove(pres.getId());
		prescriptionOverrideDAO.remove(pres.getId());
	}
	/**
	 * Returns a list of known medications.  This can be called even if the 
	 * office visit is unsaved. 
	 * @throws DBException
	 * @throws ITrustException
	 */
	public List<MedicationBean> getMedications() throws DBException, ITrustException {
		return medDAO.getAllNDCodes();
	}
	/**
	 * Validates a prescription form, converts it into a bean, and returns that bean.
	 * @param form  The form to convert.
	 * @param defaultInstructions  The default value given in the instructions 
	 * 	field.  If the field equals this value, the validation will fail.
	 * @return
	 * @throws FormValidationException
	 * @throws DBException
	 */
	public PrescriptionBean formToBean(EditPrescriptionsForm form, String defaultInstructions) throws FormValidationException, DBException {
		EditPrescriptionsValidator validator = new EditPrescriptionsValidator(defaultInstructions);
		validator.validate(form);
		PrescriptionBean bean = new PrescriptionBean();
		bean.setVisitID(getOvID());
		MedicationBean med = medDAO.getNDCode(form.getMedID());
		bean.setMedication(med);
		bean.setDosage(Integer.valueOf(form.getDosage()));
		bean.setStartDateStr(form.getStartDate());
		bean.setEndDateStr(form.getEndDate());
		bean.setInstructions(form.getInstructions());
		ArrayList<OverrideReasonBean> reasons = new ArrayList<OverrideReasonBean>();
		for(String reason : form.getOverrideCodes()){
			OverrideReasonBean override = new OverrideReasonBean();
			override.setORCode(reason);
			reasons.add(override);
		}
		bean.setReasons(reasons);
		bean.setOverrideReasonOther(form.getOverrideOther());
		return bean;
	}
	
	/**
	 * Creates a fake e-mail to notify the user that their records have been altered.
	 * 
	 * @return the e-mail to be sent
	 * @throws DBException
	 */
	private Email makeEmail(PrescriptionBean pres, List<String> warnings) throws DBException{

		Email email = new Email();
		PatientBean patient = patientDAO.getPatient(getPid());
		PersonnelBean hcp = personnelDAO.getPersonnel(getHcpid());

		
		List<String> toAddrs = new ArrayList<String>();
		toAddrs.add(patient.getEmail());
		
		StringBuffer buf = new StringBuffer();
		for(String warning : warnings){
			buf.append(warning);
			buf.append("\n");
		}
		String message = "Health care professional " + hcp.getFullName() + " has prescribed " +
    			pres.getMedication().getDescription() + ". However, the following warning(s) were found:" +
				buf.toString();
		
		email.setFrom("no-reply@itrust.com");
    	email.setToList(toAddrs); // patient and personal representative
    	email.setSubject(String.format("Prescription warning"));
    	email.setBody(message);
		return email;
	}
}
