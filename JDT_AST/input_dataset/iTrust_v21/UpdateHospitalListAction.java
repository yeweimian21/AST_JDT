package edu.ncsu.csc.itrust.action;

import edu.ncsu.csc.itrust.beans.HospitalBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.HospitalsDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.validate.HospitalBeanValidator;

/**
 * Handles updating the list of hospitals Used by hospitalListing.jsp
 * 
 * 
 */
public class UpdateHospitalListAction {
	private HospitalsDAO hospDAO;

	/**
	 * Set up
	 * 
	 * @param factory The DAOFactory used to create the DAOs used in this action.
	 * @param performerID The MID of the person updating the hospitals.
	 */
	public UpdateHospitalListAction(DAOFactory factory, long performerID) {
		this.hospDAO = factory.getHospitalsDAO();
	}

	/**
	 * Adds a hospital using the HospitalBean passed as a parameter
	 * 
	 * @param hosp the new hospital listing
	 * @return Status message
	 * @throws FormValidationException
	 */
	public String addHospital(HospitalBean hosp) throws FormValidationException {
		new HospitalBeanValidator().validate(hosp);
		
		try {
			if (hospDAO.addHospital(hosp)) {
				return "Success: " + hosp.getHospitalID() + " - " + hosp.getHospitalName() + " added";
			} else
				return "The database has become corrupt. Please contact the system administrator for assistance.";
		} catch (DBException e) {
			
			return e.getMessage();
		} catch (ITrustException e) {
			return e.getMessage();
		}
	}

	/**
	 * Updates a hospital (based on the hospital id) using new information from the HospitalBean passed as a
	 * parameter
	 * 
	 * @param hosp the new hospital information with the same hospital id
	 * @return Status message
	 * @throws FormValidationException
	 */
	public String updateInformation(HospitalBean hosp) throws FormValidationException {
		new HospitalBeanValidator().validate(hosp);
		
		try {
			int rows = 0;
			return ((0 == (rows = updateHospital(hosp))) ? "Error: Hospital not found." : "Success: " + rows
					+ " row(s) updated");
		} catch (DBException e) {
			
			return e.getMessage();
		}
	}

	/**
	 * Updates hospital
	 * 
	 * @param hosp new information
	 * @return id for the updated hospital
	 * @throws DBException
	 */
	private int updateHospital(HospitalBean hosp) throws DBException {
		return hospDAO.updateHospital(hosp);
	}
}
