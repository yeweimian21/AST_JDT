package edu.ncsu.csc.itrust.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.ncsu.csc.itrust.beans.OfficeVisitBean;
import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.beans.PatientVisitBean;
import edu.ncsu.csc.itrust.beans.PersonnelBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.OfficeVisitDAO;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;

/**
 * 
 * Action class for ViewPatientOfficeVisitHistory.jsp
 *
 */
public class ViewPatientOfficeVisitHistoryAction {
	private long loggedInMID;
	private PatientDAO patientDAO;
	private PersonnelDAO personnelDAO;
	private OfficeVisitDAO officevisitDAO;
	private ArrayList<PatientVisitBean> visits;

	/**
	 * Set up defaults
	 * @param factory The DAOFactory used to create the DAOs used in this action.
	 * @param loggedInMID The MID of the person viewing the office visits.
	 */
	public ViewPatientOfficeVisitHistoryAction(DAOFactory factory, long loggedInMID) {
		this.loggedInMID = loggedInMID;
		this.personnelDAO = factory.getPersonnelDAO();
		officevisitDAO = factory.getOfficeVisitDAO();
		this.patientDAO = factory.getPatientDAO();
		
		visits = new ArrayList<PatientVisitBean>();
		
	}
	
	/**
	 * Adds all the office visits for the logged in HCP to a list.
	 * 
	 * @throws ITrustException
	 */
	private void processOfficeVisits() throws ITrustException {
		try {
			List<PatientBean> plist = patientDAO.getAllPatients();
			
			for(PatientBean pb : plist) {
				// Create a new visit bean
				PatientVisitBean visitBean = new PatientVisitBean();
				
				// Add patient's information to the visit
				visitBean.setPatient(pb);
				visitBean.setPatientName(pb.getFullName());
				visitBean.setAddress1(pb.getStreetAddress1() +" " + pb.getStreetAddress2());
				visitBean.setAddress2(pb.getCity() + " " +pb.getState() +" " +pb.getZip());
				
				// Get this patients office visit history
				List<OfficeVisitBean> ovlist = officevisitDAO.getAllOfficeVisits(pb.getMID());
				// If they've had an office visit previously, get the date of the latest visit
				if(!ovlist.isEmpty()) {
					// The list is ordered DESC, so the first entry will be the latest
					OfficeVisitBean ov = ovlist.get(0);
					String date = ov.getVisitDateStr();
					Scanner sc = new Scanner(date);
					sc.useDelimiter("/");
					String month = sc.next();
					String day = sc.next();
					String year = sc.next();
					sc.close();
					visitBean.setLastOVDateM(month);
					visitBean.setLastOVDateD(day);
					visitBean.setLastOVDateY(year);
					visitBean.setLastOVDate(year +"-" + month +"-" + date);
				} else {
					// The patient hasn't had an office visit, so set the date to null
					visitBean.setLastOVDateM(null);
					visitBean.setLastOVDateD(null);
					visitBean.setLastOVDateY(null);
					visitBean.setLastOVDate(null);
				}
				
				visits.add(visitBean);
			}
		}
		catch (DBException dbe) {
			throw new ITrustException(dbe.getMessage());
		}
	}
	
/**
 * Get the list of patients an HCP has had office visits with
 * 
 * @return the list of patients an HCP has had office visits with
 * @throws DBException
 */
	public List<PatientVisitBean> getPatients() throws DBException {
		
		try {
			processOfficeVisits();
		}
		catch (ITrustException ie) {
			//TODO
		}

		return visits;
	}
	/**
	 * Returns a PersonnelBean for the logged in HCP
	 * @return PersonnelBean for the logged in HCP
	 * @throws ITrustException
	 */
	public PersonnelBean getPersonnel() throws ITrustException {
		return personnelDAO.getPersonnel(loggedInMID);
	}
}