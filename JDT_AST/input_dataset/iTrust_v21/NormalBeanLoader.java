package edu.ncsu.csc.itrust.beans.loaders;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc.itrust.beans.NormalBean;

public class NormalBeanLoader {

	public List<NormalBean> loadList(ResultSet rs) throws SQLException {
		ArrayList<NormalBean> list = new ArrayList<NormalBean>();
		while (rs.next()) {
			list.add(loadSingle(rs));
		}
		return list;
	}

	public NormalBean loadSingle(ResultSet rs) throws SQLException {
		NormalBean zScore = new NormalBean();
		zScore.setZ(rs.getDouble("z"));
		zScore.set_00(rs.getDouble("_00"));
		zScore.set_01(rs.getDouble("_01"));
		zScore.set_02(rs.getDouble("_02"));
		zScore.set_03(rs.getDouble("_03"));
		zScore.set_04(rs.getDouble("_04"));
		zScore.set_05(rs.getDouble("_05"));
		zScore.set_06(rs.getDouble("_06"));
		zScore.set_07(rs.getDouble("_07"));
		zScore.set_08(rs.getDouble("_08"));
		zScore.set_09(rs.getDouble("_09"));
		
		return zScore;
	}
}
