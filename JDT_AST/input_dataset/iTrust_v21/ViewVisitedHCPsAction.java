package edu.ncsu.csc.itrust.action;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;
import edu.ncsu.csc.itrust.beans.PersonnelBean;
import edu.ncsu.csc.itrust.beans.OfficeVisitBean;
import edu.ncsu.csc.itrust.beans.HCPVisitBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO;
import edu.ncsu.csc.itrust.dao.mysql.OfficeVisitDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;

/**
 * Action class for ViewVisitedHCPs.jsp
 */
public class ViewVisitedHCPsAction {

	private long patientMID;
	private PersonnelDAO docDAO;
	private OfficeVisitDAO visitDAO;
	private PatientDAO patientDAO;
	//private ArrayList<HCPVisitBean> visits;
	private DeclareHCPAction declareAction; 
	/**
	 * Set up defaults 
	 * @param factory The DAOFactory used to create the DAOs used in this action.
	 * @param loggedInMID The MID of the person viewing the visited HCPs.
	 */
	public ViewVisitedHCPsAction(DAOFactory factory, long loggedInMID) {
		patientMID = loggedInMID;
		docDAO = factory.getPersonnelDAO();
		visitDAO = factory.getOfficeVisitDAO();
		patientDAO = factory.getPatientDAO();
		
		declareAction = new DeclareHCPAction(factory, loggedInMID);
	}
	
	/**
	 * Create an HCPVisitBean from a given PersonnelBean and office visit date.
	 * 
	 * @param pb The PersonnelBean that will be visited.
	 * @param visitDate The date of the visit.  This may be the empty string.
	 * @return The new HCPVisitBean. 
	 * @throws DBException
	 */
	private HCPVisitBean makeHCPVisitBean(PersonnelBean pb, String visitDate) throws DBException {
		long hcpid = pb.getMID();
		HCPVisitBean visitBean = new HCPVisitBean();
		visitBean.setHCPMID(hcpid);
		visitBean.setHCPName(pb.getFullName());
		visitBean.setOVDate(visitDate);
		visitBean.setHCPSpecialty(pb.getSpecialty());
		visitBean.setHCPAddr(pb.getStreetAddress1() +" "+ pb.getStreetAddress2() +" "
						   + pb.getCity() +", "+ pb.getState() +" "+ pb.getZip());
		visitBean.setDesignated(patientDAO.checkDeclaredHCP(patientMID, hcpid));
		return visitBean;
	}
	
	/**
	 * Checks to see if a PersonnelBean matches against a given set of 
	 * criteria.
	 * 
	 * @param pb The PersonnelBean to check.
	 * @param lastName The last name to check against.  May be null or the empty string to ignore.
	 * @param specialty The specialty to check against.  May be null or the empty string to ignore.
	 * @param zip The zip code to check against.  May be null or the empty string to ignore.
	 * @return true if the PersonnelBean matches all the given parameters, or false otherwise.
	 */
	private boolean matchPersonnel(PersonnelBean pb, String lastName, String specialty, String zip) {
		if (lastName != null && !lastName.equals("") && !pb.getLastName().startsWith(lastName)){
			return false;
		}
		if (specialty != null && !specialty.equals("") && !specialty.equals(pb.getSpecialty())){
			return false;
		}
		if (zip != null && !zip.equals("") && !zip.equals(pb.getZip())){
			return false;
		}
		return true;
	}
	
	/**
	 * Get a list of all HCPs visited and/or designated by by the current 
	 * user.  The list can optionally be filtered by the doctor's last name, 
	 * specialty, or zip code.
	 * 
	 * @param lastName The last name (or a part of it) of the doctor to search 
	 * 				   for, or null or an empty string to accept all doctors.
	 * @param specialty The specialty of the doctor to search for, or null or 
	 * 					an empty string to accept all doctors.
	 * @param zip The zip code of the doctor to search for, or null or an empty 
	 * 			  string to accept all doctors.
	 * @return A list of HCPVisitBeans where each represents one HCP that has 
	 * 	       been visited or has been designated.
	 * @throws ITrustException
	 */
	private List<HCPVisitBean> getAllVisitedHCPs(String lastName, String specialty, String zip) 
										     throws ITrustException 
	{
		// Visited HCPs in this case includes both HCPs visited *and* HCPs 
		// designated by the patient.  These two groups are retrieved in 
		// different ways, then combined.
		List<HCPVisitBean> visits = new ArrayList<HCPVisitBean>();
		try {
			List<OfficeVisitBean> ovlist = visitDAO.getAllOfficeVisits(patientMID);
			// get most recent office visit for each provider
			LinkedHashMap<Long, OfficeVisitBean> mostRecentVisits = new LinkedHashMap<Long, OfficeVisitBean>();
			for (OfficeVisitBean ov: ovlist) {
				long id = ov.getHcpID();
				if (!mostRecentVisits.containsKey(id)) {
					mostRecentVisits.put(id, ov);
				} else {
					OfficeVisitBean old = mostRecentVisits.get(id);
					Date ovDate = ov.getVisitDate();
					Date oldDate = old.getVisitDate();
					if (oldDate.before(ovDate)) {
						mostRecentVisits.put(id, ov);
					}
				}
			}
			
			// Get visited HCPs.
			for (OfficeVisitBean ov: mostRecentVisits.values()) {
				long hcpid = ov.getHcpID();
				PersonnelBean pb = docDAO.getPersonnel(hcpid);
				if (matchPersonnel(pb, lastName, specialty, zip)) {
					HCPVisitBean visitBean = makeHCPVisitBean(pb, mostRecentVisits.get(hcpid).getVisitDateStr());
					visits.add(visitBean);
				}
			}
			
			// Get all designated HCPs.  Because a designated HCP may have been 
			// visited, we will ensure the HCP is not already in the list.
			List<PersonnelBean> dhcps = patientDAO.getDeclaredHCPs(patientMID);
		next:
			for (PersonnelBean pb: dhcps) {
				if (matchPersonnel(pb, lastName, specialty, zip)) {
					long hcpid = pb.getMID();
					// if HCP is already in visits list, skip here
					for (HCPVisitBean hv: visits) {
						if (hv.getHCPMID() == hcpid) {
							continue next;
						}
					}
					String date = "";
					HCPVisitBean visitBean = makeHCPVisitBean(pb, date);
					visits.add(visitBean);
				}
			}
		} catch (DBException dbe) {
			throw new ITrustException(dbe.getMessage());
		}
		
		return visits;
	}
	
	/**
	 * Returns a list of all the visited and/or designated HCPs.
	 * @return list of all the visited HCPs
	 */
	public List<HCPVisitBean> getVisitedHCPs() {
		List<HCPVisitBean> visits;
		try {
			visits = getAllVisitedHCPs(null, null, null);
		}
		catch (ITrustException ie) {
			visits = new ArrayList<HCPVisitBean>();
		}
			
		return visits;
	}
	
	
	/**
	 * Given an HCP's name, return the corresponding HCPVisitBean.
	 * @param name name
	 * @return r
	 */
	public HCPVisitBean getNamedHCP(String name) {
		HCPVisitBean r = new HCPVisitBean();
		for (HCPVisitBean bean : getVisitedHCPs()) {
			if (name.equals(bean.getHCPName())) {
				r = bean;
				break;
			}
		}
		return r;
	}
	
	/**
	 * Set a given HCP as undeclared.
	 * 
	 * @param name HCP to undeclare.
	 * @return An empty string.
	 * @throws ITrustException
	 */
	public String undeclareHCP(String name) throws ITrustException {
		
		for (HCPVisitBean visit: getVisitedHCPs()) {
			if (0 == visit.getHCPName().toLowerCase().compareTo(name.toLowerCase())) {
				Long mid = Long.valueOf(visit.getHCPMID());

				declareAction.undeclareHCP(mid.toString());
				visit.setDesignated(false);
			}
		}
		
		return "";
	}
	
	/**
	 * Set a given HCP as declared
	 * 
	 * @param name HCP to declare
	 * @return An empty string.
	 * @throws ITrustException
	 */
	public String declareHCP(String name) throws ITrustException {
		boolean match = false;
		for (HCPVisitBean visit: getVisitedHCPs()) {
			if (0 == visit.getHCPName().toLowerCase().compareTo(name.toLowerCase())) {
				match = true;
				Long mid = Long.valueOf(visit.getHCPMID());
				if (!patientDAO.checkDeclaredHCP(patientMID, visit.getHCPMID())) {
					declareAction.declareHCP(mid.toString());
				}
				visit.setDesignated(true);
			}
		}
		
		if (!match) {
				List<PersonnelBean> doclist = docDAO.getAllPersonnel();
				for (PersonnelBean ele: doclist) {
					if (0 == name.compareTo(ele.getFullName())) {
						HCPVisitBean visitBean;
						visitBean = new HCPVisitBean();
						visitBean.setHCPMID(ele.getMID());
						visitBean.setHCPName(ele.getFullName());
						visitBean.setOVDate("");
						visitBean.setHCPSpecialty(ele.getSpecialty());
						visitBean.setHCPAddr(ele.getStreetAddress1() +" "+ ele.getStreetAddress2() +" "+ ele.getCity() +", "+ ele.getState() +" "+ ele.getZip());
						
						visitBean.setDesignated(true);
				
						Long mid = Long.valueOf(ele.getMID());
						if (!patientDAO.checkDeclaredHCP(patientMID, mid)) {
							declareAction.declareHCP(mid.toString());
						}
					}
				}
		}
		return "";
	}
	
	/**
	 * Check to see if a given HCP is declared
	 * @param mid HCP to check
	 * @return true if the HCP is declared, otherwise false
	 */
	public boolean checkDeclared(long mid) throws DBException {
		return patientDAO.checkDeclaredHCP(patientMID, mid);
	}
	
	/**
	 * Filter the list of HCPs by last name, specialty, or zip code.
	 * @param specialty Filter by specialty.  May be null or the empty string 
	 *   			    to ignore.
	 * @param zip Filter by zip.  May be null or the empty string to ignore.
	 * @param lastName lastName
	 * @return Filtered list of HCPs.
	 */
	public List<HCPVisitBean> filterHCPList(String lastName, String specialty, String zip) {
		List<HCPVisitBean> visits;
		try {
			visits = getAllVisitedHCPs(lastName, specialty, zip);
		}
		catch (ITrustException ie) {
			visits = new ArrayList<HCPVisitBean>();
		}
			
		return visits;
	}

}
