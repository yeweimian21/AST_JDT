package edu.ncsu.csc.itrust.action;

import java.util.List;

import edu.ncsu.csc.itrust.beans.PersonnelBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;

/**
 * Responsible for allowing only designated nutritionists
 * to view the Nutritional information of patients. Patients
 * can declare their designated nutritionist
 */
public class DesignateNutritionistAction {
	
	private PersonnelDAO personnelDAO;
	private PatientDAO patientDAO;
	private long loggedInMID;
	
	public DesignateNutritionistAction(DAOFactory factory, long loggedInMID) {
		personnelDAO = factory.getPersonnelDAO();
		patientDAO = factory.getPatientDAO();
		this.loggedInMID = loggedInMID;
	}
	
	/**
	 * Returns a list of all of the HCPs who have a specialty
	 * of Nutritionist
	 */
	public List<PersonnelBean> getAllNutritionists() throws ITrustException {
		try {
			return personnelDAO.getAllNutritionists();
		} catch (DBException d) {
			throw new ITrustException("Error retrieving HCPs");
		}
	}

	/**
	 * Allows the patient to update who his designated nutritionist is.
	 * A patient may choose to have no designated nutritionist in which
	 * case no hcp may view his nutritional information.
	 * return 0 means nothing happened
	 * return -1 means the nutritionist is already his designated nutritionist
	 * return -2 means hcp is not a nutritionist
	 * return positive number means successful update
	 */
	public int updateNutritionist(long HCPID) throws ITrustException {
		/* If the HCPID = -1 (HCPID can never be -1), then the
		 * patient wishes to have 'None' as his selected hcp and 
		 * we will delete it.
		 * if not -1, we will try first to just update it, and assume that 
		 * the patient already has a designated nutritionist, if not
		 * we will add 1 for him
		 */
		try {
			int updated = 0;
			PersonnelBean personnel = personnelDAO.getPersonnel(HCPID);
			PersonnelBean currentHCP = getDesignatedNutritionist();
			if (personnel != null && currentHCP != null && 
					(personnel.getMID() == currentHCP.getMID())) {
				//the patient wants to update it to his current nutritionist
				updated = -1;
			} else if (HCPID == -1) {
				updated = patientDAO.deleteDesignatedNutritionist(loggedInMID);
				
			} else { //must want to change/add his designated nutritionist
				//must first make sure the hcp in question has 
				//specialty of nutritionist
				boolean isNutritionist = (personnel != null &&
						personnel.getSpecialty().equalsIgnoreCase("Nutritionist"));
				if (isNutritionist) {
					updated = patientDAO.
							updateDesignatedNutritionist(loggedInMID, HCPID);
					if (updated == 0) { //didn't update anything, so have to add
						updated = patientDAO.addDesignatedNutritionist(loggedInMID, HCPID);
					}
				} else {
					updated = -2;
				}
			}
			return updated;
		} catch (DBException d) {
			throw new ITrustException("Error retrieving information");
		}
	}
	
	public PersonnelBean getDesignatedNutritionist() throws ITrustException {
		try {
			long HCPID = patientDAO.getDesignatedNutritionist(loggedInMID);
			PersonnelBean HCP = personnelDAO.getPersonnel(HCPID);
			return HCP;
		} catch (DBException d) {
			throw new ITrustException("Error retrieving information");
		}
	}
	
}
