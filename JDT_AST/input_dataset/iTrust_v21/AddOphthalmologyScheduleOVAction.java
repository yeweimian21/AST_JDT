package edu.ncsu.csc.itrust.action;

import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc.itrust.beans.OphthalmologyScheduleOVRecordBean;
import edu.ncsu.csc.itrust.beans.PersonnelBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.OphthalmologyScheduleOVDAO;
import edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.validate.OphthalmologyScheduleOVValidator;

/**
 * Used for add ophthalmology office visit requests. 
 */
public class AddOphthalmologyScheduleOVAction {
	/** ophthalmologyOVDAO is the DAO that adds the ophthalmology scheduled office
	 *  visit records to the database*/
	private OphthalmologyScheduleOVDAO ophthalmologyOVDAO;
    /** PatientDAO for working with patient objects in the database*/
	private PersonnelDAO perDAO;
    
    /**
     * AddOphthalmologyScheduleOVAction is the constructor for this action class. It simply initializes the
     * instance variables.
     * @param factory The factory used to get the obstetricsDAO.
     * @param loggedInMID The MID of the logged in user.
     */
	public AddOphthalmologyScheduleOVAction(DAOFactory factory, long loggedInMID) {
		this.ophthalmologyOVDAO = factory.getOphthalmologyScheduleOVDAO();
		this.perDAO = factory.getPersonnelDAO();
	}
	
	/**
	 * Adds a new ophthalmology scheduled office visit record.
	 * @param p OphthalmologyScheduleOVRecordBean containing the info for the record to be created.
	 * @throws FormValidationException if the patient is not successfully validated.
	 * @throws ITrustException thrown if the database encounters an issue.
	 */
	public void addOphthalmologyOV(OphthalmologyScheduleOVRecordBean p) 
			throws FormValidationException, ITrustException {
		if(p != null){
			new OphthalmologyScheduleOVValidator().validate(p);
			
			//Add the ophthalmology office visit record to the database
			ophthalmologyOVDAO.addOphthalmologyScheduleOVRecord(p);
		} else {
			throw new ITrustException("Cannot add a null Ophthalmology Scheduled Office Visit.");
		}
	}
	
	/**
	 * Returns a list of PersonnelBeans of all ophthalmology personnel, ie doctors with the specialty of ophthalmologist or optometrist
	 * 
	 * @return a list of PersonnelBeans of all ophthalmology personnel
	 */
	public List<PersonnelBean> getAllOphthalmologyPersonnel(){
		List<PersonnelBean> personnel = new ArrayList<PersonnelBean>();
		try {
			personnel = perDAO.getAllOphthalmologyPersonnel();
		} catch (DBException e) {
			//If a DBException occurs print a stack trace and return an empty list
			e.printStackTrace();
		}
		
		return personnel;
	}
}
