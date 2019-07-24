package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.beans.ApptRequestBean;
import edu.ncsu.csc.itrust.beans.loaders.ApptRequestBeanLoader;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;

/**
 * 
 *
 */
@SuppressWarnings({})
public class ApptRequestDAO {

	private transient final DAOFactory factory;
	private transient final ApptRequestBeanLoader loader;

	public ApptRequestDAO(final DAOFactory factory) {
		this.factory = factory;
		loader = new ApptRequestBeanLoader();
	}

	/**
	 * 
	 * @param hcpid
	 * @return
	 * @throws DBException 
	 */
	public List<ApptRequestBean> getApptRequestsFor(final long hcpid) throws SQLException, DBException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try{
		conn = factory.getConnection();

		stmt = conn
				.prepareStatement("SELECT * FROM appointmentrequests WHERE doctor_id=? ORDER BY sched_date");

		stmt.setLong(1, hcpid);

		final ResultSet results = stmt.executeQuery();
		final List<ApptRequestBean> list = loader.loadList(results);
		results.close();
		stmt.close();
		return list;
		}catch(SQLException e){
			throw new DBException(e);
		}finally{
			DBUtil.closeConnection(conn, stmt);
		}
		
	}

	/**
	 * 
	 * @param req
	 * @throws SQLException
	 * @throws DBException 
	 */
	public void addApptRequest(final ApptRequestBean req) throws SQLException, DBException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try{
		conn = factory.getConnection();

		stmt = conn
				.prepareStatement("INSERT INTO appointmentrequests (appt_type, patient_id, doctor_id, sched_date, comment, pending, accepted) VALUES (?, ?, ?, ?, ?, ?, ?)");
		loader.loadParameters(stmt, req);

		stmt.executeUpdate();
		stmt.close();
		}catch(SQLException e){
			throw new DBException(e);
		}finally{
			DBUtil.closeConnection(conn, stmt);
		}
		
	}

	/**
	 * 
	 * @param req
	 * @throws SQLException
	 * @throws DBException 
	 */
	public void updateApptRequest(final ApptRequestBean req) throws SQLException, DBException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try{
			conn = factory.getConnection();

			stmt = conn.prepareStatement("UPDATE appointmentrequests SET pending=?, accepted=? WHERE appt_id=?");

			stmt.setBoolean(1, req.isPending());
			stmt.setBoolean(2, req.isAccepted());
			stmt.setInt(3, req.getRequestedAppt().getApptID());

			stmt.executeUpdate();
			stmt.close();
		}catch(SQLException e){
			throw new DBException(e);
		}finally{
			DBUtil.closeConnection(conn, stmt);
		}
	}
	
	public ApptRequestBean getApptRequest(final int reqID) throws SQLException, DBException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ApptRequestBean bean;
		try{
			conn = factory.getConnection();

			stmt = conn.prepareStatement("SELECT * FROM appointmentrequests WHERE appt_id=?");

			stmt.setInt(1, reqID);
			final ResultSet results = stmt.executeQuery();
			if(results.next()) {
				bean = loader.loadSingle(results);
				results.close();
				stmt.close();
			} else {
				bean = null;
				results.close();
				stmt.close();
			}
		}catch(SQLException e){
			throw new DBException(e);
		}finally{
			DBUtil.closeConnection(conn, stmt);
		}
		return bean;
	}
}
