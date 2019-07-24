package edu.ncsu.csc.itrust.beans.loaders;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import edu.ncsu.csc.itrust.beans.WardBean;

/**
 * A loader for WardBeans.
 * 
 * Loads in information to/from beans using ResultSets and PreparedStatements. Use the superclass to enforce consistency. 
 * For details on the paradigm for a loader (and what its methods do), see {@link BeanLoader}
 */
public class WardBeanLoader implements BeanLoader<WardBean> {
	public List<WardBean> loadList(ResultSet rs) throws SQLException {
		ArrayList<WardBean> list = new ArrayList<WardBean>();
		while (rs.next()) {
			list.add(loadSingle(rs));
		}
		return list;
	}

	public WardBean loadSingle(ResultSet rs) throws SQLException {
		WardBean ward = new WardBean(rs.getLong("WardID"), rs.getString("RequiredSpecialty"), rs.getLong("InHospital"));
		return ward;
	}

	public PreparedStatement loadParameters(PreparedStatement ps, WardBean bean) throws SQLException {
		throw new IllegalStateException("unimplemented!");
	}
}
