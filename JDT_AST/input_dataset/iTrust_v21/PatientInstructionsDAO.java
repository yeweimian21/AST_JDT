package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.beans.OfficeVisitBean;
import edu.ncsu.csc.itrust.beans.PatientInstructionsBean;
import edu.ncsu.csc.itrust.beans.loaders.OfficeVisitLoader;
import edu.ncsu.csc.itrust.beans.loaders.PatientInstructionsBeanLoader;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;

/**
 * Used for managing patient-specific instructions.
 * 
 * DAO stands for Database Access Object. All DAOs are intended to be reflections of the database, that is,
 * one DAO per table in the database (most of the time). For more complex sets of queries, extra DAOs are
 * added. DAOs can assume that all data has been validated and is correct.
 * 
 * DAOs should never have setters or any other parameter to the constructor than a factory. All DAOs should be
 * accessed by DAOFactory (@see {@link DAOFactory}) and every DAO should have a factory - for obtaining JDBC
 * connections and/or accessing other DAOs.
 */
public class PatientInstructionsDAO {
	private DAOFactory factory;
	private PatientInstructionsBeanLoader loader = new PatientInstructionsBeanLoader();
	
	public PatientInstructionsDAO(DAOFactory factory) {
		this.factory = factory;
	}
	
	/**
	 * Get a list of patient instructions for a given office visit.
	 * @param visitID The id of the office visit to lookup.
	 * @return The list of patient instructions.
	 * @throws DBException
	 */
	public List<PatientInstructionsBean> getList(long visitID) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement(
					"Select * From patientspecificinstructions Where patientspecificinstructions.VisitID = ? ");
			ps.setLong(1, visitID);
			ResultSet rs = ps.executeQuery();
			List<PatientInstructionsBean> loadlist = loader.loadList(rs);
			rs.close();
			return loadlist;
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	/**
	 * Add a new patient instructions record.
	 * @param bean The instructions to add.
	 * @return The id of the newly added instructions record.
	 * @throws DBException
	 */
	public long add(PatientInstructionsBean bean) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			String statement = "INSERT INTO patientspecificinstructions " +
				"(VisitID,Modified,Name,URL,Comment) VALUES (?,?,?,?,?)";
			ps = conn.prepareStatement(statement);
			ps = loader.loadParameters(ps, bean);
			ps.executeUpdate();
			return DBUtil.getLastInsert(conn);
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	/**
	 * Edit an existing patient instructions record in the database.
	 * @param bean The instruction to modify.
	 * @return The id of the modified instruction.  This will be that same as the id given in the bean itself.
	 * @throws DBException
	 */
	public long edit(PatientInstructionsBean bean) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			String statement = "UPDATE patientspecificinstructions " +
				"SET VisitID=?, Modified=?, Name=?, URL=?, Comment=? " +
				"WHERE ID=?";
			ps = conn.prepareStatement(statement);
			loader.loadParameters(ps, bean);
			ps.setLong(6, bean.getId());
			ps.executeUpdate();
			return DBUtil.getLastInsert(conn);
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	/**
	 * Remove a patient instructions record from the database.
	 * @param patientInstructionsID The id of the record to delete.
	 * @throws DBException
	 */
	public void remove(long patientInstructionsID) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("DELETE FROM patientspecificinstructions WHERE ID=? ");
			ps.setLong(1, patientInstructionsID);
			ps.executeUpdate();
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	/**
	 * Get a list of all office visits by a given patient which has patient specific instructions.
	 * @param pid The patient id to look up.
	 * @return A list of office visits.
	 * @throws DBException
	 */
	public List<OfficeVisitBean> getOfficeVisitsWithInstructions(long pid) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			OfficeVisitLoader ovloader = new OfficeVisitLoader();
			conn = factory.getConnection();
			ps = conn.prepareStatement(
					"Select * From officevisits Where " +
					"officevisits.ID in (Select VisitID From patientspecificinstructions) " +
					"and officevisits.PatientID = ?");
			ps.setLong(1, pid);
			ResultSet rs = ps.executeQuery();
			List<OfficeVisitBean> loadlist = ovloader.loadList(rs);
			rs.close();
			return loadlist;
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

}
