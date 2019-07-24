package edu.ncsu.csc.itrust.action;

import edu.ncsu.csc.itrust.beans.OphthalmologyOVRecordBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.OphthalmologyOVRecordDAO;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.validate.OphthalmologyOVValidator;

/**
 * Used for add ophthalmology office visit page (addOphalmologyOVRecord.jsp). 
 * 
 * Very similar to {@link AddObstetricsAction}
 */
public class AddOphthalmologyOVAction {
	/**ophthalmologyOVDAO is the DAO that retrieves the ophthalmology office
	 *  visit records from the database*/
	private OphthalmologyOVRecordDAO ophthalmologyOVDAO;
	/**loggedInMID is the HCP that is logged in.*/
    private long loggedInMID;
    /**loggingAction is used to write to the log.*/
    private EventLoggingAction loggingAction;

    /**
     * AddOphthalmologyOVAction is the constructor for this action class. It simply initializes the
     * instance variables.
     * @param factory The factory used to get the obstetricsDAO.
     * @param loggedInMID The MID of the logged in user.
     */
	public AddOphthalmologyOVAction(DAOFactory factory, long loggedInMID) {
		this.ophthalmologyOVDAO = factory.getOphthalmologyOVRecordDAO();
		this.loggedInMID = loggedInMID;
		this.loggingAction = new EventLoggingAction(factory);
	}
	
	/**
	 * Adds a new ophthalmology office visit record.
	 * @param p OphthalmologyOVRecordBean containing the info for the record to be created.
	 * @throws FormValidationException if the patient is not successfully validated.
	 * @throws ITrustException thrown if the database encounters an issue.
	 */
	public void addOphthalmologyOV(OphthalmologyOVRecordBean p) 
			throws FormValidationException, ITrustException {
		if(p != null){
			//Validate the bean
			new OphthalmologyOVValidator().validate(p);
			
			//Add the ophthalmology office visit record to the database
			ophthalmologyOVDAO.addOphthalmologyOVRecord(p);
			
			//Log the transaction
			loggingAction.logEvent(TransactionType.parse(8300), loggedInMID, 
					p.getMid(), "Ophthalmology Office Visit " +  p.getOid() + " added");
		} else {
			throw new ITrustException("Cannot add a null Ophthalmology Office Visit.");
		}
	}
}
