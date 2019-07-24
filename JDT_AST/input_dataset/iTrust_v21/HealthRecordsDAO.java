package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.beans.HealthRecord;
import edu.ncsu.csc.itrust.beans.loaders.HealthRecordsBeanLoader;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;

/**
 * Used for all health records where a whole history is kept.
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
public class HealthRecordsDAO {
	private HealthRecordsBeanLoader loader = new HealthRecordsBeanLoader();
	private DAOFactory factory;

	/**
	 * The typical constructor.
	 * @param factory The {@link DAOFactory} associated with this DAO, which is used for obtaining SQL connections, etc.
	 */
	public HealthRecordsDAO(DAOFactory factory) {
		this.factory = factory;
	}

	/**
	 * Returns a list of all health records for a particular patient
	 * 
	 * @param mid The MID of the patient to look up.
	 * @return A java.util.List of HealthRecords.
	 * @throws DBException
	 */
	public List<HealthRecord> getAllHealthRecords(long mid) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		List<HealthRecord> records = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM personalhealthinformation "
					+ "WHERE PatientID=? ORDER BY ASOFDATE DESC");
			ps.setLong(1, mid);
			ResultSet rs;
			rs = ps.executeQuery();
			records = loader.loadList(rs);
			rs.close();
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
		return records;
	}
	
	/**
	 * 
	 * @param ovDateUpper Date of patient's 3-year birthday set as a boundary for baby records, < 3 years of age
	 * @return A java.util.List of all baby HealthRecords
	 * @throws DBException
	 */
	public List<HealthRecord> getAllRecordsBeforeOVDate(long pid, Date ovDateLower) throws DBException{
		Connection conn = null;
		PreparedStatement ps = null;
		List<HealthRecord> records = null;
		try{
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM personalhealthinformation WHERE PatientID = ? AND OfficeVisitDate < ? ORDER BY OfficeVisitDate DESC");
			ps.setLong(1, pid);
			ps.setDate(2, new java.sql.Date(ovDateLower.getTime()));
			ResultSet rs;
			rs = ps.executeQuery();
			records = loader.loadList(rs);
			rs.close();
		}
		catch(SQLException e){
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
		return records;
	}
	
	/**
	 * 
	 * @param ovDateLower Date of patient's 3-year birthday set as a boundary for lower youth records, >= 3 years of age
	 * @param ovDateUpper Date of patient's 12-year birthday set as a boundary for upper youth records, < 12 years of age
	 * @return A java.util.List of all youth HealthRecords
	 * @throws DBException
	 */
	public List<HealthRecord> getAllRecordsBetweenOVDates(long pid, Date ovDateLower, Date ovDateUpper) throws DBException{
		Connection conn = null;
		PreparedStatement ps = null;
		List<HealthRecord> records = null;
		try{
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM personalhealthinformation WHERE PatientID = ? AND OfficeVisitDate >= ? and OfficeVisitDate < ? ORDER BY OfficeVisitDate DESC");
			ps.setLong(1, pid);
			ps.setDate(2, new java.sql.Date(ovDateLower.getTime()));
			ps.setDate(3, new java.sql.Date(ovDateUpper.getTime()));
			ResultSet rs;
			rs = ps.executeQuery();
			records = loader.loadList(rs);	
			rs.close();
		}
		catch(SQLException e){
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
		return records;
	}
	
	/**
	 * getAllRecordsAfterOVDate
	 * @param ovDateUpper Date of patient's 12-year birthday set as a boundary for lower adult records, >= 12 years of age
	 * @return A java.util.List of all adult HealthRecords
	 * @throws DBException
	 */
	public List<HealthRecord> getAllRecordsAfterOVDate(long pid, Date ovDateUpper) throws DBException{
		Connection conn = null;
		PreparedStatement ps = null;
		List<HealthRecord> records = null;
		try{
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM personalhealthinformation WHERE PatientID = ? AND OfficeVisitDate >= ? ORDER BY OfficeVisitDate DESC");
			ps.setLong(1, pid);
			ps.setDate(2, new java.sql.Date(ovDateUpper.getTime()));
			ResultSet rs;
			rs = ps.executeQuery();
			records = loader.loadList(rs);
			rs.close();
		}
		catch(SQLException e){
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
		return records;
	}
	

	/**
	 * Adds a health record for a particular patient
	 * 
	 * @param record The HealthRecord object to insert.
	 * @return A boolean indicating whether the insert was successful.
	 * @throws DBException
	 */
	public boolean add(HealthRecord record) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		int numInserted = 0;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("INSERT INTO personalhealthinformation(PatientID,OfficeVisitID,Height,Weight,HeadCircumference,"
					+ "Smoker,SmokingStatus,HouseholdSmokingStatus,BloodPressureN,BloodPressureD,CholesterolHDL,CholesterolLDL,CholesterolTri,"
					+ "HCPID,OfficeVisitDate,BMI) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			loader.loadParameters(ps, record);
			numInserted = ps.executeUpdate();
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
		return (numInserted == 1);
	}
	
	/**
	 * Removes a health record in the MySQL database by its office visit id
	 * @param record the health record to remove
	 * @return true if health record has successfully been removed. False otherwise.
	 * @throws DBException
	 */
	public boolean remove(HealthRecord record) throws DBException {
		//Boolean for keeping track whether record has been removed
		boolean removed = false;
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("DELETE FROM personalhealthinformation WHERE OfficeVisitID = ?");
			ps.setLong(1, record.getOfficeVisitID());
			ps.execute();
			removed = true;
		} catch (SQLException e) {
			//If an SQLException happens, throw a DBException
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
		return removed;
	}
	
	/**
	 * Get an health record by its office visit ID.
	 * @param ovID the office visit id of the health record to get
	 * @return the health record with the associated office visit id
	 * @throws DBException
	 */
	public HealthRecord getHealthRecordByOfficeVisit(long ovID) throws DBException {
		//Health record object to hold patient's health record associated with the office visit
		HealthRecord record = null;
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM personalhealthinformation WHERE OfficeVisitID = ?");
			ps.setLong(1, ovID);
			ResultSet rs;
			rs = ps.executeQuery();
			//Get the first and only health record
			List<HealthRecord> records = loader.loadList(rs);
			rs.close();
			if (records.size() != 0)
				record = records.get(0);
		} catch (SQLException e) {
			//If an SQLException happens, throw a DBException
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
		return record;
	}
	
	/**
	 * getAllPatientHealthRecordsByHospital
	 * @param pid pid
	 * @param hospitalID hospitalID
	 * @return records
	 * @throws DBException
	 */
	public List<HealthRecord> getAllPatientHealthRecordsByHospital(long pid, String hospitalID) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		List<HealthRecord> records = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT personalhealthinformation.* "
					+ "FROM personalhealthinformation INNER JOIN officevisits " 
					+ "ON personalhealthinformation.OfficeVisitID = officevisits.ID "
					+ "WHERE personalhealthinformation.PatientID = ? "
					+ "AND officevisits.HospitalID = ?");
			ps.setLong(1, pid);
			ps.setString(2, hospitalID);
			ResultSet rs;
			rs = ps.executeQuery();
			records = loader.loadList(rs);
			rs.close();
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
		return records;
	}
	
}
