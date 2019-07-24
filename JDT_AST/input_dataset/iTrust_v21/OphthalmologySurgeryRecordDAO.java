package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.beans.OphthalmologySurgeryRecordBean;
import edu.ncsu.csc.itrust.beans.loaders.OphthalmologySurgeryRecordLoader;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;

/**
 * Used for managing all static information related to an ophthalmology office
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
public class OphthalmologySurgeryRecordDAO {

	/** Used to get database connections*/
	private DAOFactory factory;
	/** Used to load data from ResultSets, and into PreparedStatements.*/
	private OphthalmologySurgeryRecordLoader ophthalmologyLoader;
	
	/**
	 * The typical constructor.
	 * @param factory The {@link DAOFactory} associated with this DAO, which
	 * is used for obtaining SQL connections, etc.
	 */
	public OphthalmologySurgeryRecordDAO(DAOFactory factory) {
		this.factory = factory;
		this.ophthalmologyLoader = new OphthalmologySurgeryRecordLoader();
	}
	
	/**
	 * Adds an Ophthalmology office visit record to the table.
	 * @param p The Ophthalmology office visit record to be added to the database.
	 * @throws DBException thrown when the database throws a SQLException.
	 */
	public void addOphthalmologySurgeryRecord(OphthalmologySurgeryRecordBean p) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			//first, insert the record
			ps = conn.prepareStatement("INSERT INTO ophthalmologySurgery (MID,dateVisit,"
					+ "docLastName,docFirstName,vaNumOD,vaDenOD,vaNumOS,vaDenOS,sphereOD,sphereOS,"
					+ "cylinderOD,cylinderOS,axisOD,axisOS,addOD,addOS,surgery,surgeryNotes)"
					+ " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			ps = ophthalmologyLoader.loadParameters(ps, p);
			ps.executeUpdate(); //HERE
			ps.close();
			ps = conn.prepareStatement("SELECT * FROM ophthalmologySurgery ORDER BY oid DESC limit 1");
			//then, set the OID of the original bean to the one the database generates
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
	 * Returns an ophthalmology office visit record correlating to the given 
	 * OID.
	 * @param oid The Ophthalmology office visit id.
	 * @return ObstetricsRecordBean containing the information from the 
	 * Ophthalmology office visit record.
	 * @throws DBException thrown when the database throws a SQLException.
	 */
	public OphthalmologySurgeryRecordBean getOphthalmologySurgeryRecord(long oid) 
			throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM ophthalmologySurgery WHERE OID" + " = ?");
			ps.setLong(1, oid);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				OphthalmologySurgeryRecordBean pat = ophthalmologyLoader.loadSingle(rs);
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
	 * Returns a list of ophthalmology office visit records correlating to the
	 * given MID.
	 * @param mid the patient's id.
	 * @return a list of ObstetricsRecordBean's with the information from all
	 * the ophthalmology office visit records correlating to the given MID.
	 * @throws DBException thrown when the database throws a SQLException.
	 */
	public List<OphthalmologySurgeryRecordBean> 
			getOphthalmologySurgeryRecordsByMID(long mid)throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM ophthalmologySurgery WHERE MID=? ORDER BY dateVisit DESC");
			ps.setLong(1, mid);
			ResultSet rs = ps.executeQuery();
			List<OphthalmologySurgeryRecordBean> list = ophthalmologyLoader.loadList(rs);
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
	 * Updates an ophthalmology office visit record for the given OID.
	 * @param oid the ophthalmology office visit id.
	 * @param p The OphthalmologySurgeryRecordBean representing the new information for
	 * the ophthalmology office visit record.
	 * @throws DBException thrown when the database throws a SQLException.
	 */
	public void editOphthalmologySurgeryRecordsRecord(long oid, OphthalmologySurgeryRecordBean p) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("UPDATE ophthalmologySurgery SET MID=?,dateVisit=?,docLastName=?,docFirstName=?,vaNumOD=?,vaDenOD=?,vaNumOS=?,vaDenOS=?,sphereOD=?,"
					+ "sphereOS=?,cylinderOD=?,cylinderOS=?,axisOD=?,axisOS=?,"
					+ "addOD=?,addOS=?,surgery=?,surgeryNotes=? "
					+ "WHERE OID=?");
			ophthalmologyLoader.loadParameters(ps, p);
			ps.setLong(19, oid);
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