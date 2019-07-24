package edu.ncsu.csc.itrust.beans.loaders;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc.itrust.beans.SleepEntryBean;

/**
 * SleepEntryLoader.java
 * Version 1
 * 4/6/2015
 * Copyright notice: none
 * A loader for entries into the Sleep Diary (SleepEntry's). 
 * 
 * Loads information to/from beans using PreparedStatements and ResultSets.
 */
public class SleepEntryLoader implements BeanLoader<SleepEntryBean> {
	
	/**
	 * Returns the list of Sleep entries for a patient
	 * @param rs the result set to load into beans
	 * @return the list of sleep entries for a patient
	 */
	public List<SleepEntryBean> loadList(ResultSet rs) throws SQLException {
		ArrayList<SleepEntryBean> list = new ArrayList<SleepEntryBean>();
		while (rs.next()) {
			list.add(loadSingle(rs));
		}
		return list;
	}

	/**
	 * Loads a single sleep entry from a result set
	 * @param rs the result of a query
	 * @return a sleep entry bean that has the values from the db
	 */
	@Override
	public SleepEntryBean loadSingle(ResultSet rs) throws SQLException {
		SleepEntryBean sleepEntry = new SleepEntryBean();
		
		sleepEntry.setEntryID(rs.getLong("EntryID"));
		sleepEntry.setStrDate(new SimpleDateFormat("MM/dd/yyyy")
				.format(new java.util.Date(rs.getDate(
				"Date").getTime())));
		sleepEntry.setSleepType(rs.getString("SleepType"));
		sleepEntry.setHoursSlept(rs.getDouble("Hours"));
		sleepEntry.setPatientID(rs.getLong("PatientID"));
		sleepEntry.setLabelID(rs.getLong("LabelID"));
		return sleepEntry;
	}

	/**
	 * Loads the values of the sleep entry into the prepared statement
	 * @param ps the sql statement to load into
	 * @param bean the sleep entry we want to store in the db
	 * @return a prepared statement for loading a sleep entry into the db
	 */
	@Override
	public PreparedStatement loadParameters(PreparedStatement ps,
			SleepEntryBean bean) throws SQLException {
		
		ps.setLong(1, bean.getEntryID());
		ps.setDate(2, new java.sql.Date(bean.getDate().getTime()));
		ps.setString(3, bean.getSleepType().getName());
		ps.setDouble(4, bean.getHoursSlept());
		ps.setLong(5, bean.getPatientID());
		
		return ps;
	}

}
