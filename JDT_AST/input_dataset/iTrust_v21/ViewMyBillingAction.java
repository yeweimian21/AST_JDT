/**
 * 
 */
package edu.ncsu.csc.itrust.action;

import java.sql.SQLException;
import java.util.List;
import edu.ncsu.csc.itrust.dao.mysql.BillingDAO;
import edu.ncsu.csc.itrust.beans.BillingBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;

/** This class is responsible for retrieving bills for a patient 
 */
public class ViewMyBillingAction  {
	private BillingDAO billingDAO;
	private long loggedInMID;
	
	/**
	 * @param factory The DAOFactory used to create the DAOs used in this action
	 * @param loggedInMID MID of the patient who is viewing their bills
	 */
	public ViewMyBillingAction(DAOFactory factory, long loggedInMID) {
		this.billingDAO = factory.getBillingDAO();
		this.loggedInMID = loggedInMID;
	}
	
	/**
	 * Gets all the unpaid bills for a logged in patient
	 * 
	 * @return a list of all unpaid bills
	 * @throws SQLException
	 * @throws DBException
	 */
	public List<BillingBean> getMyUnpaidBills() throws SQLException, DBException {
		return billingDAO.getUnpaidBills(loggedInMID);
	}
	
	/**
	 * Gets all the bills for a logged in patient. 
	 * 
	 * @return a list of all bills
	 * @throws SQLException
	 * @throws DBException
	 */
	public List<BillingBean> getAllMyBills() throws SQLException, DBException {
		return billingDAO.getBills(loggedInMID);
	}

}
