package edu.ncsu.csc.itrust.action;

import java.util.List;
import edu.ncsu.csc.itrust.action.base.PatientBaseAction;
import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.AuthDAO;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.enums.Role;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;

/**
 * Edits a patient's personal representatives. Used by hcp/editRepresentatives.jsp
 * 
 * 
 */
public class EditRepresentativesAction extends PatientBaseAction {
	private PatientDAO patientDAO;
	private AuthDAO authDAO;
	/**
	 * Super class validates the patient mid
	 * 
	 * @param factory The DAOFactory used in creating the DAOs for this action.
	 * @param loggedInMID The MID of the patient editing his/her representatives.
	 * @param pidString The MID of the representative in question.
	 * @throws ITrustException
	 */
	public EditRepresentativesAction(DAOFactory factory, long loggedInMID, String pidString)
			throws ITrustException {
		super(factory, pidString);
		this.patientDAO = factory.getPatientDAO();
		this.authDAO = factory.getAuthDAO();
	}
	
	/**
	 * Gets the name of the representative patient
	 * @return the full name of the representative patient
	 * @throws ITrustException if patient does not exist
	 */
	public String getRepresentativeName() throws ITrustException {
		String name = "";
		try {
			name = patientDAO.getName(pid);
		} catch (DBException e) {
			//If a DBException is caught print a stack trace and return an empty string
			e.printStackTrace();
		}
		
		return name;
	}
	
	/**
	 * Return a list of patients that pid represents
	 * 
	 * @param pid The id of the personnel we are looking up representees for.
	 * @return a list of PatientBeans
	 * @throws ITrustException
	 */
	public List<PatientBean> getRepresented(long pid) throws ITrustException {
		return patientDAO.getRepresented(pid);
	}

	/**
	 * Makes the patient (pid) represent the input mid parameter
	 * 
	 * @param pidString
	 *            the mid of the person who will be represented (the representee)
	 * @return a message
	 * @throws ITrustException
	 */
	public String addRepresentee(String pidString) throws ITrustException {
		try {
			long representee = Long.valueOf(pidString);
			if (authDAO.getUserRole(representee) != Role.PATIENT)
				throw new ITrustException("This user is not a patient!");
			else if (super.pid == representee)
				throw new ITrustException("This user cannot represent themselves.");
			else if(!patientDAO.checkIfRepresenteeIsActive(representee))
				throw new ITrustException(patientDAO.getPatient(representee).getFullName() + "cannot be added as a representee, they are not active.");
			boolean confirm = patientDAO.addRepresentative(pid, representee);
			if (confirm) {
				return "Patient represented";
			} else
				return "No change made";
		} catch (NumberFormatException e) {
			return "MID not a number";
		}
	}

	/**
	 * Makes the patient (pid) no longer represent the input mid param
	 * 
	 * @param input
	 *            the mid of the person be represented (representee)
	 * @return a message
	 * @throws ITrustException
	 */
	public String removeRepresentee(String input) throws ITrustException {
		try {
			long representee = Long.valueOf(input);
			boolean confirm = patientDAO.removeRepresentative(pid, representee);
			if (confirm) {
				return "Patient represented";
			} else
				return "No change made";
		} catch (NumberFormatException e) {
			return "MID not a number";
		}
	}
	
	public boolean checkIfPatientIsActive(long patientID) throws ITrustException
	{
		try {
			return patientDAO.checkIfPatientIsActive(patientID);
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
