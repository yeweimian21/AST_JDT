package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.List;

import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.beans.HospitalBean;
import edu.ncsu.csc.itrust.beans.PersonnelBean;
import edu.ncsu.csc.itrust.beans.PrescriptionBean;
import edu.ncsu.csc.itrust.beans.loaders.HospitalBeanLoader;
import edu.ncsu.csc.itrust.beans.loaders.PersonnelLoader;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.enums.Role;


/**
 * Used for managing information related to personnel: HCPs, UAPs, Admins
 * 
 * DAO stands for Database Access Object. 
 * All DAOs are intended to be reflections of the database, that is,
 * one DAO per table in the database (most of the time). 
 * For more complex sets of queries, extra DAOs are added. 
 * DAOs can assume that all data has been validated and is correct.
 * 
 * DAOs should never have setters or any other parameter 
 * to the constructor than a factory. All DAOs should be
 * accessed by DAOFactory (@see {@link DAOFactory}) 
 * and every DAO should have a factory - for obtaining JDBC
 * connections and/or accessing other DAOs.
 */
@SuppressWarnings({})
public class PersonnelDAO {
	private transient final DAOFactory factory;
	private transient final PersonnelLoader personnelLoader;
	private transient final HospitalBeanLoader hospBeanLoader; 

	/**
	 * The typical constructor.
	 * @param factory The {@link DAOFactory} associated with this DAO, 
	 * 		which is used for obtaining SQL connections, etc.
	 */
	public PersonnelDAO(final DAOFactory factory) {
		this.factory = factory;
		personnelLoader = new PersonnelLoader();
		hospBeanLoader = new HospitalBeanLoader();
	}

	/**
	 * Returns the name for a given MID
	 * 
	 * @param mid The MID of the personnel in question.
	 * @return A String representing the name of the personnel.
	 * @throws ITrustException
	 * @throws DBException
	 */
	public String getName(final long mid) throws ITrustException, DBException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = factory.getConnection();
			pstmt = conn.prepareStatement("SELECT firstName, lastName FROM personnel WHERE MID=?");
			pstmt.setLong(1, mid);
			ResultSet results;

			results = pstmt.executeQuery();
			if (results.next()) {
				final String result = results.getString("firstName") + " " + results.getString("lastName"); 
				results.close();
				pstmt.close();
				return result;
			} else {
				throw new ITrustException("User does not exist");
			}
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstmt);
		}
	}

	public long getNextID(final Role role) throws DBException, ITrustException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		long minID = role.getMidFirstDigit()*1000000000L;
		minID = minID == 0 ? 1 : minID;  // Do not use 0 as an ID.
	    final long maxID = minID + 999999998L;
		long nextID = minID;
		
		try {
			conn = factory.getConnection();
			
			pstmt = conn.prepareStatement("SELECT MAX(users.mid) FROM users WHERE mid BETWEEN ? AND ?");
			pstmt.setLong(1, minID);
			pstmt.setLong(2, maxID);
			final ResultSet results = pstmt.executeQuery();
			if (results.next()) {
				nextID = results.getLong(1) + 1;
				if(nextID < minID){
					nextID = minID;
				}
			}
			results.close();
			pstmt.close();
			return nextID;
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstmt);
		}
	}
	
	/**
	 * Adds an empty personnel, and returns the MID.
	 * 
	 * @return A long indicating the new MID.
	 * @param role A {@link Role} enum indicating the personnel's specific role.
	 * @throws DBException
	 * @throws ITrustException
	 */
	public long addEmptyPersonnel(final Role role) throws DBException, ITrustException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		final long nextID = getNextID(role);

		try {
			conn = factory.getConnection();
			
			pstmt = conn.prepareStatement("INSERT INTO personnel(MID, Role) VALUES(?,?)");
			pstmt.setString(1, Long.valueOf(nextID).toString());
			pstmt.setString(2, role.name());
			pstmt.executeUpdate();
			pstmt.close();
			return nextID;
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstmt);
		}
	}

	/**
	 * Retrieves a PersonnelBean with all of the 
	 * specific information for a given employee.
	 * 
	 * @param mid The MID of the personnel in question.
	 * @return A PersonnelBean representing the employee.
	 * @throws DBException
	 */
	public PersonnelBean getPersonnel(final long mid) throws DBException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		PersonnelBean bean = null;
		try {
			conn = factory.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM personnel WHERE MID = ?");
			pstmt.setLong(1, mid);
			final ResultSet results = pstmt.executeQuery();
			if (results.next()) {
				final PersonnelBean result = personnelLoader.loadSingle(results);
				results.close();
				pstmt.close();
				bean = result;
			} else{
				results.close();
				pstmt.close();
			}
				
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstmt);
		}
		return bean;
	}

	/**
	 * Updates the demographics for a personnel.
	 * 
	 * @param p The personnel bean with the updated information.
	 * @throws DBException
	 */
	public void editPersonnel(final PersonnelBean pBean) throws DBException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = factory.getConnection();
			pstmt = conn.prepareStatement("UPDATE personnel SET AMID=?,firstName=?,lastName=?,"
					+ "phone=?, address1=?,address2=?,city=?, state=?, zip=?, specialty=?, email=?"
					+ " WHERE MID=?");
			personnelLoader.loadParameters(pstmt, pBean);
			pstmt.setLong(12, pBean.getMID());
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstmt);
		}
	}

	/**
	 * Indicates whether a certain personnel is in the database.
	 * 
	 * @param pid The MID of the personnel in question.
	 * @return A boolean indicating whether this personnel exists.
	 * @throws DBException
	 */
	public boolean checkPersonnelExists(final long pid) throws DBException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = factory.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM personnel WHERE MID=?");
			pstmt.setLong(1, pid);
			final ResultSet results = pstmt.executeQuery();
			final boolean result = results.next();
			results.close();
			pstmt.close();
			
			return result;
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstmt);
		}
	}

	/**
	 * Returns all of the hospitals this LHCP is associated with.
	 * 
	 * @param mid The MID of the personnel in question.
	 * @return A java.util.List of HospitalBeans.
	 * @throws DBException
	 */
	public List<HospitalBean> getHospitals(final long mid) throws DBException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = factory.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM hcpassignedhos hah,hospitals h "
					+ "WHERE hah.HCPID=? AND hah.HosID=h.HospitalID ORDER BY HospitalName ASC");
			pstmt.setLong(1, mid);
			final ResultSet results = pstmt.executeQuery();
			final List<HospitalBean> loadlist = hospBeanLoader.loadList(results);
			results.close();
			pstmt.close();
			return loadlist;
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstmt);
		}
	}
	
	/**
	 * Returns all of the hospitals this uap's associated hcp is associated with.
	 * 
	 * @param mid The MID of the personnel in question.
	 * @return A java.util.List of HospitalBeans.
	 * @throws DBException
	 */
	public List<HospitalBean> getUAPHospitals(final long mid) throws DBException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = factory.getConnection();
			pstmt = conn.prepareStatement("SELECT DISTINCT hospitals.* FROM hcpassignedhos hah " +
						"INNER JOIN hcprelations ON hah.HCPID = hcprelations.HCP " +
						"INNER JOIN hospitals ON hah.hosID = hospitals.HospitalID " +
						"WHERE hcprelations.UAP=? ORDER BY HospitalName ASC");
			pstmt.setLong(1, mid);
			final ResultSet results = pstmt.executeQuery();
			final List<HospitalBean> loadlist = hospBeanLoader.loadList(results);
			results.close();
			pstmt.close();
			return loadlist;
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstmt);
		}
	}
	
	/**
	 * Returns all personnel of specified specialty from the specified hospital.
	 * 
	 * @param hosid, the ID of the Hospital to get personnel from 
	 * @param specialty, the type of specialty to search for
	 * @return A java.util.List of PersonnelBeans.
	 * @throws DBException
	 */
	public List<PersonnelBean> getPersonnelFromHospital(final String hosid, final String specialty) throws DBException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = factory.getConnection();
			if(specialty.equalsIgnoreCase("all")){
				pstmt = conn.prepareStatement("SELECT * FROM hcpassignedhos hah inner join personnel p where hah.hosID = ? and hah.HCPID = p.MID and p.role = 'hcp'");
				pstmt.setString(1, hosid);
			}
			else{
				pstmt = conn.prepareStatement("SELECT * FROM hcpassignedhos hah inner join personnel p where hah.hosID = ? and hah.HCPID = p.MID and p.specialty = ? and p.role = 'hcp'");
			    pstmt.setString(1, hosid);
			    pstmt.setString(2, specialty);
			}
			
			//NOTE: There is a possible NullPointerException Threat here!
			final ResultSet results = pstmt.executeQuery();
			final List<PersonnelBean> loadlist = personnelLoader.loadList(results);
			results.close();
			pstmt.close();
			return loadlist;
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstmt);
		}
	}
	
	/**
	 * Returns all personnel of specified specialty from the specified hospital.
	 * 
	 * @param hosid, the ID of the Hospital to get personnel from 
	 * @param specialty, the type of specialty to search for
	 * @return A java.util.List of PersonnelBeans.
	 * @throws DBException
	 */
	public List<PersonnelBean> getPersonnelFromHospital(final String hosid) throws DBException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = factory.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM hcpassignedhos hah inner join personnel p where hah.hosID = ? and hah.HCPID = p.MID and p.role = 'hcp'");
			pstmt.setString(1, hosid);
			
			final ResultSet results = pstmt.executeQuery();
			final List<PersonnelBean> loadlist = personnelLoader.loadList(results);
			results.close();
			pstmt.close();
			return loadlist;
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstmt);
		}
	}
	
	/**
	 * Returns all personnel in the database.
	 * 
	 * @return A java.util.List of personnel.
	 * @throws DBException
	 */
	public List<PersonnelBean> getAllPersonnel() throws DBException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = factory.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM personnel where role in ('hcp','uap','er') ");
			final ResultSet results = pstmt.executeQuery();
			final List<PersonnelBean> loadlist = personnelLoader.loadList(results);
			results.close();
			pstmt.close();
			return loadlist;
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstmt);
		}
	}

	/**
	 * Returns a list of UAPs who work for this LHCP.
	 * 
	 * @param hcpid The MID of the personnel in question.
	 * @return A java.util.List of UAPs.
	 * @throws DBException
	 */
	public List<PersonnelBean> getUAPsForHCP(final long hcpid) throws DBException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = factory.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM personnel WHERE MID IN (SELECT UAP FROM hcprelations WHERE HCP=?)");
			pstmt.setLong(1, hcpid);
			final ResultSet results = pstmt.executeQuery();
			final List<PersonnelBean> loadlist = personnelLoader.loadList(results);
			results.close();
			pstmt.close();
			return loadlist;
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstmt);
		}
	}
	
	/**
	 * Given a prescription that has been given, 
	 * this method returns all the information for the
	 * doctor who authorized that prescription.
	 * 
	 * @param prescription The PrescriptionBean 
	 * 		describing the prescription in question.
	 * @return The PersonnelBean describing 
	 * 		the doctor who authorized it.
	 * @throws DBException
	 */
	public PersonnelBean getPrescribingDoctor(final PrescriptionBean prescription) throws DBException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = factory.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM personnel WHERE MID IN (SELECT HCPID FROM officevisits WHERE ID=?)");
			pstmt.setLong(1, prescription.getVisitID());
			final ResultSet results = pstmt.executeQuery();
			final PersonnelBean loaded = personnelLoader.loadList(results).get(0);
			results.close();
			pstmt.close();
			return loaded;
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstmt);
		}
	}
	
	/**
	 * Matches all personnel who have names LIKE (as in SQL) 
	 * the first and last names passed in.
	 * 
	 * @param first The first name to be searched for.
	 * @param last The last name to be searched for.
	 * @return A java.util.List of personnel who match these names.
	 * @throws DBException
	 */
	public List<PersonnelBean> searchForPersonnelWithName(final String first, final String last) throws DBException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		List<PersonnelBean> returnList;
		if ("%".equals(first) && "%".equals(last)) {
			returnList = new Vector<PersonnelBean>();
		} else {
		
			try {
				conn = factory.getConnection();
				
				pstmt = conn.prepareStatement("SELECT * FROM personnel WHERE firstName LIKE ? AND lastName LIKE ?");
				pstmt.setString(1, first);
				pstmt.setString(2, last);
				final ResultSet results = pstmt.executeQuery();
				final List<PersonnelBean> loadlist = personnelLoader.loadList(results);
				results.close();
				pstmt.close();
				returnList = loadlist;
			} catch (SQLException e) {
				
				throw new DBException(e);
			} finally {
				DBUtil.closeConnection(conn, pstmt);
			}
		}
		return returnList;
	}
	
	/**
	 * Returns list of personnel who are Lab Techs.
	 * @return List of personnel beans.
	 * @throws DBException
	 */
	public List<PersonnelBean> getLabTechs() throws DBException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = factory.getConnection();
			
			pstmt = conn.prepareStatement("SELECT * FROM personnel WHERE role = 'lt' ");
			final ResultSet results = pstmt.executeQuery();
			final List<PersonnelBean> loadlist = personnelLoader.loadList(results);
			results.close();
			pstmt.close();
			return loadlist;
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstmt);
		}
	}
	
	
	/**
	 * Returns all experts with names "LIKE" with wildcards (as in SQL) the passed in parameters.
	 * 
	 * @param first The expert's first name.
	 * @param last The expert's last name.
	 * @return A java.util.List of ExpertBeans.
	 * @throws DBException
	 */
	public List<PersonnelBean> fuzzySearchForExpertsWithName(String first, String last) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		
		if (first.equals("%") && last.equals("%")) return new Vector<PersonnelBean>();
		
		try {
			conn = factory.getConnection();
			
			ps = conn.prepareStatement("SELECT * FROM personnel WHERE firstName LIKE ? AND lastName LIKE ? AND role='hcp'");
			ps.setString(1, "%"+first+"%");
			ps.setString(2, "%"+last+"%");
			
			ResultSet rs = ps.executeQuery();
			List<PersonnelBean> loadlist = personnelLoader.loadList(rs);
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
	 * Returns all of the personnel who have a specialty of nutritionist
	 */
	public List<PersonnelBean> getAllNutritionists() throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM personnel "
					+ "WHERE UPPER(specialty) = 'NUTRITIONIST'; ");
			ResultSet rs = ps.executeQuery();
			List<PersonnelBean> loadList = personnelLoader.loadList(rs);
			rs.close();
			ps.close();
			return loadList;
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	/**
	 * Returns all of the personnel who have a specialty of ophthalmologist or optometrist
	 * @return List of all personnel who have a specialty of ophthalmologist or optometrist.
	 * @throws DBException
	 */
	public List<PersonnelBean> getAllOphthalmologyPersonnel() throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM personnel "
					+ "WHERE specialty = 'Optometrist' or specialty = 'Ophthalmologist'; ");
			ResultSet rs = ps.executeQuery();
			List<PersonnelBean> loadList = personnelLoader.loadList(rs);
			rs.close();
			ps.close();
			return loadList;
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
}
