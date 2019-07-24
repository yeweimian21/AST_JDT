package edu.ncsu.csc.itrust.beans.loaders;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc.itrust.beans.MacronutrientsBean;

/**
 * MacronutrientsLoader.java
 * Version 1
 * 03/31/2015
 * Copyright notice: none
 * A loader for entries into Macronutrients (personalhealthinformation, patients). 
 * 
 * Loads information to/from beans using PreparedStatements and ResultSets.
 */
public class MacronutrientsLoader implements BeanLoader<MacronutrientsBean> {
	
	/**
	 * Returns the list of Food entries for a patient
	 * @param rs the result set to load into beans
	 * @return the list of food entries for a patient
	 */
	public List<MacronutrientsBean> loadList(ResultSet rs) throws SQLException {
		ArrayList<MacronutrientsBean> list = new ArrayList<MacronutrientsBean>();
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
	public MacronutrientsBean loadSingle(ResultSet rs) throws SQLException {
		MacronutrientsBean msj = new MacronutrientsBean();
		// Some sort of database issue getting the results of the query
		msj.setHeight(rs.getFloat("Height"));
		msj.setWeight(rs.getFloat("personalhealthinformation.Weight"));
		msj.setYears(rs.getDate("patients.DateOfBirth"));
		msj.setMsj(rs.getFloat("height"), rs.getFloat("weight"), msj.getYears());
		msj.setPatientID(rs.getLong("PatientID"));
		return msj;
	}

	@Override
	public PreparedStatement loadParameters(PreparedStatement ps,
			MacronutrientsBean bean) throws SQLException {
		return null;
	}

}
