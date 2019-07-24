/**
 * 
 */
package edu.ncsu.csc.itrust.beans.loaders;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import edu.ncsu.csc.itrust.beans.PatientInstructionsBean;

/**
 * A loader for PatientInstructionsBeans.
 * 
 * Loads in information to/from beans using ResultSets and PreparedStatements. Use the superclass to enforce consistency.
 * For details on the paradigm for a loader (and what its methods do), see {@link BeanLoader} 
 */
public class PatientInstructionsBeanLoader implements BeanLoader<PatientInstructionsBean> {
	/**
	 * 
	 */
	public PatientInstructionsBeanLoader() { }

	/* (non-Javadoc)
	 * @see edu.ncsu.csc.itrust.beans.loaders.BeanLoader#loadList(java.sql.ResultSet)
	 */
	public List<PatientInstructionsBean> loadList(ResultSet rs) throws SQLException {
		ArrayList<PatientInstructionsBean> list = new ArrayList<PatientInstructionsBean>();
		while (rs.next()) {
			list.add(loadSingle(rs));
		}
		return list;
	}

	/* (non-Javadoc)
	 * @see edu.ncsu.csc.itrust.beans.loaders.BeanLoader#loadSingle(java.sql.ResultSet)
	 */
	public PatientInstructionsBean loadSingle(ResultSet rs) throws SQLException {
		PatientInstructionsBean bean = new PatientInstructionsBean();
		bean.setName(rs.getString("Name"));
		bean.setComment(rs.getString("Comment"));
		bean.setUrl(rs.getString("URL"));
		bean.setModified(new java.util.Date(rs.getTimestamp("Modified").getTime()));
		bean.setId(rs.getLong("id"));
		bean.setVisitID(rs.getLong("visitID"));
		
		return bean;
	}

	/* (non-Javadoc)
	 * @see edu.ncsu.csc.itrust.beans.loaders.BeanLoader#loadParameters(java.sql.PreparedStatement, java.lang.Object)
	 */
	public PreparedStatement loadParameters(PreparedStatement ps, PatientInstructionsBean bean)
			throws SQLException {
		ps.setLong(1, bean.getVisitID());
		ps.setTimestamp(2, new java.sql.Timestamp(bean.getModified().getTime()));
		ps.setString(3, bean.getName());
		ps.setString(4, bean.getUrl());
		ps.setString(5, bean.getComment());
		
		return ps;
	}

}
