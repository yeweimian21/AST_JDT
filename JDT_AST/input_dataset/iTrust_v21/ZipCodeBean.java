package edu.ncsu.csc.itrust.beans;

import edu.ncsu.csc.itrust.enums.State;

/**
 * A bean for storing zip code information.
 * 
 * A bean's purpose is to store data. Period. Little or no functionality is to be added to a bean 
 * (with the exception of minor formatting such as concatenating phone numbers together). 
 * A bean must only have Getters and Setters (Eclipse Hint: Use Source > Generate Getters and Setters. 
 * to create these easily)
 */
public class ZipCodeBean 
{
	private String zip;
	private State state;
	private String latitude;
	private String longitude;
	private String city;
	private String fullState;

	/**
	 * Returns the zip code.
	 * @return zip code
	 */
	public String getZip() 
	{
		return zip;
	}
	
	/**
	 * Sets the zip code
	 * @param zip code
	 */
	public void setZip(String zip) 
	{
		this.zip = zip;
	}
	
	/**
	 * Gets the state
	 * @return State
	 */
	public State getState() 
	{
		return state;
	}
	
	/**
	 * Sets the state 
	 * @param state 
	 */
	public void setState(State state) 
	{
		this.state = state;
	}
	
	/**
	 * Gets the latitude
	 * @return returns the latitude
	 */
	public String getLatitude() 
	{
		return latitude;
	}
	
	/**
	 * Sets the latitude
	 * @param latitude 
	 */
	public void setLatitude(String latitude) 
	{
		this.latitude = latitude;
	}
	
	/**
	 * Gets the longitude
	 * @return longitude
	 */
	public String getLongitude() 
	{
		return longitude;
	}
	
	/**
	 * Sets the longitude
	 * @param longitude 
	 */
	public void setLongitude(String longitude) 
	{
		this.longitude = longitude;
	}
	
	/**
	 * Returns the city
	 * @return city
	 */
	public String getCity() 
	{
		return city;
	}
	
	/**
	 * Sets the city
	 * @param city
	 */
	public void setCity(String city)
	{
		this.city = city;
	}
	
	/**
	 * Gets the full state name.
	 * @return State name
	 */
	public String getFullState() 
	{
		return fullState;
	}
	
	/**
	 * Sets the full string value of state
	 * @param fullState State string
	 */
	public void setFullState(String fullState) 
	{
		this.fullState = fullState;
	}
	
	
}
