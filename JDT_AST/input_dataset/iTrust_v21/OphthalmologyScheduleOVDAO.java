package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.beans.OphthalmologyScheduleOVRecordBean;
import edu.ncsu.csc.itrust.beans.loaders.OphthalmologyScheduleOVRecordLoader;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;

/**
 * Used for managing all static information related to a scheduled ophthalmology office
 * visit record. For other information related to all aspects of patient care,
 * see the other DAOs.
 * 
 * DAO stands for Database Access Object. All DAOs are intended to be 
 * reflections of the database, that is, one DAO per table in the database 
 * (most of the time). For more complex sets of queries, extra DAOs are
 * added. DAOs can assume that all data has been validated and is correct.
 * 
 * DAOs should never have setters or any other parameter to the constructor
 * than a factory. All DAOs should be accessed by DAOFactory 
 * (@see {@link DAOFactory}) and every DAO should have a factory - for 
 * obtaining JDBC connections and/or accessing other DAOs.
 */
public class OphthalmologyScheduleOVDAO {
	/** Used to get database connections*/
	private DAOFactory factory;
	/** Used to load data from ResultSets, and into PreparedStatements.*/
	private OphthalmologyScheduleOVRecordLoader ophthalmologyScheduleOVLoader;
	
	/**
	 * The typical constructor.
	 * @param factory The {@link DAOFactory} associated with this DAO, which
	 * is used for obtaining SQL connections, etc.
	 */
	public OphthalmologyScheduleOVDAO(DAOFactory factory) {
		this.factory = factory;
		this.ophthalmologyScheduleOVLoader = new OphthalmologyScheduleOVRecordLoader();
	}
	

	/**
	 * Adds a scheduled Ophthalmology office visit record to the table.
	 * @param p The scheduled Ophthalmology office visit record to be added to the database.
	 * @throws DBException thrown when the database throws a SQLException.
	 */
	public void addOphthalmologyScheduleOVRecord(OphthalmologyScheduleOVRecordBean p) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			//first, insert the record
			ps = conn.prepareStatement("INSERT INTO ophthalmologySchedule(PATIENTMID,DOCTORMID,"
					+ "dateTime,docLastName,docFirstName,comments,pending,accepted)"
					+ " VALUES(?,?,?,?,?,?,?,?)");
			ps = ophthalmologyScheduleOVLoader.loadParameters(ps, p);
			ps.executeUpdate();
			ps.close();
			
			//then, set the OID of the original bean to the one the database generates
			ps = conn.prepareStatement("SELECT * FROM ophthalmologySchedule ORDER BY oid DESC limit 1");
			ResultSet rs = ps.executeQuery();
			rs.first();
			p.setOid(rs.getLong("oid"));
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	/**
	 * Returns a scheduled ophthalmology office visit record correlating to the given 
	 * OID.
	 * @param oid The scheduled Ophthalmology office visit id.
	 * @return OphthalmologyScheduleOVRecordBean containing the information from the 
	 * scheduled Ophthalmology office visit record.
	 * @throws DBException thrown when the database throws a SQLException.
	 */
	public OphthalmologyScheduleOVRecordBean getOphthalmologyScheduleOVRecord(long oid) 
			throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM ophthalmologySchedule WHERE OID" + " = ?");
			ps.setLong(1, oid);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				OphthalmologyScheduleOVRecordBean pat = ophthalmologyScheduleOVLoader.loadSingle(rs);
				rs.close();
				ps.close();
				return pat;
			} else{
				rs.close();
				ps.close();
				return null;
			}
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	/**
	 * Returns a list of scheduled ophthalmology office visit records correlating to the
	 * given PATIENTMID.
	 * @param mid the patient's id.
	 * @return a list of OphthalmologyScheduleOVRecordBean's with the information from all
	 * the scheduled ophthalmology office visit records correlating to the given PATIENTMID.
	 * @throws DBException thrown when the database throws a SQLException.
	 */
	public List<OphthalmologyScheduleOVRecordBean> 
			getOphthalmologyScheduleOVRecordsByPATIENTMID(long mid)throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM ophthalmologySchedule WHERE PATIENTMID=? ORDER BY dateTime DESC");
			ps.setLong(1, mid);
			ResultSet rs = ps.executeQuery();
			List<OphthalmologyScheduleOVRecordBean> list = ophthalmologyScheduleOVLoader.loadList(rs);
			rs.close();
			ps.close();
			return list;
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	/**
	 * Returns a list of scheduled ophthalmology office visit records correlating to the
	 * given DOCTORMID.
	 * @param mid the patient's id.
	 * @return a list of OphthalmologyScheduleOVRecordBean's with the information from all
	 * the scheduled ophthalmology office visit records correlating to the given DOCTORMID.
	 * @throws DBException thrown when the database throws a SQLException.
	 */
	public List<OphthalmologyScheduleOVRecordBean> 
			getOphthalmologyScheduleOVRecordsByDOCTORMID(long mid)throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM ophthalmologySchedule WHERE DOCTORMID=? ORDER BY dateTime DESC");
			ps.setLong(1, mid);
			ResultSet rs = ps.executeQuery();
			List<OphthalmologyScheduleOVRecordBean> list = ophthalmologyScheduleOVLoader.loadList(rs);
			rs.close();
			ps.close();
			return list;
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	/**
	 * Updates a scheduled ophthalmology office visit record for the given OID.
	 * @param oid the scheduled ophthalmology office visit id.
	 * @param p The OphthalmologyScheduleOVRecordBean representing the new information for
	 * the scheduled ophthalmology office visit record.
	 * @throws DBException thrown when the database throws a SQLException.
	 */
	public void editOphthalmologyScheduledOVRecordsRecord(long oid, OphthalmologyScheduleOVRecordBean p) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("UPDATE ophthalmologySchedule SET PATIENTMID=?,DOCTORMID=?,dateTime=?,"
					+ "docLastName=?,docFirstName=?,comments=?,pending=?,accepted=? "
					+ "WHERE OID=?");
			ophthalmologyScheduleOVLoader.loadParameters(ps, p);
			ps.setLong(9, oid);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
}
