package edu.ncsu.csc.itrust.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import edu.ncsu.csc.itrust.EmailUtil;
import edu.ncsu.csc.itrust.beans.Email;
import edu.ncsu.csc.itrust.beans.MessageBean;
import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.beans.PersonnelBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.MessageDAO;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.validate.EMailValidator;
import edu.ncsu.csc.itrust.validate.MessageValidator;


/**
 * Class for SendMessage.jsp.  
 */

public class SendMessageAction {
	private long loggedInMID;
	private EmailUtil emailer;
	private PatientDAO patientDAO;
	private PersonnelDAO personnelDAO;
	private MessageDAO messageDAO;
	private EMailValidator emailVal;
	private MessageValidator messVal;


	/**
	 * Sets up defaults
	 * @param factory The DAOFactory used to create the DAOs used in this action.
	 * @param loggedInMID The MID of the user sending the message.
	 */
	public SendMessageAction(DAOFactory factory, long loggedInMID) {
		this.loggedInMID = loggedInMID;
		this.patientDAO = factory.getPatientDAO();
		this.personnelDAO = factory.getPersonnelDAO();
		this.emailer = new EmailUtil(factory);
		this.messageDAO = factory.getMessageDAO();
		this.emailVal = new EMailValidator();
		this.messVal = new MessageValidator();
	}
	
	/**
	 * Sends a message
	 * 
	 * @param mBean message to be sent
	 * @throws ITrustException
	 * @throws SQLException
	 */
	public void sendMessage(MessageBean mBean) throws ITrustException, SQLException, FormValidationException {
		messVal.validate(mBean);
		emailVal.validate(mBean);
		messageDAO.addMessage(mBean);
		
		Email email = new Email();
		String senderName;
		String fromEmail;
		email.setFrom("noreply@itrust.com");
		List<String> toList = new ArrayList<String>();
		if (8999999999L < mBean.getFrom() && 8999999999L < mBean.getTo()){ //when from and to are LHCPs
			PersonnelBean sender = personnelDAO.getPersonnel(loggedInMID);
			PersonnelBean receiver = personnelDAO.getPersonnel(mBean.getTo());
			
			toList.add(receiver.getEmail());
			senderName = sender.getFullName();
			fromEmail = sender.getEmail();
			
			email.setBody(String.format("You have received a new message from %s in iTrust. To view it, go to \"http://localhost:8080/iTrust/auth/hcp/messageInbox.jsp\" and log in to iTrust using your username and password.", senderName));
		}else{
			if (6999999999L < mBean.getFrom()) {
				PersonnelBean sender = personnelDAO.getPersonnel(loggedInMID);
				
				if (6999999999L < mBean.getTo()) { //when from is any personnel and to is any personnel
					PersonnelBean receiver = personnelDAO.getPersonnel(mBean.getTo());
					toList.add(receiver.getEmail());
					
					senderName = sender.getFullName();
					
					email.setBody(String.format("You have received a new message from %s in iTrust. To view it, go to \"http://localhost:8080/iTrust/auth/hcp/messageInbox.jsp\" and log in to iTrust using your username and password.", senderName));
				} else { //when from is any personnel and to is patient
					PatientBean receiver = patientDAO.getPatient(mBean.getTo());
					toList.add(receiver.getEmail());
					
					senderName = sender.getFullName();
					
					email.setBody(String.format("You have received a new message from %s in iTrust. To view it, go to \"http://localhost:8080/iTrust/auth/patient/messageInbox.jsp\" and log in to iTrust using your username and password.", senderName));
				}
				fromEmail = sender.getEmail();
				
			} else {
				PatientBean sender = patientDAO.getPatient(loggedInMID);
				
				if (6999999999L < mBean.getTo()) { //when from is patient and to is any personnel
					PersonnelBean receiver = personnelDAO.getPersonnel(mBean.getTo());
					toList.add(receiver.getEmail());
					
					senderName = sender.getFullName();
					
					email.setBody(String.format("You have received a new message from %s in iTrust. To view it, go to \"http://localhost:8080/iTrust/auth/hcp/messageInbox.jsp\" and log in to iTrust using your username and password.", senderName));
				} else { //when from is patient and to is patient
					PatientBean receiver = patientDAO.getPatient(mBean.getTo());
					toList.add(receiver.getEmail());
					
					senderName = sender.getFullName();
					
					email.setBody(String.format("You have received a new message from %s in iTrust. To view it, go to \"http://localhost:8080/iTrust/auth/patient/messageInbox.jsp\" and log in to iTrust using your username and password.", senderName));
				}
				fromEmail = sender.getEmail();
			}
		}
		email.setToList(toList);
		email.setFrom(fromEmail);
		email.setSubject(String.format("A new message from %s", senderName));
		emailer.sendEmail(email);
		
	}
	
	/**
	 * Returns the patient's name
	 * 
	 * @param mid MId of the patient
	 * @return the name of the patient
	 * @throws ITrustException
	 */
	public String getPatientName(long mid) throws ITrustException {
		return patientDAO.getName(mid);
	}
	
	/**
	 * Returns the personnel's name
	 * 
	 * @param mid MId of the personnel
	 * @return the name of the personnel
	 * @throws ITrustException
	 */
	public String getPersonnelName(long mid) throws ITrustException {
		return personnelDAO.getName(mid);
	}
	
	/**
	 * Returns a list of the patients that the logged in HCP represents
	 * 
	 * @return list of the patients that the logged in HCP represents
	 * @throws ITrustException
	 */
	public List<PatientBean> getMyRepresentees() throws ITrustException {
		List<PatientBean> representees = new ArrayList<PatientBean>();
		try {
			representees = patientDAO.getRepresented(loggedInMID);
		} catch (DBException e) {
			//TODO
		}
		return representees;
	}
	
	/**
	 * Returns the designated HCPs for the logged in patient.
	 * 
	 * @return designated HCPs for the logged in patient.
	 * @throws ITrustException
	 */
	public List<PersonnelBean> getMyDLHCPs() throws ITrustException {
		return getDLHCPsFor(loggedInMID);
	}
	
	/**
	 * Returns the designated HCPs for the given patient.
	 * @param pid pid
	 * @return designated HCPs for the given patient.
	 * @throws ITrustException
	 */
	public List<PersonnelBean> getDLHCPsFor(long pid) throws ITrustException {
		List<PersonnelBean> dlhcps = new ArrayList<PersonnelBean>();
		try {
			dlhcps = patientDAO.getDeclaredHCPs(pid);
		} catch (DBException e) {
			//TODO
		}
		return dlhcps;		
	}
	
	/**
	 * getDLHCPByMID
	 * @param mid mid
	 * @return personnel
	 * @throws ITrustException
	 */
	public PersonnelBean getDLHCPByMID(long mid) throws ITrustException {
		return personnelDAO.getPersonnel(mid);
	
	}
}
