package edu.ncsu.csc.itrust.action;

import edu.ncsu.csc.itrust.action.base.EditEntryAction;
import edu.ncsu.csc.itrust.beans.EntryBean;
import edu.ncsu.csc.itrust.beans.FoodEntryBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.FoodEntryDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.validate.FoodEntryValidator;

/**
 * EditFoodEntryAction.java
 * Version 1
 * 2/26/2105
 * Copyright notice: none
 * Responsible for editing a food entry from a patient's
 * Food Diary. 
 */
public class EditFoodEntryAction implements EditEntryAction {

	private FoodEntryDAO foodDAO;
	private long loggedInMID;
	private FoodEntryValidator foodValidator;
	
	/**
	 * Uses the factory to instantiate the foodentrydao
	 * @param factory which DAOFactory to use
	 * @param loggedInMID who is currently logged in
	 */
	public EditFoodEntryAction(DAOFactory factory, long loggedInMID) {
		foodDAO = new FoodEntryDAO(factory);
		this.loggedInMID = loggedInMID;
		foodValidator = new FoodEntryValidator();
	}
	
	/**
	 * Edits the food entry of a patient. It first checks to make sure that the
	 * patient has the ability to edit this food entry because it belongs to 
	 * him. Patients should only be able to edit entries that belong to them.
	 * @param foodEntry the bean to be updated
	 * @return the number of rows updated (0 means nothing happened,
	 * -1 means the logged in user cannot edit this food entry, and 
	 * anything else is the number of rows updated which should never
	 * exceed 1)
	 * @throws ITrustException
	 */
	public int editEntry(EntryBean entry) 
			throws ITrustException, FormValidationException {
		FoodEntryBean foodEntry = (FoodEntryBean) entry;
		
		int numUpdated;
		if (foodEntry.getPatientID() != loggedInMID) {
			numUpdated = -1; //this user does not "own" this food entry
		} else {
			try {
				//try to validate it
				foodValidator.validate(foodEntry);
				numUpdated = foodDAO.updateFoodEntry(
						foodEntry.getEntryID(), loggedInMID, foodEntry);
			} catch (DBException d) {
				throw new ITrustException("Error updating entry from "
						+ "Food Diary");
			}
		}
		return numUpdated;
	}
}
