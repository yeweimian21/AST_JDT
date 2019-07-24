package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;

/**
 * Adds pre-existing conditions to a database and returns a list of conditions by MID
 * 
 */
public class PreExistingConditionsDAO {
	/**
	 * The max length of the field 
	 */
	private static final int CONDITION_MAX_STR_LEN = 60;
	private transient final DAOFactory factory;
	
	/**
	 * Validates that a string is plaintext only 
	 * Matches regex [a-zA-Z0-9\-_ ]+ for up to the max string length
	 * @param input The input string to validate
	 * @return Whether the string is valid or not
	 */
	public static boolean validateStr(String input) {
		if (input == null)
			return false;
		return input.matches("[a-zA-Z0-9\\-_, ]{1," + CONDITION_MAX_STR_LEN + "}");
	}
	
	/**
	 * Creates a new DAO interface to the pre-existing conditions table
	 * @param factory The DAO factory to connect to 
	 */
	public PreExistingConditionsDAO(DAOFactory factory) {
		this.factory = factory;
	}
	
	/**
	 * Gets all pre-existing conditions by MID
	 * @return a list of pre-existing conditions for a patient
	 * @throws DBException 
	 */
	public List<String> getConditionsByMID(long mid) throws DBException {
		List<String> conditions = new LinkedList<String>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = factory.getConnection();
			pstmt = conn.prepareStatement("SELECT `condition` FROM obstetricsconditions WHERE MID=?");
			pstmt.setLong(1, mid);
			ResultSet results;
			
			results = pstmt.executeQuery();
			while (results.next()) {
				conditions.add(results.getString("condition")); 
			}		
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstmt);
		}
		return conditions;
	}
	
	/**
	 * Tries to add a condition to the table of existing conditions
	 * @param mid The patient ID to add the condition for
	 * @param condition The string of the condition to add
	 * @return A message of whether it was successfully added or not
	 */
	public String putConditionByMID(long mid, String condition) {
		if (condition == null || condition.length() < 1)
			return "Cannot add empty string as a pre-existing condition";
		if (condition.length() > CONDITION_MAX_STR_LEN)
			return "Maximum length of " + CONDITION_MAX_STR_LEN + " exceeded";
		if (!validateStr(condition))
			return "Illegal condition. Only alphanumerics - , and _ are allowed.";
		try {
			Connection conn = factory.getConnection();
			Statement stmt = conn.createStatement();
			String request = "INSERT INTO `obstetricsconditions` (`mid`, `condition`) VALUES ('"
					+ mid + "', '" + condition + "');";
			
			stmt.execute(request);
			conn.close();
			return "Pre-existing condition added OK";
		} catch (SQLException e) {
			return "Database error while inserting";
		}
		
	}
}
