package edu.ncsu.csc.itrust.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.ncsu.csc.itrust.action.base.ViewEntryAction;
import edu.ncsu.csc.itrust.beans.ExerciseEntryBean;
import edu.ncsu.csc.itrust.beans.PersonnelBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.ExerciseEntryDAO;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;

/**
 * ViewExerciseEntryAction.java
 * Version 1
 * 4/5/2015
 * Copyright notice: none
 */
public class ViewExerciseEntryAction implements ViewEntryAction {

	private ExerciseEntryDAO exerciseEntryDAO;
	private PatientDAO patientDAO;
	private long loggedInMID;
	private PersonnelDAO personnelDAO;

	/**
	 * Uses the factory to construct ExerciseEntryDAO and PatientDAO
	 * 
	 * @param factory
	 *            DAO factory to use
	 * @param loggedInMID
	 *            who is currently logged in
	 */
	public ViewExerciseEntryAction(DAOFactory factory, long loggedInMID) {
		this.patientDAO = factory.getPatientDAO();
		this.personnelDAO = factory.getPersonnelDAO();
		this.exerciseEntryDAO = factory.getExerciseEntryDAO();
		this.loggedInMID = loggedInMID;
	}

	/**
	 * Takes in which patient you want to view the exercise diary for, and then
	 * returns all of the exercise dairy entries for that patient. It first checks
	 * to ensure that the person requesting the exercise diary is either a patient,
	 * or is an HCP with a specialty of fitness expert.
	 * 
	 * @param patientMID
	 *            the id of the patient whose exercise diary we want
	 * @return a list of the patient's exercise diary entries
	 */
	public List<ExerciseEntryBean> getDiary(long patientMID)
			throws ITrustException {

		PersonnelBean personnel;
		try {
			/*
			 * This nightmare if-statement just checks for two things. Either:
			 * 1) The patient is viewing their own diary.
			 * 2) A trainer designated for the provided PID is viewing the diary.
			 */
			if ((patientDAO.checkPatientExists(loggedInMID) && loggedInMID == patientMID)
					|| (((personnel = personnelDAO.getPersonnel(loggedInMID)) != null) && personnel
							.getSpecialty() != null && personnel.getSpecialty().equalsIgnoreCase("Trainer"))) {

				return exerciseEntryDAO.getPatientExerciseDiary(patientMID);
			} else {
				throw new ITrustException("You do not have permission to "
						+ "view the Exercise Diary!");
			}
		} catch (DBException d) {
			throw new ITrustException("Error retrieving Exercise Diary");
		}
	}

	/**
	 * Gets the total hours and calories burned, sorted by day.
	 * 
	 * @param patientMID
	 *            the patient we are looking at
	 * @return an entry that contains the totals for each day that a user has an
	 *         entry in his exercise diary
	 * @throws ITrustException
	 */
	public List<ExerciseEntryBean> getDiaryTotals(long patientMID)
			throws ITrustException {

		PersonnelBean personnel;
		try {
			/*
			 * This nightmare if-statement just checks for two things. Either:
			 * 1) The patient is viewing their own diary.
			 * 2) A trainer designated for the provided PID is viewing the diary.
			 */
			if ((patientDAO.checkPatientExists(loggedInMID) && loggedInMID == patientMID)
					|| (((personnel = personnelDAO.getPersonnel(loggedInMID)) != null) && personnel
							.getSpecialty() != null && personnel.getSpecialty().equalsIgnoreCase("Trainer"))) {

				return exerciseEntryDAO.getPatientExerciseDiaryTotals(patientMID);
			} else {
				throw new ITrustException("You do not have permission to "
						+ "view the Exercise Diary!");
			}
		} catch (DBException d) {
			throw new ITrustException("Error retrieving Exercise Diary");
		}
	}

	/**
	 * Returns a list of exercise diary entries between two dates.
	 * 
	 * @param lowerDate
	 *            the first date
	 * @param upperDate
	 *            the second date
	 * @return list of TransactionBeans
	 * @throws DBException
	 * @throws FormValidationException
	 */
	public List<ExerciseEntryBean> getBoundedDiary(String lowerDate,
			String upperDate, long patientMID) throws ITrustException,
			FormValidationException {
		PersonnelBean personnel;
		try {
			/*
			 * This nightmare if-statement just checks for two things. Either:
			 * 1) The patient is viewing their own diary.
			 * 2) A trainer designated for the provided PID is viewing the diary.
			 */
			if ((patientDAO.checkPatientExists(loggedInMID) && loggedInMID == patientMID)
					|| (((personnel = personnelDAO.getPersonnel(loggedInMID)) != null) && personnel
							.getSpecialty() != null && personnel.getSpecialty().equalsIgnoreCase("Trainer"))) {

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

				return exerciseEntryDAO.getBoundedExerciseDiary(lower, upper,
						patientMID);
			} else {
				throw new ITrustException("You do not have permission to "
						+ "view the Exercise Diary!");
			}
		} catch (DBException e) {
			throw new ITrustException("Error retrieving Exercise Diary");
		} catch (ParseException d) {
			throw new ITrustException("Error parsing Dates");
		}
	}

	/**
	 * Gets the total hours and calories burned between a given date range, sorted by day.
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
	public List<ExerciseEntryBean> getBoundedDiaryTotals(String lowerDate,
			String upperDate, long patientMID) throws ITrustException,
			FormValidationException {
		PersonnelBean personnel;
		try {
			/*
			 * This nightmare if-statement just checks for two things. Either:
			 * 1) The patient is viewing their own diary.
			 * 2) A trainer designated for the provided PID is viewing the diary.
			 */
			if ((patientDAO.checkPatientExists(loggedInMID) && loggedInMID == patientMID)
					|| (((personnel = personnelDAO.getPersonnel(loggedInMID)) != null) && personnel
							.getSpecialty() != null && personnel.getSpecialty().equalsIgnoreCase("Trainer"))) {

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

				return exerciseEntryDAO.getBoundedExerciseDiaryTotals(lower, upper,
						patientMID);
			} else {
				throw new ITrustException("You do not have permission to "
						+ "view the Exercise Diary!");
			}
		} catch (DBException e) {
			throw new ITrustException("Error retrieving Exercise Diary");
		} catch (ParseException d) {
			throw new ITrustException("Error parsing Dates");
		}
	}
}
