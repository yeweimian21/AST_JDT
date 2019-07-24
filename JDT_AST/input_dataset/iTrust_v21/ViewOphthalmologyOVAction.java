package edu.ncsu.csc.itrust.action;

import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc.itrust.beans.OphthalmologyOVRecordBean;
import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.OphthalmologyOVRecordDAO;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;

/**
 * Used for view ophthalmology office visit page (viewOphalmologyOVRecord.jsp). 
 * 
 * Very similar to {@link ViewObstetricsAction}
 */
public class ViewOphthalmologyOVAction {
	/**ophthalmologyOVDAO is the DAO that retrieves the ophthalmology office
	 *  visit records from the database*/
	private OphthalmologyOVRecordDAO ophthalmologyOVDAO;
	/**loggedInMID is the User that is logged in.*/
    private long loggedInMID;
    /**loggingAction is used to write to the log.*/
    private EventLoggingAction loggingAction;
    /** PatientDAO for working with patient objects in the database*/
	private PatientDAO patDAO;

    /**
     * ViewOphthalmologyOVAction is the constructor for this action class. It simply initializes the
     * instance variables.
     * @param factory The factory used to get the obstetricsDAO.
     * @param loggedInMID The MID of the logged in user.
     */
	public ViewOphthalmologyOVAction(DAOFactory factory, long loggedInMID) {
		this.ophthalmologyOVDAO = factory.getOphthalmologyOVRecordDAO();
		this.loggedInMID = loggedInMID;
		this.loggingAction = new EventLoggingAction(factory);
		this.patDAO = new PatientDAO(factory);
	}
	
    /**
     * getOphthalmologyOVByMID returns a list of ophthalmology office visits record beans for past ophthalmology care.
     * @param mid the mid of the patient.
     * @return The list of ophthalmology office visit records.
     * @throws ITrustException When there is a bad user.
     */
	public List<OphthalmologyOVRecordBean> getOphthalmologyOVByMID(long mid) throws ITrustException{
		return ophthalmologyOVDAO.getOphthalmologyOVRecordsByMID(mid);	
	}
	
	/**
	 * Retrieves an OphthalmologyOVRecordBean for an HCP.
	 * @param oid The oid of the ophthalmology office visit
	 * @return A bean containing the ophthalmology office visit.
	 * @throws ITrustException When there is a bad oid passed in.
	 */
	public OphthalmologyOVRecordBean getOphthalmologyOVForHCP(long oid) throws ITrustException{
		OphthalmologyOVRecordBean record = ophthalmologyOVDAO.getOphthalmologyOVRecord(oid);
    	loggingAction.logEvent(TransactionType.parse(8301), loggedInMID, 
				record.getMid(), "Ophthalmology Office Visit " +  oid + " viewed by " + loggedInMID);
    	return record;
	}
	
	/**
	 * Retrieves an OphthalmologyOVRecordBean for a Patient.
	 * @param oid The oid of the ophthalmology office visit
	 * @return A bean containing the ophthalmology office visit.
	 * @throws ITrustException When there is a bad oid passed in.
	 */
	public OphthalmologyOVRecordBean getOphthalmologyOVForPatient(long oid) throws ITrustException{
		OphthalmologyOVRecordBean record = ophthalmologyOVDAO.getOphthalmologyOVRecord(oid);
    	loggingAction.logEvent(TransactionType.parse(8400), loggedInMID, 
				record.getMid(), "Ophthalmology Office Visit " +  oid + " viewed by " + loggedInMID);
    	return record;
	}
	
	/**
	 * Retrieves an OphthalmologyOVRecordBean for Representee of the dependent.
	 * @param oid The oid of the ophthalmology office visit
	 * @return A bean containing the ophthalmology office visit.
	 * @throws ITrustException When there is a bad oid passed in.
	 */
	public OphthalmologyOVRecordBean getOphthalmologyOVForDependent(long oid) throws ITrustException{
		OphthalmologyOVRecordBean record = ophthalmologyOVDAO.getOphthalmologyOVRecord(oid);
    	loggingAction.logEvent(TransactionType.parse(8401), loggedInMID, 
				record.getMid(), "Ophthalmology Office Visit " +  oid + " viewed by " + loggedInMID);
    	return record;
	}
	
	/**
	 * Returns a list of PatientBeans of all patients the currently logged in patient represents and are a dependent
	 * 
	 * @param mid The mid of the patient that the dependents for are being returned.
	 * @return a list of PatientBeans of all patients the currently logged in patient represents and are a dependent
	 */
	public List<PatientBean> getDependents(long mid) {
		List<PatientBean> dependents = new ArrayList<PatientBean>();
		try {
			dependents = patDAO.getDependents(mid);
		} catch (DBException e) {
			//If a DBException occurs print a stack trace and return an empty list
			e.printStackTrace();
		}
		
		return dependents;
	}
	
	
}
