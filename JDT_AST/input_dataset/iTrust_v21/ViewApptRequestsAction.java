package edu.ncsu.csc.itrust.action;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import edu.ncsu.csc.itrust.beans.ApptBean;
import edu.ncsu.csc.itrust.beans.ApptRequestBean;
import edu.ncsu.csc.itrust.beans.MessageBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.ApptDAO;
import edu.ncsu.csc.itrust.dao.mysql.ApptRequestDAO;
import edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;

/**
 * ViewApptRequestsAction
 */
public class ViewApptRequestsAction {
	private ApptRequestDAO arDAO;
	private ApptDAO aDAO;
	private long hcpid;
	private SendMessageAction msgAction;
	private PersonnelDAO pnDAO;

	/**
	 * ViewApptRequestsAction
	 * @param hcpid hcpid
	 * @param factory factory
	 */
	public ViewApptRequestsAction(long hcpid, DAOFactory factory) {
		arDAO = factory.getApptRequestDAO();
		aDAO = factory.getApptDAO();
		pnDAO = factory.getPersonnelDAO();
		this.hcpid = hcpid;
		msgAction = new SendMessageAction(factory, hcpid);
	}

	/**
	 * getApptRequests
	 * @return reqs
	 * @throws SQLException
	 * @throws DBException
	 */
	public List<ApptRequestBean> getApptRequests() throws SQLException, DBException {
		List<ApptRequestBean> reqs = arDAO.getApptRequestsFor(hcpid);
		return reqs;
	}
	/**
	 * getNumRequests
	 * @param reqs reqs
	 * @return int 
	 * 
	 * Returns the number of times in the appointment request list
	 */
	public int getNumRequests(List<ApptRequestBean> reqs){
		int numOfPendingAppointments = 0;
		for(int i = 0; i < reqs.size(); i++){
			if(reqs.get(i).isPending() == true){
				numOfPendingAppointments++;
			}
		}
		return numOfPendingAppointments;
	}

	/**
	 * acceptApptRequest
	 * @param reqID reqID
	 * @return message
	 * @throws SQLException
	 * @throws DBException
	 */
	public String acceptApptRequest(int reqID) throws SQLException, DBException {
		ApptRequestBean req = arDAO.getApptRequest(reqID);
		if (req.isPending() && !req.isAccepted()) {
			req.setPending(false);
			req.setAccepted(true);
			arDAO.updateApptRequest(req);
			aDAO.scheduleAppt(req.getRequestedAppt());
			try {
				MessageBean msg = constructMessage(req.getRequestedAppt(), req.isAccepted());
				msgAction.sendMessage(msg);
			} catch (Exception e) {
				//TODO
			}
			return "The appointment request you selected has been accepted and scheduled.";
		} else {
			return "The appointment request you selected has already been acted upon.";
		}
	}

	/**
	 * rejectApptRequest
	 * @param reqID reqID
	 * @return message
	 * @throws SQLException
	 * @throws DBException
	 */
	public String rejectApptRequest(int reqID) throws SQLException, DBException {
		ApptRequestBean req = arDAO.getApptRequest(reqID);
		if (req.isPending() && !req.isAccepted()) {
			req.setPending(false);
			req.setAccepted(false);
			arDAO.updateApptRequest(req);
			try {
				MessageBean msg = constructMessage(req.getRequestedAppt(), req.isAccepted());
				msgAction.sendMessage(msg);
			} catch (Exception e) {
				//TODO
			}
			return "The appointment request you selected has been rejected.";
		} else {
			return "The appointment request you selected has already been acted upon.";
		}
	}

	/**
	 * constructMessage
	 * @param appt appt
	 * @param accepted accepted
	 * @return msg
	 * @throws ITrustException
	 * @throws SQLException
	 * @throws FormValidationException
	 */
	private MessageBean constructMessage(ApptBean appt, boolean accepted) throws ITrustException, SQLException, FormValidationException {
		MessageBean msg = new MessageBean();
		msg.setFrom(appt.getHcp());
		msg.setTo(appt.getPatient());
		msg.setSubject("Your appointment request");
		msg.setSentDate(new Timestamp(System.currentTimeMillis()));
		String body = "Your appointment request with " + pnDAO.getName(appt.getHcp()) + " on "
				+ appt.getDate() + " has been ";
		if (accepted)
			body += "accepted.";
		else
			body += "rejected.";
		msg.setBody(body);
		return msg;
	}
}
