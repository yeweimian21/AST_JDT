package edu.ncsu.csc.itrust.action;

import java.util.List;

import edu.ncsu.csc.itrust.beans.OphthalmologyScheduleOVRecordBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.OphthalmologyScheduleOVDAO;
import edu.ncsu.csc.itrust.exception.ITrustException;

/**
 * Used for viewing ophthalmology office visit requests.
 */
public class ViewOphthalmologyScheduleOVAction {
	
	/**ophthalmologyOVDAO is the DAO that retrieves the ophthalmology office
	 *  visit records from the database*/
	private OphthalmologyScheduleOVDAO ophthalmologyOVDAO;
    
    /**
     * ViewOphthalmologyOVAction is the constructor for this action class. It simply initializes the
     * instance variables.
     * @param factory The factory used to get the obstetricsDAO.
     * @param loggedInMID The MID of the logged in user.
     */
	public ViewOphthalmologyScheduleOVAction(DAOFactory factory, long loggedInMID) {
		this.ophthalmologyOVDAO = factory.getOphthalmologyScheduleOVDAO();
	}
    
    /**
     * getOphthalmologyScheduleOVByMID returns a list of ophthalmology scheduled office visits record beans for the patient.
     * @param mid the mid of the patient.
     * @return The list of ophthalmology office visit records.
     * @throws ITrustException When there is a bad user.
     */
	public List<OphthalmologyScheduleOVRecordBean> getOphthalmologyScheduleOVByPATIENTMID(long mid) throws ITrustException{
		return ophthalmologyOVDAO.getOphthalmologyScheduleOVRecordsByPATIENTMID(mid);	
	}
	
	/**
     * getOphthalmologyScheduleOVByMID returns a list of ophthalmology scheduled office visits record beans for the doctor.
     * @param mid the mid of the patient.
     * @return The list of ophthalmology office visit records.
     * @throws ITrustException When there is a bad user.
     */
	public List<OphthalmologyScheduleOVRecordBean> getOphthalmologyScheduleOVByDOCTORMID(long mid) throws ITrustException{
		return ophthalmologyOVDAO.getOphthalmologyScheduleOVRecordsByDOCTORMID(mid);	
	}
	
	/**
	 * Retrieves an OphthalmologyScheduleOVRecordBean for a Patient.
	 * @param oid The oid of the ophthalmology scheduled office visit
	 * @return A bean containing the ophthalmology scheduled office visit.
	 * @throws ITrustException When there is a bad oid passed in.
	 */
	public OphthalmologyScheduleOVRecordBean getOphthalmologyScheduleOVForPatient(long oid) throws ITrustException{
		OphthalmologyScheduleOVRecordBean record = ophthalmologyOVDAO.getOphthalmologyScheduleOVRecord(oid);
    	return record;
	}
	
	/**
	 * Retrieves an OphthalmologyScheduleOVRecordBean for a HCP.
	 * @param oid The oid of the ophthalmology scheduled office visit
	 * @return A bean containing the ophthalmology scheduled office visit.
	 * @throws ITrustException When there is a bad oid passed in.
	 */
	public OphthalmologyScheduleOVRecordBean getOphthalmologyScheduleOVForHCP(long oid) throws ITrustException{
		OphthalmologyScheduleOVRecordBean record = ophthalmologyOVDAO.getOphthalmologyScheduleOVRecord(oid);
    	return record;
	}
}
