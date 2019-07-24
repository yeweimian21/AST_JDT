package edu.ncsu.csc.itrust.action;

import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.ITrustException;

/**
 * Handles Getting the person's name associated with a certain mid Used by getUser.jsp
 * 
 * 
 */
public class GetUserNameAction {
	private DAOFactory factory;

	/**
	 * Set up defaults
	 * 
	 * @param factory The DAOFactory used for creating the DAOs for this action.
	 */
	public GetUserNameAction(DAOFactory factory) {
		this.factory = factory;
	}

	/**
	 * Returns the person's name that matches the inputMID param
	 * 
	 * @param inputMID The MID to look up.
	 * @return the person's name
	 * @throws DBException
	 * @throws ITrustException
	 */
	public String getUserName(String inputMID) throws ITrustException {
		try {
			long mid = Long.valueOf(inputMID);
			return factory.getAuthDAO().getUserName(mid);
		} catch (NumberFormatException e) {
			throw new ITrustException("MID not in correct form");
		}
	}
}
