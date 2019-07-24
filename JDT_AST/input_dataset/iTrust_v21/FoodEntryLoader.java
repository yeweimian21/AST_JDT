package edu.ncsu.csc.itrust.beans.loaders;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc.itrust.beans.FoodEntryBean;

/**
 * FoodEntryLoader.java
 * Version 1
 * 2/21/2015
 * Copyright notice: none
 * A loader for entries into the Food Diary (FoodEntry's). 
 * 
 * Loads information to/from beans using PreparedStatements and ResultSets.
 */
public class FoodEntryLoader implements BeanLoader<FoodEntryBean> {
	
	/**
	 * Returns the list of Food entries for a patient
	 * @param rs the result set to load into beans
	 * @return the list of food entries for a patient
	 */
	public List<FoodEntryBean> loadList(ResultSet rs) throws SQLException {
		ArrayList<FoodEntryBean> list = new ArrayList<FoodEntryBean>();
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
	public FoodEntryBean loadSingle(ResultSet rs) throws SQLException {
		FoodEntryBean foodEntry = new FoodEntryBean();
		foodEntry.setEntryID(rs.getLong("EntryID"));
		foodEntry.setDateEatenStr(new SimpleDateFormat("MM/dd/yyyy")
				.format(new java.util.Date(rs.getDate(
				"DateEaten").getTime())));
		foodEntry.setMealType(rs.getString("MealType"));
		foodEntry.setFood(rs.getString("FoodName"));
		foodEntry.setServings(rs.getDouble("Servings"));
		foodEntry.setCalories(rs.getDouble("Calories"));
		foodEntry.setFatGrams(rs.getDouble("Fat"));
		foodEntry.setMilligramsSodium(rs.getDouble("Sodium"));
		foodEntry.setCarbGrams(rs.getDouble("Carbs"));
		foodEntry.setSugarGrams(rs.getDouble("Sugar"));
		foodEntry.setFiberGrams(rs.getDouble("Fiber"));
		foodEntry.setProteinGrams(rs.getDouble("Protein"));
		foodEntry.setPatientID(rs.getLong("PatientID"));
		foodEntry.setLabelID(rs.getLong("LabelID"));
		return foodEntry;
	}

	/**
	 * Loads the values of the food entry into the prepared statement
	 * @param ps the sql statement to load into
	 * @param bean the food entry we want to store in the db
	 * @return a prepared statement for loading a food entry into the db
	 */
	@Override
	public PreparedStatement loadParameters(PreparedStatement ps,
			FoodEntryBean bean) throws SQLException {
		ps.setLong(1, bean.getEntryID());
		ps.setDate(2, new java.sql.Date(bean.getDateEaten().getTime()));
		ps.setString(3, bean.getMealType().getName());
		ps.setString(4, bean.getFood());
		ps.setDouble(5, bean.getServings());
		ps.setDouble(6, bean.getCalories());
		ps.setDouble(7, bean.getFatGrams());
		ps.setDouble(8, bean.getMilligramsSodium());
		ps.setDouble(9, bean.getCarbGrams());
		ps.setDouble(10, bean.getSugarGrams());
		ps.setDouble(11, bean.getFiberGrams());
		ps.setDouble(12, bean.getProteinGrams());
		ps.setLong(13, bean.getPatientID());
		return ps;
	}

}
