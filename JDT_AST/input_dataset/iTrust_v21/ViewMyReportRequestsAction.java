package edu.ncsu.csc.itrust.action;

import java.util.Calendar;
import java.util.List;
import edu.ncsu.csc.itrust.beans.PersonnelBean;
import edu.ncsu.csc.itrust.beans.ReportRequestBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO;
import edu.ncsu.csc.itrust.dao.mysql.ReportRequestDAO;
import edu.ncsu.csc.itrust.exception.ITrustException;

/**
 * Action class for ViewMyReports.jsp.  Allows the user to see all their reports
 */
public class ViewMyReportRequestsAction {
	private long loggedInMID;
	private ReportRequestDAO reportRequestDAO;
	private PersonnelDAO personnelDAO;

	/**
	 * Set up
	 * 
	 * @param factory The DAOFactory used to create the DAOs used in this action.
	 * @param loggedInMID The MID of the person viewing their report requests.
	 */
	public ViewMyReportRequestsAction(DAOFactory factory, long loggedInMID) {
		this.loggedInMID = loggedInMID;
		this.reportRequestDAO = factory.getReportRequestDAO();
		this.personnelDAO = factory.getPersonnelDAO();
	}

	/**
	 * Returns all the reports for the currently logged in HCP
	 * 
	 * @return list of all reports for the logged in HCP
	 * @throws ITrustException
	 */
	public List<ReportRequestBean> getAllReportRequestsForRequester() throws ITrustException {
		return reportRequestDAO.getAllReportRequestsForRequester(loggedInMID);
	}


	/**
	 * Adds a report request to the list
	 * 
	 * @param patientMID ID of the patient that the report request is for
	 * @return id
	 * @throws ITrustException
	 */
	public long addReportRequest(long patientMID) throws ITrustException {
		long id = reportRequestDAO
				.addReportRequest(loggedInMID, patientMID, Calendar.getInstance().getTime());
		return id;

	}


	/**
	 * Returns the requested report
	 * 
	 * @param ID id of the requested report
	 * @return the requested report
	 * @throws ITrustException
	 */
	public ReportRequestBean getReportRequest(int ID) throws ITrustException {
		return reportRequestDAO.getReportRequest(ID);
	}
	
/**
 * Sets the viewed status of the report.  If the report is "viewed" the HCP must request a new one to see it again.
 * 
 * @param ID id of the report
 * @throws ITrustException
 */
	public void setViewed(int ID) throws ITrustException {
		reportRequestDAO.setViewed(ID, Calendar.getInstance().getTime());
	}


	/**
	 * Gets the status of the request
	 * 
	 * @param id id of the request
	 * @return the request's status
	 * @throws ITrustException
	 */
	public String getLongStatus(long id) throws ITrustException {
		StringBuilder s = new StringBuilder();
		ReportRequestBean r = reportRequestDAO.getReportRequest(id);
		if (r.getStatus().equals(ReportRequestBean.Requested)) {
			PersonnelBean p = personnelDAO.getPersonnel(r.getRequesterMID());
			s.append(String.format("Request was requested on %s by %s", r.getRequestedDateString(), p
					.getFullName()));
		}
		
		if (r.getStatus().equals(ReportRequestBean.Viewed)) {
			PersonnelBean p = personnelDAO.getPersonnel(r.getRequesterMID());
			String fullName = "Unknown";
			if(p != null){
				fullName = p.getFullName();
				s.append(String.format("Request was requested on %s by %s, ", r.getRequestedDateString(), p
					.getFullName()));
			}
			s.append(""); // removed "<br />" because it caused unit test to fail and seems to have no
			// purpose
			s.append(String.format("and viewed on %s by %s", r.getViewedDateString(), fullName));
		}

		return s.toString();
	}

}
