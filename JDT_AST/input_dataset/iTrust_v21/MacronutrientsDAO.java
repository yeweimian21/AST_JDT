package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.beans.MacronutrientsBean;
import edu.ncsu.csc.itrust.beans.loaders.MacronutrientsLoader;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;

/**
 * MacronutrientsDAO.java
 * Version 1
 * 03/31/2015
 * Copyright notice: none
 * Contains database interactions for Macronutrients
 */

public class MacronutrientsDAO {

	private transient final DAOFactory factory;
	
	private transient final MacronutrientsLoader macronutrientsLoader;
	
	public MacronutrientsDAO(final DAOFactory factory) {
		this.factory = factory;
		macronutrientsLoader = new MacronutrientsLoader();
	}
	
	public MacronutrientsBean getMsj(long patientMID) throws DBException {
		Connection conn = null;
		PreparedStatement pstring = null;	
		try {
			conn = factory.getConnection();
			pstring = conn.prepareStatement("SELECT personalhealthinformation.Height, personalhealthinformation.Weight, "
					+ "personalhealthinformation.PatientID, patients.DateOfBirth "
					+ "FROM personalhealthinformation INNER JOIN patients ON personalhealthinformation.PatientID=patients.MID "
					+ "WHERE patients.MID = ?  ORDER BY personalhealthinformation.OfficeVisitID DESC");
			pstring.setLong(1, patientMID);
			final ResultSet results = pstring.executeQuery();
			final MacronutrientsBean msjData = 
										this.macronutrientsLoader.loadList(results).get(0);
			results.close();
			pstring.close();
			return msjData;
		} catch(SQLException e){
			throw new DBException(e);
		} finally{
			DBUtil.closeConnection(conn, pstring);
		}
	}
}
