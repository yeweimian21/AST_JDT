package edu.ncsu.csc.itrust.beans.loaders;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc.itrust.beans.OphthalmologyScheduleOVRecordBean;

/**
 * A loader for OphthalmologyScheduleOVRecordBean.
 * 
 * Loads in information to/from beans using ResultSets and PreparedStatements. Use the superclass to enforce consistency. 
 * For details on the paradigm for a loader (and what its methods do), see {@link BeanLoader}
 */
public class OphthalmologyScheduleOVRecordLoader implements BeanLoader<OphthalmologyScheduleOVRecordBean>{

	/**
	 * Returns a list of beans with data from a ResultSet.
	 * @param rs ResultSet containing data from the database
	 * @return A list of OphthalmologyScheduleOVRecordBean created from the ResultSet parameter.
	 * @throws SQLException thrown when there is a error resulting from accessing a field of the ResultSet.
	 */
	public List<OphthalmologyScheduleOVRecordBean> loadList(ResultSet rs) throws SQLException {
		List<OphthalmologyScheduleOVRecordBean> list = new ArrayList<OphthalmologyScheduleOVRecordBean>();
		while (rs.next()) {
			list.add(loadSingle(rs));
		}
		return list;
	}
	
	/**
	 * Internal method used to load results into beans from ResultSets.
	 * @param rs ResultSet that data is coming out of.
	 * @param p bean that data is going into.
	 * @throws SQLException thrown when there is a error resulting from accessing a field of the ResultSet.
	 */
	private void loadCommon(ResultSet rs, OphthalmologyScheduleOVRecordBean p) throws SQLException {
		p.setPatientmid(rs.getLong("patientmid"));
		p.setDoctormid(rs.getLong("doctormid"));
		p.setOid(rs.getLong("oid"));
		p.setDate(rs.getTimestamp("dateTime"));
		p.setDocLastName(rs.getString("docLastName"));
		p.setDocFirstName(rs.getString("docFirstName"));
		p.setComment(rs.getString("comments"));
		p.setPending(rs.getBoolean("pending"));
		p.setAccepted(rs.getBoolean("accepted"));
	}
	
	/**
	 * Takes the first result out of a ResultSet and returns a bean with that data in it.
	 * @param rs ResultSet containing data from the database.
	 * @return p bean containing the data.
	 * @throws SQLException thrown when there is a error resulting from accessing a field of the ResultSet.
	 */
	public OphthalmologyScheduleOVRecordBean loadSingle(ResultSet rs) throws SQLException {
		OphthalmologyScheduleOVRecordBean p = new OphthalmologyScheduleOVRecordBean();
		loadCommon(rs, p);
		return p;
	}
	
	/**
	 * Loads values into the parameters of a preparedStatement from the given OphthalmologyOVRecordBean.
	 * @param ps The PreparedStatement that will have it's parameters filled in.
	 * @param p The OphthalmologyOVRecordBean used to fill in the parameters of the preparedStatement.
	 * @return The preparedStatement with the parameters filled in.
	 * @throws SQLException thrown when there is a error resulting from accessing a field of the ResultSet.
	 */
	public PreparedStatement loadParameters(PreparedStatement ps, OphthalmologyScheduleOVRecordBean p) throws SQLException {
		int i = 1;
	    ps.setLong(i++, p.getPatientmid());
	    ps.setLong(i++, p.getDoctormid());
		ps.setTimestamp(i++, p.getDate());
		ps.setString(i++, p.getDocLastName());
		ps.setString(i++, p.getDocFirstName());
		ps.setString(i++, p.getComment());
		ps.setBoolean(i++, p.isPending());
		ps.setBoolean(i++, p.isAccepted());
		return ps;
	}
}
