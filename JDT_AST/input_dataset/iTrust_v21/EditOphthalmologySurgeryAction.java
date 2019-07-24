package edu.ncsu.csc.itrust.action;

import edu.ncsu.csc.itrust.beans.OphthalmologySurgeryRecordBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.OphthalmologySurgeryRecordDAO;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.validate.OphthalmologySurgeryValidator;

/**
 * Used for edit ophthalmology office visit page (editOphalmologySurgeryRecord.jsp). 
 * 
 * Very similar to {@link EditObstetricsAction}
 */
public class EditOphthalmologySurgeryAction {
	/**ophthalmologySurgeryDAO is the DAO that retrieves the ophthalmology office
	 *  visit records from the database*/
	private OphthalmologySurgeryRecordDAO ophthalmologySurgeryDAO;
	/**loggedInMID is the HCP that is logged in.*/
    private long loggedInMID;
    /**loggingAction is used to write to the log.*/
    private EventLoggingAction loggingAction;

    /**
     * EditOphthalmologySurgeryAction is the constructor for this action class. It simply initializes the
     * instance variables.
     * @param factory The factory used to get the obstetricsDAO.
     * @param loggedInMID The MID of the logged in user.
     */
	public EditOphthalmologySurgeryAction(DAOFactory factory, long loggedInMID) {
		this.ophthalmologySurgeryDAO = factory.getOphthalmologySurgeryRecordDAO();
		this.loggedInMID = loggedInMID;
		this.loggingAction = new EventLoggingAction(factory);
	}
	
	/**
	 * Edits an existing ophthalmology office visit record.
	 * @param oid The oid of the ophthalmology office visit.
	 * @param p OphthalmologySurgeryRecordBean containing the info for the record to be updated.
	 * @throws FormValidationException if the patient is not successfully validated.
	 * @throws ITrustException thrown if the database encounters an issue.
	 */
	public void editOphthalmologySurgery(long oid, OphthalmologySurgeryRecordBean p)
    		throws FormValidationException, ITrustException {
		if(p != null){
			new OphthalmologySurgeryValidator().validate(p);
			
			ophthalmologySurgeryDAO.editOphthalmologySurgeryRecordsRecord(oid, p);
			
			loggingAction.logEvent(TransactionType.parse(8602), loggedInMID, 
					p.getMid(), "Surgical Ophthalmology Office Visit " +  p.getOid() + " edited by " + loggedInMID);
		} else {
			throw new ITrustException("Cannot edit a null Surgical Ophthalmology Record.");
		}
	}
}
