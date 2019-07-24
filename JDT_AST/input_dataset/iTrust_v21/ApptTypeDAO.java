package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.beans.ApptTypeBean;
import edu.ncsu.csc.itrust.beans.loaders.ApptTypeBeanLoader;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;

@SuppressWarnings({})
public class ApptTypeDAO {
	private transient final DAOFactory factory;
	private transient final ApptTypeBeanLoader atLoader;
	
	public ApptTypeDAO(final DAOFactory factory) {
		this.factory = factory;
		this.atLoader = new ApptTypeBeanLoader();
	}
	
	
	
	public List<ApptTypeBean> getApptTypes() throws SQLException, DBException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try{
			conn = factory.getConnection();
			stmt = conn.prepareStatement("SELECT * FROM appointmenttype");
			final ResultSet results = stmt.executeQuery();
		
			final List<ApptTypeBean> atList = this.atLoader.loadList(results);
			results.close();
			stmt.close();
			return atList;
		}catch(SQLException e){
			throw new DBException(e);
		}finally{
			DBUtil.closeConnection(conn, stmt);
		}

	}
	
	public boolean addApptType(final ApptTypeBean apptType) throws SQLException, DBException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try{
			conn = factory.getConnection();
			stmt = conn.prepareStatement(
				"INSERT INTO appointmenttype (appt_type, duration) "
			  + "VALUES (?, ?)");
			this.atLoader.loadParameters(stmt, apptType);
			final int rowCount = stmt.executeUpdate();
			stmt.close();
			return rowCount > 0;
		}catch(SQLException e){
			throw new DBException(e);
		}finally{
			DBUtil.closeConnection(conn, stmt);
		}
		
		
	}
	
	public boolean editApptType(final ApptTypeBean apptType) throws SQLException, DBException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try{
			conn = factory.getConnection();
			stmt = conn.prepareStatement("UPDATE appointmenttype SET duration=? WHERE appt_type=?");
			stmt.setInt(1, apptType.getDuration());
			stmt.setString(2, apptType.getName());
			final int rowCount = stmt.executeUpdate();
			stmt.close();
			return rowCount > 0;
		}catch(SQLException e){
			throw new DBException(e);
		}finally{
			DBUtil.closeConnection(conn, stmt);
		}
		
		
	}
	public ApptTypeBean getApptType(final String apptType) throws SQLException, DBException{
		Connection conn = null;
		PreparedStatement stmt = null;
		ApptTypeBean bean = null;
		try{
			conn = factory.getConnection();
			stmt = conn.prepareStatement("SELECT * FROM appointmenttype WHERE appt_type=?");
			stmt.setString(1, apptType);
		
			final ResultSet results = stmt.executeQuery();
		
			final List<ApptTypeBean> beans = atLoader.loadList(results);
			results.close();
			stmt.close();
			if(!beans.isEmpty()){
				bean = beans.get(0);
			}
		}catch(SQLException e){
			throw new DBException(e);
		}finally{
			DBUtil.closeConnection(conn, stmt);
		}
		return bean;
	}
	
	
}
