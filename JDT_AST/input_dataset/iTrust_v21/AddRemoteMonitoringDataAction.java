package edu.ncsu.csc.itrust.action;

import java.util.List;
import edu.ncsu.csc.itrust.beans.RemoteMonitoringDataBean;
import edu.ncsu.csc.itrust.beans.TelemedicineBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.AuthDAO;
import edu.ncsu.csc.itrust.dao.mysql.RemoteMonitoringDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.validate.RemoteMonitoringDataBeanValidator;

/**
 * Handles adding remote monitoring patient data to the database
 * 
 */
public class AddRemoteMonitoringDataAction {
	private RemoteMonitoringDataBeanValidator validator = new RemoteMonitoringDataBeanValidator();
	private RemoteMonitoringDAO rmDAO;
	private AuthDAO authDAO;
	private long loggedInMID;
	private long patientMID;

	/**
	 * Constructor
	 * 
	 * @param factory The DAOFactory used to create the DAOs used in this action.
	 * @param loggedInMID The MID of the person recording the patient's data.
	 * @param patientMID The MID of the patient
	 */
	public AddRemoteMonitoringDataAction(DAOFactory factory, long loggedInMID, long patientMID) {
		this.loggedInMID = loggedInMID;
		this.rmDAO = factory.getRemoteMonitoringDAO();
		this.authDAO = factory.getAuthDAO();
		this.patientMID = patientMID;
	}
	
	public List<TelemedicineBean> getTelemedicineBean(long patientMID) throws DBException {
		return rmDAO.getTelemedicineBean(patientMID);
	} 

	/**
	 * Adds a patient's telemedicine data to the database.
	 * 
	 * @param weight
	 * @param pedometerReading
	 * @throws DBException
	 */
	public void addRemoteMonitoringData(RemoteMonitoringDataBean rmdBean)
	  throws DBException, FormValidationException, ITrustException {
		validator.validate(rmdBean);
		
		String role;
		if (loggedInMID == patientMID){
			role = "self-reported";
		} else if (authDAO.getUserRole(loggedInMID).getUserRolesString().equals("uap")){
			role = "case-manager";
		} else {
			role = "patient representative";
		}		
		//Store in DB
		rmDAO.storePatientData(patientMID, rmdBean, role, loggedInMID);
	}

	/**
	 * returns the patient name
	 * 
	 * @return patient name
	 * @throws DBException
	 * @throws ITrustException
	 */
	public String getPatientName(long pid) throws ITrustException {
		return authDAO.getUserName(pid);
	}
}
