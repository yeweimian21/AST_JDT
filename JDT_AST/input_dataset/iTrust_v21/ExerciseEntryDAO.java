package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.beans.ExerciseEntryBean;
import edu.ncsu.csc.itrust.beans.loaders.ExerciseEntryLoader;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;

/**
 * ExerciseEntryDAO.java Version 1 4/2/2015 Copyright notice: none
 * 
 * Responsible for loading the entries in a patient's exercise diary, the totals
 * a patient has performed, and adding a new entry to a patient's exercise diary
 */
public class ExerciseEntryDAO {

	private transient final DAOFactory factory;
	private transient final ExerciseEntryLoader exerciseLoader;

	/**
	 * Basic constructor
	 * 
	 * @param factory
	 *            the factory to use for getting connections
	 */
	public ExerciseEntryDAO(final DAOFactory factory) {
		this.factory = factory;
		exerciseLoader = new ExerciseEntryLoader();
	}

	/**
	 * Returns all of the entries in the Exercise Entry table that contain the
	 * ID of the patient.
	 * 
	 * @param patientMID
	 *            which patient to select Exercise Diary for
	 * @return the list of all of that patient's exercise entries
	 * @throws DBException
	 */
	public List<ExerciseEntryBean> getPatientExerciseDiary(long patientMID)
			throws DBException {
		Connection conn = null;
		PreparedStatement pstring = null;
		try {
			conn = factory.getConnection();
			pstring = conn
					.prepareStatement("SELECT * FROM exerciseEntry WHERE "
							+ "PatientID = ? ORDER BY Date DESC");
			pstring.setLong(1, patientMID);
			final ResultSet results = pstring.executeQuery();
			final List<ExerciseEntryBean> diaryList = this.exerciseLoader
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
	 * Returns the total hours and calories burned of each day in the exercise
	 * diary. The total just sums up the values for each individual exercise
	 * entry.
	 * 
	 * @param patientMID
	 *            patient whose exercise diary we want
	 * @return a list in descending order of date (dates closest to today first)
	 *         of the totals performed on each day for a patient
	 * @throws DBException
	 */
	public List<ExerciseEntryBean> getPatientExerciseDiaryTotals(long patientMID)
			throws DBException {
		Connection conn = null;
		PreparedStatement pstring = null;
		try {
			conn = factory.getConnection();
			pstring = conn.prepareStatement(" SELECT EntryID AS EntryID, Date AS Date, ExerciseType AS ExerciseType, Name AS Name, "
					+ " SUM(Hours) AS Hours, " + " SUM(Calories) AS Calories, "
					+ " Sets AS Sets, Reps AS Reps, PatientID AS PatientID, LabelID AS LabelID "
					+ " FROM exerciseEntry "
					+ " WHERE PatientID = ? "
					+ " GROUP BY Date "
					+ " ORDER BY Date DESC ");
			pstring.setLong(1, patientMID);

			final ResultSet results = pstring.executeQuery();
			final List<ExerciseEntryBean> diaryTotals = this.exerciseLoader
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
	 * Adds a exercise entry to a patient's exercise diary
	 * 
	 * @param exerciseEntry
	 *            the entry to add
	 * @throws DBException
	 */
	public void addExerciseEntry(ExerciseEntryBean exerciseEntry)
			throws DBException, ITrustException {

		Connection conn = null;
		PreparedStatement pstring = null;
		final long nextID = getNextEntryID();
		exerciseEntry.setEntryID(nextID);
		try {
			conn = factory.getConnection();
			pstring = conn
					.prepareStatement("INSERT INTO exerciseEntry(EntryID, Date, ExerciseType, "
							+ " Name, Hours, Calories, Sets, Reps, PatientID) "
							+ " VALUES (?,?,?,?,?,?,?,?,?)");
			pstring = exerciseLoader.loadParameters(pstring, exerciseEntry);
			pstring.executeUpdate();
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstring);
		}
	}

	/**
	 * Queries the db to find the highest exercise entry id currently in use and
	 * then returns the next number so it can be used for a new exercise entry.
	 * 
	 * @return the next id available for a exercise entry
	 * @throws DBException
	 * @throws ITrustException
	 */
	public long getNextEntryID() throws DBException, ITrustException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		long nextID = 1;

		try {
			conn = factory.getConnection();

			pstmt = conn.prepareStatement("SELECT MAX(exerciseEntry.EntryID) "
					+ "FROM exerciseEntry");
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
	 * Deletes the exercise entry from the exercise diary that has the same
	 * unique id as the one passed in. Returns the count of the number of rows
	 * affected. The number of rows affected should never be more than 1. The
	 * patientMID is included to try to ensure that users cannot delete exercise
	 * diary entries that belong to users other than themselves.
	 * 
	 * @param entryID
	 *            which exercise entry to delete
	 * @param patientMID
	 *            the owner of this exercise entry
	 * @return how many entries were deleted from the exercise diary
	 * @throws DBException
	 */
	public int deleteExerciseEntry(long entryID, long patientMID)
			throws DBException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = factory.getConnection();
			pstmt = conn.prepareStatement("DELETE FROM exerciseEntry "
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
	 * Updates a particular exercise entry with the new data. Neither the
	 * entryid nor the patientid will ever change, so there is no reason to
	 * include them as possibilities to change. It includes the patientMID in an
	 * attempt to ensure that patients can only update their own previous
	 * exercise entries.
	 * 
	 * @param entryID
	 *            the exercise entry to update
	 * @param patientMID
	 *            who the patient is making this update
	 * @param exerciseEntry
	 *            the edited form of the exercise entry
	 * @return the number of rows updated (should never be more than 1)
	 * @throws DBException
	 */
	public int updateExerciseEntry(long entryID, long patientMID,
			ExerciseEntryBean exerciseEntry) throws DBException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = factory.getConnection();

			pstmt = conn
					.prepareStatement("UPDATE exerciseEntry "
							+ " SET Date = ?, ExerciseType = ?, Name = ?, "
							+ " Hours = ?, Calories = ?, Sets = ?, Reps = ?, LabelID = ? "
							+ " WHERE EntryID = ? AND PatientID = ? ");

			pstmt.setDate(1, new java.sql.Date(exerciseEntry.getDate()
					.getTime()));
			pstmt.setString(2, exerciseEntry.getExerciseType().getName());
			pstmt.setString(3, exerciseEntry.getStrName());
			pstmt.setDouble(4, exerciseEntry.getHoursWorked());
			pstmt.setDouble(5, exerciseEntry.getCaloriesBurned());
			pstmt.setDouble(6, exerciseEntry.getNumSets());
			pstmt.setDouble(7, exerciseEntry.getNumReps());
			pstmt.setLong(8, exerciseEntry.getLabelID());
			pstmt.setLong(9, entryID);
			pstmt.setLong(10, patientMID);

			final int numUpdated = pstmt.executeUpdate();
			return numUpdated;
		} catch (SQLException d) {
			throw new DBException(d);
		} finally {
			DBUtil.closeConnection(conn, pstmt);
		}

	}

	/**
	 * Returns the entries in the Exercise Entry table that are within a
	 * specified date range.
	 * 
	 * @param lower
	 *            the lower date
	 * @param upper
	 *            the lower date
	 * @param patientMID
	 *            which patient to select Exercise Diary for
	 * 
	 * @return the list of exercise entries between the two dates
	 * @throws DBException
	 */
	public List<ExerciseEntryBean> getBoundedExerciseDiary(Date lower,
			Date upper, long patientMID) throws DBException {
		Connection conn = null;
		PreparedStatement pstring = null;
		try {
			conn = factory.getConnection();

			pstring = conn.prepareStatement("SELECT * FROM exerciseEntry "
					+ " WHERE PatientID = ? " + " AND Date BETWEEN ? AND ? "
					+ " ORDER BY Date DESC");

			pstring.setLong(1, patientMID);
			// Convert java.util.Date to java.sql.Date
			java.sql.Date lowerSQLDate = new java.sql.Date(lower.getTime());
			java.sql.Date upperSQLDate = new java.sql.Date(upper.getTime());
			pstring.setDate(2, lowerSQLDate);
			pstring.setDate(3, upperSQLDate);

			final ResultSet results = pstring.executeQuery();
			final List<ExerciseEntryBean> diaryList = this.exerciseLoader
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
	 * Returns the total hours and calories burned of each day in the exercise
	 * diary that falls between the two given dates. The total just sums up the
	 * values for each individual exercise entry.
	 * 
	 * @param lower
	 *            the lower date
	 * @param upper
	 *            the lower date
	 * @param patientMID
	 *            which patient to select Exercise Diary for
	 * 
	 * @return the list of exercise entries between the two dates
	 * @throws DBException
	 */
	public List<ExerciseEntryBean> getBoundedExerciseDiaryTotals(Date lower,
			Date upper, long patientMID) throws DBException {
		Connection conn = null;
		PreparedStatement pstring = null;
		try {
			conn = factory.getConnection();

			pstring = conn.prepareStatement(" SELECT EntryID AS EntryID, Date AS Date, ExerciseType AS ExerciseType, Name AS Name, "
					+ " SUM(Hours) AS Hours, " + " SUM(Calories) AS Calories, "
					+ " Sets AS Sets, Reps AS Reps, PatientID AS PatientID, LabelID AS LabelID  "
					+ " FROM exerciseEntry "
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
			final List<ExerciseEntryBean> diaryList = this.exerciseLoader
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
