package edu.ncsu.csc.itrust.dao.mysql;

import edu.ncsu.csc.itrust.beans.CDCStatsBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;

/**
 * Abstract class for interacting with the cdc health statistics tables
 * 
 * DAO stands for Database Access Object. All DAOs are intended to be reflections of the database, that is,
 * one DAO per table in the database (most of the time). For more complex sets of queries, extra DAOs are
 * added. DAOs can assume that all data has been validated and is correct.
 * 
 * DAOs should never have setters or any other parameter to the constructor than a factory. All DAOs should be
 * accessed by DAOFactory (@see {@link DAOFactory}) and every DAO should have a factory - for obtaining JDBC
 * connections and/or accessing other DAOs.
 * 
 */
public abstract class CDCStatsDAO {
	
	/**
	 * Stores a CDCStatsBean into a cdc stats table. Inserts and creates a new entry if the sex and age of the bean
	 * are not currently in the table. Otherwise, the matching row in the table will be updated with the data from the 
	 * passed in CDCStatsBean. 
	 * @param statsBean The CDCStatsBean to store in the database
	 */
	public abstract void storeStats(CDCStatsBean stats) throws DBException;
	
	/**
	 * Gets a CDCStatsBean with the specified sex and age from a cdc stats table.
	 * If there are no associated CDC stats with the specified parameters, then null is returned.
	 * @param sex integer for the sex of the patient. 1 for male and 2 for female.
	 * @param age float for the age of the patient.
	 */
	public abstract CDCStatsBean getCDCStats(int sex, float age) throws DBException;
}
