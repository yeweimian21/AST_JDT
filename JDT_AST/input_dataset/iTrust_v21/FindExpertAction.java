package edu.ncsu.csc.itrust.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import edu.ncsu.csc.itrust.beans.CustomComparator;
import edu.ncsu.csc.itrust.beans.HospitalBean;
import edu.ncsu.csc.itrust.beans.PersonnelBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.HospitalsDAO;
import edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO;
import edu.ncsu.csc.itrust.exception.DBException;

/**
 * Action class to find experts based on the distance from the user and type of expert.
 *
 */
public class FindExpertAction {

	/**
	 * HospitalDAO to grab hospitals from
	 */
	HospitalsDAO hospitalsDAO;

	/**
	 * PersonnelDAO to find experts from
	 */
	PersonnelDAO personnelDAO;

	/**
	 * Constructor simply is used to initialize the DAOs
	 * @param factory used to initialize DAOs
	 */
	public FindExpertAction(DAOFactory factory){
		hospitalsDAO = new HospitalsDAO(factory);
		personnelDAO = new PersonnelDAO(factory);
	}

	/**
	 * 
	 * Main method used to find the hospitals and all specified personnel within range
	 * 
	 * @param distance The maximum distance that a hospital must be within range in order for it to return
	 * @param specialty The specialty that the user is interested in
	 * @param patientZip Zipcode entered by the patient
	 * @param zipRange The range to search hospitals for. The amount of zipcode digits to match, starting with the first digit.
	 * @return A relationship between hospitals within the defined proximity and the specified experts at the hospital.
	 */
	public HashMap<HospitalBean, List<PersonnelBean>> findHospitalsBySpecialty(String specialty, String patientZip, int zipRange){
		HashMap<HospitalBean, List<PersonnelBean>> experts = null;
		try {
			//Grab all hospitals and filter them based on distance
			List<HospitalBean> hospitals = filterHospitals(hospitalsDAO.getAllHospitals(), patientZip, zipRange);
			//Find experts in hospitals
			experts = findExperts(hospitals, specialty);
		} catch (DBException e) {
			//
		}
		return experts;
	}

	/**
	 * Method used to find experts of specified specialty from hospitals that are in range
	 * @param hospitals The hospitals within the proximity of the user
	 * @param specialty The expertise specified
	 * @return A relationship between the hospitals within proximity and the personnel with the specified expertise within them.
	 */
	public HashMap<HospitalBean, List<PersonnelBean>> findExperts(List<HospitalBean> hospitals, String specialty){
		HashMap<HospitalBean, List<PersonnelBean>> experts = new HashMap<HospitalBean, List<PersonnelBean>>();
		try{
			//Go through all nearby hospitals
			for(HospitalBean hospital : hospitals){
				//Put the specified experts into a hashmap with the hospital
				experts.put(hospital, personnelDAO.getPersonnelFromHospital(hospital.getHospitalID(), specialty));
			}
		} catch (DBException e){
			//
		}
		return experts;
	}
	
	/**
	 * Method used to find experts of specified specialty from hospitals that are in range
	 * @param hospitals The hospitals within the proximity of the user
	 * @param specialty The expertise specified
	 * @return A relationship between the hospitals within proximity and the personnel with the specified expertise within them.
	 */
	public List<PersonnelBean> findExpertsForLocalHospitals(List<HospitalBean> hospitals, String specialty){
		List<PersonnelBean> beans = new ArrayList<PersonnelBean>();
		boolean searchForAll;
		searchForAll = specialty.equalsIgnoreCase("all");
		if (searchForAll) {
			
		}
		try{
			//Go through all nearby hospitals
			for(HospitalBean hospital : hospitals){
				//Put the specified experts into a hashmap with the hospital
				//experts.put(hospital, personnelDAO.getPersonnelFromHospital(hospital.getHospitalID(), specialty));
				List<PersonnelBean> personnelBeans = personnelDAO.getPersonnelFromHospital(hospital.getHospitalID(), specialty);
				for (PersonnelBean personnelBean : personnelBeans) {
					beans.add(personnelBean);
				}
			}
		} catch (DBException e){
			e.printStackTrace();
		}
		Collections.sort(beans, new CustomComparator());
		return beans;
	}
	
	public List<HospitalBean> findHospitalsAssignedToHCP(long pid) throws SQLException
	{
		return hospitalsDAO.getHospitalsAssignedToPhysician(pid);
	}

	/**
	 * Filters hospitals down to just the hospitals in the specified range of the user
	 * @param hospitals Hospitals to filter
	 * @param patientZip Zipcode entered by the patient
	 * @param zipRange The range to search hospitals for. The amount of zipcode digits to match, starting with the first digit.
	 * @return All hospitals within the specified range of the user
	 */
	public List<HospitalBean> filterHospitals(List<HospitalBean> hospitals, String patientZip, int zipRange){
		List<HospitalBean> inRange = new ArrayList<HospitalBean>();
		for (int i = 0; i < hospitals.size(); i++) {
			try {
				//A hospital is in range if its zipcode matches the user entered one from the first number to the zipRangeth number
				if (hospitals.get(i).getHospitalZip().substring(0, zipRange).equals(patientZip.substring(0, zipRange)))
					inRange.add(hospitals.get(i));
			} catch (NullPointerException e1) {
				//Examine next hospital if current has null zipcode entry
			} catch (IndexOutOfBoundsException e2) {
				//If the hospital's zip code is not at least 5 digits long, examine next hospital
			}
		}
		return inRange;
	}
	
}
