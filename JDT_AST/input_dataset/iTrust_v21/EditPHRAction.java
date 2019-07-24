package edu.ncsu.csc.itrust.action;

import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc.itrust.EmailUtil;
import edu.ncsu.csc.itrust.action.base.PatientBaseAction;
import edu.ncsu.csc.itrust.beans.MedicationBean;
import edu.ncsu.csc.itrust.beans.PersonnelBean;
import edu.ncsu.csc.itrust.beans.AllergyBean;
import edu.ncsu.csc.itrust.beans.DiagnosisBean;
import edu.ncsu.csc.itrust.beans.Email;
import edu.ncsu.csc.itrust.beans.FamilyMemberBean;
import edu.ncsu.csc.itrust.beans.HealthRecord;
import edu.ncsu.csc.itrust.beans.OfficeVisitBean;
import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.beans.PrescriptionBean;
import edu.ncsu.csc.itrust.beans.ProcedureBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.AllergyDAO;
import edu.ncsu.csc.itrust.dao.mysql.FamilyDAO;
import edu.ncsu.csc.itrust.dao.mysql.HealthRecordsDAO;
import edu.ncsu.csc.itrust.dao.mysql.ICDCodesDAO;
import edu.ncsu.csc.itrust.dao.mysql.NDCodesDAO;
import edu.ncsu.csc.itrust.dao.mysql.OfficeVisitDAO;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.dao.mysql.ProceduresDAO;
import edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.NoHealthRecordsException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.risk.ChronicDiseaseMediator;
import edu.ncsu.csc.itrust.risk.RiskChecker;
import edu.ncsu.csc.itrust.validate.AllergyBeanValidator;


/**
 * Edits the patient health record for a given patient Used by editPHR.jsp
 */
public class EditPHRAction extends PatientBaseAction {
	private DAOFactory factory;
	private PatientDAO patientDAO;
	private AllergyDAO allergyDAO;
	private FamilyDAO familyDAO;
	private HealthRecordsDAO hrDAO;
	private OfficeVisitDAO ovDAO;
	private ICDCodesDAO icdDAO;
	private ProceduresDAO procDAO;
	private ChronicDiseaseMediator diseaseMediator;
	private PersonnelDAO personnelDAO;
	private PersonnelBean HCPUAP;
	private PatientBean patient;
	private EmailUtil emailutil;
	private NDCodesDAO ndcodesDAO; //NEW
	private EventLoggingAction loggingAction;
	
	/**
	 * Super class validates the patient id
	 * 
	 * @param factory The DAOFactory to be used in creating DAOs for this action.
	 * @param loggedInMID The MID of the currently logged in user who is authorizing this action.
	 * @param pidString The MID of the patient whose personal health records are being added.
	 * @throws ITrustException
	 * @throws NoHealthRecordsException
	 */
	public EditPHRAction(DAOFactory factory, long loggedInMID, String pidString) throws ITrustException {
		super(factory, pidString);
		this.patientDAO = factory.getPatientDAO();
		this.allergyDAO = factory.getAllergyDAO();
		this.familyDAO = factory.getFamilyDAO();
		this.hrDAO = factory.getHealthRecordsDAO();
		this.ovDAO = factory.getOfficeVisitDAO();
		this.icdDAO = factory.getICDCodesDAO();
		this.personnelDAO = factory.getPersonnelDAO();
		this.HCPUAP = personnelDAO.getPersonnel(loggedInMID);
		this.patient = patientDAO.getPatient(pid);
		this.procDAO = factory.getProceduresDAO();
		this.ndcodesDAO = factory.getNDCodesDAO(); //NEW
		this.loggingAction = new EventLoggingAction(factory);
		emailutil = new EmailUtil(factory);
		this.factory = factory;
	}

	/**
	 * Adds an allergy to the patient's records
	 * @param pid pid
	 * @param ndcode ndcode
	 * @return "Allergy Added", exception message, a list of invalid fields, or "" (only if description is null)
	 * @throws FormValidationException
	 * @throws ITrustException
	 */
	public String updateAllergies(long pid, String description) throws FormValidationException, ITrustException {
		AllergyBean bean = new AllergyBean();
		bean.setPatientID(pid);
		bean.setDescription(description);
		AllergyBeanValidator abv = new AllergyBeanValidator();
		abv.validate(bean);
		
		//now, set the ndcode if it happens to exist for the description
		for (MedicationBean med : ndcodesDAO.getAllNDCodes()) {
			if (med.getDescription().equalsIgnoreCase(bean.getDescription())) {
				bean.setNDCode(med.getNDCode());
				break;
			}
		}

		String patientName = patientDAO.getName(pid);
		List<AllergyBean> allergies = allergyDAO.getAllergies(pid);
		for (AllergyBean current : allergies){
			if (current.getDescription().equalsIgnoreCase(bean.getDescription())) {
				return "Allergy " + bean.getNDCode() + " - " + bean.getDescription()
						+ " has already been added for " + patientName + ".";
			}
		}
		
		allergyDAO.addAllergy(bean);
		emailutil.sendEmail(makeEmail());
		/*
		 * adding loop that checks for allergy conflicts. The loop runs through every prescription bean
		 * and checks for conflict.
		 */
		List<PrescriptionBean> beansRx = patientDAO.getCurrentPrescriptions(pid);
		for(int i = 0; i < beansRx.size(); i++) {
			if(beansRx.get(i).getMedication().getNDCode().equals(bean.getNDCode())) {
				return "Medication " + beansRx.get(i).getMedication().getNDCode()
						+ " - " + beansRx.get(i).getMedication().getDescription()
						+ " is currently prescribed to " + patientName + ".";
			}
		}
		
		//log that this was added
		loggingAction.logEvent(TransactionType.parse(6700), HCPUAP.getMID(), 
				patient.getMID(), "An allergy record has been added: " +  bean.getId());
		
		return "Allergy Added"; //If loop is successful, it will never reach here.
	}

	/**
	 * Returns a PatientBean for the patient
	 * 
	 * @return PatientBean
	 * @throws ITrustException
	 */
	public PatientBean getPatient() throws ITrustException {
		return patientDAO.getPatient(pid);
	}

	/**
	 * Returns a list of AllergyBeans for the patient
	 * 
	 * @return list of AllergyBeans
	 * @throws ITrustException
	 */
	public List<AllergyBean> getAllergies() throws ITrustException {
		return allergyDAO.getAllergies(pid);
	}

	/**
	 * Returns a list of FamilyMemberBeans for the patient
	 * 
	 * @return list of FamilyMemberBeans
	 * @throws ITrustException
	 */
	public List<FamilyMemberBean> getFamily() throws ITrustException {
		List<FamilyMemberBean> fam = new ArrayList<FamilyMemberBean>();
		List<FamilyMemberBean> parents = null;
		parents = familyDAO.getParents(pid);
		fam.addAll(parents);
		fam.addAll(familyDAO.getSiblings(pid));
		fam.addAll(familyDAO.getChildren(pid));

		
		if(parents != null) {
			List<FamilyMemberBean> grandparents = new ArrayList<FamilyMemberBean>();
			for(FamilyMemberBean parent : parents) {
				grandparents.addAll(familyDAO.getParents(parent.getMid()));
			}
			
			fam.addAll(grandparents);
			
			for(FamilyMemberBean gp : grandparents) {
				gp.setRelation("Grandparent");
			}
		}
		return fam;
	}


	/**
	 * Returns a list of HealthRecords for the patient
	 * 
	 * @return allHealthRecords
	 * @throws ITrustException
	 */
	public List<HealthRecord> getAllHealthRecords() throws ITrustException {
		List<HealthRecord> allHealthRecords = hrDAO.getAllHealthRecords(pid);
		return allHealthRecords;
	}

	/**
	 * Returns a list of OfficeVisitBeans
	 * 
	 * @return office visits
	 * @throws ITrustException
	 */
	public List<OfficeVisitBean> getAllOfficeVisits() throws ITrustException {
		return ovDAO.getAllOfficeVisits(pid);
	}
	
	/**
	 * getProcedures
	 * @param visitID
	 * @return list
	 * @throws DBException
	 */
	public List<ProcedureBean> getProcedures(long visitID) throws DBException {
		return procDAO.getList(visitID);
	}
	
	/**
	 * Returns a list of diseases for which the patient is at risk
	 * 
	 * @return list of RiskCheckers
	 * @throws ITrustException
	 * @throws NoHealthRecordsException
	 */
	public List<RiskChecker> getDiseasesAtRisk() throws NoHealthRecordsException, ITrustException {
		this.diseaseMediator = new ChronicDiseaseMediator(factory, pid);
		return diseaseMediator.getDiseaseAtRisk();
	}
	
	/**
	 * Checks to see if a particular family member has high blood pressure
	 * 
	 * @param member the family member to check
	 * @return true if the family member has high blood pressure.  False if there are no records or the family member does not have high blood pressure
	 * @throws ITrustException
	 */
	public boolean doesFamilyMemberHaveHighBP(FamilyMemberBean member) throws ITrustException {
		List<HealthRecord> records = hrDAO.getAllHealthRecords(member.getMid());
		if(records.size() == 0)
			return false;
		for(HealthRecord record : records) {
			if(record.getBloodPressureSystolic() > 240 || record.getBloodPressureDiastolic() > 120 )
				return true;
		}
		return false;
	}
	
	/**
	 * Checks to see if a particular family member has high cholesterol
	 * 
	 * @param member the family member to check
	 * @return true if the family member has high cholesterol.  False if there are no records or the family member does not
	 * @throws ITrustException
	 */	
	public boolean doesFamilyMemberHaveHighCholesterol(FamilyMemberBean member) throws ITrustException {
		List<HealthRecord> records = hrDAO.getAllHealthRecords(member.getMid());
		if(records.size() == 0)
			return false;
		for(HealthRecord record : records) {
			if(record.getCholesterolHDL() < 35 || record.getCholesterolLDL() > 250 )
				return true;
		}
		return false;
	}
	
	/**
	 * Checks to see if a particular family member has diabetes
	 * 
	 * @param member the family member to check
	 * @return true if the family member has diabetes.  False if there are no records or the family member does not
	 * @throws ITrustException
	 */	

	public boolean doesFamilyMemberHaveDiabetes(FamilyMemberBean member) throws ITrustException {
		List<DiagnosisBean> diagnoses = patientDAO.getDiagnoses(member.getMid());
		if(diagnoses.size() == 0)
			return false;
		for(DiagnosisBean diag : diagnoses) {
			if(diag.getICDCode().startsWith("250"))
				return true;
		}
		return false;
	}
	
	/**
	 * Checks to see if a particular family member has cancer
	 * 
	 * @param member the family member to check
	 * @return true if the family member has cancer.  False if there are no records or the family member does not
	 * @throws ITrustException
	 */	

	public boolean doesFamilyMemberHaveCancer(FamilyMemberBean member) throws ITrustException {
		List<DiagnosisBean> diagnoses = patientDAO.getDiagnoses(member.getMid());
		if(diagnoses.size() == 0)
			return false;
		for(DiagnosisBean diag : diagnoses) {
			if(diag.getICDCode().startsWith("199"))
				return true;
		}
		return false;
	}
	
	/**
	 * Checks to see if a particular family member has heart disease
	 * 
	 * @param member the family member to check
	 * @return true if the family member has heart disease.  False if there are no records or the family member does not
	 * @throws ITrustException
	 */	
	public boolean doesFamilyMemberHaveHeartDisease(FamilyMemberBean member) throws ITrustException {
		List<DiagnosisBean> diagnoses = patientDAO.getDiagnoses(member.getMid());
		if(diagnoses.size() == 0)
			return false;
		for(DiagnosisBean diag : diagnoses) {
			if(diag.getICDCode().startsWith("402"))
				return true;
		}
		return false;
	}
	
	/**
	 * Checks to see if a particular family member smokes
	 * 
	 * @param member the family member to check
	 * @return true if the family member smokes.  False if there are no records or the family member does not
	 * @throws ITrustException
	 */	
	public boolean isFamilyMemberSmoker(FamilyMemberBean member) throws ITrustException {
		List<HealthRecord> records = hrDAO.getAllHealthRecords(member.getMid());
		if(records.size() == 0)
			return false;
		for(HealthRecord record : records) {
			if(record.isSmoker())
				return true;
		}
		return false;
	}
	

	/**
	 * Checks to see if the family member is dead and returns their cause of death if so
	 * 
	 * @param member the family member to check
	 * @return the cause of death if there is one; otherwise null
	 * @throws ITrustException
	 */
	public String getFamilyMemberCOD(FamilyMemberBean member) throws ITrustException {
		PatientBean patient = patientDAO.getPatient(member.getMid());
		if(patient.getCauseOfDeath() == null)
			return "";
		DiagnosisBean diag = icdDAO.getICDCode(patient.getCauseOfDeath());
		if(diag == null)
			return "";
		return diag.getDescription();
	}
	
	/**
	 * Creates a fake e-mail to notify the user that their records have been altered.
	 * 
	 * @return the e-mail to be sent
	 * @throws DBException
	 */
	private Email makeEmail() throws DBException{

		Email email = new Email();
		List<PatientBean> reps = patientDAO.getRepresenting(patient.getMID());
		
		List<String> toAddrs = new ArrayList<String>();
		toAddrs.add(patient.getEmail());
		for (PatientBean r: reps) {
			toAddrs.add(r.getEmail());
		}
		
		email.setFrom("no-reply@itrust.com");
    	email.setToList(toAddrs); // patient and personal representative
    	email.setSubject(String.format("Your medical records have been altered"));
    	email.setBody("Health care professional "+ HCPUAP.getFullName() +" has altered your medical records. " +
    				"She is not on your list of designated health care professionals.");
		return email;
	}
}
