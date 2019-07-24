package edu.ncsu.csc.itrust.action;

import edu.ncsu.csc.itrust.action.base.DeleteEntryAction;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.ExerciseEntryDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;

/**
 * DeleteExerciseEntryAction.java Version 1 4/5/2105 Copyright notice: none
 * Responsible for deleting an exercise entry from a patient's Exercise Diary.
 */
public class DeleteExerciseEntryAction implements DeleteEntryAction {

	private ExerciseEntryDAO exerciseDAO;
	private long loggedInMID;

	/**
	 * Uses the factory to instantiate the ExerciseEntryDAO
	 * 
	 * @param factory
	 *            which DAOFactory to use
	 * @param loggedInMID
	 *            who is currently logged in
	 */
	public DeleteExerciseEntryAction(DAOFactory factory, long loggedInMID) {
		exerciseDAO = new ExerciseEntryDAO(factory);
		this.loggedInMID = loggedInMID;
	}

	/**
	 * Deletes a exercise entry from the db
	 * 
	 * @param entryID
	 *            the exercise entry to delete
	 * @return the number of rows deleted (should never exceed 1)
	 * @throws ITrustException
	 */
	public int deleteEntry(long entryID) throws ITrustException {
		try {
			int numDeleted = exerciseDAO.deleteExerciseEntry(entryID,
					loggedInMID);
			return numDeleted;
		} catch (DBException d) {
			throw new ITrustException("Error deleting entry from "
					+ "Exercise Diary");
		}
	}
}
