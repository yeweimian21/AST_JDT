package edu.ncsu.csc.itrust.beans.loaders;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import edu.ncsu.csc.itrust.beans.CDCStatsBean;

public class CDCStatsBeanLoader implements BeanLoader<CDCStatsBean> {
	public PreparedStatement loadParameters(PreparedStatement ps, CDCStatsBean bean) throws SQLException {
		int i = 1;
		ps.setInt(i++, bean.getSex());
		ps.setFloat(i++, bean.getAge());
		ps.setDouble(i++, bean.getL());
		ps.setDouble(i++, bean.getM());
		ps.setDouble(i++, bean.getS());
		
		return ps;
	}
	
	public CDCStatsBean loadSingle(ResultSet rs) throws SQLException {
		CDCStatsBean stats = new CDCStatsBean();
		stats.setSex(rs.getInt("sex"));
		stats.setAge(rs.getFloat("age"));
		stats.setL(rs.getDouble("L"));
		stats.setM(rs.getDouble("M"));
		stats.setS(rs.getDouble("S"));
		return stats;
	}

	public List<CDCStatsBean> loadList(ResultSet rs) throws SQLException {
		ArrayList<CDCStatsBean> list = new ArrayList<CDCStatsBean>();
		while (rs.next()) {
			list.add(loadSingle(rs));
		}
		return list;
	}
}
