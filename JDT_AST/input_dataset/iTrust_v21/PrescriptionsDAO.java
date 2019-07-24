/**
 * 
 */
package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.beans.PrescriptionBean;
import edu.ncsu.csc.itrust.beans.loaders.PrescriptionBeanLoader;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;

/**
 * Used for managing prescriptions given during a particular office visit.
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
 */
public class PrescriptionsDAO {
	private DAOFactory factory;
	private PrescriptionBeanLoader loader = new PrescriptionBeanLoader();
	
	public PrescriptionsDAO(DAOFactory factory) {
		this.factory = factory;
	}
	
	public List<PrescriptionBean> getList(long visitID) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("Select * From ovmedication,ndcodes Where ovmedication.VisitID = ? "
					+ "AND ndcodes.Code=ovmedication.NDCode");
			ps.setLong(1, visitID);
			ResultSet rs = ps.executeQuery();
			List<PrescriptionBean> loadlist = loader.loadList(rs);
			rs.close();
			return loadlist;
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	/**
	 * Adds a prescription bean to the database.
	 * @param pres The prescription bean to be added.
	 * @return The unique ID of the newly added bean.
	 * @throws DBException
	 */
	public long add(PrescriptionBean pres) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			
			
			ps = conn
					.prepareStatement("INSERT INTO ovmedication (VisitID,NDCode,StartDate,EndDate,Dosage,Instructions,OverrideOther) VALUES (?,?,?,?,?,?,?)");
			loader.loadParameters(ps, pres);
			ps.executeUpdate();
			
			return DBUtil.getLastInsert(conn);
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	
	/**
	 * Edits an existing prescription bean.
	 * 
	 * @param pres The newly updated prescription bean.
	 * @return A long indicating the ID of the newly updated prescription bean.
	 * @throws DBException
	 */
	public long edit(PrescriptionBean pres) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			String statement = "UPDATE ovmedication " +
				"SET VisitID=?, NDCode=?, StartDate=?, EndDate=?, Dosage=?, Instructions=?, OverrideOther=? " +
				"WHERE ID=?";
			ps = conn.prepareStatement(statement);
			loader.loadParameters(ps, pres);
			ps.setLong(8, pres.getId());
			ps.executeUpdate();
			return DBUtil.getLastInsert(conn);
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	/**
	 * Removes the given medication from its office visit
	 * 
	 * @param ovMedicationID The unique ID of the medication to be removed.
	 * @throws DBException
	 */
	public void remove(long ovMedicationID) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("DELETE FROM ovmedication WHERE ID=? ");
			ps.setLong(1, ovMedicationID);
			ps.executeUpdate();
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
}
