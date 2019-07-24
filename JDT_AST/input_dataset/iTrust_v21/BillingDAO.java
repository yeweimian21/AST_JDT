package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.beans.BillingBean;
import edu.ncsu.csc.itrust.beans.loaders.BillingBeanLoader;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;

/**
 * this class is used to interact with the datbaase to add, get, edit, or remove bills
 */
public class BillingDAO {

	/**DAOfactory used to make a BillingDAO*/
	private transient final DAOFactory factory;
	/**used to get load things into the database or from the database*/
	private transient final BillingBeanLoader bbloader;
	
	/**
	 * makes a new BillingDAO
	 * @param factory the DAOfactory used to make a BillingDAO
	 */
	public BillingDAO(final DAOFactory factory) {
		this.factory = factory;
		this.bbloader = new BillingBeanLoader();
	}

	/**
	 * adds a bill to the database
	 * @param bill the bill being added to the database
	 * @return 
	 * @throws DBException
	 */
	public long addBill(final BillingBean bill) throws DBException {
		Connection conn = null;
		PreparedStatement pstring = null;
		try{
			conn = factory.getConnection();
	
			pstring = conn.prepareStatement(
					"INSERT INTO billing (appt_id, PatientID, HCPID, amt, status, ccHolderName, billingAddress, ccType, ccNumber, cvv, insHolderName, insID, insProviderName, insAddress1, insAddress2, insCity, insState, insZip, insPhone, submissions, billTimeS) "
				  + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			pstring = this.bbloader.loadParameters(pstring, bill);
			pstring.setDate(21, new java.sql.Date(new Date().getTime()));
			pstring.executeUpdate();
			pstring.close();
			return DBUtil.getLastInsert(conn);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstring);
		}
	}
	
	/**
	 * Returns the bill with a specific billID.
	 * @param billID The ID of the bill
	 * @return The bill with the id.
	 * @throws DBException
	 */
	public BillingBean getBillId(long billID) throws DBException {
		Connection conn = null;
		PreparedStatement pstring = null;
		ResultSet rs = null;
		BillingBean bill = null;
		try{
			conn = factory.getConnection();
	
			pstring = conn.prepareStatement("SELECT * FROM billing WHERE billID=?"); 
			pstring.setLong(1, billID);
			rs = pstring.executeQuery();
			if(rs.next()){
				bill = this.bbloader.loadSingle(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstring);
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return bill;
	}
	
	/**
	 * Returns a bill for a specific office visit.
	 * @param billID The office visit id that I am looking for.
	 * @return The bill for that visit.
	 * @throws DBException
	 */
	public BillingBean getBillWithOVId(long billID) throws DBException {
		Connection conn = null;
		PreparedStatement pstring = null;
		ResultSet rs = null;
		BillingBean bill = null;
		try{
			conn = factory.getConnection();
	
			pstring = conn.prepareStatement("SELECT * FROM billing WHERE appt_id=?"); 
			pstring.setLong(1, billID);
			rs = pstring.executeQuery();
			if(rs.next()){
				bill = this.bbloader.loadSingle(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstring);
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return bill;
	}
	
	/**
	 * gets a list of billing beans of all the bills from the database for the given patient id
	 * @param mid the patient id
	 * @return the list of bills for the given patient id
	 * @throws DBException
	 */
	public List<BillingBean> getBills(long mid) throws DBException {
		Connection conn = null;
		PreparedStatement pstring = null;
		try {
			conn = factory.getConnection();
		
			pstring = conn.prepareStatement("SELECT * FROM billing WHERE patientID=?");
		
			pstring.setLong(1, mid);
		
			final ResultSet results = pstring.executeQuery();
			final List<BillingBean> bList = this.bbloader.loadList(results);
			results.close();
			pstring.close();
			return bList;
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstring);
		}
	}
	
	/**
	 * Get a list of the patient's unpaid bills 
	 * 
	 * @param mid
	 * @return list of BillingBeans where status is Unsubmitted
	 * @throws DBException
	 */
	public List<BillingBean> getUnpaidBills(long mid) throws DBException {
		Connection conn = null;
		PreparedStatement pstring = null;
		try {
			conn = factory.getConnection();
		
			pstring = conn.prepareStatement("SELECT * FROM billing WHERE patientID=? and status='Unsubmitted'");
		
			pstring.setLong(1, mid);
		
			final ResultSet results = pstring.executeQuery();
			final List<BillingBean> bList = this.bbloader.loadList(results);
			results.close();
			pstring.close();
			return bList;
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstring);
		}
	}
	
	/**
	 * Get a list of the bills paid with insurance. 
	 * 
	 * @param mid
	 * @return list of BillingBeans where paid by insurance.
	 * @throws DBException
	 */
	public List<BillingBean> getInsuranceBills() throws DBException {
		Connection conn = null;
		PreparedStatement pstring = null;
		final List<BillingBean> bList;
		try {
			conn = factory.getConnection();
		
			pstring = conn.prepareStatement("SELECT * FROM billing WHERE isInsurance=1 ORDER BY subTime DESC");
		
			final ResultSet results = pstring.executeQuery();
			bList = this.bbloader.loadList(results);
			results.close();
			pstring.close();
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstring);
		}

		return bList;
	}
	
	/**
	 * edits the bill giving as a parameter in the database
	 * @param bill the bill that needs to be edited(already with the edit)
	 * @throws DBException
	 */
	public void editBill(final BillingBean bill) throws DBException {
		Connection conn = null;
		PreparedStatement pstring = null;
		try {
			conn = factory.getConnection();
			
			pstring = conn.prepareStatement("UPDATE billing SET appt_id=?, patientID=?, HCPID=?, amt=?, status=?, ccHolderName=?, billingAddress=?, ccType=?, ccNumber=?, cvv=?, insHolderName=?, insID=?, insProviderName=?, insAddress1=?, insAddress2=?, insCity=?, insState=?, insZip=?, insPhone=?, submissions=?, isInsurance=?, subTime=? WHERE billID=?");
			
			pstring.setInt(23, bill.getBillID());
			this.bbloader.loadParameters(pstring, bill);
			if(bill.getSubTime() != null)
				pstring.setString(22, bill.getSubTime().toString());
			else
				pstring.setString(22, new Timestamp(0).toString());
		
			pstring.executeUpdate();
			pstring.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstring);
		}
	}
	
	/**
	 * removes a bill from the database
	 * @param bill the bill that needs to be deleted
	 * @throws DBException
	 */
	public void removeBill(final BillingBean bill) throws DBException {
		Connection conn = null;
		PreparedStatement pstring = null;
		try {
			conn = factory.getConnection();
		
			pstring = conn.prepareStatement("DELETE FROM billing WHERE billID=?");
		
			pstring.setInt(1, bill.getBillID());
		
			pstring.executeUpdate();
			pstring.close();
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstring);
		}
	}
	
	/**
	 * getPendingNum gets the number of pending bills.
	 * @return the nubmer of pending bills.
	 * @throws DBException
	 */
	public int getPendingNum() throws DBException{
		Connection conn = null;
		PreparedStatement pstring = null;
		int result = 0;
		try {
			conn = factory.getConnection();
		
			pstring = conn.prepareStatement("SELECT COUNT(*) FROM billing WHERE status='Pending' ORDER BY subTime DESC");
		
			final ResultSet results = pstring.executeQuery();
			if(results.next())
				result = results.getInt(1);
			results.close();
			pstring.close();
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstring);
		}

		return result;
	}
	
	/**
	 * getDeniedNum gets the number of your denied bills.
	 * @param mid Your Medical id.
	 * @return the nubmer of your denied bills.
	 * @throws DBException
	 */
	public int getDeniedNum(long mid) throws DBException{
		Connection conn = null;
		PreparedStatement pstring = null;
		int result = 0;
		try {
			conn = factory.getConnection();
		
			pstring = conn.prepareStatement("SELECT COUNT(*) FROM billing WHERE status='Denied' AND PatientID=? ORDER BY subTime DESC");
			pstring.setLong(1, mid);
			final ResultSet results = pstring.executeQuery();
			if(results.next())
				result = results.getInt(1);
			results.close();
			pstring.close();
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstring);
		}

		return result;
	}
	
	/**
	 * getDeniedNum gets the number of your denied bills.
	 * @param mid Your Medical id.
	 * @return the nubmer of your denied bills.
	 * @throws DBException
	 */
	public int getApprovedNum(long mid) throws DBException{
		Connection conn = null;
		PreparedStatement pstring = null;
		int result = 0;
		try {
			conn = factory.getConnection();
		
			pstring = conn.prepareStatement("SELECT COUNT(*) FROM billing WHERE status='Approved' AND PatientID=? ORDER BY subTime DESC");
			pstring.setLong(1, mid);
			final ResultSet results = pstring.executeQuery();
			if(results.next())
				result = results.getInt(1);
			results.close();
			pstring.close();
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstring);
		}

		return result;
	}
}
