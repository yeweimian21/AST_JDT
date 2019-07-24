package edu.ncsu.csc.itrust.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.ncsu.csc.itrust.action.base.ViewEntryAction;
import edu.ncsu.csc.itrust.beans.FoodEntryBean;
import edu.ncsu.csc.itrust.beans.PersonnelBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.FoodEntryDAO;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;

/**
 * We need to decide how we want to do this. We can include a check here in
 * getting a food diary to ensure that the person viewing it is either a patient
 * or an HCP with the specialty of 'nutritionist' before letting them get the
 * food diary, or we could break this up into separate classes and separate jsp
 * pages. ViewFoodEntryAction.java Version 1 2/21/2015 Copyright notice: none
 */
public class ViewFoodEntryAction implements ViewEntryAction {

	private FoodEntryDAO foodEntryDAO;
	private PatientDAO patientDAO;
	private long loggedInMID;
	private PersonnelDAO personnelDAO;

	/**
	 * Uses the factory to construct foodentrydao and patientdao
	 * 
	 * @param factory
	 *            DAO factory to use
	 * @param loggedInMID
	 *            who is currently logged in
	 */
	public ViewFoodEntryAction(DAOFactory factory, long loggedInMID) {
		this.patientDAO = factory.getPatientDAO();
		this.personnelDAO = factory.getPersonnelDAO();
		this.foodEntryDAO = factory.getFoodEntryDAO();
		this.loggedInMID = loggedInMID;
	}

	/**
	 * Takes in which patient you want to view the food diary for, and then
	 * returns all of the food dairy entries for that patient. It first checks
	 * to ensure that the person requesting the food diary is either a patient,
	 * or is an HCP with a specialty of nutritionist.
	 * 
	 * @param patientMID
	 *            the id of the patient whose food diary we want
	 * @return a list of the patient's food diary entries
	 */
	public List<FoodEntryBean> getDiary(long patientMID)
			throws ITrustException {

		PersonnelBean personnel;
		try {
			/*
			 * This nightmare if-statement just checks for two things. Either:
			 * 1) The patient is viewing their own diary.
			 * 2) A nutritionist designated for the provided PID is viewing the diary.
			 * the Nutritionist must be the designated nutritionist
			 */
			if ((patientDAO.checkPatientExists(loggedInMID) && loggedInMID == patientMID)
					|| (((personnel = personnelDAO.getPersonnel(loggedInMID)) != null) && personnel
							.getSpecialty() != null && personnel.getSpecialty().equalsIgnoreCase("Nutritionist") && 
							(patientDAO.getDesignatedNutritionist(patientMID) == loggedInMID))) {
				return foodEntryDAO.getPatientFoodDiary(patientMID);
			} else {
				throw new ITrustException("You do not have permission to "
						+ "view the Food Diary!");
			}
		} catch (DBException d) {
			throw new ITrustException("Error retrieving Food Diary");
		}
	}

	/**
	 * Gets the totals of carbs, protein, sugar, calories, sodium, fat, and
	 * fiber a user has eaten sorted by day.
	 * 
	 * @param patientMID
	 *            the patient we are looking at
	 * @return an entry that contains the totals for each day that a user has an
	 *         entry in his food diary
	 * @throws ITrustException
	 */
	public List<FoodEntryBean> getDiaryTotals(long patientMID)
			throws ITrustException {

		PersonnelBean personnel;
		try {
			/*
			 * This nightmare if-statement just checks for two things. Either:
			 * 1) The patient is viewing their own diary.
			 * 2) A nutritionist designated for the provided PID is viewing the diary.
			 */
			if ((patientDAO.checkPatientExists(loggedInMID) && loggedInMID == patientMID)
					|| (((personnel = personnelDAO.getPersonnel(loggedInMID)) != null) && personnel
							.getSpecialty() != null && personnel.getSpecialty().equalsIgnoreCase("Nutritionist") && 
							(patientDAO.getDesignatedNutritionist(patientMID) == loggedInMID))) {
				return foodEntryDAO.getPatientFoodDiaryTotals(patientMID);
			} else {
				throw new ITrustException("You do not have permission to "
						+ "view the Food Diary!");
			}
		} catch (DBException d) {
			throw new ITrustException("Error retrieving Food Diary");
		}
	}

	/**
	 * Returns a list of food diary entries between two dates.
	 * 
	 * @param lowerDate
	 *            the first date
	 * @param upperDate
	 *            the second date
	 * @return list of TransactionBeans
	 * @throws DBException
	 * @throws FormValidationException
	 */
	public List<FoodEntryBean> getBoundedDiary(String lowerDate,
			String upperDate, long patientMID) throws ITrustException,
			FormValidationException {
		PersonnelBean personnel;
		try {
			/*
			 * This nightmare if-statement just checks for two things. Either:
			 * 1) The patient is viewing their own diary.
			 * 2) A nutritionist designated for the provided PID is viewing the diary.
			 */
			if ((patientDAO.checkPatientExists(loggedInMID) && loggedInMID == patientMID)
					|| (((personnel = personnelDAO.getPersonnel(loggedInMID)) != null) && personnel
							.getSpecialty() != null && personnel.getSpecialty().equalsIgnoreCase("Nutritionist") && 
									(patientDAO.getDesignatedNutritionist(patientMID) == loggedInMID))) {

				/*
				 * Month can have 1 or 2 digits, same with day, and year must
				 * have 4.
				 */
				Pattern p = Pattern
						.compile("[0-9]{1,2}?/[0-9]{1,2}?/[0-9]{4}?");
				Matcher m = p.matcher(lowerDate);
				Matcher n = p.matcher(upperDate);
				/*
				 * If it fails to match either of them, throw the form
				 * validation exception
				 */
				if (!m.matches() || !n.matches()) {
					throw new FormValidationException(
							"Enter dates in MM/dd/yyyy");
				}

				Date lower = new SimpleDateFormat("MM/dd/yyyy")
						.parse(lowerDate);
				Date upper = new SimpleDateFormat("MM/dd/yyyy")
						.parse(upperDate);

				if (lower.after(upper)) {
					throw new FormValidationException(
							"Start date must be before end date!");
				}

				return foodEntryDAO.getBoundedFoodDiary(lower, upper,
						patientMID);
			} else {
				throw new ITrustException("You do not have permission to "
						+ "view the Food Diary!");
			}
		} catch (DBException e) {
			throw new ITrustException("Error retrieving Food Diary");
		} catch (ParseException d) {
			throw new ITrustException("Error parsing Dates");
		}
	}

	/**
	 * Gets the totals of carbs, protein, sugar, calories, sodium, fat, and
	 * fiber a user has eaten in a given date range, sorted by day.
	 * 
	 * @param lowerDate
	 *            the first date
	 * @param upperDate
	 *            the second date
	 * @param patientMID
	 *            the patient we are looking at
	 * @return an entry that contains the totals for each day in the given range
	 *         for the patient
	 * @throws ITrustException
	 */
	public List<FoodEntryBean> getBoundedDiaryTotals(String lowerDate,
			String upperDate, long patientMID) throws ITrustException,
			FormValidationException {
		PersonnelBean personnel;
		try {
			/*
			 * This nightmare if-statement just checks for two things. Either:
			 * 1) The patient is viewing their own diary.
			 * 2) A nutritionist designated for the provided PID is viewing the diary.
			 */
			if ((patientDAO.checkPatientExists(loggedInMID) && loggedInMID == patientMID)
					|| (((personnel = personnelDAO.getPersonnel(loggedInMID)) != null) && personnel
							.getSpecialty() != null && personnel.getSpecialty().equalsIgnoreCase("Nutritionist") && 
									(patientDAO.getDesignatedNutritionist(patientMID) == loggedInMID))) {

				/*
				 * Month can have 1 or 2 digits, same with day, and year must
				 * have 4.
				 */
				Pattern p = Pattern
						.compile("[0-9]{1,2}?/[0-9]{1,2}?/[0-9]{4}?");
				Matcher m = p.matcher(lowerDate);
				Matcher n = p.matcher(upperDate);
				/*
				 * If it fails to match either of them, throw the form
				 * validation exception
				 */
				if (!m.matches() || !n.matches()) {
					throw new FormValidationException(
							"Enter dates in MM/dd/yyyy");
				}

				Date lower = new SimpleDateFormat("MM/dd/yyyy")
						.parse(lowerDate);
				Date upper = new SimpleDateFormat("MM/dd/yyyy")
						.parse(upperDate);

				if (lower.after(upper)) {
					throw new FormValidationException(
							"Start date must be before end date!");
				}

				return foodEntryDAO.getBoundedFoodDiaryTotals(lower, upper,
						patientMID);
			} else {
				throw new ITrustException("You do not have permission to "
						+ "view the Food Diary!");
			}
		} catch (DBException e) {
			throw new ITrustException("Error retrieving Food Diary");
		} catch (ParseException d) {
			throw new ITrustException("Error parsing Dates");
		}
	}
}
