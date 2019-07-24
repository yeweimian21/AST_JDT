package edu.ncsu.csc.itrust.action;

import java.sql.SQLException;
import java.util.List;
import edu.ncsu.csc.itrust.beans.ApptBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.ApptDAO;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;

/**
 * ApptAction
 */
public abstract class ApptAction {
	
	/**apptDAO*/
	protected ApptDAO apptDAO;
	/**patientDAO*/
	protected PatientDAO patientDAO;
	/**personnelDAO*/
	protected PersonnelDAO personnelDAO;
	
	/**
	 * ApptAction
	 * @param factory factory
	 * @param loggedInMID loggedMID
	 */
	public ApptAction(DAOFactory factory, long loggedInMID) {
		this.apptDAO = factory.getApptDAO();
		this.patientDAO = factory.getPatientDAO();
		this.personnelDAO = factory.getPersonnelDAO();
	}
	
	/**
	 * Driver method to get all appointment conflicts, used in jsp files
	 * @param mid mid
	 * @param appt appt
	 * @return conflicts
	 * @throws SQLException
	 * @throws DBException 
	 */
	public List<ApptBean> getConflictsForAppt(long mid, ApptBean appt) throws SQLException, DBException{
		return apptDAO.getAllHCPConflictsForAppt(mid, appt);
	}	
	
	/**
	 * returns a list of appointments that conflict for a given patient/hcp
	 * @param mid the MID of the user
	 * @return list of apptBeans
	 * @throws SQLException 
	 * @throws DBException 
	 */
	public List<ApptBean> getAllConflicts(long mid) throws SQLException, DBException{
		if(mid < 7000000000L)
			return apptDAO.getAllConflictsForPatient(mid);
		else
			return apptDAO.getAllConflictsForDoctor(mid);
	}
	
	/**
	 * Gets a users's name from their MID
	 * 
	 * @param mid the MID of the user
	 * @return the user's name
	 * @throws ITrustException
	 */
	public String getName(long mid) throws ITrustException {
		if(mid < 7000000000L)
			return patientDAO.getName(mid);
		else
			return personnelDAO.getName(mid);
	}
}
