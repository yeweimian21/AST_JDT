package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.beans.ProcedureBean;
import edu.ncsu.csc.itrust.beans.loaders.ProcedureBeanLoader;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;

/**
 * Used for managing medical procedures given during a particular office visit.
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
public class ProceduresDAO {
	private DAOFactory factory;
	private ProcedureBeanLoader loader = new ProcedureBeanLoader(true);
	
	/**
	 * @param factory
	 */
	public ProceduresDAO(DAOFactory factory) {
		this.factory = factory;
	}
	

	/**
	 * List procedure bean in database
	 * @param visitID
	 * @return procedure
	 * @throws DBException
	 */
	public List<ProcedureBean> getList(long visitID) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("Select * From ovprocedure,cptcodes,officevisits Where ovprocedure.VisitID = ? "
					+ "AND officevisits.ID=ovprocedure.VisitID AND cptcodes.Code=ovprocedure.CPTCode");
			ps.setLong(1, visitID);
			ResultSet rs = ps.executeQuery();
			List<ProcedureBean> loadlist = loader.loadList(rs);
			rs.close();
			return loadlist;
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	
	/**
	 * @param visitID
	 * @return list of inmmunization
	 * @throws DBException
	 */
	public List<ProcedureBean> getImmunizationList(long visitID) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("Select * From ovprocedure,cptcodes,officevisits Where ovprocedure.VisitID = ? "
					+ "AND officevisits.ID=ovprocedure.VisitID AND cptcodes.Code=ovprocedure.CPTCode AND cptcodes.attribute='immunization'");
			ps.setLong(1, visitID);
			ResultSet rs = ps.executeQuery();
			List<ProcedureBean> loadlist = loader.loadList(rs);
			rs.close();
			return loadlist;
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	
	public List<ProcedureBean> getMedProceduresList(long visitID) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("Select * From ovprocedure,cptcodes,officevisits Where ovprocedure.VisitID = ? "
					+ "AND officevisits.ID=ovprocedure.VisitID AND cptcodes.Code=ovprocedure.CPTCode AND cptcodes.attribute is NULL");
			ps.setLong(1, visitID);
			ResultSet rs = ps.executeQuery();
			List<ProcedureBean> loadlist = loader.loadList(rs);
			rs.close();
			return loadlist;
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	
	
	/**
	 * Adds a diagnosis bean to the database.
	 * @param pres The prescription bean to be added.
	 * @return The unique ID of the newly added bean.
	 * @throws DBException
	 */
	public long add(ProcedureBean bean) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			String statement = "INSERT INTO ovprocedure " +
				"(VisitID,CPTCode) VALUES (?,?)";
			ps = conn.prepareStatement(statement);
			ps.setLong(1, bean.getVisitID());
			ps.setString(2, bean.getCPTCode());
			ps.executeUpdate();
			return DBUtil.getLastInsert(conn);
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	
	/**
	 * Edits an existing procedure bean.
	 * 
	 * @param pres The newly updated prescription bean.
	 * @return A long indicating the ID of the newly updated prescription bean.
	 * @throws DBException
	 */
	public long edit(ProcedureBean bean) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			String statement = "UPDATE ovprocedure " +
				"SET VisitID=?, CPTCode=?" +
				"WHERE ID=?";
			ps = conn.prepareStatement(statement);
			ps.setLong(1, bean.getVisitID());
			ps.setString(2, bean.getCPTCode());
			ps.setLong(3, bean.getOvProcedureID());
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
	public void remove(long ovProcedureID) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("DELETE FROM ovprocedure WHERE ID=? ");
			ps.setLong(1, ovProcedureID);
			ps.executeUpdate();
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

}
