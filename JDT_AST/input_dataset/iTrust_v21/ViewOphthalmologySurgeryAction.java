package edu.ncsu.csc.itrust.action;

import java.util.List;

import edu.ncsu.csc.itrust.beans.OphthalmologySurgeryRecordBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.OphthalmologySurgeryRecordDAO;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.exception.ITrustException;

/**
 * Used for view ophthalmology office visit page (viewOphalmologySurgeryRecord.jsp). 
 * 
 * Very similar to {@link ViewObstetricsAction}
 */
public class ViewOphthalmologySurgeryAction {
	/**ophthalmologySurgeryDAO is the DAO that retrieves the ophthalmology office
	 *  visit records from the database*/
	private OphthalmologySurgeryRecordDAO ophthalmologySurgeryDAO;
	/**loggedInMID is the User that is logged in.*/
    private long loggedInMID;
    /**loggingAction is used to write to the log.*/
    private EventLoggingAction loggingAction;

    /**
     * ViewOphthalmologySurgeryAction is the constructor for this action class. It simply initializes the
     * instance variables.
     * @param factory The factory used to get the obstetricsDAO.
     * @param loggedInMID The MID of the logged in user.
     */
	public ViewOphthalmologySurgeryAction(DAOFactory factory, long loggedInMID) {
		this.ophthalmologySurgeryDAO = factory.getOphthalmologySurgeryRecordDAO();
		this.loggedInMID = loggedInMID;
		this.loggingAction = new EventLoggingAction(factory);
	}
	
    /**
     * getOphthalmologySurgeryByMID returns a list of ophthalmology office visits record beans for past ophthalmology care.
     * @param mid the mid of the patient.
     * @return The list of ophthalmology office visit records.
     * @throws ITrustException When there is a bad user.
     */
	public List<OphthalmologySurgeryRecordBean> getOphthalmologySurgeryByMID(long mid) throws ITrustException{
		return ophthalmologySurgeryDAO.getOphthalmologySurgeryRecordsByMID(mid);	
	}
	
	/**
	 * Retrieves an OphthalmologySurgeryRecordBean for an HCP.
	 * @param oid The oid of the ophthalmology office visit
	 * @return A bean containing the ophthalmology office visit.
	 * @throws ITrustException When there is a bad oid passed in.
	 */
	public OphthalmologySurgeryRecordBean getOphthalmologySurgeryForHCP(long oid) throws ITrustException{
		OphthalmologySurgeryRecordBean record = ophthalmologySurgeryDAO.getOphthalmologySurgeryRecord(oid);
    	loggingAction.logEvent(TransactionType.parse(8601), loggedInMID, 
				record.getMid(), "Surgical Ophthalmology Office Visit " +  oid + " viewed by " + loggedInMID);
    	return record;
	}
	
	
}
