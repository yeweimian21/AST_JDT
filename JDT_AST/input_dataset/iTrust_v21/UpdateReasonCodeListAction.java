package edu.ncsu.csc.itrust.action;

import edu.ncsu.csc.itrust.beans.OverrideReasonBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.DrugReactionOverrideCodesDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.validate.OverrideReasonBeanValidator;

/**
 * Handles updating the Reason Codes List Used by editReasonCodes.jsp
 * 
 * The National Drug Code (NDC) is a universal product identifier used in the
 * United States for drugs intended for human use.
 * 
 * @see http://archinte.ama-assn.org/cgi/content/full/163/21/2625/TABLEIOI20692T4
 */
public class UpdateReasonCodeListAction {
	private DrugReactionOverrideCodesDAO orcDAO;
	private OverrideReasonBeanValidator validator = new OverrideReasonBeanValidator();

	/**
	 * Set up defaults.
	 * 
	 * @param factory The DAOFactory used to create the DAOs used in this action.
	 * @param performerID The MID of the user updating the ND lists.
	 */
	public UpdateReasonCodeListAction(DAOFactory factory, long performerID) {
		orcDAO = factory.getORCodesDAO();
	}

	/**
	 * Adds a new ND Code (prescription) to the list
	 * 
	 * @param orc
	 *            The new ND Code to be added
	 * @return Status message
	 * @throws FormValidationException
	 */
	public String addORCode(OverrideReasonBean orc) throws FormValidationException {
		validator.validate(orc);
		try {
			if (orcDAO.addORCode(orc)) {
				return "Success: " + orc.getORCode() + " - " + orc.getDescription() + " added";
			} else
				return "The database has become corrupt. Please contact the system administrator for assistance.";
		} catch (DBException e) {
			
			return e.getMessage();
		} catch (ITrustException e) {
			return e.getMessage();
		}
	}

	/**
	 * Updates the ND Code with new information from the OverrideReasonBean
	 * 
	 * @param orc
	 *            the OverrideReasonBean that holds new information but the same code
	 * @return status message
	 * @throws FormValidationException
	 */
	public String updateInformation(OverrideReasonBean orc) throws FormValidationException {
		validator.validate(orc);
		try {
			int rows = updateCode(orc);
			if (0 == rows) {
				return "Error: Code not found.";
			} else {
				return "Success: " + rows + " row(s) updated";
			}
		} catch (DBException e) {
			
			return e.getMessage();
		}
	}

	/**
	 * Override Reason information should already be validated
	 * 
	 * @param orc
	 * @return
	 * @throws DBException
	 */
	private int updateCode(OverrideReasonBean orc) throws DBException {
		return orcDAO.updateCode(orc);
	}

}
