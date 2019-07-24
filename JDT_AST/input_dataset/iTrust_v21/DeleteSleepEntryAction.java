package edu.ncsu.csc.itrust.action;

import edu.ncsu.csc.itrust.action.base.DeleteEntryAction;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.SleepEntryDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;

/**
 * DeleteSleepEntryAction.java
 * Version 1
 * 4/6/2105
 * Copyright notice: none
 * Responsible for deleting an sleep entry from a patient's Sleep Diary.
 */
public class DeleteSleepEntryAction implements DeleteEntryAction {

	private SleepEntryDAO sleepDAO;
	private long loggedInMID;

	/**
	 * Uses the factory to instantiate the SleepEntryDAO
	 * 
	 * @param factory
	 *            which DAOFactory to use
	 * @param loggedInMID
	 *            who is currently logged in
	 */
	public DeleteSleepEntryAction(DAOFactory factory, long loggedInMID) {
		sleepDAO = new SleepEntryDAO(factory);
		this.loggedInMID = loggedInMID;
	}

	/**
	 * Deletes a sleep entry from the db
	 * 
	 * @param entryID
	 *            the sleep entry to delete
	 * @return the number of rows deleted (should never exceed 1)
	 * @throws ITrustException
	 */
	public int deleteEntry(long entryID) throws ITrustException {
		try {
			int numDeleted = sleepDAO.deleteSleepEntry(entryID,
					loggedInMID);
			return numDeleted;
		} catch (DBException d) {
			throw new ITrustException("Error deleting entry from "
					+ "Sleep Diary");
		}
	}
}
