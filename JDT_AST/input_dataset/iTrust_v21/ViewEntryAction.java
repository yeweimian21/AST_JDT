package edu.ncsu.csc.itrust.action.base;

import java.util.List;

import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;

/**
 * ViewEntryAction.java
 * Version 1
 * 4/2/2015
 * Copyright notice: none
 * General behavior for viewing entries in the Wellness Diary.
 * Food Diary. 
 * 
 */
public interface ViewEntryAction {
	
	/**
	 * Gets all entries in the diary for a given patient.
	 * 
	 * @param patientMID
	 *            the id of the patient whose diary we want
	 * @return a list of the patient's diary entries
	 */
	public List<?> getDiary(long patientMID) throws ITrustException;

	/**
	 * Gets the totals relevant to the specific Wellness
	 * Diary page, sorted by day.
	 * 
	 * @param patientMID
	 *            the patient we are looking at
	 * @return an entry that contains the totals for each day that
	 *         a user has an entry in his diary
	 * @throws ITrustException
	 */
	public List<?> getDiaryTotals(long patientMID) throws ITrustException;

	/**
	 * Returns a list of diary entries between two dates.
	 * 
	 * @param lowerDate
	 *            the first date
	 * @param upperDate
	 *            the second date
	 * @return list of TransactionBeans
	 * @throws DBException
	 * @throws FormValidationException
	 */
	public List<?> getBoundedDiary(String lowerDate, String upperDate, long patientMID)
			throws ITrustException,	FormValidationException;

	/**
	 * Gets the totals relevant to the specific Wellness Diary
	 * page, bound between two dates, sorted by entry date.
	 * 
	 * @param lowerDate
	 *            the first date
	 * @param upperDate
	 *            the second date
	 * @param patientMID
	 *            the patient we are looking at
	 * @return an entry that contains the totals for each
	 *         day in the bounded range.
	 * @throws ITrustException
	 */
	public List<?> getBoundedDiaryTotals(String lowerDate, String upperDate, long patientMID)
			throws ITrustException, FormValidationException;
	
}