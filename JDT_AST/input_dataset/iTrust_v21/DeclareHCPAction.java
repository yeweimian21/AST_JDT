package edu.ncsu.csc.itrust.action;

import java.util.List;
import edu.ncsu.csc.itrust.beans.PersonnelBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.AuthDAO;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.enums.Role;
import edu.ncsu.csc.itrust.exception.ITrustException;

/**
 * Used by the patient to declare HCPs as "designated", in editHCPs.jsp.
 * 
 * 
 */
public class DeclareHCPAction {
	private PatientDAO patientDAO;
	private AuthDAO authDAO;
	private long loggedInMID;

	/**
	 * Sets up defaults
	 * 
	 * @param factory The DAO factory to be used for generating the DAOs for this action.
	 * @param loggedInMID
	 *            This patient
	 */
	public DeclareHCPAction(DAOFactory factory, long loggedInMID) {
		this.loggedInMID = loggedInMID;
		this.patientDAO = factory.getPatientDAO();
		this.authDAO = factory.getAuthDAO();
	}

	/**
	 * Lists the declared HCPs for this current patient
	 * 
	 * @return Returns a list of the declared HCPs
	 * @throws ITrustException
	 */
	public List<PersonnelBean> getDeclaredHCPS() throws ITrustException {
		return patientDAO.getDeclaredHCPs(loggedInMID);
	}

	/**
	 * Validate an HCP's MID and declare them, if possible
	 * 
	 * @param hcpStr
	 *            The MID of an HCP to declare
	 * @return A status message,
	 * @throws ITrustException
	 */
	public String declareHCP(String hcpStr) throws ITrustException {
		try {
			long hcpID = Long.valueOf(hcpStr);
			if (authDAO.getUserRole(hcpID) != Role.HCP)
				throw new ITrustException("This user is not a licensed healthcare professional!");

			boolean confirm = patientDAO.declareHCP(loggedInMID, hcpID);

			if (confirm) {
				return "HCP successfully declared";
			} else
				return "HCP not declared";
		} catch (NumberFormatException e) {
			throw new ITrustException("HCP's MID not a number");
		} 
	}

	/**
	 * Validate an HCP's MID and undeclare them, if possible
	 * 
	 * @param input
	 *            The MID of an HCP to undeclare
	 * @return
	 * @throws ITrustException
	 */
	public String undeclareHCP(String input) throws ITrustException {
		try {
			long hcpID = Long.valueOf(input);
			boolean confirm = patientDAO.undeclareHCP(loggedInMID, hcpID);
			if (confirm) {
				return "HCP successfully undeclared";
			} else
				return "HCP not undeclared";
		} catch (NumberFormatException e) {
			throw new ITrustException("HCP's MID not a number");
		} 
	}
}
