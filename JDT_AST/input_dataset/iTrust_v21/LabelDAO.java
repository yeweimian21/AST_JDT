package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.beans.LabelBean;
import edu.ncsu.csc.itrust.beans.loaders.LabelLoader;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;

public class LabelDAO {
	private transient final DAOFactory factory;
	private transient final LabelLoader labelLoader;
	
	/**
	 * Basic constructor
	 * @param factory the factory to use for getting connections
	 */
	public LabelDAO(final DAOFactory factory) {
		this.factory = factory;
		labelLoader = new LabelLoader();
	}
	
	/**
	 * Returns a single label
	 * @param entryID ID of the label in the database
	 * @return a LabelBean if there exist any labels
	 * @throws DBException
	 */
	public LabelBean getLabel(long entryID) throws DBException {
		Connection conn = null;
		PreparedStatement pstring = null;	
		try {
			conn = factory.getConnection();
			pstring = conn.prepareStatement("SELECT * FROM labels WHERE EntryID = ?");
			pstring.setLong(1, entryID);
			final ResultSet results = pstring.executeQuery();
			if (results.next()) {
				LabelBean bean = labelLoader.loadSingle(results);
				results.close();
				pstring.close();
				return bean;
			} else{
				results.close();
				pstring.close();
				return null;
			}
		} catch(SQLException e){
			
			throw new DBException(e);
		} finally{
			DBUtil.closeConnection(conn, pstring);
		}
	}
	
	/**
	 * Returns all of the entries in the Labels table that contain
	 * the ID of the patient.
	 * @param patientMID which patient to select labels for
	 * @return the list of all of that patient's labels
	 * @throws DBException
	 */
	public List<LabelBean> getLabels(long patientMID) throws DBException {
		Connection conn = null;
		PreparedStatement pstring = null;	
		try {
			conn = factory.getConnection();
			pstring = conn.prepareStatement("SELECT * FROM labels WHERE "
					+ "PatientID = ? ORDER BY LabelName ASC");
			pstring.setLong(1, patientMID);
			final ResultSet results = pstring.executeQuery();
			final List<LabelBean> labelList = this.labelLoader.loadList(results);
			results.close();
			pstring.close();
			return labelList;
		} catch(SQLException e){
			
			throw new DBException(e);
		} finally{
			DBUtil.closeConnection(conn, pstring);
		}
	}
	
	
	/**
	 * Adds a label 
	 * @param label the label to add
	 * @throws DBException
	 */
	public void addLabel(LabelBean label) 
			throws DBException, ITrustException {
		
		Connection conn = null;
		PreparedStatement pstring = null;
		try {
			conn = factory.getConnection();
			pstring = conn.prepareStatement("INSERT INTO labels(EntryID,"
					+ " PatientID, LabelName, LabelColor) "
					+ "VALUES (?,?,?,?)");
			pstring = labelLoader.loadParameters(pstring, label);
			pstring.executeUpdate();
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstring);
		}
	}
	
	/**
	 * Updates a particular label with the new data. Neither the entryid
	 * nor the patientid will ever change, so there is no reason to include them
	 * as possiblities to change. It includes the patientMID in an attempt to
	 * ensure that patients can only udpate their own previous food entries.
	 * 
	 * @param entryID
	 *            the label to update
	 * @param patientMID
	 *            who the patient is making this update
	 * @param label
	 *            the edited form of the label
	 * @return the number of rows updated (should never be more than 1)
	 * @throws DBException
	 */
	public int editLabel(long entryID, long patientMID,
			LabelBean label) throws DBException {

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = factory.getConnection();
			pstmt = conn.prepareStatement("UPDATE labels "
					+ "SET LabelName = ?, LabelColor = ? " 
					+ "WHERE EntryID = ? AND PatientID = ?");
			pstmt.setString(1, label.getLabelName());
			pstmt.setString(2, label.getLabelColor());
			pstmt.setLong(3, entryID);
			pstmt.setLong(4, patientMID);
			final int numUpdated = pstmt.executeUpdate();
			return numUpdated;
		} catch (SQLException d) {
			throw new DBException(d);
		} finally {
			DBUtil.closeConnection(conn, pstmt);
		}

	}
	
	/**
	 * Deletes the label from the database that has the same unique id
	 * as the one passed in. Returns the count of the number of rows affected.
	 * The number of rows affected should never be more than 1. The patientMID
	 * is included to try to ensure that users cannot delete food diary 
	 * entries that belong to users other than themselves.
	 * @param entryID which food entry to delete
	 * @param patientMID the owner of this food entry
	 * @return how many entries were deleted from the food diary
	 * @throws DBException
	 */
	public int deleteLabel(long entryID, long patientMID) 
			throws DBException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = factory.getConnection();
			pstmt = conn.prepareStatement("DELETE FROM labels "
					+ "WHERE EntryID = ? AND PatientID = ?");
			pstmt.setLong(1, entryID);
			pstmt.setLong(2, patientMID);
			final int numDeleted = pstmt.executeUpdate();
			return numDeleted;
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstmt);
		}
	}
}
