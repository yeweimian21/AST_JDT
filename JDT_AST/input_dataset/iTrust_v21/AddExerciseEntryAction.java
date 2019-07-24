package edu.ncsu.csc.itrust.action;

import edu.ncsu.csc.itrust.action.base.AddEntryAction;
import edu.ncsu.csc.itrust.beans.EntryBean;
import edu.ncsu.csc.itrust.beans.ExerciseEntryBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.ExerciseEntryDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.validate.ExerciseEntryValidator;

/**
 * AddExerciseEntryAction.java
 * Version 1
 * 4/2/2015
 * Copyright notice: none
 * Responsible for adding a new exercise entry to a patient's Exercise Diary.
 */
public class AddExerciseEntryAction implements AddEntryAction {

	private ExerciseEntryDAO exerciseDAO;
	private ExerciseEntryValidator exerciseValidator;
	private long loggedInMID;

	/**
	 * Uses the factory to instantiate the ExerciseEntryDAO
	 * 
	 * @param factory
	 *            which DAOFactory to use
	 * @param loggedInMID
	 *            who is currently logged in
	 */
	public AddExerciseEntryAction(DAOFactory factory, long loggedInMID) {
		exerciseDAO = new ExerciseEntryDAO(factory);
		exerciseValidator = new ExerciseEntryValidator();
		this.loggedInMID = loggedInMID;
	}

	/**
	 * Adds a new exercise entry to the exercise diary of the specified patient.
	 * Currently it checks to ensure that the patient whose exercise dairy we are
	 * adding to is the logged in patient, and that the patient exists.
	 * Since only patients can add to only their own exercise diaries,
	 * we do not need the MID of the patient whose exercise diary we are adding to,
	 * we just use the MID of the logged in user.
	 * 
	 * @param entry
	 *            the ExerciseEntryBean to add to the exercise Diary
	 * @return Either a string saying the exercise entry was added successfully, or
	 *         a string saying there was an error
	 * @throws FormValidationException
	 */
	public String addEntry(EntryBean entry)
			throws FormValidationException {
		try {
			ExerciseEntryBean exerciseEntry = (ExerciseEntryBean) entry;
			
			exerciseValidator.validate(exerciseEntry);
			exerciseEntry.setPatientID(loggedInMID);
			exerciseDAO.addExerciseEntry(exerciseEntry);
			return "Success: " + exerciseEntry.getStrName() + " for "
					+ exerciseEntry.getStrDate().toString()
					+ " was added successfully!";
		} catch (DBException e) {
			return e.getMessage();
		} catch (ITrustException d) {
			return d.getMessage();
		}
	}
}
