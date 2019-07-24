package edu.ncsu.csc.itrust.action;

import edu.ncsu.csc.itrust.beans.BillingBean;
import edu.ncsu.csc.itrust.beans.OfficeVisitBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.BillingDAO;
import edu.ncsu.csc.itrust.dao.mysql.OfficeVisitDAO;
import edu.ncsu.csc.itrust.exception.DBException;

/**
 * VerifyClaimAction handles the interaction between a user and the
 * DAOs. 
 */
public class VerifyClaimAction {
	/**The DAO to access the billing table*/
	private BillingDAO billAccess;
	/**The DAO to access the office visits DAO*/
	private OfficeVisitDAO ovAccess;
	/**The bill that we are verifying*/
	private BillingBean bill;
	/**The office visit associated with the bill*/
	private OfficeVisitBean ov;
	
	/**
	 * VerifyClaimAction simply initializes the instance variables.
	 * @param factory The DAO factory
	 * @param bID The ID of the bill.
	 */
	public VerifyClaimAction(DAOFactory factory, long bID){
		billAccess = factory.getBillingDAO();
		ovAccess = factory.getOfficeVisitDAO();
		try {
			bill = billAccess.getBillId(bID);
			ov = ovAccess.getOfficeVisit(bill.getApptID());
		} catch (DBException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * getBill returns the bill we are handling.
	 * @return The billing bean.
	 */
	public BillingBean getBill(){
		return this.bill;
	}
	
	/**
	 * getOV returns the office visit we are handling.
	 * @return The office visit bean.
	 */
	public OfficeVisitBean getOV(){
		return this.ov;
	}
	
	/**
	 * denyClaim handles the user choosing to deny the claim.
	 */
	public void denyClaim(){
		bill.setStatus("Denied");
		try {
			this.billAccess.editBill(bill);
		} catch (DBException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * approveClaim handles the user choosing to approve the claim.
	 */
	public void approveClaim(){
		bill.setStatus("Approved");
		try {
			this.billAccess.editBill(bill);
		} catch (DBException e) {
			e.printStackTrace();
		}
	}
}
