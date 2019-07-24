package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.beans.HospitalBean;
import edu.ncsu.csc.itrust.beans.loaders.HospitalBeanLoader;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;

/**
 * Used for managing hospitals
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
public class HospitalsDAO {
	private DAOFactory factory;
	private HospitalBeanLoader hospitalLoader = new HospitalBeanLoader();

	/**
	 * The typical constructor.
	 * @param factory The {@link DAOFactory} associated with this DAO, which is used for obtaining SQL connections, etc.
	 */
	public HospitalsDAO(DAOFactory factory) {
		this.factory = factory;
	}

	/**
	 * Returns a list of all hospitals sorted alphabetically
	 * 
	 * @return A java.util.List of HospitalBeans.
	 * @throws DBException
	 */
	public List<HospitalBean> getAllHospitals() throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM hospitals ORDER BY HospitalName");
			ResultSet rs = ps.executeQuery();
			List<HospitalBean> loadlist = hospitalLoader.loadList(rs);
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
	 * Returns a particular hospital given its ID
	 * 
	 * @param id The String ID of the hospital.
	 * @return A HospitalBean representing this hospital.
	 * @throws DBException
	 */
	public HospitalBean getHospital(String id) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM hospitals WHERE HospitalID = ?");
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()){
				HospitalBean loaded = hospitalLoader.loadSingle(rs);
				rs.close();
				ps.close();
				return loaded;
			}
			rs.close();
			ps.close();
			return null;
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	/**
	 * Adds a hospital
	 * 
	 * @param hosp The HospitalBean object to insert.
	 * @return A boolean indicating whether the insertion was successful.
	 * @throws DBException
	 * @throws ITrustException
	 */
	public boolean addHospital(HospitalBean hosp) throws DBException, ITrustException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("INSERT INTO hospitals (HospitalID, HospitalName, Address, City, State, Zip) " + "VALUES (?,?,?,?,?,?)");
			ps.setString(1, hosp.getHospitalID());
			ps.setString(2, hosp.getHospitalName());
			ps.setString(3, hosp.getHospitalAddress());
			ps.setString(4, hosp.getHospitalCity());
			ps.setString(5, hosp.getHospitalState());
			ps.setString(6, hosp.getHospitalZip());
			boolean check = (1==ps.executeUpdate());
			ps.close();
			return check;
		} catch (SQLException e) {
			
			if (1062 == e.getErrorCode())
				throw new ITrustException("Error: Hospital already exists.");
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	/**
	 * Updates a particular hospital's description. Returns the number of rows affected (should be 1)
	 * 
	 * @param hosp The HospitalBean to update.
	 * @return An int indicating the number of affected rows.
	 * @throws DBException
	 */
	public int updateHospital(HospitalBean hosp) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("UPDATE hospitals SET HospitalName=?,Address=?,City=?,State=?,Zip=?" + "WHERE HospitalID = ?");
			ps.setString(1, hosp.getHospitalName());
			ps.setString(2, hosp.getHospitalAddress());
			ps.setString(3, hosp.getHospitalCity());
			ps.setString(4, hosp.getHospitalState());
			ps.setString(5, hosp.getHospitalZip());
			ps.setString(6, hosp.getHospitalID());
			int check = ps.executeUpdate();
			ps.close();
			return check;
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	/**
	 * Assign an HCP to a hospital. If they have already been assigned to that hospital, then an
	 * iTrustException is thrown.
	 * 
	 * @param hcpID The HCP's MID to assign to the hospital.
	 * @param hospitalID The ID of the hospital to assign them to.
	 * @return A boolean indicating whether the assignment was a success.
	 * @throws DBException
	 * @throws ITrustException
	 */
	public boolean assignHospital(long hcpID, String hospitalID) throws DBException, ITrustException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("INSERT INTO hcpassignedhos (HCPID, HosID) VALUES (?,?)");
			ps.setLong(1, hcpID);
			ps.setString(2, hospitalID);
			boolean check = (1 == ps.executeUpdate());
			ps.close();
			return check;
		} catch (SQLException e) {
			if (1062 == e.getErrorCode())
				throw new ITrustException("HCP " + hcpID + " already assigned to hospital " + hospitalID);
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	/**
	 * Unassigns an HCP to a hospital. Returns whether or not any changes were made
	 * 
	 * @param hcpID The MID of the HCP to remove.
	 * @param hospitalID The ID of the hospital being removed from.
	 * @return A boolean indicating success.
	 * @throws DBException
	 */
	public boolean removeHospitalAssignment(long hcpID, String hospitalID) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("DELETE FROM hcpassignedhos WHERE HCPID = ? AND HosID = ?");
			ps.setLong(1, hcpID);
			ps.setString(2, hospitalID);
			boolean check = (1 == ps.executeUpdate());
			ps.close();
			return check;
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	/**
	 * Removes all hospital assignments for a particular HCP. Returns the number of rows affected.
	 * 
	 * @param hcpID The MID of the HCP.
	 * @return An int representing the number of hospital assignments removed.
	 * @throws DBException
	 */
	public int removeAllHospitalAssignmentsFrom(long hcpID) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("DELETE FROM hcpassignedhos WHERE HCPID = ?");
			ps.setLong(1, hcpID);
			int check = ps.executeUpdate();
			ps.close();
			return check;
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	/**
	 * Checks to see if the LT has an assigned hospital
	 * 
	 * @param hcpID The MID of the LT.
	 * @return true If the LT has an assigned hospital to them, false if not
	 * @throws DBException
	 */
	public boolean checkLTHasHospital(long hcpID) throws DBException{
		Connection conn = null;
		PreparedStatement ps = null;
		try{
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM hcpassignedhos WHERE HCPID = ?");
			ps.setLong(1, hcpID);
			ResultSet rs = ps.executeQuery();
			if (rs.next()){
				rs.close();
				ps.close();
				return true;
			}
			rs.close();
			ps.close();
			return false;
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	public List<HospitalBean> getAllPatientHospitals(long pid) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT DISTINCT hospitals.* FROM personalhealthinformation " +
					"INNER JOIN officevisits ON personalhealthinformation.OfficeVisitID = officevisits.ID " +
					"INNER JOIN hospitals ON officevisits.HospitalID = hospitals.HospitalID " +
					"WHERE officevisits.HospitalID != '' AND officevisits.HospitalID IS NOT NULL " +
					"AND personalhealthinformation.PatientID = ?");
			ps.setLong(1, pid);
			ResultSet rs = ps.executeQuery();
			List<HospitalBean> loadlist = hospitalLoader.loadList(rs);
			rs.close();
			ps.close();
			return loadlist;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	public List<HospitalBean> getHospitalsAssignedToPhysician(long pid) throws SQLException
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<String> hosID = new ArrayList<String>();
		List<HospitalBean> nameOfHospitals = new ArrayList<HospitalBean>();
		HospitalBeanLoader loader = new HospitalBeanLoader();
 		try
		{
			conn = factory.getConnection();
			ps = conn.prepareStatement("Select hosID from hcpassignedhos where HCPID=?");
			ps.setString(1, String.valueOf(pid));
			rs = ps.executeQuery();
			while(rs.next())
			{
				hosID.add(rs.getString("hosID"));
			}
			if(hosID.size() == 0)
			{	
				return null;
			}
			for (String string : hosID) {
				ps = conn.prepareStatement("select * from hospitals where hospitals.HospitalID=?");
				ps.setString(1, String.valueOf(string));
				rs = ps.executeQuery();
				while(rs.next())
				{
					nameOfHospitals.add(loader.loadSingle(rs));
				}
			}		
		} catch (SQLException e) {
			
			
		} finally {
			DBUtil.closeConnection(conn, ps);
			if(rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					
				}
		}
 		
 		return nameOfHospitals;	
	}
	

}
