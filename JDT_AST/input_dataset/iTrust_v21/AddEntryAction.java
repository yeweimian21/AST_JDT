package edu.ncsu.csc.itrust.action.base;

import edu.ncsu.csc.itrust.beans.EntryBean;
import edu.ncsu.csc.itrust.exception.FormValidationException;

/**
 * AddEntryAction.java
 * Version 1
 * 4/2/2015
 * Copyright notice: none
 * General behavior for adding entries to a Wellness Diary subcategory.
 * Food Diary. 
 * 
 */
public interface AddEntryAction {
	/**
	 * Adds a new entry to its associated diary in the database.
	 * 
	 * @param entry
	 *            the Bean to add.
	 * @return Success/error message.
	 * @throws FormValidationException
	 */
	public String addEntry(EntryBean entry) throws FormValidationException;
}