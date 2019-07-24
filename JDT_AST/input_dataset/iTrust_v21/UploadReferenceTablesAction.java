package edu.ncsu.csc.itrust.action;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import edu.ncsu.csc.itrust.beans.CDCStatsBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.CDCBmiStatsDAO;
import edu.ncsu.csc.itrust.dao.mysql.CDCHeadCircStatsDAO;
import edu.ncsu.csc.itrust.dao.mysql.CDCHeightStatsDAO;
import edu.ncsu.csc.itrust.dao.mysql.CDCStatsDAO;
import edu.ncsu.csc.itrust.dao.mysql.CDCWeightStatsDAO;
import edu.ncsu.csc.itrust.exception.DBException;

/**
 * UploadReferenceTablesAction is an action class that is used for uploading reference tables. It reads 
 * in csv files and parses and loads them to a specified reference table. Contains methods for storing 
 * csv files for weight, height, bmi, and head circumference statistics. Each store method also verifies 
 * whether the passed in file is correctly formatted.
 *
 */
public class UploadReferenceTablesAction {
	/**
	 * Regex pattern to match the header format of a CDC health stats csv. Checks that header contains 
	 * at least sex, age, L, M, and S fields
	 */
	private static final String headerFormat = "Sex,Agemos,L,M,S.*";
	/**
	 * Regex pattern to match each row of data in a CDC health stats csv. Checks that header contains at 
	 * least sex, age, L, M, and S fields
	 */
	private static final String statsDataFormat = "\\d,\\d+\\.?\\d?(,-?\\d+\\.\\d+){3}(,-?\\d+\\.\\d+)*";
	
	private CDCWeightStatsDAO weightStatsDAO;
	private CDCHeightStatsDAO heightStatsDAO;
	private CDCHeadCircStatsDAO headCircStatsDAO;
	private CDCBmiStatsDAO bmiStatsDAO;
	
	/**
	 * Constructor for UploadReferenceTablesAction. Reads in DAOFactory and initializes 
	 * CDCStatsDAO fields with the factory.
	 * @param factory the DAOfactory to use for database transactions
	 */
	public UploadReferenceTablesAction(DAOFactory factory) {
		weightStatsDAO = new CDCWeightStatsDAO(factory);
		heightStatsDAO = new CDCHeightStatsDAO(factory);
		headCircStatsDAO = new CDCHeadCircStatsDAO(factory);
		bmiStatsDAO = new CDCBmiStatsDAO(factory);
	}
	
	/**
	 * Parses a CSV file and stores the statistics for average patient weights
	 * @param weightCSV InputStream object with weight statistics csv file
	 * @return true if data is stored correctly in the database.
	 * 		   false if csv file is of the incorrect format and cannot be stored
	 */
	public boolean storeWeightStats(InputStream weightCSV) {
		return storeCDCStats(weightCSV, weightStatsDAO);
	}
	
	/**
	 * Parses a CSV file and stores the statistics for average patient heights/lengths
	 * @param heightCSV InputStream object with height statistics csv file
	 * @return true if data is stored correctly in the database.
	 * 		   false if csv file is of the incorrect format and cannot be stored
	 */
	public boolean storeHeightStats(InputStream heightCSV) {
		return storeCDCStats(heightCSV, heightStatsDAO);
	}
	
	/**
	 * Parses a CSV file and stores the statistics for average patient head circumferences
	 * @param  @param headCircCSV InputStream object with head circumference statistics csv file
	 * @return true if data is stored correctly in the database.
	 * 		   false if csv file is of the incorrect format and cannot be stored
	 */
	public boolean storeHeadCircStats(InputStream headCircCSV) {
		return storeCDCStats(headCircCSV, headCircStatsDAO);
	}
	
	/**
	 * Parses a CSV file and stores the statistics for average patient BMIs
	 * @param bmiCSV InputStream object with bmi statistics csv file
	 * @return true if data is stored correctly in the database.
	 * 		   false if csv file is of the incorrect format and cannot be stored
	 */
	public boolean storeBMIStats(InputStream bmiCSV) {
		return storeCDCStats(bmiCSV, bmiStatsDAO);
	}
	
	/**
	 * Parses a csv file and sends it to the CDCStatsDAO that is passed in to store the data. 
	 * First verifies whether the csv file is formatted correctly. If the file is of the correct
	 * format then the file is parsed and passed into the specified CDCStatsDAO for storing.
	 * @param healthStatsCSV InputStream with the csv file containing the health statistics to 
	 * store in the database
	 * @param dao the CDCStatsDAO to use to store the data from the csv file
	 * @return true if data is stored correctly in the database.
	 * 		   false if csv file is of the incorrect format and cannot be stored
	 */
	@SuppressWarnings("resource")
	private boolean storeCDCStats(InputStream healthStatsCSV, CDCStatsDAO dao) {
		BufferedInputStream csvFile = new BufferedInputStream(healthStatsCSV);
		try {
			csvFile.mark(csvFile.available() + 1);
			//If the csv file cannot be verified, close the InputStream and return false
			if (!verifyCDCStatsCSV(csvFile)) {
				csvFile.close();
				return false;
			}
			csvFile.reset();
		} catch (IOException e) {
			return false;
		}
		
		//Create scanner to read each line of data in the csv
		Scanner csvScanner = new Scanner(csvFile, "UTF-8");
		//Scanner to parse through each row of data
		Scanner rowScanner = null;
		//String for saving a row of data
		String row = "";
		
		//Scan and throw away the header line.
		csvScanner.nextLine();
		
		//CDCStatsBean for storing health metric statistics taken from the csv file
		CDCStatsBean statsBean = null;
		
		try {
			while (csvScanner.hasNextLine()) {
				//Create new CDCStatsBean for storing a new row of data
				statsBean = new CDCStatsBean();
				
				//Read a row of data
				row = csvScanner.nextLine();
				
				//Read the next row if row is a header
				if (row.matches(headerFormat))
					continue;
				
				//Create scanner to parse each row by using commas as the delimiter
				rowScanner = new Scanner(row).useDelimiter(",");
				//Get the sex field
				statsBean.setSex(rowScanner.nextInt());
				//Get the age field
				statsBean.setAge(rowScanner.nextFloat());
				//Get the L field
				statsBean.setL(rowScanner.nextDouble());
				//Get the M field
				statsBean.setM(rowScanner.nextDouble());
				//Get the S field
				statsBean.setS(rowScanner.nextDouble());
				
				//Insert CDCStatsBean into the database
				dao.storeStats(statsBean);
			}
		} catch (DBException e) {
			//If there is a DBException close the InputStream and 
			//return false since not all the data has been added correctly
			try {
				healthStatsCSV.close();
			} catch (IOException e1) {
				//Still return false if I/O error occurs
			}
			return false;
		}
		
		return true;	
	}
	
	/**
	 * Verifies that a health stats csv file is of the correct format. Checks that the header is formatted
	 * correctly, and then checks that each data row contains only integers, doubles, and commas
	 * @param healthStatsCSV InputStream with the csv file whose format needs to be checked
	 * @return true if the csv file is correctly formatted. false otherwise.
	 */
	@SuppressWarnings("resource")
	private boolean verifyCDCStatsCSV(InputStream healthStatsCSV) {
		//Create scanner to read each line of data in the csv
		Scanner csvScanner = new Scanner(healthStatsCSV, "UTF-8");
		//String for holding a line of data from the CSV file
		String line = "";
		
		//Read the header line
		if (csvScanner.hasNextLine()) {
			line = csvScanner.nextLine();
		} else {
			csvScanner.close();
			//If the csv file does not contain a line, the csv file is incorrect
			return false;
		}
		//If the header line is incorrectly formatted then the csv file is incorrect
		if (!line.matches(headerFormat)) {
			csvScanner.close();
			return false;
		}
		
		//Verify that each consecutive line follows correct formatting
		while (csvScanner.hasNextLine()) {
			line = csvScanner.nextLine();
			//If none of the lines match a data line format nor a header line
			//format then the csv file is incorrect
			if (!line.matches(statsDataFormat) && !line.matches(headerFormat)) {
				csvScanner.close();
				return false;
			}
		}
		
		//The csv file passes the verification process it is deemed correct
		return true;
	}
}
