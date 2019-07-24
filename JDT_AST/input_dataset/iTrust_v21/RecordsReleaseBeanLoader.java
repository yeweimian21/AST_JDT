package edu.ncsu.csc.itrust.beans.loaders;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import edu.ncsu.csc.itrust.beans.RecordsReleaseBean;

public class RecordsReleaseBeanLoader implements BeanLoader<RecordsReleaseBean> {

	public List<RecordsReleaseBean> loadList(ResultSet rs) throws SQLException {
		ArrayList<RecordsReleaseBean> list = new ArrayList<RecordsReleaseBean>();
		while (rs.next()) {
			list.add(loadSingle(rs));
		}
		return list;
	}

	public RecordsReleaseBean loadSingle(ResultSet rs) throws SQLException {
		RecordsReleaseBean bean = new RecordsReleaseBean();
		bean.setReleaseID(rs.getLong("releaseID"));
		bean.setDateRequested(rs.getTimestamp("requestDate"));
		bean.setPid(rs.getLong("pid"));
		bean.setReleaseHospitalID(rs.getString("releaseHospitalID"));
		bean.setRecHospitalName(rs.getString("recHospitalName"));
		bean.setRecHospitalAddress(rs.getString("recHospitalAddress"));
		bean.setDocFirstName(rs.getString("docFirstName"));
		bean.setDocLastName(rs.getString("docLastName"));
		bean.setDocPhone(rs.getString("docPhone"));
		bean.setDocEmail(rs.getString("docEmail"));
		bean.setJustification(rs.getString("justification"));
		bean.setStatus(rs.getInt("status"));
		return bean;
	}

	public PreparedStatement loadParameters(PreparedStatement ps, RecordsReleaseBean bean) throws SQLException {
		int i = 1;
		ps.setTimestamp(i++, bean.getDateRequested());
		ps.setLong(i++, bean.getPid());
		ps.setString(i++, bean.getReleaseHospitalID());
		ps.setString(i++, bean.getRecHospitalName());
		ps.setString(i++, bean.getRecHospitalAddress());
		ps.setString(i++, bean.getDocFirstName());
		ps.setString(i++, bean.getDocLastName());
		ps.setString(i++, bean.getDocPhone());
		ps.setString(i++, bean.getDocEmail());
		ps.setString(i++, bean.getJustification());
		ps.setInt(i++, bean.getStatus());
		
		return ps;
	}

}
