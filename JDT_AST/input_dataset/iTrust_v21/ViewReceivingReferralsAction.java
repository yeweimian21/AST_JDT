package edu.ncsu.csc.itrust.action;

import java.util.List;
import edu.ncsu.csc.itrust.beans.OfficeVisitBean;
import edu.ncsu.csc.itrust.beans.ReferralBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.OfficeVisitDAO;
import edu.ncsu.csc.itrust.dao.mysql.ReferralDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;

/**
 */
public class ViewReceivingReferralsAction {

	private ReferralDAO referralDAO;
	private OfficeVisitDAO ovDAO;
	private long hcpID;
	
	public ViewReceivingReferralsAction(DAOFactory factory, long patientID) throws ITrustException {
		this.referralDAO = factory.getReferralDAO();
		this.ovDAO = factory.getOfficeVisitDAO();
		this.hcpID = patientID;
	}

	public List<ReferralBean> getReferralsForReceivingHCP() throws DBException{
		return referralDAO.getReferralsForReceivingHCP(hcpID);
	}
	
	public int getReferralsForReceivingHCPUnread() throws DBException{
		return referralDAO.getReferralsForReceivingHCPUnread(hcpID).size();
	}
	
	public ReferralBean getReferralByID(int id) throws DBException{
		return referralDAO.getReferral(id);
	}
	
	public boolean updateReferral(ReferralBean bean) throws DBException {
		referralDAO.editReferral(bean);
		return true;
	}
	
	public OfficeVisitBean getOVDate(long id) throws DBException {
		return ovDAO.getOfficeVisit(id);
	}
	

}
