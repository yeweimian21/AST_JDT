package edu.ncsu.csc.itrust.beans.loaders;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc.itrust.beans.RequiredProceduresBean;

/**
 * Loads a ResultSet into a number of RequiredProceduresBean objects automatically.
 */
public class RequiredProceduresBeanLoader implements BeanLoader<RequiredProceduresBean> {

	/**
	 * Create a new bean loader object.
	 */
	public RequiredProceduresBeanLoader() {
	}

	/**
	 * Loads a list of RequiredProceduresBean objects from a specified ResultSet.
	 * @param rs ResultSet to be loaded
	 * @return resulting list of RequiredProceduresBean objects
	 * @throws SQLException if the ResultSet is not valid
	 */
	public List<RequiredProceduresBean> loadList(ResultSet rs) throws SQLException {
		ArrayList<RequiredProceduresBean> list = new ArrayList<RequiredProceduresBean>();
		while (rs.next()) {
			list.add(loadSingle(rs));
		}
		return list;
	}

	/**
	 * Loads a single RequiredProceduresBean object from the specified ResultSet.
	 * @param rs ResultSet to be loaded from
	 * @return the next RequiredProceduresBean object to be loaded from rs
	 * @throws SQLException if the ResultSet is not valid
	 */
	public RequiredProceduresBean loadSingle(ResultSet rs) throws SQLException {
		RequiredProceduresBean reqBean = new RequiredProceduresBean();
		reqBean.setCptCode(rs.getString("cptCode"));
		reqBean.setDescription(rs.getString("description"));
		reqBean.setAgeGroup(rs.getInt("ageGroup"));
		reqBean.setAttribute(rs.getString("attribute"));
		reqBean.setAgeMax(rs.getInt("ageMax"));
		return reqBean;
	}

	/**
	 * Loads the parameters for a specified RequiredProceduresBean into a specified PreparedStatement.
	 * @param ps PreparedStatement object into which parameters are to be loaded
	 * @param bean RequiredProceduresBean object whose parameters are to be loaded
	 * @return loaded PreparedStatement object
	 * @throws SQLException if the RequiredProceduresBean object is not valid
	 */
	public PreparedStatement loadParameters(PreparedStatement ps, RequiredProceduresBean bean)
			throws SQLException {
		ps.setString(1, bean.getDescription());
		return ps;
	}
}
