package edu.ncsu.csc.itrust.action;

import java.util.ArrayList;
import java.util.List;
import edu.ncsu.csc.itrust.EmailUtil;
import edu.ncsu.csc.itrust.beans.Email;
import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.beans.PersonnelBean;
import edu.ncsu.csc.itrust.beans.PrescriptionBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.Messages;


/**
 * Handles retrieving beans for viewPrescriptionRecords.jsp
 * 
 * 
 */
public class ViewPrescriptionRecordsAction {
	private PatientDAO patientDAO;
	private PersonnelDAO personnelDAO;
	private EmailUtil emailer;
	private long loggedInMID;

	/**
	 * Set up defaults
	 * 
	 * @param factory The DAOFactory used to create the DAOs used in this action.
	 * @param loggedInMID The MID of the person viewing prescription records.
	 */
	public ViewPrescriptionRecordsAction(DAOFactory factory, long loggedInMID) {
		this.emailer = new EmailUtil(factory);
		this.patientDAO = factory.getPatientDAO();
		this.personnelDAO = factory.getPersonnelDAO();
		this.loggedInMID = loggedInMID;
	}
	
	/**
	 * Gets a PatientBean from an MID
	 * 
	 * @param patientID MID of the patient
	 * @return PatientBean for the MID given
	 * @throws ITrustException
	 */
	public PatientBean getPatient(long patientID) throws ITrustException {
		return patientDAO.getPatient(patientID);
	}
	
	/**
	 * Gets the logged in person's representees
	 * 
	 * @return list of PatientBeans holding the representees
	 * @throws ITrustException
	 */
	public List<PatientBean> getRepresentees() throws ITrustException {
		return patientDAO.getRepresented(loggedInMID);
	}
	
	/**
	 * Returns the prescribing HCP for a prescription
	 * 
	 * @param prescription item in question
	 * @return HCP who prescribed the prescription
	 * @throws ITrustException
	 */
	public PersonnelBean getPrescribingDoctor(PrescriptionBean prescription) throws ITrustException {
		return personnelDAO.getPrescribingDoctor(prescription);
	}
	
	/**
	 * Returns all the prescriptions for a given patient
	 * 
	 * @param patientID patient in question
	 * @return list of all the prescriptions for that patient
	 * @throws ITrustException
	 */
	public List<PrescriptionBean> getPrescriptionsForPatient(long patientID) throws ITrustException {
		PatientBean patient = patientDAO.getPatient(patientID);
		if (loggedInMID == patientID) {
			return patientDAO.getPrescriptions(patientID);
		}
		
		List<String> toList = new ArrayList<String>();
		toList.add(patient.getEmail());
		
		List<PatientBean> representatives = patientDAO.getRepresenting(patientID);
		for(PatientBean representative : representatives) {
			if (loggedInMID == representative.getMID()) {
				return patientDAO.getPrescriptions(patientID);
			}
			toList.add(representative.getEmail());
		}
		
		List<PersonnelBean> dlhcps = patientDAO.getDeclaredHCPs(patientID);
		for(PersonnelBean dlhcp : dlhcps) {
			if (loggedInMID == dlhcp.getMID()) {
				return patientDAO.getPrescriptions(patientID);
			}
			List<PersonnelBean> uaps = personnelDAO.getUAPsForHCP(dlhcp.getMID());
			for(PersonnelBean uap : uaps) {
				if (loggedInMID == uap.getMID()) {
					return patientDAO.getPrescriptions(patientID);
				}
			}
		}
		
		Email email = new Email();
		email.setToList(toList);
		email.setFrom("noreply@itrust.com"); //$NON-NLS-1$
		email.setSubject(Messages.getString("ViewPrescriptionRecordsAction.1")); //$NON-NLS-1$
		email.setBody(Messages.getString("ViewPrescriptionRecordsAction.2")); //$NON-NLS-1$
		emailer.sendEmail(email);
		return patientDAO.getPrescriptions(patientID);
	}
}
