package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.beans.DiagnosisBean;
import edu.ncsu.csc.itrust.beans.OfficeVisitBean;
import edu.ncsu.csc.itrust.beans.loaders.DiagnosisBeanLoader;
import edu.ncsu.csc.itrust.beans.loaders.OfficeVisitLoader;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;

/**
 * Used for doing tasks related to office visits. Use this for linking diagnoses to office visits, and similar
 * tasks.
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
public class OfficeVisitDAO {
	private DAOFactory factory;
	private OfficeVisitLoader officeVisitLoader = new OfficeVisitLoader();
	private DiagnosisBeanLoader diagnosisLoader = new DiagnosisBeanLoader(true);

	/**
	 * The typical constructor.
	 * @param factory The {@link DAOFactory} associated with this DAO, which is used for obtaining SQL connections, etc.
	 */
	public OfficeVisitDAO(DAOFactory factory) {
		this.factory = factory;
	}

	/**
	 * Adds an visit and return its ID
	 * 
	 * @param ov The OfficeVisitBean to be added.
	 * @return A long indicating the unique ID for the office visit.
	 * @throws DBException
	 */
	public long add(OfficeVisitBean ov) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("INSERT INTO officevisits (VisitDate, Notes, HCPID, PatientID, HospitalID, IsERIncident, appt_type) VALUES (?,?,?,?,?,?,?)");
			setValues(ps, ov);
			if(ov.getAppointmentType() != null)
				ps.setString(7, ov.getAppointmentType());
			else 
				ps.setString(7, "General Checkup");
			ps.executeUpdate();
			ps.close();
			return DBUtil.getLastInsert(conn);
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	private void setValues(PreparedStatement ps, OfficeVisitBean ov) throws SQLException {
		ps.setDate(1, new java.sql.Date(ov.getVisitDate().getTime()));
		ps.setString(2, ov.getNotes());
		ps.setLong(3, ov.getHcpID());
		ps.setLong(4, ov.getPatientID());
		ps.setString(5, ov.getHospitalID());
		ps.setBoolean(6, ov.isERIncident());
	}

	/**
	 * Updates the information in a particular office visit.
	 * 
	 * @param ov The Office Visit bean representing the changes.
	 * @throws DBException
	 */
	public void update(OfficeVisitBean ov) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("UPDATE officevisits SET VisitDate=?, Notes=?, HCPID=?, "
					+ "PatientID=?, HospitalID=? WHERE ID=?");
			setValues(ps, ov);
			ps.setLong(6, ov.getID());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	/**
	 * Returns a particular office visit given an ID
	 * 
	 * @param visitID The unique ID of the office visit.
	 * @return An OfficeVisitBean with the specifics for that office visit.
	 * @throws DBException
	 */
	public OfficeVisitBean getOfficeVisit(long visitID) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("Select * From officevisits Where ID = ?");
			ps.setLong(1, visitID);
			ResultSet rs = ps.executeQuery();
			if (rs.next()){
				OfficeVisitBean result = officeVisitLoader.loadSingle(rs); 
				rs.close();
				ps.close();
				return result;
			}
			else{
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
	 * Returns whether or not an office visit actually exists
	 * 
	 * @param ovID The ID of the office visit to be checked.
	 * @param pid The MID of the patient associated with this transaction.
	 * @return A boolean indicating its existence.
	 * @throws DBException
	 */
	public boolean checkOfficeVisitExists(long ovID, long pid) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM officevisits WHERE ID=? AND PatientID=?");
			ps.setLong(1, ovID);
			ps.setLong(2, pid);
			ResultSet rs = ps.executeQuery();
			boolean check = rs.next();
			rs.close();
			ps.close();
			return check;
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	/**
	 * Returns a list of all office visits for a given patient
	 * 
	 * @param pid The MID of the patient in question.
	 * @return A java.util.List of OfficeVisitBeans.
	 * @throws DBException
	 */
	public List<OfficeVisitBean> getAllOfficeVisits(long pid) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn
					.prepareStatement("SELECT * FROM officevisits WHERE PatientID=? ORDER BY VisitDate DESC");
			ps.setLong(1, pid);
			ResultSet rs = ps.executeQuery();
			List<OfficeVisitBean> loadlist = officeVisitLoader.loadList(rs);
			rs.close();
			ps.close();
			return loadlist;
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	/**
	 * Returns a list of all office visits for a given patient
	 * 
	 * @param mid The MID of the LHCP you are looking up.
	 * @return A java.util.List of Office Visits.
	 * @throws DBException
	 */
	public List<OfficeVisitBean> getAllOfficeVisitsForLHCP(long mid) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			if (mid == 0L) throw new SQLException("HCPID cannot be null");
			conn = factory.getConnection();
			ps = conn
					.prepareStatement("SELECT * FROM officevisits WHERE HCPID=? ORDER BY VisitDate DESC");
			ps.setLong(1, mid);
			ResultSet rs = ps.executeQuery();
			List<OfficeVisitBean> loadlist = officeVisitLoader.loadList(rs);
			rs.close();
			ps.close();
			return loadlist;
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	/**
	 * Returns a list of all office visits for a given patient
	 * 
	 * @param pid The MID of the patient.
	 * @return A java.util.List of Office Visits.
	 * @throws DBException
	 */
	public List<OfficeVisitBean> getOfficeVisitsWithNoSurvey(long pid) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn
					.prepareStatement("SELECT * FROM officevisits where id not in (select visitid from ovsurvey) and PatientID = ? ORDER BY VisitDate DESC");
			ps.setLong(1, pid);
			ResultSet rs = ps.executeQuery();
			List<OfficeVisitBean> loadlist = officeVisitLoader.loadList(rs);
			rs.close();
			ps.close();
			return loadlist;
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	
	/**
	 * Returns a list of all office visits at a given hospital
	 * @param hospitalID the id of the hospital
	 * @return a list of the OfficeVisitBeans that hold the office visits
	 * @throws DBException in the event of a database error
	 */
	public List<OfficeVisitBean> getOfficeVisitsFromHospital(String hospitalID) throws DBException{
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM officevisits WHERE hospitalID = ? ORDER BY ID DESC");
			ps.setString(1, hospitalID);
			ResultSet rs = ps.executeQuery();
			List<OfficeVisitBean> loadlist = officeVisitLoader.loadList(rs);
			rs.close();
			ps.close();
			return loadlist;
		} catch (SQLException e){
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
		
	}
	
	/**
	 * Gets all office visits corresponding to a particular ICD diagnosis.
	 * 
	 * @param icdcode A string represnting the ICD diagnosis to look up.
	 * @return A java.util.List of Office visits.
	 * @throws DBException
	 */
	public List<OfficeVisitBean> getAllOfficeVisitsForDiagnosis(String icdcode) throws DBException {
		
		List<DiagnosisBean> diags = null;
		List<OfficeVisitBean> ovs = new ArrayList<OfficeVisitBean>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs= null;
		
		try {
			if (icdcode == null) 
				throw new SQLException("icdcode cannot be null");
			
			conn = factory.getConnection();
			
			ps = conn.prepareStatement("SELECT * FROM ovdiagnosis ovd, icdcodes icd WHERE ovd.ICDCode=? and icd.Code=?");
			ps.setString(1, icdcode);
			ps.setString(2, icdcode);
			rs = ps.executeQuery();
			diags = diagnosisLoader.loadList(rs);
			rs.close();
			ps.close();
			ps = null;
			rs = null;
			
			for (DiagnosisBean bean: diags) {
				ps = conn.prepareStatement("SELECT * FROM officevisits ov WHERE ov.ID=?");
				ps.setInt(1, (int)bean.getVisitID());
				rs = ps.executeQuery();
				
				if (rs.next()) {
					ovs.add(officeVisitLoader.loadSingle(rs));
				}
				rs.close();
				ps.close();
			}
			
			return ovs;
		
		}
		catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
}
