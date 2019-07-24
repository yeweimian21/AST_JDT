package edu.ncsu.csc.itrust.action.base;

import edu.ncsu.csc.itrust.beans.EntryBean;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;

public interface EditEntryAction {
	/**
	 * Communicates changes between a JSP page and the server for a given
	 * Wellness Diary entry.
	 * 
	 * @param entry
	 *            the bean to be updated
	 * @return the number of rows updated (0 means nothing happened, -1 means
	 *         the logged in user cannot edit this entry, and anything else is
	 *         the number of rows updated which should never exceed 1)
	 * @throws ITrustException
	 */
	public int editEntry(EntryBean entry) throws ITrustException,
			FormValidationException;
}
