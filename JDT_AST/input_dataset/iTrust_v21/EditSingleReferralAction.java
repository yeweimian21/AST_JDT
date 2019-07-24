package edu.ncsu.csc.itrust.action;

import edu.ncsu.csc.itrust.beans.MessageBean;
import edu.ncsu.csc.itrust.beans.ReferralBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.OfficeVisitDAO;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO;
import edu.ncsu.csc.itrust.dao.mysql.ReferralDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;

/**
 * Class that allows creating, editing, and deleting of a single referral.
 *  
 *
 */
public class EditSingleReferralAction {
	
	private ReferralDAO referralDAO;
	private PatientDAO patientDAO;
	private PersonnelDAO personnelDAO;
	private OfficeVisitDAO ovDAO;
	private SendMessageAction messageAction;
	private long hcpid;
	
    public EditSingleReferralAction(DAOFactory factory, long hcpid)
	  throws ITrustException {
	    referralDAO = factory.getReferralDAO();
	    patientDAO = factory.getPatientDAO();
	    personnelDAO = factory.getPersonnelDAO();
	    ovDAO = factory.getOfficeVisitDAO();
	    messageAction = new SendMessageAction(factory, hcpid);
	    this.hcpid = hcpid;
	}
    
	/**
	 * Get a specific referral.
	 * @param id
	 * @return
	 * @throws DBException
	 */
	public ReferralBean getReferral(long id) throws DBException {
		return referralDAO.getReferral(id);
	}
	
	/**
	 * Add a new referral to the database.
	 * @param bean
	 * @throws Exception
	 */
	public void addReferral(ReferralBean bean) throws Exception {
		if (bean.getSenderID() != hcpid) {
			throw new ITrustException("New referrals must be from the current HCP.");
		}
		long id = referralDAO.addReferral(bean);
		bean = referralDAO.getReferral(id);
		sendReferralCreatedMessages(bean);
	}
	
	/**
	 * Edit an existing referral.
	 * @param bean
	 * @throws ITrustException
	 */
	public void editReferral(ReferralBean bean) throws ITrustException {
		if (bean.getSenderID() != hcpid) {
			throw new ITrustException("Edited referrals must be from the current HCP.");
		}
		referralDAO.editReferral(bean);
	}
	
	/**
	 * Delete a referral from the database.
	 * @param bean
	 * @throws Exception
	 */
	public void deleteReferral(ReferralBean bean) throws Exception {
		if (bean.getSenderID() != hcpid) {
			throw new ITrustException("Deleted referrals must be from the current HCP.");
		}
		referralDAO.removeReferral(bean.getId());
		sendReferralCancelledMessages(bean);
	}

	/**
	 * Get the patient name associated with the given referral.
	 * @param bean
	 * @return The patient's name as a String.
	 * @throws ITrustException
	 */
	public String getPatientName(ReferralBean bean) throws ITrustException {
		return patientDAO.getName(bean.getPatientID());
	}
	
	/**
	 * Get the name of the receiving HCP associated with the given referral.
	 * @param bean
	 * @return The HCP's name as a String.
	 * @throws ITrustException
	 */
	public String getReceivingHCPName(ReferralBean bean) throws ITrustException {
		return personnelDAO.getName(bean.getReceiverID());
	}
	
	public String getReceivingHCPSpecialty(ReferralBean bean) throws ITrustException {
		String s = personnelDAO.getPersonnel(bean.getReceiverID()).getSpecialty();
		return s==null ? "" : s;
	}
	
	public String getOfficeVisitDate(ReferralBean bean) throws ITrustException {
		return ovDAO.getOfficeVisit(bean.getOvid()).getVisitDateStr();
	}
	
	/**
	 * Send messages to the users associated with creating a referral.
	 * @param bean
	 * @throws Exception
	 */
	public void sendReferralCreatedMessages(ReferralBean bean) throws Exception {
		StringBuilder body = new StringBuilder();
		body.append("Referral information:\n");
		String senderSpecialty = personnelDAO.getPersonnel(bean.getSenderID()).getSpecialty();
		String receiverSpecialty = personnelDAO.getPersonnel(bean.getReceiverID()).getSpecialty();
		senderSpecialty = senderSpecialty==null ? "no specialty" : senderSpecialty;
		receiverSpecialty = receiverSpecialty==null ? "no specialty" : receiverSpecialty;
		body.append("Sending HCP: " + personnelDAO.getName(bean.getSenderID()) + " (" + 
								      senderSpecialty + ")\n");
		body.append("Receiving HCP: " + personnelDAO.getName(bean.getReceiverID()) + " (" + 
										receiverSpecialty + ")\n");
		body.append("Patient: " + getPatientName(bean) + "\n");
		body.append("Notes: " + bean.getReferralDetails() + "\n");
		body.append("Created on: " + bean.getTimeStamp() + "\n");
		
		
		MessageBean senderMsg = new MessageBean();
		senderMsg.setFrom(bean.getSenderID());
		senderMsg.setTo(bean.getSenderID());
		senderMsg.setBody(body.toString());
		senderMsg.setSubject("You Created a New Referral");
		senderMsg.setRead(0);
		messageAction.sendMessage(senderMsg);
		
		MessageBean receiverMsg = new MessageBean();
		receiverMsg.setFrom(bean.getSenderID());
		receiverMsg.setTo(bean.getReceiverID());
		receiverMsg.setBody(body.toString());
		receiverMsg.setSubject("You Received a New Referral");
		receiverMsg.setRead(0);
		messageAction.sendMessage(receiverMsg);
		
		MessageBean patientMsg = new MessageBean();
		patientMsg.setFrom(bean.getSenderID());
		patientMsg.setTo(bean.getPatientID());
		patientMsg.setBody(body.toString());
		patientMsg.setSubject("You Received a New Referral");
		patientMsg.setRead(0);
		messageAction.sendMessage(patientMsg);
	}
	
	/**
	 * Send messages to the users associated with canceling a referral.
	 * @param bean
	 * @throws Exception
	 */
	public void sendReferralCancelledMessages(ReferralBean bean) throws Exception {
		StringBuilder body = new StringBuilder();
		body.append("The referral created on "+bean.getTimeStamp()+"has been deleted.\n\n");
		body.append("The referral is reproduced here for your convienence:\n");
		body.append("Sending HCP: " + personnelDAO.getName(bean.getSenderID()) + " (" + 
									  personnelDAO.getPersonnel(bean.getSenderID()).getSpecialty() + ")\n");
		body.append("Receiving HCP: " + personnelDAO.getName(bean.getReceiverID()) + " (" + 
				  						personnelDAO.getPersonnel(bean.getReceiverID()).getSpecialty() + ")\n");
		body.append("Patient: " + getPatientName(bean) + "\n");
		body.append("Notes: " + bean.getReferralDetails() + "\n");
		body.append("Created on: " + bean.getTimeStamp() + "\n");
		
		MessageBean receiverMsg = new MessageBean();
		receiverMsg.setFrom(bean.getSenderID());
		receiverMsg.setTo(bean.getReceiverID());
		receiverMsg.setBody(body.toString());
		receiverMsg.setSubject("Your Referral Was Cancelled");
		receiverMsg.setRead(0);
		messageAction.sendMessage(receiverMsg);
		
		MessageBean patientMsg = new MessageBean();
		patientMsg.setFrom(bean.getSenderID());
		patientMsg.setTo(bean.getPatientID());
		patientMsg.setBody(body.toString());
		patientMsg.setSubject("Your Referral Was Cancelled");
		patientMsg.setRead(0);
		messageAction.sendMessage(patientMsg);
	}
	
}
