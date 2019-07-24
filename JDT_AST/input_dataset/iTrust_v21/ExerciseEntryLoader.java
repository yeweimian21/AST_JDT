package edu.ncsu.csc.itrust.beans.loaders;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc.itrust.beans.ExerciseEntryBean;

/**
 * ExerciseEntryLoader.java
 * Version 1
 * 2/21/2015
 * Copyright notice: none
 * A loader for entries into the Exercise Diary (ExerciseEntry's). 
 * 
 * Loads information to/from beans using PreparedStatements and ResultSets.
 */
public class ExerciseEntryLoader implements BeanLoader<ExerciseEntryBean> {
	
	/**
	 * Returns the list of Exercise entries for a patient
	 * @param rs the result set to load into beans
	 * @return the list of exercise entries for a patient
	 */
	public List<ExerciseEntryBean> loadList(ResultSet rs) throws SQLException {
		ArrayList<ExerciseEntryBean> list = new ArrayList<ExerciseEntryBean>();
		while (rs.next()) {
			list.add(loadSingle(rs));
		}
		return list;
	}

	/**
	 * Loads a single exercise entry from a result set
	 * @param rs the result of a query
	 * @return a exercise entry bean that has the values from the db
	 */
	@Override
	public ExerciseEntryBean loadSingle(ResultSet rs) throws SQLException {
		ExerciseEntryBean exerciseEntry = new ExerciseEntryBean();
		
		exerciseEntry.setEntryID(rs.getLong("EntryID"));
		exerciseEntry.setStrDate(new SimpleDateFormat("MM/dd/yyyy")
				.format(new java.util.Date(rs.getDate(
				"Date").getTime())));
		exerciseEntry.setExerciseType(rs.getString("ExerciseType"));
		exerciseEntry.setStrName(rs.getString("Name"));
		exerciseEntry.setHoursWorked(rs.getDouble("Hours"));
		exerciseEntry.setCaloriesBurned(rs.getInt("Calories"));
		exerciseEntry.setNumSets(rs.getInt("Sets"));
		exerciseEntry.setNumReps(rs.getInt("Reps"));
		exerciseEntry.setPatientID(rs.getLong("PatientID"));
		exerciseEntry.setLabelID(rs.getLong("LabelID"));
		return exerciseEntry;
	}

	/**
	 * Loads the values of the exercise entry into the prepared statement
	 * @param ps the sql statement to load into
	 * @param bean the exercise entry we want to store in the db
	 * @return a prepared statement for loading a exercise entry into the db
	 */
	@Override
	public PreparedStatement loadParameters(PreparedStatement ps,
			ExerciseEntryBean bean) throws SQLException {
		
		ps.setLong(1, bean.getEntryID());
		ps.setDate(2, new java.sql.Date(bean.getDate().getTime()));
		ps.setString(3, bean.getExerciseType().getName());
		ps.setString(4, bean.getStrName());
		ps.setDouble(5, bean.getHoursWorked());
		ps.setInt(6, bean.getCaloriesBurned());
		ps.setInt(7, bean.getNumSets());
		ps.setInt(8, bean.getNumReps());
		ps.setLong(9, bean.getPatientID());
		
		return ps;
	}

}
