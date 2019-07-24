package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.beans.ObstetricsRecordBean;
import edu.ncsu.csc.itrust.beans.loaders.ObstetricsRecordLoader;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;

/**
 * Used for managing all static information related to an obstetrics record. For other information related to all aspects
 * of patient care, see the other DAOs.
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
public class ObstetricsRecordDAO {
	private DAOFactory factory;
	private ObstetricsRecordLoader obstetricsLoader;

	/**
	 * The typical constructor.
	 * @param factory The {@link DAOFactory} associated with this DAO, which is used for obtaining SQL connections, etc.
	 */
	public ObstetricsRecordDAO(DAOFactory factory) {
		this.factory = factory;
		this.obstetricsLoader = new ObstetricsRecordLoader();
	}
 
	/**
	 * Adds an obstetrics record to the table
	 * 
	 * @throws DBException
	 */
	public void addObstetricsRecord(ObstetricsRecordBean p) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			//first, insert the record
			//used to have OID, between MID and pregId (also VALUES had another ?,
			ps = conn.prepareStatement("INSERT INTO obstetrics(MID,pregId,LMP,EDD,weeksPregnant,"
					+ "dateVisit,yearConception,hoursInLabor,deliveryType,pregnancyStatus,"
					+ "weight,bloodPressureS,bloodPressureD,FHR,FHU)"
					+ " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			ps = obstetricsLoader.loadParameters(ps, p);
			ps.executeUpdate();
			ps.close();
			
			//then, set the OID of the original bean to the one the database generates
			ps = conn.prepareStatement("SELECT * FROM obstetrics WHERE pregId=? ORDER BY oid DESC limit 1"); //it will be the largest OID in the DB
			ps.setLong(1, p.getPregId());
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
	 * Returns an obstetrics record correlating to the given OID
	 * 
	 * @param oid the obstetrics id
	 * @return ObstetricsRecordBean with the information from the obstetrics record
	 * @throws DBException
	 */
	public ObstetricsRecordBean getObstetricsRecord(long oid) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM obstetrics WHERE OID = ?");
			ps.setLong(1, oid);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				ObstetricsRecordBean pat = obstetricsLoader.loadSingle(rs);
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
	 * Returns a list of obstetrics records correlating to the given MID
	 * 
	 * @param mid the patient's id
	 * @return a list of ObstetricsRecordBean's with the information from all the obstetrics records
	 * correlating to the given MID
	 * @throws DBException
	 */
	public List<ObstetricsRecordBean> getObstetricsRecordsByMID(long mid)
			throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM obstetrics WHERE MID=? ORDER BY dateVisit DESC");
			ps.setLong(1, mid);
			ResultSet rs = ps.executeQuery();
			List<ObstetricsRecordBean> list = obstetricsLoader.loadList(rs);
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
	 * Same as above method for get records by MID, but filters by PregnancyStatus
	 * @param mid
	 * @param pregStatus
	 * @return
	 * @throws DBException
	 */
	public List<ObstetricsRecordBean> getObstetricsRecordsByMIDPregStatus(long mid, String pregStatus)
			throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM obstetrics WHERE MID=? AND pregnancyStatus=?"
					+ " ORDER BY dateVisit DESC");
			ps.setLong(1, mid);
			ps.setString(2, pregStatus);
			ResultSet rs = ps.executeQuery();
			List<ObstetricsRecordBean> list = obstetricsLoader.loadList(rs);
			rs.close();
			ps.close();
			return list;
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	public ObstetricsRecordBean getObstetricsRecordsByMIDLargestpregId(long mid)
			throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM obstetrics WHERE MID=? ORDER BY pregId DESC");
			ps.setLong(1, mid);

			ResultSet rs = ps.executeQuery();
			ObstetricsRecordBean bean = null;
			//check if result set was empty, then load if not
			if (rs.first()) {
				bean = obstetricsLoader.loadSingle(rs);
			}
			rs.close();
			ps.close();
			return bean; //this will return null if there weren't any that meet this criteria
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	/**
	 * Updates an obstetrics record for the given OID
	 * 
	 * @param p The ObstetricsRecordBean representing the new information for the obstetrics record.
	 * @throws DBException
	 */
	public void editObstetricsRecord(long oid, ObstetricsRecordBean p) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("UPDATE obstetrics SET MID=?,pregId=?,LMP=?,EDD=?,weeksPregnant=?,"
					+ "dateVisit=?,yearConception=?,hoursInLabor=?,deliveryType=?,pregnancyStatus=?,"
					+ "weight=?,bloodPressureS=?,bloodPressureD=?,fhr=?,fhu=? "
					+ "WHERE OID=?");

			obstetricsLoader.loadParameters(ps, p);
			ps.setLong(16, oid);
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
