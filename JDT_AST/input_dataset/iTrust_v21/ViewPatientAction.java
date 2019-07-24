package edu.ncsu.csc.itrust.action;

import java.util.List;

import edu.ncsu.csc.itrust.Messages;
import edu.ncsu.csc.itrust.action.base.PatientBaseAction;
import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;

/**
 * ViewPatientAction is just a class to help the edit demographics page get all the users that should
 * be displayed on the page.
 */
public class ViewPatientAction extends PatientBaseAction {

	/**patientDAO is the patientDAO that retrieves the users from the database*/
	private PatientDAO patientDAO;
	/**loggedInMID is the patient that is logged in.*/
	private long loggedInMID;
	/**Viewer is the patient bean for the person that is logged in*/ 
	private PatientBean viewer;

	/**
	 * ViewPateintAction is the constructor for this action class. It simply initializes the
	 * instance variables.
	 * @param factory The facory used to get the patientDAO.
	 * @param loggedInMID The MID of the logged in user.
	 * @param pidString The user ID patient we are viewing.
	 * @throws ITrustException When there is a bad user.
	 */
	public ViewPatientAction(DAOFactory factory, long loggedInMID, String pidString)
			throws ITrustException {
		super(factory, pidString);
		this.patientDAO = factory.getPatientDAO();
		this.loggedInMID = loggedInMID;
		this.viewer = patientDAO.getPatient(loggedInMID);
	}
	
	/**
	 * getViewablePateints returns a list of patient beans that should be viewed by this
	 * patient.
	 * @return The list of this users dependents and this user.
	 * @throws ITrustException When there is a bad user.
	 */
	public List<PatientBean> getViewablePatients() throws ITrustException{
		List<PatientBean> result;
		try {
			result = patientDAO.getDependents(loggedInMID);
			
			//see if this viewer has updated his information at all, and then add him to the list
			this.viewer = patientDAO.getPatient(this.viewer.getMID());
			result.add(0, viewer);
			
		} catch (DBException e) {
			throw new ITrustException("Invalid User");
		}
		return result;
	}
	
	/**
	 * Retrieves a PatientBean for the mid passed in as a String
	 * 
	 * @param input
	 *            the mid for which the PatientBean will be returned
	 * @return PatientBean
	 * @throws ITrustException
	 */
	public PatientBean getPatient(String input) throws ITrustException {
		try {
			long mid = Long.valueOf(input);
			PatientBean patient = patientDAO.getPatient(mid);
			if (patient != null) {
				return patient;
			} else
				throw new ITrustException(Messages.getString("ViewPatientAction.1")); //not sure if this message exists
		} catch (NumberFormatException e) {
			throw new ITrustException(Messages.getString("ViewPatientAction.2")); //not sure if this message exists
		}
	}
}
