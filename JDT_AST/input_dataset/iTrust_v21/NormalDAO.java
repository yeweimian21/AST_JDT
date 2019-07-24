package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.beans.NormalBean;
import edu.ncsu.csc.itrust.beans.loaders.NormalBeanLoader;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;

/**
 * Used for getting data from the normal distribution table.
 * 
 * DAO stands for Database Access Object. All DAOs are intended to be reflections of the database, that is,
 * one DAO per table in the database (most of the time). For more complex sets of queries, extra DAOs are
 * added. DAOs can assume that all data has been validated and is correct.
 * 
 * DAOs should never have setters or any other parameter to the constructor than a factory. All DAOs should be
 * accessed by DAOFactory (@see {@link DAOFactory}) and every DAO should have a factory - for obtaining JDBC
 * connections and/or accessing other DAOs.
 * 
 */
public class NormalDAO {
	private DAOFactory factory;
	private NormalBeanLoader loader = new NormalBeanLoader();
	
	/**
	 * Constructor for NormalDAO. Saves the DAOFactory that is passed in to do database transactions in.
	 * 
	 * @param factory
	 */
	public NormalDAO(DAOFactory factory) {
		this.factory = factory;
	}
	
	/**
	 * Gets a normal bean from the normal distribution table. Reads in a double value that is formatted to 
	 * 1 decimal place. Matches rows in the table based on the z column. Only returns one NormalBean object. 
	 * Otherwise returns null if there is no match.
	 * 
	 * @param z double value used to match the z field in the normaltable table. Needs to be a decimal place value.
	 * @return NormalBean with the specified z value in the parameter
	 * @throws DBException
	 */
	public NormalBean getNormal(double z) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		NormalBean normalBean = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM normaltable WHERE z=?");
			ps.setDouble(1, z);
			ResultSet rs = ps.executeQuery();
			List<NormalBean> beans = loader.loadList(rs);
			rs.close();
			//Get the first and only NormalBean from the list if it exists
			if (beans.size() > 0)
				normalBean = beans.get(0);
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
		
		return normalBean;
	}
}
