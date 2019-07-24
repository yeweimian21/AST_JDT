/**
 * 
 */
package edu.ncsu.csc.itrust.action;

import java.util.ArrayList;
import java.util.List;
import edu.ncsu.csc.itrust.action.base.EditOfficeVisitBaseAction;
import edu.ncsu.csc.itrust.beans.ReferralBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO;
import edu.ncsu.csc.itrust.dao.mysql.ReferralDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;

/**
 * Class for creating and editing referrals associated with a particular office 
 * visit.
 *  
 */
public class EditReferralsAction extends EditOfficeVisitBaseAction {
	
	private ReferralDAO referralDAO;
	private PatientDAO patientDAO;
	private PersonnelDAO personnelDAO;
	
	public EditReferralsAction(DAOFactory factory, long hcpid, 
			   	String pidString, String ovIDString) 
		throws ITrustException {
		super(factory, hcpid, pidString, ovIDString);
		referralDAO = factory.getReferralDAO();
	    patientDAO = factory.getPatientDAO();
	    personnelDAO = factory.getPersonnelDAO();
	}
	
	public EditReferralsAction(DAOFactory factory, long hcpid, 
				String pidString) 
		throws ITrustException {
		super(factory, hcpid, pidString);
		referralDAO = factory.getReferralDAO();
	    patientDAO = factory.getPatientDAO();
	    personnelDAO = factory.getPersonnelDAO();
	}
	
	/**
	 * Get all referrals associated with this office visit.
	 * @return List of ReferralBeans.
	 * @throws DBException
	 */
	public List<ReferralBean> getReferrals() throws DBException {
		if (isUnsaved()) {
			return new ArrayList<ReferralBean>();
		} else {
			return referralDAO.getReferralsFromOV(getOvID());
		}
	}
	
	/**
	 * Get a specific referral.
	 * @param id The id of the desired referral.
	 * @return ReferralBean
	 * @throws DBException
	 */
	public ReferralBean getReferral(long id) throws DBException {
		return referralDAO.getReferral(id);
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
	
	
}
