package edu.ncsu.csc.itrust.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import edu.ncsu.csc.itrust.beans.ProcedureBean;
import edu.ncsu.csc.itrust.beans.RequiredProceduresBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO;
import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.dao.mysql.RequiredProceduresDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;

public class ViewImmunizationReportAction {


	private RequiredProceduresDAO requiredDAO;
	private PatientDAO patDAO;
	private PersonnelDAO personnelDAO;

	
	public ViewImmunizationReportAction(DAOFactory factory, long pid, long loggedInMID) throws ITrustException {
		
		requiredDAO = factory.getRequiredProceduresDAO();
		patDAO = factory.getPatientDAO();
		personnelDAO = factory.getPersonnelDAO();
	
	}
	/*
	 * This method will return a list of all of the required procedures currently in the system.
	 */
	public List<ProcedureBean> getAllImmunizations(long pid) throws DBException {
		return requiredDAO.getAllImmunizations(pid);
	}
	
	/*
	 * This method will return a list of all of the required procedures for the ageGroup passed in.
	 */
	public List<RequiredProceduresBean> getRequiredImmunizations(long pid, int ageGroup) throws DBException {
		return requiredDAO.getRequiredImmunizations(pid, ageGroup);
	}
	
	/*
	 * This method will return a list of all of the procedures that the patient passed in will need in 
	 * order to be covered for their age group.
	 */
	public List<RequiredProceduresBean> getNeededImmunizations(long pid, int ageGroup) throws DBException {
		
		List<RequiredProceduresBean> needed = requiredDAO.getNeededImmunizations(pid, ageGroup);
		List<RequiredProceduresBean> required = new ArrayList<RequiredProceduresBean>();
		
		PatientBean patient = patDAO.getPatient(pid);
		int age = patient.getAge();
		Date dateofBirth = patient.getDateOfBirth();
		
		Calendar cal = Calendar.getInstance();
		cal.set(1994, 1, 1);
		
		boolean over5 = false;
		boolean over18= false;
		boolean mmrNeeded= true;
		boolean hepBneeded = true;
		
		//check to see if the patient is over the age of 5 and 18 respectively
		if(age >= 5 ){
			over5 = true;
			if(age>= 18){
				over18 = true;
			}
		}
		
		//check to see if the patient was born BEFORE 1994 and 1957 respectively.
		if(dateofBirth.before(cal.getTime())){
			hepBneeded = false;
			cal.set(1957, 1, 1);
			if(dateofBirth.before(cal.getTime())){
				mmrNeeded = false;
			}
		}
		
		
		//add the required procedures to the list, checking our controlling variables for extenuating vaccinations
		for(RequiredProceduresBean p: needed){
			boolean needs = true;
			if(p.getCptCode().equals("90645") && over5) {
				needs = false;
			}
			if(p.getCptCode().equals("90712") && over18) {
				needs = false;
			}
			if(p.getCptCode().equals("90707") && !mmrNeeded) {
				needs = false;
			}
			if(p.getCptCode().equals("90371") && !hepBneeded) {
				needs = false;
			}
			if(needs) {
				required.add(p);
			}
		}
		
		//RETURN THAT SHIT
		return required;
	}
	
	/**
	 * Get an HCP's name from their MID.
	 * @param hcpId String MID of the HCP to get
	 * @return the name of the specified HCP
	 * @throws ITrustException if the HCP ID does not exist
	 */
	public String getHcpNameFromID(String hcpId) throws ITrustException {
		return personnelDAO.getName(Long.parseLong(hcpId));
	}
}
