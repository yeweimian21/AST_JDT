package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.beans.AdverseEventBean;
import edu.ncsu.csc.itrust.beans.loaders.AdverseEventBeanLoader;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;
import java.sql.Timestamp;

/**
 * Used for the logging mechanism.
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
public class AdverseEventDAO {
	private transient final DAOFactory factory;
	private transient final AdverseEventBeanLoader aeLoader;

	/**
	 * The typical constructor.
	 * @param factory The {@link DAOFactory} associated with this DAO, 
	 * which is used for obtaining SQL connections, etc.
	 */
	public AdverseEventDAO(final DAOFactory factory) {
		this.factory = factory;
		this.aeLoader = new AdverseEventBeanLoader();
	}

	/**
	 * Gets all the adverse event reports for a certain user MID.
	 * @param mid The MID of the user to be looked up.
	 * @return A java.util.List of AdverseEventBeans.
	 * @throws SQLException
	 * @throws DBException 
	 */
public List<AdverseEventBean> getReportsFor(final long mid) throws DBException, SQLException {
		Connection conn = null;
		PreparedStatement pstring = null;	
		try {
			conn = factory.getConnection();
			pstring = conn.prepareStatement("SELECT * FROM adverseevents WHERE PatientMID = ?");
			pstring.setLong(1, mid);
			final ResultSet results = pstring.executeQuery();
			final List<AdverseEventBean> aeList = this.aeLoader.loadList(results);
			results.close();
			pstring.close();
			return aeList;
		} catch(SQLException e){
			
			throw new DBException(e);
		} finally{
			DBUtil.closeConnection(conn, pstring);
		}
	}

	/**
	 * Adds a message to the database.
	 * @param aeBean aeBean
	 * @param hcpmid hcpmid
	 * @throws SQLException
	 * @throws DBException 
	 */
	public void addReport(final AdverseEventBean aeBean, final long hcpmid) throws DBException, SQLException {
		Connection conn = null;
		PreparedStatement pstring = null;
		try{
			conn = factory.getConnection();
			pstring = conn.prepareStatement(
					"INSERT INTO adverseevents (PatientMID, PresImmu, Code, Comment, Prescriber, Status) "
					  + "VALUES (?, ?, ?, ?, ?, ?)");
			pstring.setString(1, aeBean.getMID());
			pstring.setString(2, aeBean.getDrug());
			pstring.setString(3, aeBean.getCode());
			pstring.setString(4, aeBean.getDescription());
			pstring.setLong(5, hcpmid);
			pstring.setString(6, "Active");
			pstring.executeUpdate();
			pstring.close();
		} catch(SQLException e){
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstring);
		}
	}
	
	/**
	 * getHCPMID
	 * @param id id
	 * @return hcpMID
	 * @throws DBException
	 * @throws SQLException
	 */
	public long getHCPMID(final int id) throws DBException, SQLException{
		Connection conn = null;
		PreparedStatement pstring = null;
		try {
			conn = factory.getConnection();
			pstring = conn.prepareStatement("SELECT * FROM adverseevents WHERE id=?");
			pstring.setInt(1, id);
			final ResultSet results = pstring.executeQuery();
			long hcpMID = 0;
			if(results.next()) {
				
				 hcpMID = results.getLong("Prescriber");
			}
			results.close();
			pstring.close();
			return hcpMID;
		} catch(SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstring);
		}
	}
	
	/**
	 * getReport
	 * @param reportID reportID
	 * @return aeList
	 * @throws DBException
	 * @throws SQLException
	 */
	public AdverseEventBean getReport(final int reportID) throws DBException, SQLException{
		Connection conn = null;
		PreparedStatement pstring = null;
		try {
			conn = factory.getConnection();
			pstring = conn.prepareStatement("SELECT * FROM adverseevents WHERE id=?");
			pstring.setInt(1, reportID);
			final ResultSet results = pstring.executeQuery();
			final List<AdverseEventBean> aeList = aeLoader.loadList(results);
			results.close();
			pstring.close();
			return aeList.get(0);
		} catch(SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstring);			
		}
	}
	
	/**
	 * removeReport
	 * @param reportID reportID
	 * @return lastInsert
	 * @throws DBException
	 * @throws SQLException
	 */
	public long removeReport(final int reportID) throws DBException, SQLException{
		Connection conn = null;
		PreparedStatement pstring = null;
		try {
			conn = factory.getConnection();
			pstring = conn.prepareStatement("UPDATE adverseevents SET Status = ? WHERE id = ?");
			final String removed = "removed";
			pstring.setString(1, removed);
			pstring.setInt(2, reportID);
			pstring.executeUpdate();
			pstring.close();
			final long lastInsert = DBUtil.getLastInsert(conn);			
			return lastInsert;
		} catch(SQLException e){
			
			throw new DBException(e);
		} finally {
			
			DBUtil.closeConnection(conn, pstring);	
		}
	}
	
	/**
	 * getUnremovedAdverseEventsByCode
	 * @param code code
	 * @return loadlist
	 * @throws DBException
	 * @throws SQLException
	 */
	public List<AdverseEventBean> getUnremovedAdverseEventsByCode(final String code) throws DBException, SQLException
	{
		Connection conn = null;
		PreparedStatement pstring = null;
		try {
			conn = factory.getConnection();
			pstring = conn.prepareStatement("SELECT * FROM adverseevents WHERE code=? AND status=?");
			pstring.setString(1, code);
			pstring.setString(2, "Active");
			ResultSet results = pstring.executeQuery();
			
			final List<AdverseEventBean> loadlist = aeLoader.loadList(results);
			results.close();
			pstring.close();
			return loadlist;
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			
			DBUtil.closeConnection(conn, pstring);
		}
		
	}
	
	/**
	 * getNameForCode
	 * @param code code
	 * @return result
	 * @throws DBException
	 * @throws SQLException
	 */
	public String getNameForCode(final String code) throws DBException, SQLException
	{
		Connection conn = null;
		PreparedStatement pstring = null;
		String result;
		try {
			conn = factory.getConnection();
			pstring = conn.prepareStatement("SELECT PresImmu FROM adverseevents WHERE code=?");
			pstring.setString(1, code);
			ResultSet results = pstring.executeQuery();
			if(results.next()) {
				 result =  results.getString("PresImmu");
				 results.close();
				 pstring.close();
				 return result;
			}else{
				result = "Name not Found";
				results.close();
				pstring.close();
			}
			
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			
			DBUtil.closeConnection(conn, pstring);
		}
		return result;
	}
	
	/**
	 * getPerscriptions
	 * @param start start
	 * @param end end
	 * @return aeList
	 * @throws ParseException
	 * @throws DBException
	 * @throws SQLException
	 */
	public List<AdverseEventBean> getPerscriptions(final String start, final String end) throws ParseException, DBException, SQLException{
		Connection conn = null;
		PreparedStatement pstring = null;
		
		try{
			final SimpleDateFormat fmt = new SimpleDateFormat("MM/dd/yyy", Locale.US);
			final Date beginning = fmt.parse(start);
			final Date ending = fmt.parse(end);
	 
			conn = factory.getConnection();
			pstring = conn.prepareStatement("SELECT * FROM adverseevents,ndcodes WHERE adverseevents.TimeLogged >= ? AND adverseevents.TimeLogged <= ? AND ndcodes.Code=adverseevents.Code ORDER BY adverseevents.Code, adverseevents.TimeLogged DESC");
			 
			pstring.setTimestamp(1, new Timestamp(beginning.getTime()));
			pstring.setTimestamp(2, new Timestamp(ending.getTime() + 1000L * 60L * 60 * 24L));
			final ResultSet results = pstring.executeQuery();
			final List<AdverseEventBean> aeList = aeLoader.loadList(results);
			results.close();
			pstring.close();
			return aeList;
		} catch(SQLException e){
			throw new DBException(e);
		} finally {
			
			DBUtil.closeConnection(conn, pstring);	
		}
	}
	
	/**
	 * getImmunizations
	 * @param start start
	 * @param end end
	 * @return aeList
	 * @throws ParseException
	 * @throws DBException
	 * @throws SQLException
	 */
	public List<AdverseEventBean> getImmunizations(final String start, final String end) throws ParseException, DBException, SQLException{
		Connection conn = null;
		PreparedStatement pstring = null;
		try {
			final SimpleDateFormat fmt = new SimpleDateFormat("MM/dd/yyy", Locale.US);
			final Date beginning = fmt.parse(start);
			final Date ending = fmt.parse(end);
	 
			conn = factory.getConnection(); 
			pstring = conn.prepareStatement("SELECT * FROM adverseevents,cptcodes WHERE adverseevents.TimeLogged >= ? AND adverseevents.TimeLogged <= ? AND cptcodes.Code=adverseevents.Code ORDER BY adverseevents.Code, adverseevents.TimeLogged DESC");
			pstring.setTimestamp(1, new Timestamp(beginning.getTime()));
			pstring.setTimestamp(2, new Timestamp(ending.getTime() + 1000L * 60L * 60 * 24L));
			final ResultSet results = pstring.executeQuery();
			
			final List<AdverseEventBean> aeList = aeLoader.loadList(results);
			results.close();
			pstring.close();
			return aeList;
		} catch(SQLException e){
			
			throw new DBException(e);
		} finally {
			
			DBUtil.closeConnection(conn, pstring);			
		}
	}
}