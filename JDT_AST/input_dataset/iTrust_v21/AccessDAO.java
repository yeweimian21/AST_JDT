package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;

/**
 * AccessDAO is for all queries related to authorization.
 * 
 * DAO stands for Database Access Object. All DAOs are intended to be reflections of the database, that is,
 * one DAO per table in the database (most of the time). For more complex sets of queries, extra DAOs are
 * added. DAOs can assume that all data has been validated and is correct.
 * 
 * DAOs should never have setters or any other parameter to the constructor than a factory. All DAOs should be
 * accessed by DAOFactory (@see {@link DAOFactory}) and every DAO should have a factory - for obtaining JDBC
 * connections and/or accessing other DAOs.
 * 
 * 
 *  
 * 
 */
@SuppressWarnings({})
public class AccessDAO {
	private transient final DAOFactory factory;

	/**
	 * The typical constructor.
	 * @param factory The {@link DAOFactory} associated with this DAO, 
	 * which is used for obtaining SQL connections, etc.
	 */
	public AccessDAO(final DAOFactory factory) {
		this.factory = factory;
	}

	/**
	 * Returns the number of minutes it would take for a session to time out. 
	 * This is done by effectively using the database table as a hash table. 
	 * If a row in GlobalVariables does not exist, one is inserted
	 * with the default value '20'.
	 * 
	 * @return An int for the number of minutes.
	 * @throws DBException
	 */
	public int getSessionTimeoutMins() throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT Value FROM globalvariables WHERE Name='Timeout'");
			ResultSet rs = ps.executeQuery();
			if (rs.next()){
				int returnVal = rs.getInt("Value");				
				rs.close();
				return returnVal;
			}
			else {
				rs.close();
				insertDefaultTimeout(20);
				return 20;
			}
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	/**
	 * Sets the number of minutes it would take for a session to timeout.
	 * 
	 * @param mins An int specifying the number of minutes
	 * @throws DBException
	 */
	public void setSessionTimeoutMins(int mins) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("UPDATE globalvariables SET Value=? WHERE Name='Timeout'");
			ps.setInt(1, mins);
			int numUpdated = ps.executeUpdate();
			if (numUpdated == 0) // no value in the table
				insertDefaultTimeout(mins);
			ps.close();
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	private void insertDefaultTimeout( final int mins) throws SQLException, DBException {
		Connection conn = null;
		PreparedStatement pstring = null;
		try{
			conn = factory.getConnection();
			pstring = conn.prepareStatement("INSERT INTO globalvariables(Name,Value) VALUES ('Timeout', ?)");
			pstring.setInt(1, mins);
			pstring.executeUpdate();
			pstring.close();
		}catch(SQLException e){
			throw new DBException(e);
		}finally{
			DBUtil.closeConnection(conn, pstring);
		}
	}
}
