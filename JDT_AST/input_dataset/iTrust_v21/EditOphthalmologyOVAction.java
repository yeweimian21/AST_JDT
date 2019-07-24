package edu.ncsu.csc.itrust.action;

import edu.ncsu.csc.itrust.beans.OphthalmologyOVRecordBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.OphthalmologyOVRecordDAO;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.validate.OphthalmologyOVValidator;

/**
 * Used for edit ophthalmology office visit page (editOphalmologyOVRecord.jsp). 
 * 
 * Very similar to {@link EditObstetricsAction}
 */
public class EditOphthalmologyOVAction {
	/**ophthalmologyOVDAO is the DAO that retrieves the ophthalmology office
	 *  visit records from the database*/
	private OphthalmologyOVRecordDAO ophthalmologyOVDAO;
	/**loggedInMID is the HCP that is logged in.*/
    private long loggedInMID;
    /**loggingAction is used to write to the log.*/
    private EventLoggingAction loggingAction;

    /**
     * EditOphthalmologyOVAction is the constructor for this action class. It simply initializes the
     * instance variables.
     * @param factory The factory used to get the obstetricsDAO.
     * @param loggedInMID The MID of the logged in user.
     */
	public EditOphthalmologyOVAction(DAOFactory factory, long loggedInMID) {
		this.ophthalmologyOVDAO = factory.getOphthalmologyOVRecordDAO();
		this.loggedInMID = loggedInMID;
		this.loggingAction = new EventLoggingAction(factory);
	}
	
	/**
	 * Edits an existing ophthalmology office visit record.
	 * @param oid The oid of the ophthalmology office visit.
	 * @param p OphthalmologyOVRecordBean containing the info for the record to be updated.
	 * @throws FormValidationException if the patient is not successfully validated.
	 * @throws ITrustException thrown if the database encounters an issue.
	 */
	public void editOphthalmologyOV(long oid, OphthalmologyOVRecordBean p)
    		throws FormValidationException, ITrustException {
		if(p != null){
			new OphthalmologyOVValidator().validate(p);
			
			ophthalmologyOVDAO.editOphthalmologyOVRecordsRecord(oid, p);
			
			loggingAction.logEvent(TransactionType.parse(8302), loggedInMID, 
					p.getMid(), "Ophthalmology Office Visit " +  p.getOid() + " edited by " + loggedInMID);
		} else {
			throw new ITrustException("Cannot edit a null Obstetrics Record.");
		}
	}
}
