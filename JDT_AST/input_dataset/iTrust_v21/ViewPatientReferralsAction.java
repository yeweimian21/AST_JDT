package edu.ncsu.csc.itrust.action;

import java.util.List;
import edu.ncsu.csc.itrust.beans.OfficeVisitBean;
import edu.ncsu.csc.itrust.beans.ReferralBean;
import edu.ncsu.csc.itrust.beans.VerboseReferralBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.OfficeVisitDAO;
import edu.ncsu.csc.itrust.dao.mysql.ReferralDAO;
import edu.ncsu.csc.itrust.enums.SortDirection;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;

/**
 * This class is an action class that sits in between the JSP and the DAO The methods help facilitate
 * functionality for the viewPatientReferrals.jsp
 * 
 */
public class ViewPatientReferralsAction {

	private ReferralDAO referralDAO;
	private OfficeVisitDAO ovDAO;
	private long patientID;

	public ViewPatientReferralsAction(DAOFactory factory, long patientID) throws ITrustException {
		this.referralDAO = factory.getReferralDAO();
		this.ovDAO = factory.getOfficeVisitDAO();
		this.patientID = patientID;
	}

	/**
	 * Get all referrals for a patient sorted by the given field and in the given direction.
	 * 
	 * @param field
	 *            The name of the pseudo-field to sort by.
	 * @param dir
	 *            The direction of the sort.
	 * @return
	 * @throws DBException
	 */
	public List<VerboseReferralBean> getReferrals(String field, SortDirection dir) throws DBException {
		return referralDAO.getPatientQuery(patientID).query(field, dir);
	}

	/**
	 * This method returns the number of messages for a given patient that have not been read.
	 * 
	 * @return
	 * @throws DBException
	 */
	public int getReferralsForPatientUnread() throws DBException {
		return referralDAO.getReferralsForPatientUnread(patientID).size();
	}

	/**
	 * This method gets a referral by its id
	 * 
	 * @param id
	 * @return a referral bean
	 * @throws DBException
	 */
	public ReferralBean getReferralByID(int id) throws DBException {
		return referralDAO.getReferral(id);
	}

	/**
	 * This method updates the referral in the DAO
	 * 
	 * @param bean
	 * @return a boolean expression used for testability
	 * @throws DBException
	 */
	public boolean updateReferral(ReferralBean bean) throws DBException {
		referralDAO.editReferral(bean);
		return true;
	}
	
	/**
	 * This method updates the referral in the DAO
	 * 
	 * @param bean
	 * @return a boolean expression used for testability
	 * @throws DBException
	 */
	public boolean setReferralMessage(long messageID, long referralID) throws DBException {
		referralDAO.setReferralMessage(messageID, referralID);
		return true;
	}

	/**
	 * This method returns an office visit bean given its id. The purpose is to use the bean in the related
	 * JSP to get the office visit date.
	 * 
	 * @param id
	 * @return an office visit bean
	 * @throws DBException
	 */
	public OfficeVisitBean getOVDate(long id) throws DBException {
		return ovDAO.getOfficeVisit(id);
	}

}
