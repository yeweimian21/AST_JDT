package edu.ncsu.csc.itrust.dao.mysql;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.tomcat.util.buf.HexUtils;

import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.enums.Role;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;

/**
 * AuthDAO is for anything that has to do with authentication. 
 * Most methods access the users table.
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
public class AuthDAO {
	
	/**LOGIN_TIMEOUT*/
	public static final long LOGIN_TIMEOUT = 15 * 60 * 1000; // 15 min
	private static final int SALT_LEN = 32;
	private transient final DAOFactory factory;
	private SecureRandom shaker;

	/**
	 * The typical constructor.
	 * @param factory The {@link DAOFactory} associated with this DAO, 
	 * which is used for obtaining SQL connections, etc.
	 */
	public AuthDAO(final DAOFactory factory) {
		this.factory = factory;
		this.shaker = new SecureRandom();
	}

	/**
	 * Add a particular user to the system. 
	 * Does not add user-specific information (e.g. Patient or HCP).
	 * Initially sets security question to a random set of characters, 
	 * so that nobody should be able to guess its value.
	 * 
	 * @param mid The user's MID as a Long.
	 * @param role The role of the user as a Role enum {@link Role}
	 * @param password The password for the new user.
	 * @return A string representing the newly added randomly-generated password. 
	 * @throws DBException
	 */
	public String addUser(final Long mid, final Role role, final String password) throws DBException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = factory.getConnection();
			pstmt = conn
					.prepareStatement("INSERT INTO users (MID, PASSWORD, SALT, ROLE, sQuestion, sAnswer) VALUES (?,?,?,?,?,?)");
			pstmt.setLong(1, mid);
			String salt = shakeSalt();
			final String hashedPassword = DigestUtils.sha256Hex(password + salt);
			
			pstmt.setString(2, hashedPassword);
			pstmt.setString(3, salt);
			pstmt.setString(4, role.toString());
			pstmt.setString(5, "Enter the random password given in your account email");
			pstmt.setString(6, password);
			pstmt.executeUpdate();
			pstmt.close();
			return password;
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstmt);
		}
	}
	
	/**
	 * Reset the security question and answer for a particular user
	 * 
	 * @param question The security question as a string.
	 * @param answer The security answer as a string.
	 * @param mid The MID of the user as a long.
	 * @throws DBException
	 */
	public void setSecurityQuestionAnswer(final String question, final String answer, final long mid) throws DBException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = factory.getConnection();
			pstmt = conn.prepareStatement("UPDATE users SET sQuestion = ?, sAnswer = ? WHERE MID = ?");
			pstmt.setString(1, question);
			pstmt.setString(2, answer);
			pstmt.setLong(3, mid);
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstmt);
		}
	}

	/**
	 * Returns the user name of a user from just the MID
	 * 
	 * @param mid The MID of the user to get the name of.
	 * @return The user's name as a String.
	 * @throws ITrustException
	 */
	public String getUserName(final long mid) throws ITrustException {
		final Role role = getUserRole(mid);
		String uName;
		switch (role) {
			case HCP:
			case PHA:
			case ADMIN:
			case UAP:
			case ER:
			case LT:
				uName = factory.getPersonnelDAO().getName(mid);
				break;
			case PATIENT:
				uName = factory.getPatientDAO().getName(mid);
				break;
			case TESTER:
				uName = String.valueOf(mid);
				break;
			default:
				throw new ITrustException("Role " + role + " not supported");
		}
		return uName;
	}

	/**
	 * Returns the role of a particular MID
	 * 
	 * @param mid The MID of the user to look up.
	 * @return The {@link Role} of the user as an enum.
	 * @throws ITrustException
	 */
	public Role getUserRole(final long mid) throws ITrustException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = factory.getConnection();
			pstmt = conn.prepareStatement("SELECT role FROM users WHERE MID=?");
			pstmt.setLong(1, mid);
			final ResultSet results;
			results = pstmt.executeQuery();
			if (results.next()) {
				final Role result = Role.parse(results.getString("role"));
				results.close();
				pstmt.close();
				return result;
			} else {
				results.close();
				pstmt.close();
				throw new ITrustException("User does not exist");
			}
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstmt);
		}
	}
	
	/**
	 * Returns whether a user is deactivated.
	 * Currently works only for patients
	 * 
	 * @param mid The MID of the user to look up.
	 * @return Activation status of the user
	 * @throws ITrustException
	 */
	public boolean getDeactivated(final long mid) throws ITrustException{
		final Role role = getUserRole(mid);
		boolean isDeactivated;
		if(role.equals(Role.PATIENT)) {
			Connection conn = null;
			PreparedStatement pstmt = null;
			try {
				conn = factory.getConnection();
				pstmt = conn.prepareStatement("SELECT DateOfDeactivation FROM patients WHERE MID=?");
				pstmt.setLong(1, mid);
				ResultSet results;
				results = pstmt.executeQuery();
				if (results.next()) {
					if(results.getString("DateOfDeactivation")==null){
						results.close();
						pstmt.close();
						isDeactivated = false;
					}else{
						results.close();
						pstmt.close();
						isDeactivated = true;
					}
				} else {
					results.close();
					pstmt.close();
					throw new ITrustException("User does not exist");
				}
			} catch (SQLException e) {
				
				throw new DBException(e);
			} finally {
				DBUtil.closeConnection(conn, pstmt);
			}
		} else {
			isDeactivated = false;
		}
		return isDeactivated;
	}
	

	/**
	 * Change the password of a particular user
	 * 
	 * @param mid The MID of the user whose password we are changing.
	 * @param password The new password.
	 * @throws DBException
	 */
	public void resetPassword(final long mid, final String password) throws DBException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = factory.getConnection();
			pstmt = conn.prepareStatement("UPDATE users SET password=?, salt=? WHERE MID=?");
			String salt = shakeSalt();
			String newPassword = DigestUtils.sha256Hex(password + salt);
			pstmt.setString(1, newPassword);
			pstmt.setString(2, salt);
			pstmt.setLong(3, mid);
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstmt);
		}
	}

	/**
	 * Return the security question for a particular user.
	 * 
	 * @param mid The MID of the user we are looking up.
	 * @return The security question of the user we are looking up.
	 * @throws ITrustException
	 */
	public String getSecurityQuestion(final long mid) throws ITrustException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = factory.getConnection();
			pstmt = conn.prepareStatement("SELECT sQuestion FROM users WHERE MID=?");
			pstmt.setLong(1, mid);
			final ResultSet results = pstmt.executeQuery();
			if (results.next()){
				final String result = results.getString("sQuestion"); 
				results.close();
				pstmt.close();
				return result;
			}
			else{
				results.close();
				pstmt.close();
				throw new ITrustException("No security question set for MID: " + mid);
			}
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstmt);
		}
	}

	/**
	 * Return the security answer of a particular user
	 * 
	 * @param mid The MID of the user we are looking up.
	 * @return The security answer as a String.
	 * @throws ITrustException
	 */
	public String getSecurityAnswer(final long mid) throws ITrustException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = factory.getConnection();
			pstmt = conn.prepareStatement("SELECT sAnswer FROM users WHERE MID=?");
			pstmt.setLong(1, mid);
			final ResultSet results = pstmt.executeQuery();
			if (results.next()){
				final String result =  results.getString("sAnswer");
				results.close();
				pstmt.close();
				return result;
			}
			else{
				results.close();
				pstmt.close();
				throw new ITrustException("No security answer set for MID " + mid);
			}
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstmt);
		}
	}

	/**
	 * Record a login failure, which blacklists the ipAddress. 
	 * Uses the database table like a hash table where
	 * the key is the user's IP address. 
	 * If the user's IP address is not in the table, a row with "1" is
	 * added.
	 * 
	 * @param ipAddr The IP address of the user as a String.
	 * @throws DBException
	 */
	public void recordLoginFailure(final String ipAddr) throws DBException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = factory.getConnection();
			pstmt = conn
					.prepareStatement("UPDATE loginfailures SET FailureCount=FailureCount+1, lastFailure=CURRENT_TIMESTAMP WHERE IPAddress=?");
			pstmt.setString(1, ipAddr);
			final int numUpdated = pstmt.executeUpdate();
			if (numUpdated == 0) { // if there wasn't an empty row to begin with
				insertLoginFailureRow(ipAddr, 1);	// now they have a row AND a strike against
			}										// 'em
			pstmt.close();
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstmt);
		}
	}

	/**
	 * Record a reset password failure, which blacklists the ipAddress. 
	 * Uses the database table like a hash
	 * table where the key is the user's IP address. 
	 * If the user's IP address is not in the table, 
	 * a row with "1" is added.
	 * 
	 * @param ipAddr The IP address of the user as a String.
	 * @throws DBException
	 */
	public void recordResetPasswordFailure(final String ipAddr) throws DBException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = factory.getConnection();
			pstmt = conn
					.prepareStatement("UPDATE resetpasswordfailures SET failurecount=failurecount+1 WHERE ipaddress=?");
			pstmt.setString(1, ipAddr);
			final int numUpdated = pstmt.executeUpdate();
			if (numUpdated == 0) { // if there wasn't an empty row to begin with
				insertResetPasswordRow(ipAddr, 1); // now they have a row AND a strike against
			}
			pstmt.close();
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstmt);
		}
	}

	/**
	 * Return the number of failures from resetting a password, given an IP address.
	 * 
	 * @param ipAddr An IP address for the associated attempt as a String.
	 * @return An int representing the number of failures.
	 * @throws DBException
	 */
	public int getResetPasswordFailures(final String ipAddr) throws DBException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int numFailures;
		try {
			conn = factory.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM resetpasswordfailures WHERE IPADDRESS=?");
			pstmt.setString(1, ipAddr);
			final ResultSet results = pstmt.executeQuery();
			if (results.next()) {
				// if we're more than X minutes out, clear the failure count
				if (System.currentTimeMillis() - results.getTimestamp("lastFailure").getTime() > LOGIN_TIMEOUT) {
					updateResetFailuresToZero(ipAddr);
					results.close();
					pstmt.close();
					numFailures = 0;
				} else {
					final int result = results.getInt("failureCount");
					results.close();
					pstmt.close();
					numFailures = result;
				}
			} else {
				insertResetPasswordRow(ipAddr, 0 );
				results.close();
				pstmt.close();
				numFailures = 0;
			}
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstmt);
		}
		return numFailures;
	}

	/**
	 * Return the number of failures from login failures a password, 
	 * given an IP address.
	 * 
	 * @param ipAddr The IP address for this attempt as a String.
	 * @return An int representing the number of failures which have occured.
	 * @throws DBException
	 */
	public int getLoginFailures(final String ipAddr) throws DBException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int numFailures;
		try {
			conn = factory.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM loginfailures WHERE IPADDRESS=?");
			pstmt.setString(1, ipAddr);
			final ResultSet results = pstmt.executeQuery();
			if (results.next()) {
				// if we're more than X minutes out, clear the failure count
				if (System.currentTimeMillis() - results.getTimestamp("lastFailure").getTime() > LOGIN_TIMEOUT) {
					updateFailuresToZero(ipAddr);
					results.close();
					numFailures = 0;
				} else {
					final int result = results.getInt("failureCount");
					results.close();
					pstmt.close();
					numFailures = result;
				}
			} else {
				insertLoginFailureRow(ipAddr, 0);
				results.close();
				pstmt.close();
				numFailures = 0;
			}
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstmt);
		}
		return numFailures;
	}

	private void insertLoginFailureRow(final String ipAddr, int failureCount) throws DBException, SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try { 
			conn = factory.getConnection(); pstmt = conn.prepareStatement("INSERT INTO loginfailures(IPAddress, failureCount) VALUES(?,?)");
			pstmt.setString(1, ipAddr);
			pstmt.setInt(2, failureCount);
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstmt);
		}
	}

	private void insertResetPasswordRow(final String ipAddr, final int failureCount) throws DBException, SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try { 
			conn = factory.getConnection();
			pstmt = conn.prepareStatement("INSERT INTO resetpasswordfailures(IPAddress, failureCount) VALUES(?,?)");
			pstmt.setString(1, ipAddr);
			pstmt.setInt(2, failureCount);
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstmt);
		}
	}

	private void updateFailuresToZero(final String ipAddr) throws DBException, SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = factory.getConnection();
			pstmt = conn.prepareStatement("UPDATE loginfailures SET failureCount=0 WHERE IPAddress=?");
			pstmt.setString(1, ipAddr);
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstmt);
		}
	}
	
	/**
	 * resetLoginFailuresToZero
	 * @param ipAddr irAddr
	 * @throws DBException
	 * @throws SQLException
	 */
	public void resetLoginFailuresToZero(final String ipAddr) throws DBException, SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try{
			conn = factory.getConnection();
			pstmt = conn
					.prepareStatement("UPDATE loginfailures SET failureCount=0 WHERE IPAddress=?");
			pstmt.setString(1, ipAddr);
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstmt);
		}
	}
	
	
	private void updateResetFailuresToZero(final String ipAddr) throws DBException, SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try{
			conn = factory.getConnection(); 
			pstmt = conn.prepareStatement("UPDATE resetpasswordfailures SET failureCount=0 WHERE IPAddress=?");
			pstmt.setString(1, ipAddr);
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstmt);
		}
	}

	
	/**
	 * Check that a user actually exists.
	 * 
	 * @param mid mid
	 * @return check
	 * @throws DBException
	 */
	public boolean checkUserExists(final long mid) throws DBException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = factory.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM users WHERE MID=?");
			pstmt.setLong(1, mid);
			final ResultSet results = pstmt.executeQuery();
			final boolean check = results.next();
			results.close();
			pstmt.close();
			return check;
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstmt);
		}
	}
	
	
	/**
	 * Check that a password belongs to a user
	 * 
	 * @param mid MID of the user
	 * @param password Users password
	 * @return check
	 */
	public boolean authenticatePassword(final long mid, final String password) throws DBException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			String salt = getSalt(mid);
			conn = factory.getConnection();
			pstmt = conn.prepareStatement("Select * FROM users WHERE MID=? AND password=?");
			pstmt.setLong(1, mid);
			pstmt.setString(2, DigestUtils.sha256Hex(password + salt));
			final ResultSet results = pstmt.executeQuery();
			final boolean check = results.next();
			results.close();
			pstmt.close();
			return check;
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstmt);
		}
		
	}
	
	
	/**
	 * Change the dependency status of the specified user
	 * @param mid the MID of the user to change dependency status
	 * @param dependency the dependency status to change user to
	 * @throws DBException
	 */
	public void setDependent(long mid, boolean dependency) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("UPDATE users SET isDependent=? WHERE MID=?");
			ps.setBoolean(1, dependency);
			ps.setLong(2, mid);
			ps.executeUpdate();
			ps.close();
		} catch(SQLException e) {
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	
	/**
	 * Check that the specified user is a dependent
	 * @param mid MID of the user
	 * @return true if the user is a dependent, false otherwise
	 * @throws DBException if the SQL statement is not valid
	 */
	public boolean isDependent(final long mid) throws DBException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = factory.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM users WHERE MID=? AND isDependent=1");
			pstmt.setLong(1, mid);
			final ResultSet results = pstmt.executeQuery();
			final boolean check = results.next();
			results.close();
			pstmt.close();
			return check;
		} catch(SQLException e) {
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstmt);
		}
	}
	
	/**
	 * getSalt returns the salt for user with MID mid.
	 * @param mid The user we are looking for.
	 * @return The salt for that user.
	 */
	public String getSalt(long mid){
		String result = "";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT Salt FROM users WHERE MID=?");
			ps.setLong(1, mid);
			rs = ps.executeQuery();
			if(rs.next()){
				result = rs.getString(1);
			} else {
				result = shakeSalt();
			}
		} catch (SQLException e){
			result = shakeSalt();
		} finally {
			DBUtil.closeConnection(conn, ps);
			if(rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
				}
		}
		return result;
	}
	
	/**
	 * shakeSalt gets some salt from the shaker.
	 * @return A random string 64 characters long. Perhaps the chars was throwing it off?
	 */
	private String shakeSalt(){
		byte[] buf = new byte[SALT_LEN];
		shaker.nextBytes(buf);
		return HexUtils.toHexString(buf);
	}
}
