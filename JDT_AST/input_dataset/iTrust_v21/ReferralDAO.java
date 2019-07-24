package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.beans.ReferralBean;
import edu.ncsu.csc.itrust.beans.VerboseReferralBean;
import edu.ncsu.csc.itrust.beans.loaders.ReferralBeanLoader;
import edu.ncsu.csc.itrust.beans.loaders.VerboseReferralBeanLoader;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.enums.SortDirection;
import edu.ncsu.csc.itrust.exception.DBException;

/**
 * Used to update referrals, and fetch lists of referrals sent to and
 * from HCPs.
 *
 * DAO stands for Database Access Object. All DAOs are intended to be reflections of the database, that is,
 * one DAO per table in the database (most of the time). For more complex sets of queries, extra DAOs are
 * added. DAOs can assume that all data has been validated and is correct.
 * 
 * DAOs should never have setters or any other parameter to the constructor than a factory. All DAOs should be
 * accessed by DAOFactory (@see {@link DAOFactory}) and every DAO should have a factory - for obtaining JDBC
 * connections and/or accessing other DAOs.
 */
public class ReferralDAO {
	
	private DAOFactory factory;
	private ReferralBeanLoader referralLoader;
	private VerboseReferralBeanLoader verboseLoader;

	/**
	 * The typical constructor.
	 * @param factory The {@link DAOFactory} associated with this DAO, which is used for obtaining SQL connections, etc.
	 */
	public ReferralDAO(DAOFactory factory) {
		this.factory = factory;
		referralLoader = new ReferralBeanLoader();
		verboseLoader = new VerboseReferralBeanLoader();
	}
	
	/**
	 * Get all referrals associated with a particular office visit.
	 * @param ovid The office visit id.
	 * @return A list of ReferralBeans.
	 * @throws DBException
	 */
	public List<ReferralBean> getReferralsFromOV(long ovid) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM referrals WHERE ovID = ?");
			ps.setLong(1, ovid);
			ResultSet rs = ps.executeQuery();
			
			List<ReferralBean> loadlist = referralLoader.loadList(rs);
			rs.close();
			ps.close();
			return loadlist;
			
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	/**
	 * Gets a list of all referrals sent from an HCP
	 * @param mid The HCP's mid.
	 * @return The list of the referrals they sent.
	 * @throws DBException
	 */
	public List<ReferralBean> getReferralsSentFrom(long mid) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM referrals WHERE SenderID = ?");
			ps.setLong(1, mid);
			ResultSet rs = ps.executeQuery();
			
			
			List<ReferralBean> loadlist = referralLoader.loadList(rs);
			rs.close();
			ps.close();
			return loadlist;
			
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	/**
	 * Get a specific referral.
	 * @param id The id of the referral to retrieve.
	 * @return A ReferralBean.
	 * @throws DBException
	 */
	public ReferralBean getReferral(long id) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM referrals WHERE id = ?");
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				ReferralBean loadlist = referralLoader.loadSingle(rs);
				rs.close();
				ps.close();
				return loadlist;
			} else{
				rs.close();
				return null;
			}
			
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	
	/**
	 * Set referral message.
	 * @param messageID messageID
	 * @param referralID referralID
	 * @return A ReferralBean.
	 * @throws DBException
	 */
	public boolean setReferralMessage(long messageID, long referralID) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("INSERT INTO referralmessage (messageID,referralID) VALUES (?,?) ");
			ps.setLong(1, messageID);
			ps.setLong(2, referralID);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
		return true;
	}
	
	
	/**
	 * Set referral message.
	 * @param messageID messageID
	 * @return A ReferralBean.
	 * @throws DBException
	 */
	public long isReferralMessage(long messageID) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM referralmessage WHERE messageID = ?");
			ps.setLong(1, messageID);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				long a = rs.getLong(2);
				rs.close();
				ps.close();
				return a;
			} else{
				rs.close();
				ps.close();
				return 0;
			}
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	
	/**
	 * Gets a list of all referrals sent to an HCP
	 * @param mid The HCP's mid.
	 * @return The list of the referrals sent to them.
	 * @throws DBException
	 */
	public List<ReferralBean> getReferralsSentTo(long mid) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM referrals WHERE ReceiverID = ?");
			ps.setLong(1, mid);
			ResultSet rs = ps.executeQuery();
			
			
			List<ReferralBean> loadlist = referralLoader.loadList(rs);
			rs.close();
			ps.close();
			return loadlist;
			
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	/**
	 * Gets a list of all referrals a HCP has received
	 * @param mid The patients's mid.
	 * @return The list of the referrals they received.
	 * @throws DBException
	 */
	public List<ReferralBean> getReferralsForReceivingHCP(long mid) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM referrals WHERE ReceiverID = ? ORDER BY PriorityCode ASC");
			ps.setLong(1, mid);
			ResultSet rs = ps.executeQuery();
			List<ReferralBean> loadlist = referralLoader.loadList(rs);
			rs.close();
			ps.close();
			return loadlist;
			
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	/**
	 * Gets a list of all referrals sent to a patient.
	 * @param mid The patients's mid.
	 * @return The list of the referrals they received.
	 * @throws DBException
	 */
	public List<ReferralBean> getReferralsForPatient(long mid) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM referrals WHERE PatientID = ? ORDER BY viewed_by_patient, PriorityCode ASC");
			ps.setLong(1, mid);
			ResultSet rs = ps.executeQuery();
			List<ReferralBean> loadlist = referralLoader.loadList(rs);
			rs.close();
			ps.close();
			return loadlist;
			
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	/**
	 * Gets a list of all referrals sent to a patient
	 * @param mid The patients's mid.
	 * @return The list of the referrals they received that were unread.
	 * @throws DBException
	 */
	public List<ReferralBean> getReferralsForReceivingHCPUnread(long mid) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM referrals WHERE ReceiverID = ? AND viewed_by_HCP = false");
			ps.setLong(1, mid);
			ResultSet rs = ps.executeQuery();
			List<ReferralBean> loadlist = referralLoader.loadList(rs);
			rs.close();
			ps.close();
			return loadlist;
			
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	/**
	 * Gets a list of all referrals sent to a patient
	 * @param mid The patients's mid.
	 * @return The list of the referrals they received that were unread.
	 * @throws DBException
	 */
	public List<ReferralBean> getReferralsForPatientUnread(long mid) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM referrals WHERE PatientID = ? AND viewed_by_patient = false");
			ps.setLong(1, mid);
			ResultSet rs = ps.executeQuery();
			List<ReferralBean> loadlist = referralLoader.loadList(rs);
			rs.close();
			ps.close();
			return loadlist;
			
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	/**
	 * Updates a given referral in the database.
	 * @param r The referral to update.
	 * @throws DBException
	 */
	public void editReferral(ReferralBean r) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("UPDATE referrals SET PatientID=?,SenderID=?,ReceiverID=?,"
					+ "ReferralDetails=?,OVID=?,viewed_by_patient=?,viewed_by_HCP=?,PriorityCode=?  WHERE ID=?");
			referralLoader.loadParameters(ps, r);
			ps.setLong(9, r.getId());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	/**
	 * Adds a given referral to the database.
	 * @param r The referral to add.
	 * @return DBUtil
	 * @throws DBException
	 */
	public long addReferral(ReferralBean r) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("INSERT INTO referrals (PatientID,SenderID,ReceiverID,"
					+ "ReferralDetails,OVID,viewed_by_patient,viewed_by_HCP,PriorityCode,TimeStamp)  "
					+ "VALUES (?,?,?,?,?,?,?,?,NOW())");
			ps = referralLoader.loadParameters(ps, r);
			ps.executeUpdate();
			ps.close();
			return DBUtil.getLastInsert(conn);
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	/**
	 * Removes the given referral.
	 * 
	 * @param id The unique ID of the referral to be removed.
	 * @throws DBException
	 */
	public void removeReferral(long id) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("DELETE FROM referrals WHERE ID=? ");
			ps.setLong(1, id);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}


	/**
	 * An abstract class that encapsulates a sorted query of referrals.  
	 * Derived classes provide the user id which all retrieved referrals will 
	 * contain.
	 */
	public abstract class ReferralListQuery {
		
		private DAOFactory factory;
		private long userid;
		private HashMap<String, String> sortColumns;
		
		/**
		 * Create a new ReferralListQuery object.
		 * @param factory factory
		 * @param userid userid
		 */
		public ReferralListQuery(DAOFactory factory, long userid) {
			this.factory = factory;
			this.userid = userid;
			// initialize lookup map
			sortColumns = new HashMap<String, String>();
			sortColumns.put("patientName", "CONCAT(patients.lastName, ' ', patients.firstName)");
			sortColumns.put("receiverName", "CONCAT(preceiver.lastName, preceiver.firstName)");
			sortColumns.put("senderName", "CONCAT(psender.lastName, psender.firstName)");
			sortColumns.put("timestamp", "referrals.timestamp");
			sortColumns.put("priority", "referrals.PriorityCode");
		}
		
		/**
		 * Perform the query.
		 * 
		 * @param sortField The pseudo-field name in which to sort.
		 * @param dir The direction of the desired sort (ascending or 
		 * 			  descending)
		 * @return A list of VerboseReferralBeans.
		 * @throws DBException
		 */
		protected List<VerboseReferralBean> doquery(String sortField, SortDirection dir) throws DBException {
			String stmt = 
				"SELECT " +
				    "CONCAT(psender.firstName,' ',psender.lastName) AS senderName, " +
				    "CONCAT(preceiver.firstName,' ',preceiver.lastName) AS receiverName, " +
				    "referrals.*, " +
				    "officevisits.visitDate, " +
				    "CONCAT(patients.firstName,' ',patients.lastName) AS patientName " +
				"FROM " +
				    "referrals, " +
				    "personnel AS psender, " +
				    "personnel AS preceiver, " +
				    "patients, " +
				    "officevisits " +
				"WHERE " +
				    "referrals.SenderID=psender.mid " +
				    "AND referrals.ReceiverID=preceiver.mid " +
				    "AND referrals.PatientID=patients.mid " +
				    "AND referrals.ovid=officevisits.id ";
			stmt += String.format("AND %s = ? ", getUserField());
			stmt += buildSort(sortField, dir);
			
			Connection conn = null;
			PreparedStatement ps = null;
			try {
				conn = factory.getConnection();
				ps = conn.prepareStatement(stmt);
				ps.setLong(1, getUserId());
				ResultSet rs = ps.executeQuery();
				List<VerboseReferralBean> loadlist = verboseLoader.loadList(rs);
				rs.close();
				ps.close();
				return loadlist;
				
			} catch (SQLException e) {
				
				throw new DBException(e);
			} finally {
				DBUtil.closeConnection(conn, ps);
			}
		}
		
		/**
		 * Perform the query.
		 * 
		 * @param sortField The pseudo-field name in which to sort.
		 * @param dir The direction of the desired sort (ascending or 
		 * 			  descending)
		 * @return A list of VerboseReferralBeans.
		 * @throws DBException
		 */
		public List<VerboseReferralBean> query(String sortField, SortDirection dir) throws DBException {
			List<VerboseReferralBean> beans = doquery(sortField, dir);
			return beans;
		}
		
		/**
		 * Get the name of the user pseudo-field which is used to limit the 
		 * query.  Only referrals where this field equals a specific user id 
		 * will be returned.  This must be overridden by derived classes.
		 * 
		 * @return The user field as a string.
		 */
		protected abstract String getUserField();
		
		/**
		 * getUserId
		 * @return long
		 */
		protected long getUserId() { return userid; }
		
		/**
		 * Builds the sort portion of the SQL query (i.e. the ORDER BY... portion).
		 * 
		 * @param sortField The pseudo-field to sort on.
		 * @param dir The sort direction.
		 * @return A string which can be a part of an SQL query.
		 */
		protected String buildSort(String sortField, SortDirection dir) {
			String sortexp = sortColumns.get(sortField);
			return String.format(" ORDER BY %s %s ", sortexp, dir);
		}
	}
	
	/**
	 * Concrete ReferralListQuery for accessing an HCPs sent referrals.
	 */
	public class SenderReferralListQuery extends ReferralListQuery {

		/**
		 * SenderReferralListQuery
		 * @param factory factory
		 * @param userid userid
		 */
		public SenderReferralListQuery(DAOFactory factory, long userid) {
			super(factory, userid);
		}

		@Override
		protected String getUserField() {
			return "referrals.SenderID";
		}
	}
	
	/**
	 * Concrete ReferralListQuery for accessing an HCPs received referrals.
	 */
	public class ReceiverReferralListQuery extends ReferralListQuery {

		/**
		 * ReceiverReferralListQuery
		 * @param factory factory
		 * @param userid userid
		 */
		public ReceiverReferralListQuery(DAOFactory factory, long userid) {
			super(factory, userid);
		}

		@Override
		protected String getUserField() {
			return "referrals.ReceiverID";
		}
	}
	
	/**
	 * Concrete ReferralListQuery for accessing a patients referrals.
	 */
	public class PatientReferralListQuery extends ReferralListQuery {

		/**
		 * PatientReferralListQuery
		 * @param factory factory
		 * @param userid userid
		 */
		public PatientReferralListQuery(DAOFactory factory, long userid) {
			super(factory, userid);
		}

		@Override
		protected String getUserField() {
			return "referrals.PatientID";
		}
	}
	
	/**
	 * Get a referral query for a sending HCP.
	 * @param mid The HCP id.
	 * @return A ReferralListQuery object.
	 */
	public ReferralListQuery getSenderQuery(long mid) {
		return new SenderReferralListQuery(this.factory, mid);
	}
	
	/**
	 * Get a referral query for a receiving HCP.
	 * @param mid The HCP id.
	 * @return A ReferralListQuery object.
	 */
	public ReferralListQuery getReceiverQuery(long mid) {
		return new ReceiverReferralListQuery(this.factory, mid);
	}
	
	/**
	 * Get a referral query for a patient.
	 * @param pid pid
	 * @return A ReferralListQuery object.
	 */
	public ReferralListQuery getPatientQuery(long pid) {
		return new PatientReferralListQuery(this.factory, pid);
	}
	
	
}
