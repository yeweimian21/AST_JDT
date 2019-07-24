package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.beans.ReportRequestBean;
import edu.ncsu.csc.itrust.beans.loaders.ReportRequestBeanLoader;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;

/**
 * Used for handling data related to report requests.
 * 
 * DAO stands for Database Access Object. 
 * All DAOs are intended to be reflections of the database, that is,
 * one DAO per table in the database (most of the time). 
 * For more complex sets of queries, extra DAOs are
 * added. DAOs can assume that all data has been validated and is correct.
 * 
 * DAOs should never have setters or any other parameter 
 * to the constructor than a factory. All DAOs should be
 * accessed by DAOFactory (@see {@link DAOFactory}) 
 * and every DAO should have a factory - for obtaining JDBC
 * connections and/or accessing other DAOs.
 */
@SuppressWarnings({})
public class ReportRequestDAO {
	private transient final DAOFactory factory;
	private transient final ReportRequestBeanLoader loader;

	/**
	 * The typical constructor.
	 * @param factory The {@link DAOFactory} associated with this DAO, 
	 * 		which is used for obtaining SQL connections, etc.
	 */
	public ReportRequestDAO(final DAOFactory factory) {
		this.factory = factory;
		loader = new ReportRequestBeanLoader();
	}
	
	/**
	 * Returns a full bean describing a given report request.
	 * 
	 * @param id The unique ID of the bean in the database.
	 * @return The bean describing this report request.
	 * @throws DBException
	 */
	public ReportRequestBean getReportRequest(final long id) throws DBException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			if (id == 0L) {
				throw new SQLException("ID cannot be null");
			}
			conn = factory.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM reportrequests WHERE ID = ?");
			pstmt.setLong(1, id);
			final ResultSet results = pstmt.executeQuery();
			results.next();
			final ReportRequestBean result = loader.loadSingle(results);
			results.close();
			return result;
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstmt);
		}
	}
	
	/**
	 * Returns all report requests associated with a given requester.
	 * 
	 * @param mid The MID of the personnel in question.
	 * @return A java.util.List of report requests.
	 * @throws DBException
	 */
	public List<ReportRequestBean> getAllReportRequestsForRequester(final long mid) throws DBException {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			if (mid == 0L) {
				throw new SQLException("RequesterMID cannot be null");
			}
			conn = factory.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM reportrequests WHERE RequesterMID = ?");
			pstmt.setLong(1, mid);
			final ResultSet results = pstmt.executeQuery();
			final List<ReportRequestBean> loadlist = loader.loadList(results);
			results.close();
			return loadlist;
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstmt);
		}
	}

	/**
	 * Returns all of the report requests associated with a specific patient.
	 * 
	 * @param pid The MID of the patient in question.
	 * @return A java.util.List of report requests.
	 * @throws DBException
	 */
	public List<ReportRequestBean> getAllReportRequestsForPatient(final long pid) throws DBException {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			if (pid == 0L) {
				throw new SQLException("PatientMID cannot be null");
			}
			conn = factory.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM reportrequests WHERE PatientMID = ?");
			pstmt.setLong(1, pid);
			final ResultSet results = pstmt.executeQuery();
			final List<ReportRequestBean> loadlist = loader.loadList(results);
			results.close();
			return loadlist;
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstmt);
		}
	}
	
	/**
	 * Adds a request for a report.
	 * 
	 * @param requesterMID The MID of the requester.
	 * @param patientMID The MID of the patient in question.
	 * @param date The date the request was made.
	 * @return A long of the unique ID of the report request.
	 * @throws DBException
	 */
	public long addReportRequest(final long requesterMID, final long patientMID, final Date date) throws DBException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			if (requesterMID == 0L) {
				throw new SQLException("RequesterMID cannot be null");
			}
			conn = factory.getConnection();
			pstmt = conn.prepareStatement("INSERT INTO reportrequests (ID, RequesterMID, PatientMID, RequestedDate, Status) VALUES (null,?,?,?,'Requested')");
			pstmt.setLong(1, requesterMID);
			pstmt.setLong(2, patientMID);
			pstmt.setTimestamp(3, new java.sql.Timestamp(date.getTime()));
			pstmt.executeUpdate();
			return DBUtil.getLastInsert(conn);
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstmt);
		}
	}
	
	/**
	 * Sets the status of a report request to 'Viewed'
	 * 
	 * @param ID The unique ID of the request in question.
	 * @param date The date the request was viewed.
	 * @throws DBException
	 */
	public void setViewed(final long ID, final Date date) throws DBException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			if (ID == 0L) {
				throw new SQLException("ID cannot be null");
			}
			conn = factory.getConnection();
			pstmt = conn.prepareStatement("UPDATE reportrequests set ViewedDate = ?, Status = 'Viewed' where ID = ?");
			pstmt.setTimestamp(1, new java.sql.Timestamp(date.getTime()));
			pstmt.setLong(2, ID);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstmt);
		}
		
	}

}
