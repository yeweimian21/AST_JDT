package edu.ncsu.csc.itrust.action;

import edu.ncsu.csc.itrust.action.base.EditEntryAction;
import edu.ncsu.csc.itrust.beans.EntryBean;
import edu.ncsu.csc.itrust.beans.SleepEntryBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.SleepEntryDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.validate.SleepEntryValidator;

/**
 * EditSleepEntryAction.java
 * Version 1
 * 4/6/2105
 * Copyright notice: none
 * Responsible for editing a sleep entry from a patient's
 * Sleep Diary. 
 */
public class EditSleepEntryAction implements EditEntryAction {

	private SleepEntryDAO sleepDAO;
	private long loggedInMID;
	private SleepEntryValidator sleepValidator;
	
	/**
	 * Uses the factory to instantiate the SleepEntryDAO
	 * @param factory which DAOFactory to use
	 * @param loggedInMID who is currently logged in
	 */
	public EditSleepEntryAction(DAOFactory factory, long loggedInMID) {
		sleepDAO = new SleepEntryDAO(factory);
		this.loggedInMID = loggedInMID;
		sleepValidator = new SleepEntryValidator();
	}
	
	/**
	 * Edits the sleep entry of a patient. It first checks to make sure that the
	 * patient has the ability to edit this sleep entry because it belongs to 
	 * him. Patients should only be able to edit entries that belong to them.
	 * @param sleepEntry the bean to be updated
	 * @return the number of rows updated (0 means nothing happened,
	 * -1 means the logged in user cannot edit this sleep entry, and 
	 * anything else is the number of rows updated which should never
	 * exceed 1)
	 * @throws ITrustException
	 */
	public int editEntry(EntryBean entry) 
			throws ITrustException, FormValidationException {
		SleepEntryBean sleepEntry = (SleepEntryBean) entry;
		
		int numUpdated;
		if (sleepEntry.getPatientID() != loggedInMID) {
			numUpdated = -1; //this user does not "own" this sleep entry
		} else {
			try {
				//try to validate it
				sleepValidator.validate(sleepEntry);
				numUpdated = sleepDAO.updateSleepEntry(
						sleepEntry.getEntryID(), loggedInMID, sleepEntry);
			} catch (DBException d) {
				throw new ITrustException("Error updating entry from "
						+ "Sleep Diary");
			}
		}
		return numUpdated;
	}
}
