package edu.ncsu.csc.itrust.action;

import java.util.Collections;
import java.util.List;

import edu.ncsu.csc.itrust.beans.FoodEntryBean;
import edu.ncsu.csc.itrust.beans.MacronutrientsBean;
import edu.ncsu.csc.itrust.beans.PersonnelBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.MacronutrientsDAO;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO;
import edu.ncsu.csc.itrust.exception.ITrustException;


/**
 * ViewMacronutrientsAction.java
 * Version 1
 * 4/1/2015
 * Copyright notice: none
 * 
 * Responsible for loading the entries in a patient's food diary,
 * the totals a patient has eaten, and adding a new entry to a patient's 
 * food diary
 */
public class ViewMacronutrientsAction {
	// MacronutrientsBean FoodEntryBean
	
	private MacronutrientsDAO macronutrientsDAO;
	private PatientDAO patientDAO;
	private long loggedInMID;
	private PersonnelDAO personnelDAO;
	
	public ViewMacronutrientsAction(DAOFactory factory, long loggedInMID) {
		this.patientDAO = factory.getPatientDAO();
		this.personnelDAO = factory.getPersonnelDAO();
		this.macronutrientsDAO = factory.getMacronutrientsDAO();
		this.loggedInMID = loggedInMID;
	}
	
	public MacronutrientsBean getMsjData(long patientMID) throws ITrustException {
		PersonnelBean personnel;
		try {
			if ((patientDAO.checkPatientExists(loggedInMID) && 
				loggedInMID == patientMID) ||
			(((personnel = personnelDAO.getPersonnel(loggedInMID)) != null) && 
			personnel.getSpecialty().equalsIgnoreCase("Nutritionist"))) {
			
				
				return macronutrientsDAO.getMsj(patientMID);
			} else {
				throw new ITrustException("You do not have permission to "
						+ "view Macronutrients data!");
			}
		} catch (IndexOutOfBoundsException e) {
			throw new ITrustException("Patient does not have complete information to calculate Macronutrients.");
		}
	}
	
	public List<FoodEntryBean> reverse(List<FoodEntryBean> list) {
		Collections.reverse(list);
		
		return list;
	}
}
