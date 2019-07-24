package edu.ncsu.csc.itrust.action;

import edu.ncsu.csc.itrust.action.base.EditEntryAction;
import edu.ncsu.csc.itrust.beans.EntryBean;
import edu.ncsu.csc.itrust.beans.ExerciseEntryBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.ExerciseEntryDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.validate.ExerciseEntryValidator;

/**
 * EditExerciseEntryAction.java
 * Version 1
 * 4/5/2105
 * Copyright notice: none
 * Responsible for editing a exercise entry from a patient's
 * Exercise Diary. 
 */
public class EditExerciseEntryAction implements EditEntryAction {

	private ExerciseEntryDAO exerciseDAO;
	private long loggedInMID;
	private ExerciseEntryValidator exerciseValidator;
	
	/**
	 * Uses the factory to instantiate the ExerciseEntryDAO
	 * @param factory which DAOFactory to use
	 * @param loggedInMID who is currently logged in
	 */
	public EditExerciseEntryAction(DAOFactory factory, long loggedInMID) {
		exerciseDAO = new ExerciseEntryDAO(factory);
		this.loggedInMID = loggedInMID;
		exerciseValidator = new ExerciseEntryValidator();
	}
	
	/**
	 * Edits the exercise entry of a patient. It first checks to make sure that the
	 * patient has the ability to edit this exercise entry because it belongs to 
	 * him. Patients should only be able to edit entries that belong to them.
	 * @param exerciseEntry the bean to be updated
	 * @return the number of rows updated (0 means nothing happened,
	 * -1 means the logged in user cannot edit this exercise entry, and 
	 * anything else is the number of rows updated which should never
	 * exceed 1)
	 * @throws ITrustException
	 */
	public int editEntry(EntryBean entry) 
			throws ITrustException, FormValidationException {
		ExerciseEntryBean exerciseEntry = (ExerciseEntryBean) entry;
		
		int numUpdated;
		if (exerciseEntry.getPatientID() != loggedInMID) {
			numUpdated = -1; //this user does not "own" this exercise entry
		} else {
			try {
				//try to validate it
				exerciseValidator.validate(exerciseEntry);
				numUpdated = exerciseDAO.updateExerciseEntry(
						exerciseEntry.getEntryID(), loggedInMID, exerciseEntry);
			} catch (DBException d) {
				throw new ITrustException("Error updating entry from "
						+ "Exercise Diary");
			}
		}
		return numUpdated;
	}
}
