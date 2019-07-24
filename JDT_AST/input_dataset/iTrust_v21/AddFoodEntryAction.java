package edu.ncsu.csc.itrust.action;

import edu.ncsu.csc.itrust.action.base.AddEntryAction;
import edu.ncsu.csc.itrust.beans.EntryBean;
import edu.ncsu.csc.itrust.beans.FoodEntryBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.FoodEntryDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.validate.FoodEntryValidator;

/**
 * AddFoodEntryAction.java Version 1 2/21/2015 Copyright notice: none
 * Responsible for adding a new food entry to a patient's Food Diary.
 */
public class AddFoodEntryAction implements AddEntryAction {

	private FoodEntryDAO foodDAO;
	private FoodEntryValidator foodValidator;
	private long loggedInMID;

	/**
	 * Uses the factory to instantiate the foodentrydao
	 * 
	 * @param factory
	 *            which DAOFactory to use
	 * @param loggedInMID
	 *            who is currently logged in
	 */
	public AddFoodEntryAction(DAOFactory factory, long loggedInMID) {
		foodDAO = new FoodEntryDAO(factory);
		foodValidator = new FoodEntryValidator();
		this.loggedInMID = loggedInMID;
	}

	/**
	 * Adds a new food entry to the food diary of the specified patient.
	 * Currently it checks to ensure that the patient whose food dairy we are
	 * adding to is the logged in patient, and that the patient exists, but I
	 * don't know if we need to have those checks. Since only patients can add
	 * to only their own food diaries, we do not need the MID of the patient
	 * whose food diary we are adding to, we just use the MID of the logged in
	 * user.
	 * 
	 * @param entry
	 *            the FoodEntryBean to add to the Food Diary
	 * @return Either a string saying the food entry was added successfully, or
	 *         a string saying there was an error
	 * @throws FormValidationException
	 */
	public String addEntry(EntryBean entry)
			throws FormValidationException {
		try {
			FoodEntryBean foodEntry = (FoodEntryBean) entry;
			
			foodValidator.validate(foodEntry);
			foodEntry.setPatientID(loggedInMID);
			foodDAO.addFoodEntry(foodEntry);
			return "Success: " + foodEntry.getFood() + " for "
					+ foodEntry.getDateEatenStr().toString()
					+ " was added successfully!";
		} catch (DBException e) {
			return e.getMessage();
		} catch (ITrustException d) {
			return d.getMessage();
		}
	}
}
