package edu.ncsu.csc.itrust.action;

import java.util.List;
import edu.ncsu.csc.itrust.beans.HospitalBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.HospitalsDAO;
import edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO;
import edu.ncsu.csc.itrust.exception.ITrustException;

/**
 * Manages the assignment of HCPs to hospitals Used by hospitalAssignments.jsp
 * 
 * 
 */
public class ManageHospitalAssignmentsAction {
	private PersonnelDAO personnelDAO;
	private HospitalsDAO hospitalsDAO;
	/**
	 * Set up defaults
	 * 
	 * @param factory
	 *            The DAOFactory used to create the DAOs used in this action.
	 * @param loggedInMID
	 *            The MID of the user managing hospitals.
	 */
	public ManageHospitalAssignmentsAction(DAOFactory factory, long loggedInMID) {
		this.personnelDAO = factory.getPersonnelDAO();
		this.hospitalsDAO = factory.getHospitalsDAO();
	}

	/**
	 * Returns a list of hospitals to which the given mid is not currently assigned
	 * 
	 * @param midString
	 * @return list of HospitalBeans
	 * @throws ITrustException
	 */
	public List<HospitalBean> getAvailableHospitals(String midString) throws ITrustException {
		try {
			long mid = Long.valueOf(midString);
			List<HospitalBean> allHospitals = hospitalsDAO.getAllHospitals();
			List<HospitalBean> ourHospitals = personnelDAO.getHospitals(mid);
			while (!ourHospitals.isEmpty()) {
				allHospitals.remove(ourHospitals.remove(0));
			}

			return allHospitals;
		} catch (NumberFormatException e) {
			throw new ITrustException("HCP's MID not a number");
		}
	}

	/**
	 * Returns a list of hospitals to which the given mid is currently assigned
	 * 
	 * @param midString
	 * @return list of HosptialBeans
	 * @throws ITrustException
	 */
	public List<HospitalBean> getAssignedHospitals(String midString) throws ITrustException {
		try {
			long mid = Long.valueOf(midString);
			return personnelDAO.getHospitals(mid);
		} catch (NumberFormatException e) {
			throw new ITrustException("HCP's MID not a number");
		}
	}

	/**
	 * Assigns the mid to the hospital
	 * 
	 * @param midString
	 *            The MID of the person assigned to the hospital as a String.
	 * @param hospitalID
	 *            The ID of the hospital.
	 * @return message indicating the status of the assignment
	 * @throws ITrustException
	 */
	public String assignHCPToHospital(String midString, String hospitalID) throws ITrustException {
		try {
			long hcpID = Long.valueOf(midString);
			boolean confirm = hospitalsDAO.assignHospital(hcpID, hospitalID);
			if (confirm) {/*
						 * only patient is mentioned for transaction type 0, but spec looks like personnel
						 * should be included too...
						 */
				return "HCP successfully assigned.";
			} else
				return "Assignment did not occur";
		} catch (NumberFormatException e) {
			throw new ITrustException("HCP's MID not a number");
		}
	}

	/**
	 * Removes HCPs assignment to the designated hospital
	 * 
	 * @param midString
	 *            the HCP's mid
	 * @param hospitalID
	 *            the hospital id to be removed
	 * @return Status message
	 * @throws ITrustException
	 */
	public String removeHCPAssignmentToHospital(String midString, String hospitalID) throws ITrustException {
		try {
			long hcpID = Long.valueOf(midString);
			boolean confirm = hospitalsDAO.removeHospitalAssignment(hcpID, hospitalID);
			if (confirm) {
				return "HCP successfully unassigned";
			} else
				return "HCP not unassigned";
		} catch (NumberFormatException e) {
			throw new ITrustException("HCP's MID not a number");
		}
	}

	/**
	 * Removes all hospital assignments for the given hcp mid
	 * 
	 * @param midString
	 *            HCP's mid
	 * @return status message
	 * @throws ITrustException
	 */
	public int removeAllAssignmentsFromHCP(String midString) throws ITrustException {
		try {
			long hcpID = Long.valueOf(midString);
			int numAssignments = hospitalsDAO.removeAllHospitalAssignmentsFrom(hcpID);

			return numAssignments;
		} catch (NumberFormatException e) {
			throw new ITrustException("HCP's MID not a number");
		}
	}

	/**
	 * Checks if the hcpID param is a HCP
	 * 
	 * @param hcpID
	 *            the String to be checked
	 * @return the mid as a long if the hcpID is a HCP's mid
	 * @throws ITrustException
	 */
	public long checkHCPID(String hcpID) throws ITrustException {
		try {
			long pid = Long.valueOf(hcpID);
			if (personnelDAO.checkPersonnelExists(pid))
				return pid;
			else
				throw new ITrustException("HCP does not exist");
		} catch (NumberFormatException e) {
			throw new ITrustException("HCP ID is not a number: " + e.getMessage());
		}
	}

	/**
	 * Checks if the HCP is a LT if it is then check to see if a hospital is assigned to them
	 * 
	 * @param hcpID
	 *            the String to be checked
	 * @return true If the LT has an assigned hospital, false if not
	 * @throws ITrustException
	 */
	public boolean checkLTHospital(String hcpID) throws ITrustException{
		try{
			long pid = Long.valueOf(hcpID);
			if(personnelDAO.getPersonnel(pid).getRole().toString().equals("LT")){
				if(hospitalsDAO.checkLTHasHospital(pid)){
					return true;
				}
				return false;
			}
		} catch (NumberFormatException e) {
			throw new ITrustException("LT ID is not a number: " + e.getMessage());
		}
		return false;
	}
}
