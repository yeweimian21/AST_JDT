package edu.ncsu.csc.itrust.beans;

import java.util.Date;

/**
 * A bean for storing data about diagnosis counts
 * 
 * A bean's purpose is to store data. Period. Little or no functionality is to be added to a bean 
 * (with the exception of minor formatting such as concatenating phone numbers together). 
 * A bean must only have Getters and Setters (Eclipse Hint: Use Source > Generate Getters and Setters 
 * to create these easily)
 * 
 *  
 */
public class DiagnosisStatisticsBean {

	/** The local zip code analyzed */
	String zipCode;
	/** The diagnosis count for the zip code */
	long zipStats;
	/** The diagnosis count for the region */
	long regionStats;
	/** The beginning of the time period covered */
	Date startDate;
	/** The end of the time period covered */
	Date endDate;
	
	/**
	 * Constructor for an empty bean 
	 */
	public DiagnosisStatisticsBean() {
	}
	
	/**
	 * Constructor for the bean. Accepts stats and zip code
	 * @param zipCode The zip code analyzed
	 * @param zipStats The count of diagnoses for the zip code
	 * @param regionStats The count of diagnoses for the region
	 */
	public DiagnosisStatisticsBean(String zipCode, long zipStats, long regionStats) {
		this.zipCode = zipCode;
		this.zipStats = zipStats;
		this.regionStats = regionStats;
		
	}
	
	/**
	 * Constructor for the bean. Accepts stats and zip code
	 * @param zipCode The zip code analyzed
	 * @param zipStats The count of diagnoses for the zip code
	 * @param regionStats The count of diagnoses for the region
	 * @param startDate The beginning of the data's time period
	 * @param endDate The end of the data's time period
	 */
	public DiagnosisStatisticsBean(String zipCode, long zipStats, long regionStats, Date startDate, Date endDate) {
		this.zipCode = zipCode;
		this.zipStats = zipStats;
		this.regionStats = regionStats;
		this.startDate = (Date) startDate.clone();
		this.endDate = (Date) endDate.clone();
	}

	/**
	 * Getter for Zip code
	 * @return The stored Zip code
	 */
	public String getZipCode() {
		return zipCode;
	}

	/**
	 * Setter for Zip code
	 * @param zipCode The zip code to be stored
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	/**
	 * Getter for Zip code count
	 * @return The count of diagnoses for the zip code
	 */
	public long getZipStats() {
		return zipStats;
	}

	/**
	 * Setter for Zip code count
	 * @param zipStats The count of diagnoses to be stored
	 */
	public void setZipStats(long zipStats) {
		this.zipStats = zipStats;
	}

	/**
	 * Getter for Region count
	 * @return The count of diagnoses for the region
	 */
	public long getRegionStats() {
		return regionStats;
	}

	/**
	 * Setter for Region count
	 * @param regionStats The count of regional diagnoses to be stored
	 */
	public void setRegionStats(long regionStats) {
		this.regionStats = regionStats;
	}
	
	/**
	 * Getter for Start Date
	 * @return The start date for the data
	 */
	public Date getStartDate() {
		return (Date) startDate.clone();
	}
	
	/**
	 * Setter for Start Date
	 * @param startDate The data's start date
	 */
	public void setStartDate(Date startDate) {
		this.startDate = (Date) startDate.clone();
	}
	
	/**
	 * Getter for End Date
	 * @return The end date for the data
	 */
	public Date getEndDate() {
		return (Date) endDate.clone();
	}
	
	/**
	 * Setter for the End Date
	 * @param endDate
	 */
	public void setEndDate(Date endDate) {
		this.endDate = (Date) endDate.clone();
	}
}
