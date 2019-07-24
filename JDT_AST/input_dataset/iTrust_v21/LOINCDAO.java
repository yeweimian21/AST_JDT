package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.beans.LOINCbean;
import edu.ncsu.csc.itrust.beans.loaders.LOINCBeanLoader;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;

/**
 * DAO stands for Database Access Object. 
 * All DAOs are intended to be reflections of the database, 
 * that is, one DAO per table in the database (most of the time). 
 * For more complex sets of queries, 
 * extra DAOs are added. 
 * DAOs can assume that all data has been validated and is correct.
 * 
 * DAOs should never have setters or any other parameter 
 * to the constructor than a factory. All DAOs should be
 * accessed by DAOFactory (@see {@link DAOFactory}) 
 * and every DAO should have a factory - for obtaining JDBC
 * connections and/or accessing other DAOs.
 * 
 * Logical Observation Identifiers Names and Codes (LOINC) 
 * is a database and universal standard for 
 * identifying medical laboratory observations. 
 * @see http://loinc.org/
 */
@SuppressWarnings({})
public class LOINCDAO {
	private transient final DAOFactory factory;
	private transient final LOINCBeanLoader loincLoader;
	
	private static final int LOINC_EXISTS_ERR_CODE = 1062;

	/**
	 * The typical constructor.
	 * @param factory The {@link DAOFactory} associated with this DAO, 
	 * 		which is used for obtaining SQL connections, etc.
	 */
	public LOINCDAO(final DAOFactory factory) {
		this.factory = factory;
		loincLoader = new LOINCBeanLoader();
	}
	
	/**
	 * Adds a LOINC
	 * 
	 * @param hosp The LOINCbean representing the new code to be added.
	 * @throws DBException
	 * @throws ITrustException
	 */
	public void addLOINC(final LOINCbean hosp) throws DBException, ITrustException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = factory.getConnection();
			pstmt = conn.prepareStatement("INSERT INTO loinc (LaboratoryProcedureCode, Component, KindOfProperty, TimeAspect, System, ScaleType, MethodType) " + "VALUES (?,?,?,?,?,?,?)");
			pstmt.setString(1, hosp.getLabProcedureCode());
			pstmt.setString(2, hosp.getComponent());
			pstmt.setString(3, hosp.getKindOfProperty());
			pstmt.setString(4, hosp.getTimeAspect());
			pstmt.setString(5, hosp.getSystem());
			pstmt.setString(6, hosp.getScaleType());
			pstmt.setString(7, hosp.getMethodType());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			
			if (LOINC_EXISTS_ERR_CODE == e.getErrorCode()) {
				throw new ITrustException("Error: LOINC already exists.");
			}
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstmt);
		}
	}	

	/**
	 * Returns all LOINCs associated with LaboratoryProcedureCode
	 * 
	 * @param labProcCode The code of the Laboratory Procedure Code as a String.
	 * @return A java.util.List of LOINCBeans matching the Laboratory Procedure Code.
	 * @throws DBException
	 */
	public List<LOINCbean> getProcedures(final String labProcCode) throws DBException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = factory.getConnection();
			pstmt = conn.prepareStatement("Select * From loinc Where LaboratoryProcedureCode = ? ");
			pstmt.setString(1, labProcCode);
			final ResultSet results = pstmt.executeQuery();
			final List<LOINCbean> loadlist = loincLoader.loadList(results);
			results.close();
			return loadlist;
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstmt);
		}
	}
	

	
	/**
	 * Updates the information in LOINC.
	 * 
	 * @param ov
	 * @throws DBException
	 */
	public int update(final LOINCbean ov) throws DBException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = factory.getConnection();
			pstmt = conn.prepareStatement("UPDATE loinc SET Component=?, KindOfProperty=?, TimeAspect=?, System=?, ScaleType=?, MethodType=? WHERE LaboratoryProcedureCode=?");
			pstmt.setString(1, ov.getComponent());
			pstmt.setString(2, ov.getKindOfProperty());
			pstmt.setString(3, ov.getTimeAspect());
			pstmt.setString(4, ov.getSystem());
			pstmt.setString(5, ov.getScaleType());
			pstmt.setString(6, ov.getMethodType());
			pstmt.setString(7, ov.getLabProcedureCode());
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstmt);
		}
	}
	
	/**
	 * Returns all LOINCs as a list.
	 * 
	 * @return A java.util.List of LOINCbeans.
	 * @throws DBException
	 */
	public List<LOINCbean> getAllLOINC() throws DBException {
		Connection conn = null;
		PreparedStatement pstmt = null;
			try {
			conn = factory.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM loinc");
			final ResultSet results = pstmt.executeQuery();
			final List<LOINCbean> loadlist = loincLoader.loadList(results);
			results.close();
			return loadlist;
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstmt);
		}
	}


}
