package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.beans.FoodEntryBean;
import edu.ncsu.csc.itrust.beans.loaders.FoodEntryLoader;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;

/**
 * FoodEntryDAO.java Version 1 2/21/2015 Copyright notice: none
 * 
 * Responsible for loading the entries in a patient's food diary, the totals a
 * patient has eaten, and adding a new entry to a patient's food diary
 */
public class FoodEntryDAO {

	private transient final DAOFactory factory;
	private transient final FoodEntryLoader foodLoader;

	/**
	 * Basic constructor
	 * 
	 * @param factory
	 *            the factory to use for getting connections
	 */
	public FoodEntryDAO(final DAOFactory factory) {
		this.factory = factory;
		foodLoader = new FoodEntryLoader();
	}

	/**
	 * Returns all of the entries in the Food Entry table that contain the ID of
	 * the patient.
	 * 
	 * @param patientMID
	 *            which patient to select Food Diary for
	 * @return the list of all of that patient's food entries
	 * @throws DBException
	 */
	public List<FoodEntryBean> getPatientFoodDiary(long patientMID)
			throws DBException {
		Connection conn = null;
		PreparedStatement pstring = null;
		try {
			conn = factory.getConnection();
			pstring = conn.prepareStatement("SELECT * FROM foodEntry WHERE "
					+ "PatientID = ? ORDER BY DateEaten DESC");
			pstring.setLong(1, patientMID);
			final ResultSet results = pstring.executeQuery();
			final List<FoodEntryBean> diaryList = this.foodLoader
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
	 * Returns the totals of calories, fat, sodium, carbs, sugar, fiber, and
	 * protein of each day in the food diary. The total is calculated by
	 * multiplying the number of servings by the grams/milligrams per serving
	 * for each individual food entry.
	 * 
	 * @param patientMID
	 *            patient whose food diary we want
	 * @return a list in descending order of date (dates closest to today first)
	 *         of the totals eaten on each day for a patient
	 * @throws DBException
	 */
	public List<FoodEntryBean> getPatientFoodDiaryTotals(long patientMID)
			throws DBException {
		Connection conn = null;
		PreparedStatement pstring = null;
		try {
			conn = factory.getConnection();
			/*
			 * We don't care about the mealtype, foodname, or servings but we
			 * need to have some for the loader, so just bring back whatever.
			 */
			pstring = conn.prepareStatement("SELECT DateEaten AS DateEaten, "
					+ "EntryID AS EntryID, MealType AS MealType, "
					+ "FoodName AS FoodName, " + "Servings AS Servings, "
					+ "SUM(Servings * Calories) AS Calories, "
					+ "SUM(Servings * Fat) AS Fat, "
					+ "SUM(Servings * Sodium) AS Sodium, "
					+ "SUM(Servings * Carbs) AS Carbs, "
					+ "SUM(Servings * Sugar) AS Sugar, "
					+ "SUM(Servings * Fiber) AS Fiber, "
					+ "SUM(Servings * Protein) AS Protein, "
					+ "PatientID AS PatientID, "
					+ "LabelID AS LabelID FROM foodEntry "
					+ "WHERE PatientID = ? GROUP BY DateEaten "
					+ "ORDER BY DateEaten DESC");
			pstring.setLong(1, patientMID);
			final ResultSet results = pstring.executeQuery();
			final List<FoodEntryBean> diaryTotals = this.foodLoader
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
	 * Adds a food entry to a patient's food diary
	 * 
	 * @param foodEntry
	 *            the entry to add
	 * @throws DBException
	 */
	public void addFoodEntry(FoodEntryBean foodEntry) throws DBException,
			ITrustException {

		Connection conn = null;
		PreparedStatement pstring = null;
		final long nextID = getNextEntryID();
		foodEntry.setEntryID(nextID);
		try {
			conn = factory.getConnection();
			pstring = conn.prepareStatement("INSERT INTO foodEntry(EntryID,"
					+ " DateEaten, MealType, FoodName, Servings, Calories, "
					+ "Fat, Sodium, Carbs, Sugar, Fiber, Protein, PatientID) "
					+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)");
			pstring = foodLoader.loadParameters(pstring, foodEntry);
			pstring.executeUpdate();
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstring);
		}
	}

	/**
	 * Queries the db to find the highest food entry id currently in use and
	 * then returns the next number so it can be used for a new food entry.
	 * 
	 * @return the next id available for a food entry
	 * @throws DBException
	 * @throws ITrustException
	 */
	public long getNextEntryID() throws DBException, ITrustException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		long nextID = 1;

		try {
			conn = factory.getConnection();

			pstmt = conn.prepareStatement("SELECT MAX(foodEntry.EntryID) "
					+ "FROM foodEntry");
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
	 * Deletes the food entry from the food diary that has the same unique id as
	 * the one passed in. Returns the count of the number of rows affected. The
	 * number of rows affected should never be more than 1. The patientMID is
	 * included to try to ensure that users cannot delete food diary entries
	 * that belong ot users other than themselves.
	 * 
	 * @param entryID
	 *            which food entry to delete
	 * @param patientMID
	 *            the owner of this food entry
	 * @return how many entries were deleted from the food diary
	 * @throws DBException
	 */
	public int deleteFoodEntry(long entryID, long patientMID)
			throws DBException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = factory.getConnection();
			pstmt = conn.prepareStatement("DELETE FROM foodEntry "
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
	 * Updates a particular food entry with the new data. Neither the entryid
	 * nor the patientid will ever change, so there is no reason to include them
	 * as possiblities to change. It includes the patientMID in an attempt to
	 * ensure that patients can only udpate their own previous food entries.
	 * 
	 * @param entryID
	 *            the food entry to update
	 * @param patientMID
	 *            who the patient is making this update
	 * @param foodEntry
	 *            the edited form of the food entry
	 * @return the number of rows updated (should never be more than 1)
	 * @throws DBException
	 */
	public int updateFoodEntry(long entryID, long patientMID,
			FoodEntryBean foodEntry) throws DBException {

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = factory.getConnection();
			pstmt = conn.prepareStatement("UPDATE foodEntry "
					+ "SET DateEaten = ?, MealType = ?, "
					+ " FoodName = ?, Servings = ?, Calories = ?, "
					+ "Fat = ?, Sodium = ?, Carbs = ?, Sugar = ?, "
					+ "Fiber = ?, Protein = ?, LabelID = ? " 
					+ "WHERE EntryID = ? AND PatientID = ?");
			pstmt.setDate(1, new java.sql.Date(foodEntry.getDateEaten()
					.getTime()));

			pstmt.setString(2, foodEntry.getMealType().getName());
			pstmt.setString(3, foodEntry.getFood());
			pstmt.setDouble(4, foodEntry.getServings());
			pstmt.setDouble(5, foodEntry.getCalories());
			pstmt.setDouble(6, foodEntry.getFatGrams());
			pstmt.setDouble(7, foodEntry.getMilligramsSodium());
			pstmt.setDouble(8, foodEntry.getCarbGrams());
			pstmt.setDouble(9, foodEntry.getSugarGrams());
			pstmt.setDouble(10, foodEntry.getFiberGrams());
			pstmt.setDouble(11, foodEntry.getProteinGrams());
			pstmt.setLong(12, foodEntry.getLabelID());
			pstmt.setLong(13, entryID);
			pstmt.setLong(14, patientMID);
			
			final int numUpdated = pstmt.executeUpdate();
			return numUpdated;
		} catch (SQLException d) {
			throw new DBException(d);
		} finally {
			DBUtil.closeConnection(conn, pstmt);
		}

	}

	/**
	 * Returns the entries in the Food Entry table that are within a specified
	 * date range.
	 * 
	 * @param lower
	 *            the lower date
	 * @param upper
	 *            the lower date
	 * @param patientMID
	 *            which patient to select Food Diary for
	 * 
	 * @return the list of food entries between the two dates
	 * @throws DBException
	 */
	public List<FoodEntryBean> getBoundedFoodDiary(Date lower, Date upper,
			long patientMID) throws DBException {
		Connection conn = null;
		PreparedStatement pstring = null;
		try {
			conn = factory.getConnection();

			pstring = conn.prepareStatement("SELECT * FROM foodEntry "
					+ " WHERE PatientID = ? "
					+ " AND DateEaten BETWEEN ? AND ? "
					+ " ORDER BY DateEaten DESC");

			pstring.setLong(1, patientMID);
			// Convert java.util.Date to java.sql.Date
			java.sql.Date lowerSQLDate = new java.sql.Date(lower.getTime());
			java.sql.Date upperSQLDate = new java.sql.Date(upper.getTime());
			pstring.setDate(2, lowerSQLDate);
			pstring.setDate(3, upperSQLDate);

			final ResultSet results = pstring.executeQuery();
			final List<FoodEntryBean> diaryList = this.foodLoader
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

	public List<FoodEntryBean> getBoundedFoodDiaryTotals(Date lower,
			Date upper, long patientMID) throws DBException {
		Connection conn = null;
		PreparedStatement pstring = null;
		try {
			conn = factory.getConnection();

			pstring = conn.prepareStatement("SELECT DateEaten AS DateEaten, "
					+ " EntryID AS EntryID, MealType AS MealType, "
					+ " FoodName AS FoodName, " + "Servings AS Servings, "
					+ " SUM(Servings * Calories) AS Calories, "
					+ " SUM(Servings * Fat) AS Fat, "
					+ " SUM(Servings * Sodium) AS Sodium, "
					+ " SUM(Servings * Carbs) AS Carbs, "
					+ " SUM(Servings * Sugar) AS Sugar, "
					+ " SUM(Servings * Fiber) AS Fiber, "
					+ " SUM(Servings * Protein) AS Protein,"
					+ " LabelID AS LabelID,"
					+ " PatientID AS PatientID "
					+ " FROM foodEntry "
					+ " WHERE PatientID = ? "
					+ " AND DateEaten BETWEEN ? AND ? "
					+ " GROUP BY DateEaten " + " ORDER BY DateEaten DESC");

			// Add params
			pstring.setLong(1, patientMID);
			// Convert java.util.Date to java.sql.Date
			java.sql.Date lowerSQLDate = new java.sql.Date(lower.getTime());
			java.sql.Date upperSQLDate = new java.sql.Date(upper.getTime());
			pstring.setDate(2, lowerSQLDate);
			pstring.setDate(3, upperSQLDate);

			final ResultSet results = pstring.executeQuery();
			final List<FoodEntryBean> diaryList = this.foodLoader
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
