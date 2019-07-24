package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.beans.SleepEntryBean;
import edu.ncsu.csc.itrust.beans.loaders.SleepEntryLoader;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;

/**
 * SleepEntryDAO.java Version 1 4/6/2015 Copyright notice: none
 * 
 * Responsible for loading the entries in a patient's sleep diary, the totals
 * a patient has slept, and adding a new entry to a patient's sleep diary
 */
public class SleepEntryDAO {
	private transient final DAOFactory factory;
	private transient final SleepEntryLoader sleepLoader;

	/**
	 * Basic constructor
	 * 
	 * @param factory
	 *            the factory to use for getting connections
	 */
	public SleepEntryDAO(final DAOFactory factory) {
		this.factory = factory;
		sleepLoader = new SleepEntryLoader();
	}

	/**
	 * Returns all of the entries in the Sleep Entry table that contain the
	 * ID of the patient.
	 * 
	 * @param patientMID
	 *            which patient to select Sleep Diary for
	 * @return the list of all of that patient's sleep entries
	 * @throws DBException
	 */
	public List<SleepEntryBean> getPatientSleepDiary(long patientMID)
			throws DBException {
		Connection conn = null;
		PreparedStatement pstring = null;
		try {
			conn = factory.getConnection();
			pstring = conn
					.prepareStatement("SELECT * FROM sleepEntry WHERE "
							+ "PatientID = ? ORDER BY Date DESC");
			pstring.setLong(1, patientMID);
			final ResultSet results = pstring.executeQuery();
			final List<SleepEntryBean> diaryList = this.sleepLoader
					.loadList(results);
			results.close();
			pstring.close();
			return diaryList;
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstring);
		}
	}

	/**
	 * Returns the total hours slept for each day in the sleep
	 * diary. The total just sums up the values for each individual sleep
	 * entry.
	 * 
	 * @param patientMID
	 *            patient whose sleep diary we want
	 * @return a list in descending order of date (dates closest to today first)
	 *         of the totals slept on each day for a patient
	 * @throws DBException
	 */
	public List<SleepEntryBean> getPatientSleepDiaryTotals(long patientMID)
			throws DBException {
		Connection conn = null;
		PreparedStatement pstring = null;
		try {
			conn = factory.getConnection();
			pstring = conn.prepareStatement(" SELECT EntryID AS EntryID, Date AS Date, SleepType AS SleepType, "
					+ " SUM(Hours) AS Hours, PatientID AS PatientID, LabelID AS LabelID "
					+ " FROM sleepEntry "
					+ " WHERE PatientID = ? "
					+ " GROUP BY Date "
					+ " ORDER BY Date DESC ");
			pstring.setLong(1, patientMID);

			final ResultSet results = pstring.executeQuery();
			final List<SleepEntryBean> diaryTotals = this.sleepLoader
					.loadList(results);
					
			results.close();
			pstring.close();
			return diaryTotals;
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstring);
		}
	}

	/**
	 * Adds a sleep entry to a patient's sleep diary
	 * 
	 * @param sleepEntry
	 *            the entry to add
	 * @throws DBException
	 */
	public void addSleepEntry(SleepEntryBean sleepEntry)
			throws DBException, ITrustException {

		Connection conn = null;
		PreparedStatement pstring = null;
		final long nextID = getNextEntryID();
		sleepEntry.setEntryID(nextID);
		try {
			conn = factory.getConnection();
			pstring = conn
					.prepareStatement("INSERT INTO sleepEntry(EntryID, Date, SleepType, "
							+ " Hours, PatientID) "
							+ " VALUES (?,?,?,?,?)");
			pstring = sleepLoader.loadParameters(pstring, sleepEntry);
			pstring.executeUpdate();
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstring);
		}
	}

	/**
	 * Queries the db to find the highest sleep entry id currently in use and
	 * then returns the next number so it can be used for a new sleep entry.
	 * 
	 * @return the next id available for a sleep entry
	 * @throws DBException
	 * @throws ITrustException
	 */
	public long getNextEntryID() throws DBException, ITrustException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		long nextID = 1;

		try {
			conn = factory.getConnection();

			pstmt = conn.prepareStatement("SELECT MAX(sleepEntry.EntryID) "
					+ "FROM sleepEntry");
			final ResultSet results = pstmt.executeQuery();
			if (results.next()) {
				nextID = results.getLong(1) + 1;
			}
			results.close();
			pstmt.close();
			return nextID;
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstmt);
		}
	}

	/**
	 * Deletes the sleep entry from the sleep diary that has the same
	 * unique id as the one passed in. Returns the count of the number of rows
	 * affected. The number of rows affected should never be more than 1. The
	 * patientMID is included to try to ensure that users cannot delete sleep
	 * diary entries that belong to users other than themselves.
	 * 
	 * @param entryID
	 *            which sleep entry to delete
	 * @param patientMID
	 *            the owner of this sleep entry
	 * @return how many entries were deleted from the sleep diary
	 * @throws DBException
	 */
	public int deleteSleepEntry(long entryID, long patientMID)
			throws DBException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = factory.getConnection();
			pstmt = conn.prepareStatement("DELETE FROM sleepEntry "
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

	/**
	 * Updates a particular sleep entry with the new data. Neither the
	 * entryid nor the patientid will ever change, so there is no reason to
	 * include them as possibilities to change. It includes the patientMID in an
	 * attempt to ensure that patients can only update their own previous
	 * sleep entries.
	 * 
	 * @param entryID
	 *            the sleep entry to update
	 * @param patientMID
	 *            who the patient is making this update
	 * @param sleepEntry
	 *            the edited form of the sleep entry
	 * @return the number of rows updated (should never be more than 1)
	 * @throws DBException
	 */
	public int updateSleepEntry(long entryID, long patientMID,
			SleepEntryBean sleepEntry) throws DBException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = factory.getConnection();

			pstmt = conn
					.prepareStatement("UPDATE sleepEntry "
							+ " SET Date = ?, SleepType = ?, Hours = ?, LabelID = ? "
							+ " WHERE EntryID = ? AND PatientID = ? ");

			pstmt.setDate(1, new java.sql.Date(sleepEntry.getDate()
					.getTime()));
			pstmt.setString(2, sleepEntry.getSleepType().getName());
			pstmt.setDouble(3, sleepEntry.getHoursSlept());
			pstmt.setLong(4, sleepEntry.getLabelID());
			pstmt.setLong(5, entryID);
			pstmt.setLong(6, patientMID);

			final int numUpdated = pstmt.executeUpdate();
			return numUpdated;
		} catch (SQLException d) {
			throw new DBException(d);
		} finally {
			DBUtil.closeConnection(conn, pstmt);
		}

	}

	/**
	 * Returns the entries in the Sleep Entry table that are within a
	 * specified date range.
	 * 
	 * @param lower
	 *            the lower date
	 * @param upper
	 *            the lower date
	 * @param patientMID
	 *            which patient to select Sleep Diary for
	 * 
	 * @return the list of sleep entries between the two dates
	 * @throws DBException
	 */
	public List<SleepEntryBean> getBoundedSleepDiary(Date lower,
			Date upper, long patientMID) throws DBException {
		Connection conn = null;
		PreparedStatement pstring = null;
		try {
			conn = factory.getConnection();

			pstring = conn.prepareStatement("SELECT * FROM sleepEntry "
					+ " WHERE PatientID = ? " + " AND Date BETWEEN ? AND ? "
					+ " ORDER BY Date DESC");

			pstring.setLong(1, patientMID);
			// Convert java.util.Date to java.sql.Date
			java.sql.Date lowerSQLDate = new java.sql.Date(lower.getTime());
			java.sql.Date upperSQLDate = new java.sql.Date(upper.getTime());
			pstring.setDate(2, lowerSQLDate);
			pstring.setDate(3, upperSQLDate);

			final ResultSet results = pstring.executeQuery();
			final List<SleepEntryBean> diaryList = this.sleepLoader
					.loadList(results);
			results.close();
			pstring.close();

			return diaryList;
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstring);
		}
	}

	/**
	 * Returns the total hours and calories burned of each day in the sleep
	 * diary that falls between the two given dates. The total just sums up the
	 * values for each individual sleep entry.
	 * 
	 * @param lower
	 *            the lower date
	 * @param upper
	 *            the lower date
	 * @param patientMID
	 *            which patient to select Sleep Diary for
	 * 
	 * @return the list of sleep entries between the two dates
	 * @throws DBException
	 */
	public List<SleepEntryBean> getBoundedSleepDiaryTotals(Date lower,
			Date upper, long patientMID) throws DBException {
		Connection conn = null;
		PreparedStatement pstring = null;
		try {
			conn = factory.getConnection();

			pstring = conn.prepareStatement(" SELECT EntryID AS EntryID, Date AS Date, SleepType AS SleepType, "
					+ " SUM(Hours) AS Hours, PatientID AS PatientID, LabelID AS LabelID  "
					+ " FROM sleepEntry "
					+ " WHERE PatientID = ? " + " AND Date BETWEEN ? AND ? "
					+ " GROUP BY Date " + " ORDER BY Date DESC");

			// Add params
			pstring.setLong(1, patientMID);
			// Convert java.util.Date to java.sql.Date
			java.sql.Date lowerSQLDate = new java.sql.Date(lower.getTime());
			java.sql.Date upperSQLDate = new java.sql.Date(upper.getTime());
			pstring.setDate(2, lowerSQLDate);
			pstring.setDate(3, upperSQLDate);

			final ResultSet results = pstring.executeQuery();
			final List<SleepEntryBean> diaryList = this.sleepLoader
					.loadList(results);
			results.close();
			pstring.close();

			return diaryList;
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstring);
		}
	}
}
