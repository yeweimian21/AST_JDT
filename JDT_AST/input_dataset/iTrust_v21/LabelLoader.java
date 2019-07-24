package edu.ncsu.csc.itrust.beans.loaders;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc.itrust.beans.LabelBean;

public class LabelLoader implements BeanLoader<LabelBean> {
	
	/**
	 * Returns the list of labels for a patient
	 * @param rs the result set to load into beans
	 * @return the list of labels for a patient
	 */
	public List<LabelBean> loadList(ResultSet rs) throws SQLException {
		ArrayList<LabelBean> list = new ArrayList<LabelBean>();
		while (rs.next()) {
			list.add(loadSingle(rs));
		}
		return list;
	}

	/**
	 * Loads a single food entry from a result set
	 * @param rs the result of a query
	 * @return a food entry bean that has the values from the db
	 */
	@Override
	public LabelBean loadSingle(ResultSet rs) throws SQLException {
		LabelBean label = new LabelBean();
		label.setEntryID(rs.getLong("EntryID"));
		label.setPatientID(rs.getLong("PatientID"));
		label.setLabelName(rs.getString("LabelName"));
		label.setLabelColor(rs.getString("LabelColor"));
		return label;
	}

	/**
	 * Loads the values of the food entry into the prepared statement
	 * @param ps the sql statement to load into
	 * @param bean the food entry we want to store in the db
	 * @return a prepared statement for loading a food entry into the db
	 */
	@Override
	public PreparedStatement loadParameters(PreparedStatement ps,
			LabelBean bean) throws SQLException {
		ps.setLong(1, bean.getEntryID());
		ps.setLong(2, bean.getPatientID());
		ps.setString(3, bean.getLabelName());
		ps.setString(4, bean.getLabelColor());
		return ps;
	}
}
