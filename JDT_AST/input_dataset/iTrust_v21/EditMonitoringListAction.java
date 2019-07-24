package edu.ncsu.csc.itrust.action;

import java.util.List;
import edu.ncsu.csc.itrust.beans.RemoteMonitoringDataBean;
import edu.ncsu.csc.itrust.beans.TelemedicineBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.AuthDAO;
import edu.ncsu.csc.itrust.dao.mysql.RemoteMonitoringDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;

/**
 * Handles changes (adds and removes) to the monitoring list for a certain HCP.
 * 
 */
public class EditMonitoringListAction {
	private RemoteMonitoringDAO rmDAO;
	private AuthDAO authDAO;
	private long loggedInMID;

	/**
	 * Constructor
	 * 
	 * @param factory The DAOFactory used to create the DAOs used in this action.
	 * @param loggedInMID The MID of the HCP editing their monitoring list.
	 */
	public EditMonitoringListAction(DAOFactory factory, long loggedInMID) {
		this.loggedInMID = loggedInMID;
		this.rmDAO = factory.getRemoteMonitoringDAO();
		this.authDAO = factory.getAuthDAO();
	}

	/**
	 * Adds a patient to the current HCP's remote monitoring list
	 * 
	 * @param patientMID the patient
	 * @param permissions Array indicating what data the patient is allowed to enter. 
	 			Ordered by Systolic Blood Pressure, Diastolic Blood Pressure, Glucose Level, Weight, Pedometer Reading.
	 * @return true if added successfully. False if already in list.
	 * @throws DBException
	 */
	public boolean addToList(long patientMID, TelemedicineBean tBean) throws DBException {
		return rmDAO.addPatientToList(patientMID, loggedInMID, tBean);
	}
	
	/**
	 * Removes a patient from the current HCP's remote monitoring list
	 * 
	 * @param patientMID the patient
	 * @return true if removed successfully. False if not in list.
	 * @throws DBException
	 */
	public boolean removeFromList(long patientMID) throws DBException {
		return rmDAO.removePatientFromList(patientMID, loggedInMID);
	}
	
	/**
	 * Returns whether a patient is in an HCP's list already
	 * @param patientMID the patient
	 * @return true if in DB, false otherwise
	 * @throws DBException
	 */
	public boolean isPatientInList(long patientMID) throws DBException {
		List<RemoteMonitoringDataBean> dataset = rmDAO.getPatientsData(loggedInMID);
		for(RemoteMonitoringDataBean d: dataset) {
			if(d.getPatientMID() == patientMID)
				return true;
		}
		return false;
	}
	
	/**
	 * returns the patient name
	 * 
	 * @return patient name
	 * @throws DBException
	 * @throws ITrustException
	 */
	public String getPatientName(long pid) throws DBException, ITrustException {
		return authDAO.getUserName(pid);
	}
	
}
