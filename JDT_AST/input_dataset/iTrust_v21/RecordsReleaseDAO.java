package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.beans.RecordsReleaseBean;
import edu.ncsu.csc.itrust.beans.loaders.RecordsReleaseBeanLoader;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;

public class RecordsReleaseDAO {
	DAOFactory factory;
	RecordsReleaseBeanLoader loader = new RecordsReleaseBeanLoader();
	
	public RecordsReleaseDAO(DAOFactory factory) {
		this.factory = factory;
	}
	
	public boolean addRecordsRelease(RecordsReleaseBean bean) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		int numInserted = 0;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("INSERT INTO recordsrelease(requestDate,pid,releaseHospitalID,recHospitalName,"
					+ "recHospitalAddress,docFirstName,docLastName,docPhone,docEmail,justification,status) "
					+ "VALUES(?,?,?,?,?,?,?,?,?,?,?)");
			loader.loadParameters(ps, bean);
			numInserted = ps.executeUpdate();
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
		//If the records release bean was inserted properly return true
		if (numInserted == 1)
			return true;
		//Otherwise return false
		return false;
	}
	
	public boolean updateRecordsRelease(RecordsReleaseBean bean) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		int numUpdated = 0;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("UPDATE recordsrelease SET requestDate=?, pid=?, releaseHospitalID=?, "
					+ "recHospitalName=?, recHospitalAddress=?, docFirstName=?, docLastName=?, docPhone=?, "
					+ "docEmail=?, justification=?, status=? WHERE releaseID=?");
			loader.loadParameters(ps, bean);
			ps.setLong(12, bean.getReleaseID());
			numUpdated = ps.executeUpdate();
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
		//If the records release bean was updated properly return true
		if (numUpdated == 1)
			return true;
		//Otherwise return false
		return false;
	}
	
	public RecordsReleaseBean getRecordsReleaseByID(long releaseID) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM recordsrelease WHERE releaseID=?");
			ps.setLong(1, releaseID);
			ResultSet rs;
			rs = ps.executeQuery();
			//Get the first and only records release bean
			List<RecordsReleaseBean> list = loader.loadList(rs);
			rs.close();
			if (list.size() > 0){
				 return list.get(0);
			} else{
				return null;
			}
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
		
	}
	
	public List<RecordsReleaseBean> getAllRecordsReleasesByHospital(String hospitalID) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM recordsrelease "
					+ "WHERE releaseHospitalID=? ORDER BY requestDate DESC");
			ps.setString(1, hospitalID);
			ResultSet rs;
			rs = ps.executeQuery();
			List<RecordsReleaseBean> releases = loader.loadList(rs);
			rs.close();
			return releases;
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
		
	}
	
	public List<RecordsReleaseBean> getAllRecordsReleasesByPid(long pid) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM recordsrelease "
					+ "WHERE pid=? ORDER BY requestDate DESC");
			ps.setLong(1, pid);
			ResultSet rs;
			rs = ps.executeQuery();
			List<RecordsReleaseBean> releases = loader.loadList(rs);
			rs.close();
			return releases;
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
		
	}
}
