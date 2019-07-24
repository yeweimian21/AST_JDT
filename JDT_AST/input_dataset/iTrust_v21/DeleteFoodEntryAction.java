package edu.ncsu.csc.itrust.action;

import edu.ncsu.csc.itrust.action.base.DeleteEntryAction;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.FoodEntryDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;


/**
 * DeleteFoodEntryAction.java
 * Version 1
 * 2/26/2105
 * Copyright notice: none
 * Responsible for deleting a food entry from a patient's
 * Food Diary. 
 * I would like to include validation here that a patient
 * is not trying to delete somebody else's food entry, but I do
 * not see how to do that without querying the database first,
 * and I would prefer to keep trips to db as low as possible.
 */
public class DeleteFoodEntryAction implements DeleteEntryAction {

	private FoodEntryDAO foodDAO;
	private long loggedInMID;
	
	/**
	 * Uses the factory to instantiate the foodentrydao
	 * @param factory which DAOFactory to use
	 * @param loggedInMID who is currently logged in
	 */
	public DeleteFoodEntryAction(DAOFactory factory, long loggedInMID) {
		foodDAO = new FoodEntryDAO(factory);
		this.loggedInMID = loggedInMID;
	}
	
	/**
	 * Deletes a food entry from the db
	 * @param entryID the food entry to delete
	 * @return the number of rows deleted (should never exceed 1)
	 * @throws ITrustException
	 */
	public int deleteEntry(long entryID) throws ITrustException {
		try {
			int numDeleted = foodDAO.deleteFoodEntry(entryID, loggedInMID);
			return numDeleted;
		} catch (DBException d) {
			throw new ITrustException("Error deleting entry from "
					+ "Food Diary");
		}
	}
}
