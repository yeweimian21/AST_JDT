package edu.ncsu.csc.itrust.action;

import edu.ncsu.csc.itrust.action.base.AddEntryAction;
import edu.ncsu.csc.itrust.beans.EntryBean;
import edu.ncsu.csc.itrust.beans.SleepEntryBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.SleepEntryDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.validate.SleepEntryValidator;

/**
 * AddSleepEntryAction.java
 * Version 1
 * 4/6/2015
 * Copyright notice: none
 * Responsible for adding a new sleep entry to a patient's Sleep Diary.
 */
public class AddSleepEntryAction implements AddEntryAction {

	private SleepEntryDAO sleepDAO;
	private SleepEntryValidator sleepValidator;
	private long loggedInMID;

	/**
	 * Uses the factory to instantiate the SleepEntryDAO
	 * 
	 * @param factory
	 *            which DAOFactory to use
	 * @param loggedInMID
	 *            who is currently logged in
	 */
	public AddSleepEntryAction(DAOFactory factory, long loggedInMID) {
		sleepDAO = new SleepEntryDAO(factory);
		sleepValidator = new SleepEntryValidator();
		this.loggedInMID = loggedInMID;
	}

	/**
	 * Adds a new sleep entry to the sleep diary of the specified patient.
	 * Currently it checks to ensure that the patient whose sleep dairy we are
	 * adding to is the logged in patient, and that the patient exists.
	 * Since only patients can add to only their own sleep diaries,
	 * we do not need the MID of the patient whose sleep diary we are adding to,
	 * we just use the MID of the logged in user.
	 * 
	 * @param entry
	 *            the SleepEntryBean to add to the sleep Diary
	 * @return Either a string saying the sleep entry was added successfully, or
	 *         a string saying there was an error
	 * @throws FormValidationException
	 */
	public String addEntry(EntryBean entry)
			throws FormValidationException {
		try {
			SleepEntryBean sleepEntry = (SleepEntryBean) entry;
			
			sleepValidator.validate(sleepEntry);
			sleepEntry.setPatientID(loggedInMID);
			sleepDAO.addSleepEntry(sleepEntry);
			return "Success: Entry for "
					+ sleepEntry.getStrDate().toString()
					+ " was added successfully!";
		} catch (DBException e) {
			return e.getMessage();
		} catch (ITrustException d) {
			return d.getMessage();
		}
	}
}
