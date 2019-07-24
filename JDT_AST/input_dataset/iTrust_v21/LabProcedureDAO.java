package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.beans.LabProcedureBean;
import edu.ncsu.csc.itrust.beans.loaders.LabProcedureBeanLoader;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;

/**
 * A DAO for managing lab procedure codes. Database Access Object. All info coming into a DAO is already validated. 
 * Just worry about DB stuff here. Note that all DAOs need to have a DAOFactory with which to access other 
 * DAOs and to get connections. Also, every DAO must have a constructor with a DAOFactory as a parameter.
 */
public class LabProcedureDAO {
	private DAOFactory factory;
	private LabProcedureBeanLoader labProcedureLoader;

	/**
	 * The typical constructor.
	 * @param factory The {@link DAOFactory} associated with this DAO, which is used for obtaining SQL connections, etc.
	 */
	public LabProcedureDAO(DAOFactory factory) {
		this.factory = factory;
		labProcedureLoader = new LabProcedureBeanLoader();
	}
	
	/**
	 * Get a list of the lab procedures associated with a given patient.
	 * @param id The MID of the patient as a long.
	 * @return A java.util.List of LabProcedureBeans
	 * @throws DBException
	 */
	public List<LabProcedureBean> getLabProceduresForPatient(long id) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			if (id == 0L) throw new SQLException("PatientMID cannot be null");
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM labprocedure WHERE PatientMID = ? AND Rights = ? ORDER BY UpdatedDate DESC");
			ps.setLong(1, id);
			ps.setString(2, LabProcedureBean.Allow);
			ResultSet rs = ps.executeQuery();
			List<LabProcedureBean> loadlist = labProcedureLoader.loadList(rs);
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
	 * Get lab procedures for a specific office visit, but excluding lab 
	 * procedures which a patient does not have access to.
	 * 
	 * @param ovid Office visit id.
	 * @return
	 * @throws DBException
	 */
	public List<LabProcedureBean> getLabProceduresForPatientOV(long ovid) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM labprocedure WHERE OfficeVisitID = ? AND Rights = ? ORDER BY UpdatedDate DESC");
			ps.setLong(1, ovid);
			ps.setString(2, LabProcedureBean.Allow);
			ResultSet rs = ps.executeQuery();
			List<LabProcedureBean> loadlist = labProcedureLoader.loadList(rs);
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
	 * Gets all the lab procedures for a given patient that occur within the next month.
	 * @param id The MID of the patient as a long.
	 * @return A java.util.List of LabProcedureBeans.
	 * @throws DBException
	 */
	public List<LabProcedureBean> getLabProceduresForPatientForNextMonth(long id) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			if (id == 0L) throw new SQLException("PatientMID cannot be null");
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM labprocedure WHERE PatientMID = ? AND Rights = ? AND Status = ? AND (DateDiff(SYSDATE(),UpdatedDate) <= 30) ORDER BY UpdatedDate DESC");
			ps.setLong(1, id);
			ps.setString(2, LabProcedureBean.Allow);
			ps.setString(3, LabProcedureBean.Completed);
			ResultSet rs = ps.executeQuery();
			List<LabProcedureBean> loadlist = labProcedureLoader.loadList(rs);
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
	 * Gets an individual lab procedure.
	 * @param id The ID of the lab procedure.
	 * @return A LabProcedureBean representing the procedure.
	 * @throws DBException
	 */
	public LabProcedureBean getLabProcedure(long id) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM labprocedure WHERE LaboratoryProcedureID = ?");
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			rs.next();
			LabProcedureBean loaded = labProcedureLoader.loadSingle(rs);
			rs.close();
			ps.close();
			return loaded;
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	/**
	 * Gets all procedures for Patient
	 * @param mid patient id
	 * @return A java.util.List of LabProcedureBeans.
	 * @throws DBException
	 */
	public List<LabProcedureBean> getAllLabProceduresDate(long mid) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			if (mid == 0L) throw new SQLException("PatientMID cannot be null");
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM labprocedure WHERE PatientMID = ? ORDER BY UpdatedDate DESC");
			ps.setLong(1, mid);
			ResultSet rs = ps.executeQuery();
			List<LabProcedureBean> loadlist = labProcedureLoader.loadList(rs);
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
	 * This gets all the procedures for a particular patient on a particular office visit
	 * @param mid The MID of the patient.
	 * @param ovid The Office Visit ID.
	 * @return A java.util.List of LabProcedureBeans.
	 * @throws DBException
	 */
	public List<LabProcedureBean> getAllLabProceduresForDocOV(long mid, long ovid) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			if (mid == 0L) throw new SQLException("PatientMID cannot be null");
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM labprocedure WHERE PatientMID = ? AND OfficeVisitID = ? ORDER BY UpdatedDate DESC");
			ps.setLong(1, mid);
			ps.setLong(2, ovid);
			ResultSet rs = ps.executeQuery();
			List<LabProcedureBean> loadlist = labProcedureLoader.loadList(rs);
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
	 * This gets all the procedures for a particular patient on a particular office visit
	 * @param ovid The Office Visit ID.
	 * @return A java.util.List of LabProcedureBeans
	 * @throws DBException
	 */
	public List<LabProcedureBean> getAllLabProceduresForDocOV(long ovid) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM labprocedure WHERE OfficeVisitID = ? ");
			ps.setLong(1, ovid);
			ResultSet rs = ps.executeQuery();
			List<LabProcedureBean> loadlist = labProcedureLoader.loadList(rs);
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
	 * Gets all lab procedures, period.
	 * @return A java.util.List of LabProcedureBeans.
	 * @throws DBException
	 */
	public List<LabProcedureBean> getAllLabProcedures() throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM labprocedure ORDER BY UpdatedDate ASC");
			ResultSet rs = ps.executeQuery();
			List<LabProcedureBean> loadlist = labProcedureLoader.loadList(rs);
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
	 * Get all lab procedures associated with a particular HCP.
	 * @param mid The HCP's id.
	 * @return
	 * @throws DBException
	 */
	public List<LabProcedureBean> getHCPLabProcedures(long mid) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			if (mid == 0L) throw new SQLException("HCP id cannot be null");
			conn = factory.getConnection();
			ps = conn.prepareStatement(
					"SELECT * FROM LabProcedure WHERE labprocedure.OfficeVisitID IN " +
					    "(SELECT officevisits.ID FROM officevisits WHERE officevisits.HCPID = ?)"
					);
			ps.setLong(1, mid);
			ResultSet rs = ps.executeQuery();
			List<LabProcedureBean> loadlist = labProcedureLoader.loadList(rs);
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
	 * Get all lab procedures associated with a particular HCP and Patient.
	 * @param mid The HCP's id.
	 * @return
	 * @throws DBException
	 */
	public List<LabProcedureBean> getLabProcedures(long mid, long pid) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			if (mid == 0L) throw new SQLException("HCP id cannot be null");
			if (pid == 0L) throw new SQLException("HCP id cannot be null");
			conn = factory.getConnection();
			ps = conn.prepareStatement(
					"SELECT * FROM labprocedure WHERE labprocedure.OfficeVisitID IN " +
					    "(SELECT officevisits.ID FROM officevisits WHERE " +
					    " officevisits.HCPID = ? AND officevisits.PatientID = ?)"
					);
			ps.setLong(1, mid);
			ps.setLong(2, pid);
			ResultSet rs = ps.executeQuery();
			List<LabProcedureBean> loadlist = labProcedureLoader.loadList(rs);
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
	 * Get a count of all pending lab procedures for a particular HCP.
	 * @param mid
	 * @return
	 * @throws DBException
	 */
	public int getHCPPendingCount(long mid) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			int count = 0;
			conn = factory.getConnection();
			ps = conn.prepareStatement(
					"SELECT COUNT(*) FROM labprocedure WHERE Status = ? AND labprocedure.OfficeVisitID IN " +
					    "(SELECT officevisits.ID FROM officevisits WHERE officevisits.HCPID = ?)"
					);
			ps.setString(1, LabProcedureBean.Pending);
			ps.setLong(2, mid);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
			rs.close();
			ps.close();
			return count;
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	/**
	 * Gets the lab procedures for a given LHCP that occur within the next month.
	 * @param ovid The Office Visit ID conducted by the LHCP in question.
	 * @return A java.util.List of LabProcedureBeans.
	 * @throws DBException
	 */
	public List<LabProcedureBean> getLabProceduresForLHCPForNextMonth(long ovid) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			if (ovid == 0L) throw new SQLException("OfficeVisitID cannot be null");
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM labprocedure WHERE OfficeVisitID = ? AND Status = ? AND (DateDiff(SYSDATE(),UpdatedDate) <= 30) ORDER BY UpdatedDate DESC");
			ps.setLong(1, ovid);
			ps.setString(2, LabProcedureBean.Completed);
			ResultSet rs = ps.executeQuery();
			List<LabProcedureBean> loadlist = labProcedureLoader.loadList(rs);
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
	 * Inserts a lab procedure into the database.
	 * @param b The LabProcedureBean to be inserted.
	 * @return A long containing the ID of the newly inserted lab procedure bean.
	 * @throws DBException
	 */
	public long addLabProcedure(LabProcedureBean b) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			if (b.getPid() == 0L) throw new SQLException("PatientMID cannot be null");
			conn = factory.getConnection();
			ps = conn.prepareStatement(
					"INSERT INTO labprocedure " +
					"(PatientMID, LaboratoryProcedureCode, Status, Commentary, " + 
					 "Results, OfficeVisitID, Rights, LabTechID, PriorityCode, " + 
					 "NumericalResults, LowerBound, UpperBound) " + 
					"VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
			ps.setLong(1, b.getPid());
			ps.setString(2, b.getLoinc());
			ps.setString(3, b.getStatus());
			ps.setString(4, b.getCommentary());
			ps.setString(5, b.getResults());
			ps.setLong(6, b.getOvID());
			ps.setString(7, b.getRights());
			ps.setLong(8, b.getLTID());
			ps.setInt(9, b.getPriorityCode());
			ps.setString(10, b.getNumericalResult());
			ps.setString(11, b.getLowerBound());
			ps.setString(12, b.getUpperBound());
			
			ps.executeUpdate();
			ps.close();
			return DBUtil.getLastInsert(conn);
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	/**
	 * Updates an existing lab procedure.
	 * @param b The LabProcedureBean representing the procedure to be updated.
	 * @throws DBException
	 */
	public void updateLabProcedure(LabProcedureBean b) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			if (b.getPid() == 0L) throw new SQLException("PatientMID cannot be null");
			conn = factory.getConnection();
			ps = conn.prepareStatement("UPDATE labprocedure SET "+
					" Status = ?, Commentary = ?, Results = ?, UpdatedDate = ?, "+
					" LabTechID = ?, PriorityCode = ? , NumericalResults = ?, "+
					" LowerBound = ?, UpperBound = ? "+
					" WHERE LaboratoryProcedureID=?");
			ps.setString(1, b.getStatus());
			ps.setString(2, b.getCommentary());
			ps.setString(3, b.getResults());
			ps.setTimestamp(4, new java.sql.Timestamp(System.currentTimeMillis()));
			ps.setLong(5, b.getLTID());
			ps.setInt(6, b.getPriorityCode());
			ps.setString(7, b.getNumericalResult());
			ps.setString(8, b.getLowerBound());
			ps.setString(9, b.getUpperBound());
			ps.setLong(10, b.getProcedureID());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	/**
	 * Marks a lab procedure as viewed by the patient
	 * @param b The LabProcedureBean representing the procedure to be marked as viewed.
	 * @throws DBException
	 */
	public void markViewed(LabProcedureBean b) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			if (b.getPid() == 0L) throw new SQLException("PatientMID cannot be null");
			conn = factory.getConnection();
			ps = conn.prepareStatement("UPDATE labprocedure SET ViewedByPatient = ? WHERE LaboratoryProcedureID=?");
			ps.setBoolean(1, b.isViewedByPatient());
			ps.setLong(2, b.getProcedureID());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	/**
	 * Get the count of unviewed lab procedures for a particular patient.
	 * @param pid
	 * @return
	 * @throws DBException
	 */
	public int getPatientUnviewedCount(long pid) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			if (pid == 0L) throw new SQLException("PatientMID cannot be null");
			int count = 0;
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT COUNT(*) FROM labprocedure WHERE PatientMID = ? AND Rights = ? AND Status = ? AND ViewedByPatient = FALSE ");
			ps.setLong(1, pid);
			ps.setString(2, LabProcedureBean.Allow);
			ps.setString(3, LabProcedureBean.Completed);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
			rs.close();
			ps.close();
			return count;
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	
	/**
	 * Gets all the lab procedures that correspond to a particular LOINC.
	 * @param id The LOINC in question.
	 * @return A java.util.List of LabProcedureBeans.
	 * @throws DBException
	 */
	public List<LabProcedureBean> getAllLabProceduresLOINC(long id, String loinc) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM labprocedure WHERE PatientMID = ? AND LaboratoryProcedureCode = ? AND Status = ?");
			ps.setLong(1, id);
			ps.setString(2, loinc);
			ps.setString(3, "Completed");
			ResultSet rs = ps.executeQuery();
			List<LabProcedureBean> loadlist = labProcedureLoader.loadList(rs);
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
	 * Updates the rights of a user on a given lab procedure.
	 * @param b The LabProcedureBean in question.
	 * @throws DBException
	 */
	public void updateRights(LabProcedureBean b) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			if (b.getPid() == 0L) throw new SQLException("PatientMID cannot be null");
			conn = factory.getConnection();
			ps = conn.prepareStatement("UPDATE labprocedure SET Rights = ?, UpdatedDate = ? WHERE LaboratoryProcedureID=?");
			ps.setString(1, b.getRights());
			ps.setTimestamp(2, new java.sql.Timestamp(System.currentTimeMillis()));
			ps.setLong(3, b.getProcedureID());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	

	/**
	 * Delete a given lab procedure form the database.
	 * @param procedureID
	 * @throws DBException
	 */
	public void removeLabProcedure(long procedureID) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("DELETE FROM labprocedure WHERE LaboratoryProcedureID=? ");
			ps.setLong(1, procedureID);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	/**
	 * Get a list of the lab procedures in transit associated with a given Lab Tech.
	 * @param id The MID of the LT as a long.
	 * @return A java.util.List of LabProcedureBeans
	 * @throws DBException
	 */
	public List<LabProcedureBean> getLabProceduresInTransitForLabTech(long id) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			if (id == 0L) throw new SQLException("LabTechID cannot be null");
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM labprocedure WHERE LabTechID = ? AND Status = ? ORDER BY UpdatedDate ASC");
			ps.setLong(1, id);
			ps.setString(2, LabProcedureBean.In_Transit);
			ResultSet rs = ps.executeQuery();
			List<LabProcedureBean> loadlist = labProcedureLoader.loadList(rs);
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
	 * Get a list of the lab procedures received for a given Lab Tech.
	 * @param id The MID of the LT as a long.
	 * @return A java.util.List of LabProcedureBeans
	 * @throws DBException
	 */
	public List<LabProcedureBean> getLabProceduresReceivedForLabTech(long id) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			if (id == 0L) throw new SQLException("LabTechID cannot be null");
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM labprocedure WHERE LabTechID = ? AND Status = ? ORDER BY PriorityCode ASC, UpdatedDate DESC");
			ps.setLong(1, id);
			ps.setString(2, LabProcedureBean.Received);
			ResultSet rs = ps.executeQuery();
			List<LabProcedureBean> loadlist = labProcedureLoader.loadList(rs);
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
	 * Get a list of the lab procedures testing for a given Lab Tech.
	 * @param id The MID of the LT as a long.
	 * @return A java.util.List of LabProcedureBeans
	 * @throws DBException
	 */
	public List<LabProcedureBean> getLabProceduresTestingForLabTech(long id) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			if (id == 0L) throw new SQLException("LabTechID cannot be null");
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM labprocedure WHERE LabTechID = ? AND Status = ? ORDER BY UpdatedDate DESC");
			ps.setLong(1, id);
			ps.setString(2, LabProcedureBean.Testing);
			ResultSet rs = ps.executeQuery();
			List<LabProcedureBean> loadlist = labProcedureLoader.loadList(rs);
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
	 * Get the count of the In_Transit and Received lab procedures assigned to 
	 * a specific lab tech.
	 *  
	 * @param mid
	 * @return
	 * @throws DBException
	 */
	public int getLabTechQueueSize(long mid) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			if (mid == 0L) throw new SQLException("LabTechID cannot be null");
			conn = factory.getConnection();
			int count = 0;
			ps = conn.prepareStatement("SELECT COUNT(*) FROM labprocedure WHERE LabTechID = ? AND (Status = ? OR Status = ?)");
			ps.setLong(1, mid);
			ps.setString(2, LabProcedureBean.In_Transit);
			ps.setString(3, LabProcedureBean.Received);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
			rs.close();
			ps.close();
			return count;
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	/**
	 * Get the count of the In_Transit and Received lab procedures assigned to 
	 * a specific lab tech grouped by priority.
	 *  
	 * @param mid
	 * @return
	 * @throws DBException
	 */
	public int[] getLabTechQueueSizeByPriority(long mid) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		int[] sizes = new int[4];

		try {
			if (mid == 0L) throw new SQLException("LabTechID cannot be null");
			conn = factory.getConnection();
			for(int i=1; i<=3; i++) {
				int count = 0;
				ps = conn.prepareStatement("SELECT COUNT(*) FROM labprocedure WHERE LabTechID = ? AND (Status = ? OR Status = ?) AND PriorityCode = ?");
				ps.setLong(1, mid);
				ps.setString(2, LabProcedureBean.In_Transit);
				ps.setString(3, LabProcedureBean.Received);
				ps.setInt(4, i);
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					count = rs.getInt(1);
				}
				rs.close();
				sizes[i] = count;
			}
			
			ps.close();
			return sizes;
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	/**
	 * @param id
	 * @param parseLong
	 * @param parseLong2
	 * @param parseLong3
	 * @throws DBException 
	 */
	public void submitTestResults(long id, String numericalResult, String numericalResultUnit, String upper, String lower) throws DBException {	
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("UPDATE labprocedure SET NumericalResults = ?, NumericalResultsUnit = ?, UpperBound = ?, LowerBound = ?, Status = ?, UpdatedDate = ? WHERE LaboratoryProcedureID=?");
			ps.setString(1, numericalResult);
			ps.setString(2, numericalResultUnit);
			ps.setString(3, upper);
			ps.setString(4, lower);
			ps.setString(5, "Pending");
			ps.setTimestamp(6, new java.sql.Timestamp(System.currentTimeMillis()));
			ps.setLong(7, id);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
		
	}

	/**
	 * @param parseLong
	 * @throws DBException 
	 */
	public void submitReceivedLP(long id) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("UPDATE labprocedure SET Status = ?, UpdatedDate = ? WHERE LaboratoryProcedureID=?");
			ps.setString(1, "Received");
			ps.setTimestamp(2, new java.sql.Timestamp(System.currentTimeMillis()));
			ps.setLong(3, id);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	/**
	 * @param parseLong
	 * @throws DBException 
	 */
	public void setLPToTesting(long id) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("UPDATE labprocedure SET Status = ?, UpdatedDate = ? WHERE LaboratoryProcedureID=?");
			ps.setString(1, "Testing");
			ps.setTimestamp(2, new java.sql.Timestamp(System.currentTimeMillis()));
			ps.setLong(3, id);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	public List<LabProcedureBean> getAllLabProceduresLOINC(long id) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			if (id == 0L) throw new SQLException("PatientMID cannot be null");
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM labprocedure WHERE PatientMID = ? ORDER BY LaboratoryProcedureCode ASC");
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			List<LabProcedureBean> loadlist = labProcedureLoader.loadList(rs);
			rs.close();
			ps.close();
			return loadlist;
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
}
