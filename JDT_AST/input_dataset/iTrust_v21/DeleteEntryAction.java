package edu.ncsu.csc.itrust.action.base;

import edu.ncsu.csc.itrust.exception.ITrustException;

public interface DeleteEntryAction {
	/**
	 * Deletes a diary entry from the db
	 * 
	 * @param entryID
	 *            the entry to delete
	 * @return the number of rows deleted (should never exceed 1)
	 * @throws ITrustException
	 */
	public int deleteEntry(long entryID) throws ITrustException;
}
