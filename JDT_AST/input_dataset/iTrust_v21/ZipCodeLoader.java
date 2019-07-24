package edu.ncsu.csc.itrust.beans.loaders;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc.itrust.beans.ZipCodeBean;
import edu.ncsu.csc.itrust.enums.State;

/**
 * A loader for ZipCodeBeans.
 * 
 * Loads in information to/from beans using ResultSets and PreparedStatements. Use the superclass to enforce consistency. 
 * For details on the paradigm for a loader (and what its methods do), see {@link BeanLoader}
 */
public class ZipCodeLoader implements BeanLoader<ZipCodeBean> 
{
	/**
	 * Loads a list of ZipCodeBeans
	 */
	public List<ZipCodeBean> loadList(ResultSet rs) throws SQLException {
		List<ZipCodeBean> list = new ArrayList<ZipCodeBean>();
		while (rs.next())
			list.add(loadSingle(rs));
		return list;
	}

	/**
	 * Loads a single result set into a zip code bean.
	 */
	public ZipCodeBean loadSingle(ResultSet rs) throws SQLException {
		ZipCodeBean bean = new ZipCodeBean();
		bean.setCity(rs.getString("city"));
		bean.setFullState(rs.getString("full_state"));
		bean.setLatitude(rs.getString("latitude"));
		bean.setLongitude(rs.getString("longitude"));
		bean.setState(State.parse(rs.getString("state")));
		bean.setZip(rs.getString("zip"));
		return bean;
	}
	

	@Override
	/**
	 * 
	 */
	public PreparedStatement loadParameters(PreparedStatement ps, ZipCodeBean bean) throws SQLException {
		
		return null;
	}
}
