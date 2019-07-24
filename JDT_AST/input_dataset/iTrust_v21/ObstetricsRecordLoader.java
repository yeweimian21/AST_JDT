package edu.ncsu.csc.itrust.beans.loaders;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc.itrust.beans.ObstetricsRecordBean;
import edu.ncsu.csc.itrust.enums.DeliveryType;
import edu.ncsu.csc.itrust.enums.PregnancyStatus;

/**
 * A loader for ObstetricsRecordBeans.
 * 
 * Loads in information to/from beans using ResultSets and PreparedStatements. Use the superclass to enforce consistency. 
 * For details on the paradigm for a loader (and what its methods do), see {@link BeanLoader}
 */
public class ObstetricsRecordLoader implements BeanLoader<ObstetricsRecordBean> {
	/**
	 * loadList
	 * @param rs rs
	 * @throws SQLException
	 */
	public List<ObstetricsRecordBean> loadList(ResultSet rs) throws SQLException {
		List<ObstetricsRecordBean> list = new ArrayList<ObstetricsRecordBean>();
		while (rs.next()) {
			list.add(loadSingle(rs));
		}
		return list;
	}

	private void loadCommon(ResultSet rs, ObstetricsRecordBean p) throws SQLException {
		p.setMid(rs.getLong("mid"));
		p.setOid(rs.getLong("oid")); //this sets the return bean
		p.setPregId(rs.getLong("pregId"));
		try {
			p.setLmp(new SimpleDateFormat("MM/dd/yyyy").format(new Date(rs.getDate("lmp").getTime())));
		}catch (NullPointerException e) {
			//totally fine. It just means it wasn't set in the record we grabbed, so don't worry.
		}
		try {
			p.setEdd(new SimpleDateFormat("MM/dd/yyyy").format(new Date(rs.getDate("edd").getTime())));
		}catch (NullPointerException e) {
			//totally fine. It just means it wasn't set in the record we grabbed, so don't worry.
		}
		p.setWeeksPregnant(rs.getString("weeksPregnant"));
		//this one, however, unlike the others above, should throw an NPE if it gets through because EVERY record has this one set
		p.setDateVisit(new SimpleDateFormat("MM/dd/yyyy").format(new Date(rs.getDate("dateVisit").getTime())));
		p.setYearConception(rs.getInt("yearConception"));
		p.setHoursInLabor(rs.getDouble("hoursInLabor"));
		p.setDeliveryType(DeliveryType.valueOf(rs.getString("deliveryType").split("[ ]")[0]));
		p.setPregnancyStatus(PregnancyStatus.valueOf(rs.getString("pregnancyStatus").split("[ ]")[0]));
		p.setWeight(rs.getDouble("weight"));
		p.setBloodPressureS(rs.getInt("bloodPressureS"));
		p.setBloodPressureD(rs.getInt("bloodPressureD"));
		p.setFhr(rs.getInt("fhr"));
		p.setFhu(rs.getDouble("fhu"));
	}
	
	/**
	 * loadSingle
	 * @param rs rs
	 * @return p
	 * @throws SQLException
	 */
	public ObstetricsRecordBean loadSingle(ResultSet rs) throws SQLException {
		ObstetricsRecordBean p = new ObstetricsRecordBean();
		loadCommon(rs, p);
		return p;
	}
	
	/**
	 * loadParameters
	 * @throws SQLException
	 */
	public PreparedStatement loadParameters(PreparedStatement ps, ObstetricsRecordBean p) throws SQLException {
		int i = 1;
	    ps.setLong(i++, p.getMid());
	    ps.setLong(i++, p.getPregId());
	    if (p.getLmp() != null)
	    	ps.setDate(i++, new java.sql.Date(p.getLmp().getTime()));
	    else
	    	ps.setDate(i++, new java.sql.Date(0));
	    if (p.getEdd() != null)
	    	ps.setDate(i++, new java.sql.Date(p.getEdd().getTime()));
	    else
	    	ps.setDate(i++, new java.sql.Date(0));
		ps.setString(i++, p.getWeeksPregnant());
		ps.setDate(i++, new java.sql.Date(p.getDateVisit().getTime()));
		ps.setInt(i++, p.getYearConception());
		ps.setDouble(i++, p.getHoursInLabor());
		ps.setString(i++, p.getDeliveryType().toString());
		ps.setString(i++, p.getPregnancyStatus().toString());
		ps.setDouble(i++, p.getWeight());
		ps.setInt(i++, p.getBloodPressureS());
		ps.setInt(i++, p.getBloodPressureD());
		ps.setInt(i++, p.getFhr());
		ps.setDouble(i++, p.getFhu());
		return ps;
	}
	
}
