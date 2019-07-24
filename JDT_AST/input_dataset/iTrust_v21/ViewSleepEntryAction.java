package edu.ncsu.csc.itrust.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.ncsu.csc.itrust.action.base.ViewEntryAction;
import edu.ncsu.csc.itrust.beans.SleepEntryBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.SleepEntryDAO;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;

/**
 * ViewSleepEntryAction.java
 * Version 1
 * 4/6/2015
 * Copyright notice: none
 */
public class ViewSleepEntryAction implements ViewEntryAction {

	private SleepEntryDAO sleepEntryDAO;
	private PatientDAO patientDAO;
	private long loggedInMID;
	private PersonnelDAO personnelDAO;

	/**
	 * Uses the factory to construct SleepEntryDAO and PatientDAO
	 * 
	 * @param factory
	 *            DAO factory to use
	 * @param loggedInMID
	 *            who is currently logged in
	 */
	public ViewSleepEntryAction(DAOFactory factory, long loggedInMID) {
		this.patientDAO = factory.getPatientDAO();
		this.personnelDAO = factory.getPersonnelDAO();
		this.sleepEntryDAO = factory.getSleepEntryDAO();
		this.loggedInMID = loggedInMID;
	}

	/**
	 * Takes in which patient you want to view the sleep diary for, and then
	 * returns all of the sleep dairy entries for that patient. It first checks
	 * to ensure that the person requesting the sleep diary is either a patient
	 * or a HCP.
	 * 
	 * @param patientMID
	 *            the id of the patient whose sleep diary we want
	 * @return a list of the patient's sleep diary entries
	 */
	public List<SleepEntryBean> getDiary(long patientMID)
			throws ITrustException {
		try {
			/*
			 * This nightmare if-statement just checks for two things. Either:
			 * 1) The patient is viewing their own diary.
			 * 2) A HCP is viewing the diary.
			 */
			if ((patientDAO.checkPatientExists(loggedInMID) && loggedInMID == patientMID)
					|| (personnelDAO.getPersonnel(loggedInMID)) != null) {

				return sleepEntryDAO.getPatientSleepDiary(patientMID);
			} else {
				throw new ITrustException("You do not have permission to "
						+ "view the Sleep Diary!");
			}
		} catch (DBException d) {
			throw new ITrustException("Error retrieving Sleep Diary");
		}
	}

	/**
	 * Gets the total hours slept, sorted by day.
	 * 
	 * @param patientMID
	 *            the patient we are looking at
	 * @return an entry that contains the totals for each day that a user has an
	 *         entry in his sleep diary
	 * @throws ITrustException
	 */
	public List<SleepEntryBean> getDiaryTotals(long patientMID)
			throws ITrustException {
		try {
			/*
			 * This nightmare if-statement just checks for two things. Either:
			 * 1) The patient is viewing their own diary.
			 * 2) A HCP is viewing the diary.
			 */
			if ((patientDAO.checkPatientExists(loggedInMID) && loggedInMID == patientMID)
					|| (personnelDAO.getPersonnel(loggedInMID)) != null) {

				return sleepEntryDAO.getPatientSleepDiaryTotals(patientMID);
			} else {
				throw new ITrustException("You do not have permission to "
						+ "view the Sleep Diary!");
			}
		} catch (DBException d) {
			throw new ITrustException("Error retrieving Sleep Diary");
		}
	}

	/**
	 * Returns a list of sleep diary entries between two dates.
	 * 
	 * @param lowerDate
	 *            the first date
	 * @param upperDate
	 *            the second date
	 * @return list of TransactionBeans
	 * @throws DBException
	 * @throws FormValidationException
	 */
	public List<SleepEntryBean> getBoundedDiary(String lowerDate,
			String upperDate, long patientMID) throws ITrustException,
			FormValidationException {
		try {
			/*
			 * This nightmare if-statement just checks for two things. Either:
			 * 1) The patient is viewing their own diary.
			 * 2) A HCP is viewing the diary.
			 */
			if ((patientDAO.checkPatientExists(loggedInMID) && loggedInMID == patientMID)
					|| (personnelDAO.getPersonnel(loggedInMID)) != null) {

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

				return sleepEntryDAO.getBoundedSleepDiary(lower, upper,
						patientMID);
			} else {
				throw new ITrustException("You do not have permission to "
						+ "view the Sleep Diary!");
			}
		} catch (DBException e) {
			throw new ITrustException("Error retrieving Sleep Diary");
		} catch (ParseException d) {
			throw new ITrustException("Error parsing Dates");
		}
	}

	/**
	 * Gets the total hours slept between a given date range, sorted by day.
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
	public List<SleepEntryBean> getBoundedDiaryTotals(String lowerDate,
			String upperDate, long patientMID) throws ITrustException,
			FormValidationException {
		try {
			/*
			 * This nightmare if-statement just checks for two things. Either:
			 * 1) The patient is viewing their own diary.
			 * 2) A HCP is viewing the diary.
			 */
			if ((patientDAO.checkPatientExists(loggedInMID) && loggedInMID == patientMID)
					|| (personnelDAO.getPersonnel(loggedInMID)) != null) {

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

				return sleepEntryDAO.getBoundedSleepDiaryTotals(lower, upper,
						patientMID);
			} else {
				throw new ITrustException("You do not have permission to "
						+ "view the Sleep Diary!");
			}
		} catch (DBException e) {
			throw new ITrustException("Error retrieving Sleep Diary");
		} catch (ParseException d) {
			throw new ITrustException("Error parsing Dates");
		}
	}
}
