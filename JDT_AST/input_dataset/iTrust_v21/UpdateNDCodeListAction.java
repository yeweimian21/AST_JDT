package edu.ncsu.csc.itrust.action;

import edu.ncsu.csc.itrust.beans.MedicationBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.NDCodesDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.validate.MedicationBeanValidator;

/**
 * Handles updating the ND Code (Prescription) List Used by editNDCodes.jsp
 * 
 * The National Drug Code (NDC) is a universal product identifier used in the
 * United States for drugs intended for human use.
 * 
 * @see http://www.fda.gov/Drugs/InformationOnDrugs/ucm142438.htm
 */
public class UpdateNDCodeListAction {
	private NDCodesDAO ndDAO;
	private MedicationBeanValidator validator = new MedicationBeanValidator();

	/**
	 * Set up defaults.
	 * 
	 * @param factory The DAOFactory used to create the DAOs used in this action.
	 * @param performerID The MID of the user updating the ND lists.
	 */
	public UpdateNDCodeListAction(DAOFactory factory, long performerID) {
		ndDAO = factory.getNDCodesDAO();
	}

	/**
	 * Adds a new ND Code (prescription) to the list
	 * 
	 * @param med
	 *            The new ND Code to be added
	 * @return Status message
	 * @throws FormValidationException
	 */
	public String addNDCode(MedicationBean med) throws FormValidationException {
		validator.validate(med);
		try {
			if (ndDAO.addNDCode(med)) {
				return "Success: " + med.getNDCode() + " - " + med.getDescription() + " added";
			} else
				return "The database has become corrupt. Please contact the system administrator for assistance.";
		} catch (DBException e) {
			
			return e.getMessage();
		} catch (ITrustException e) {
			return e.getMessage();
		}
	}

	/**
	 * Updates the ND Code with new information from the MedicationBean
	 * 
	 * @param med
	 *            the MedicationBean that holds new information but the same code
	 * @return status message
	 * @throws FormValidationException
	 */
	public String updateInformation(MedicationBean med) throws FormValidationException {
		validator.validate(med);
		try {
			int rows = updateCode(med);
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
	 * Medication information should already be validated
	 * 
	 * @param med
	 * @return
	 * @throws DBException
	 */
	private int updateCode(MedicationBean med) throws DBException {
		return ndDAO.updateCode(med);
	}

	/**
	 * Removes a ND Code (prescription) from the list
	 * 
	 * @param med The ND Code to be removed
	 * 
	 * @return Status message
	 * @throws DBException
	 */
	public String removeNDCode(MedicationBean med) throws DBException {
		try {
			if (ndDAO.getNDCode(med.getNDCode()) == null) {
				return "Drug does not exist or already has been removed from the database.";
			}
		}
		 catch (DBException e) {
				
				return e.getMessage();
		 }
		try {
			if (ndDAO.removeNDCode(med)) {
				return "Success: " + med.getNDCode() + " - " + med.getDescription() + " removed";
			} else
				return "The database has become corrupt. Please contact the system administrator for assistance.";
		} catch (DBException e) {
			
			return e.getMessage();
		} catch (ITrustException e) {
			return e.getMessage();
		}
	}
}
