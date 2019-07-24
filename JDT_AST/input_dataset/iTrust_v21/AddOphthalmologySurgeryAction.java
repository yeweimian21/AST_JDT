package edu.ncsu.csc.itrust.action;

import edu.ncsu.csc.itrust.beans.OphthalmologySurgeryRecordBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.OphthalmologySurgeryRecordDAO;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.validate.OphthalmologySurgeryValidator;

/**
 * Used for add ophthalmology office visit page (addOphalmologySurgeryRecord.jsp). 
 * 
 * Very similar to {@link AddObstetricsAction}
 */
public class AddOphthalmologySurgeryAction {
	/**ophthalmologySurgeryDAO is the DAO that retrieves the surgical ophthalmology 
	 * office visit records from the database*/
	private OphthalmologySurgeryRecordDAO ophthalmologySurgeryDAO;
	/**loggedInMID is the HCP that is logged in.*/
    private long loggedInMID;
    /**loggingAction is used to write to the log.*/
    private EventLoggingAction loggingAction;

    /**
     * AddOphthalmologySurgeryAction is the constructor for this action class. It simply initializes the
     * instance variables.
     * @param factory The factory used to get the ophthalmologySurgeryRecordDAO.
     * @param loggedInMID The MID of the logged in user.
     */
	public AddOphthalmologySurgeryAction(DAOFactory factory, long loggedInMID) {
		this.ophthalmologySurgeryDAO = factory.getOphthalmologySurgeryRecordDAO();
		this.loggedInMID = loggedInMID;
		this.loggingAction = new EventLoggingAction(factory);
	}
	
	/**
	 * Adds a new ophthalmology office visit record.
	 * @param p OphthalmologySurgeryRecordBean containing the info for the record to be created.
	 * @throws FormValidationException if the patient is not successfully validated.
	 * @throws ITrustException thrown if the database encounters an issue.
	 */
	public void addOphthalmologySurgery(OphthalmologySurgeryRecordBean p) 
			throws FormValidationException, ITrustException {
		if(p != null){
			//Validate the bean
			new OphthalmologySurgeryValidator().validate(p);
			
			//Add the ophthalmology office visit record to the database
			ophthalmologySurgeryDAO.addOphthalmologySurgeryRecord(p);
			
			//Log the transaction
			loggingAction.logEvent(TransactionType.parse(8600), loggedInMID, 
					p.getMid(), "Surgical Ophthalmology Office Visit " +  p.getOid() + " added");
		} else {
			throw new ITrustException("Cannot add a null Surgical Ophthalmology Record.");
		}
	}
}
