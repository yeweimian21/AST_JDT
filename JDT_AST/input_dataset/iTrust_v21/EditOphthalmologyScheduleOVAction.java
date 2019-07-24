package edu.ncsu.csc.itrust.action;

import edu.ncsu.csc.itrust.beans.OphthalmologyScheduleOVRecordBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.OphthalmologyScheduleOVDAO;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.validate.OphthalmologyScheduleOVValidator;

/**
 * Used for editing ophthalmology office visit requests. 
 */
public class EditOphthalmologyScheduleOVAction {
	/**ophthalmologyOVDAO is the DAO that retrieves the ophthalmology office
	 *  visit records from the database*/
	private OphthalmologyScheduleOVDAO ophthalmologyOVDAO;
	
	/**
     * EditOphthalmologyScheduleOVAction is the constructor for this action class. It simply initializes the
     * instance variables.
     * @param factory The factory used to get the obstetricsDAO.
     * @param loggedInMID The MID of the logged in user.
     */
	public EditOphthalmologyScheduleOVAction(DAOFactory factory, long loggedInMID) {
		this.ophthalmologyOVDAO = factory.getOphthalmologyScheduleOVDAO();
	}
	
	/**
	 * Edits an existing ophthalmology scheduled office visit record.
	 * @param oid The oid of the ophthalmology scheduled office visit.
	 * @param p OphthalmologyScheduleOVRecordBean containing the info for the record to be updated.
	 * @throws FormValidationException if the patient is not successfully validated.
	 * @throws ITrustException thrown if the database encounters an issue.
	 */
	public void editOphthalmologyScheduleOV(long oid, OphthalmologyScheduleOVRecordBean p)
    		throws FormValidationException, ITrustException {
		if(p != null){
			new OphthalmologyScheduleOVValidator().validate(p);
			
			ophthalmologyOVDAO.editOphthalmologyScheduledOVRecordsRecord(oid, p);
		} else {
			throw new ITrustException("Cannot edit a null Obstetrics Record.");
		}
	}
}
